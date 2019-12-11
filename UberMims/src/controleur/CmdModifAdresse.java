package controleur;

import java.util.Iterator;
import java.util.Map;

import algo.Dijkstra;
import model.ContraintesTournee;
import model.PointEnlevement;
import model.PointLivraison;
import model.Tournee;
import model.Intersection;
import model.Chemin;

public class CmdModifAdresse implements Commande {
	private ContraintesTournee contraintes;
	private Tournee tournee;
	private PointEnlevement enlevement;
	private PointLivraison livraison;
	private PointEnlevement newEnlevement;
	private PointLivraison newLivraison;
	private boolean isEnlevement;
	private Map<String, Map<String, Chemin>> plusCourtsChemins;
	
	public CmdModifAdresse(ContraintesTournee c, Tournee t, PointEnlevement e, Intersection newInter, Map<String, Map<String, Chemin>> plusCourtsChemins) {
		this.contraintes = c;
		this.tournee = t;
		this.enlevement = e;
		for(PointLivraison l : c.getPointsLivraison())
		{
			if(l.getId() == e.getIdLivraison()) 
				this.livraison = l;
		}
		newEnlevement = new PointEnlevement(newInter, livraison.getId(), enlevement.getTempsEnlevement());
		newLivraison = livraison;
		this.plusCourtsChemins = plusCourtsChemins;
		isEnlevement = true;
	}
	
	public CmdModifAdresse(ContraintesTournee c, Tournee t, PointLivraison l, Intersection newInter, Map<String, Map<String, Chemin>> plusCourtsChemins) {
		this.contraintes = c;
		this.tournee = t;
		this.livraison = l;
		for(PointEnlevement e : c.getPointsEnlevement())
		{
			if(e.getId() == l.getIdEnlevement()) 
				this.enlevement = e;
		}
		newEnlevement = enlevement;
		newLivraison = new PointLivraison(newInter, enlevement.getId(), livraison.getTempsLivraison());
		this.plusCourtsChemins = plusCourtsChemins;
		isEnlevement = false;
	}

	@Override
	public void doCode() {
		contraintes.removeLivraison(enlevement, livraison);
		System.out.println("enlev="+newEnlevement.getId());
		contraintes.addLivraison(newEnlevement, newLivraison);
		
		if(isEnlevement) {
			int i = 0;
			for(Chemin c : tournee.getPlusCourteTournee()) {
				if(c.getDerniere() == enlevement) {
					Chemin newC = plusCourtsChemins.get(c.getPremiere().getId()).get(newEnlevement.getId());
					tournee.getPlusCourteTournee().set(i, newC);
				} else if(c.getPremiere() == enlevement) {
					Chemin newC = plusCourtsChemins.get(newEnlevement.getId()).get(c.getDerniere().getId());
					tournee.getPlusCourteTournee().set(i, newC);
				}
				++i;
			}
		} else {
			int i = 0;
			for(Chemin c : tournee.getPlusCourteTournee()) {
				if(c.getDerniere() == livraison) {
					Chemin newC = plusCourtsChemins.get(c.getPremiere().getId()).get(newLivraison.getId());
					tournee.getPlusCourteTournee().set(i, newC);
				} else if(c.getPremiere() == livraison) {
					Chemin newC = plusCourtsChemins.get(newLivraison.getId()).get(c.getDerniere().getId());
					tournee.getPlusCourteTournee().set(i, newC);
				}
				++i;
			}
		}
	}

	@Override
	public void undoCode() {
		contraintes.removeLivraison(newEnlevement, newLivraison);
		contraintes.addLivraison(enlevement, livraison);
		
		if(isEnlevement) {
			int i = 0;
			for(Chemin c : tournee.getPlusCourteTournee()) {
				if(c.getDerniere() == newEnlevement) {
					Chemin newC = plusCourtsChemins.get(c.getPremiere().getId()).get(enlevement.getId());
					tournee.getPlusCourteTournee().set(i, newC);
				} else if(c.getPremiere() == newEnlevement) {
					Chemin newC = plusCourtsChemins.get(enlevement.getId()).get(c.getDerniere().getId());
					tournee.getPlusCourteTournee().set(i, newC);
				}
				++i;
			}
		} else {
			int i = 0;
			for(Chemin c : tournee.getPlusCourteTournee()) {
				if(c.getDerniere() == newLivraison) {
					Chemin newC = plusCourtsChemins.get(c.getPremiere().getId()).get(livraison.getId());
					tournee.getPlusCourteTournee().set(i, newC);
				} else if(c.getPremiere() == newLivraison) {
					Chemin newC = plusCourtsChemins.get(livraison.getId()).get(c.getDerniere().getId());
					tournee.getPlusCourteTournee().set(i, newC);
				}
				++i;
			}
		}

	}

}
