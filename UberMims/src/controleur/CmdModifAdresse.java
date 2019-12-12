package controleur;

import java.util.HashMap;
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
			if(l.getId().equals(e.getIdLivraison()))
				this.livraison = l;
		}
		newEnlevement = new PointEnlevement(newInter, livraison.getId(), enlevement.getTempsEnlevement());
		newLivraison = new PointLivraison(livraison, newInter.getId(), livraison.getTempsLivraison());
		this.plusCourtsChemins = plusCourtsChemins;
		isEnlevement = true;
	}
	
	public CmdModifAdresse(ContraintesTournee c, Tournee t, PointLivraison l, Intersection newInter, Map<String, Map<String, Chemin>> plusCourtsChemins) {
		this.contraintes = c;
		this.tournee = t;
		this.livraison = l;
		for(PointEnlevement e : c.getPointsEnlevement())
		{
			if(e.getId().equals(l.getIdEnlevement()))
				this.enlevement = e;
		}
		newLivraison = new PointLivraison(newInter, enlevement.getId(), livraison.getTempsLivraison());
		newEnlevement = new PointEnlevement(enlevement, newInter.getId(), enlevement.getTempsEnlevement());
		this.plusCourtsChemins = plusCourtsChemins;
		isEnlevement = false;
	}

	@Override
	public void doCode() {
		contraintes.removeLivraison(enlevement, livraison);
		contraintes.addLivraison(newEnlevement, newLivraison);
		
		if(isEnlevement) {
			int i = 0;
			for(Chemin c : tournee.getPlusCourteTournee()) {
				if(c.getDerniere().equals(enlevement)) {
					Chemin newC = plusCourtsChemins.get(c.getPremiere().getId()).get(newEnlevement.getId());
					tournee.getPlusCourteTournee().set(i, newC);
				} else if(c.getPremiere().equals(enlevement)) {
					Chemin newC = plusCourtsChemins.get(newEnlevement.getId()).get(c.getDerniere().getId());
					tournee.getPlusCourteTournee().set(i, newC);
				}
				++i;
			}
		} else {
			int i = 0;
			for(Chemin c : tournee.getPlusCourteTournee()) {
				if(c.getDerniere().equals(livraison)) {
					Chemin newC = plusCourtsChemins.get(c.getPremiere().getId()).get(newLivraison.getId());
					tournee.getPlusCourteTournee().set(i, newC);
				} else if(c.getPremiere().equals(livraison)) {
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
				if(c.getDerniere().equals(newEnlevement)) {
					Chemin newC = plusCourtsChemins.get(c.getPremiere().getId()).get(enlevement.getId());
					tournee.getPlusCourteTournee().set(i, newC);
				} else if(c.getPremiere().equals(newEnlevement)) {
					Chemin newC = plusCourtsChemins.get(enlevement.getId()).get(c.getDerniere().getId());
					tournee.getPlusCourteTournee().set(i, newC);
				}
				++i;
			}
		} else {
			int i = 0;
			for(Chemin c : tournee.getPlusCourteTournee()) {
				if(c.getDerniere().equals(newLivraison)) {
					Chemin newC = plusCourtsChemins.get(c.getPremiere().getId()).get(livraison.getId());
					tournee.getPlusCourteTournee().set(i, newC);
				} else if(c.getPremiere().equals(newLivraison)) {
					Chemin newC = plusCourtsChemins.get(livraison.getId()).get(c.getDerniere().getId());
					tournee.getPlusCourteTournee().set(i, newC);
				}
				++i;
			}
		}
	}
	
	public static void main(String[] args) {
		Controleur c = new Controleur();
		try {
			c.chargerPlan("fichiersXML2019/petitPlan.xml", 600, 800);
			c.chargerTournee("fichiersXML2019/demandePetit2.xml");
		} catch(Exception e) {
			System.out.println();
		}
		c.calculerTournee();
		System.out.println("\n\n\n\n\n\n");
		for(Chemin ch : c.getTournee().getPlusCourteTournee())
			System.out.print(ch.getPremiere().getId() + " -> " + ch.getDerniere().getId() +", ");
		
		System.out.println();
		ContraintesTournee contraintes = c.getContraintes();
		PointEnlevement e = c.getContraintes().getPointsEnlevement().get(0);
		Intersection inter = Controleur.plan.getIntersections().get("342868447");

		Map<String, Intersection> intersectionsAVisiter = new HashMap<>();
		
		intersectionsAVisiter.put(contraintes.getDepot().getId(), contraintes.getDepot());
		for(Intersection i: contraintes.getPointsEnlevement()) {
			intersectionsAVisiter.put(i.getId(), i);
		}
		for(Intersection i: contraintes.getPointsLivraison()) {
			intersectionsAVisiter.put(i.getId(), i);
		}
		intersectionsAVisiter.put(inter.getId(), inter);
		Dijkstra d = new Dijkstra();
		Map<String, Map<String, Chemin>> plusCourtsChemins = d.plusCourtsCheminsPlan(Controleur.plan.getIntersections(), intersectionsAVisiter);
		
		
		CmdModifAdresse cmd = new CmdModifAdresse(c.getContraintes(), c.getTournee(), e, inter, plusCourtsChemins);
		cmd.doCode();

		for(Chemin ch : c.getTournee().getPlusCourteTournee()) {
			System.out.print(ch.getPremiere().getId() + " -> " + ch.getDerniere().getId() +", ");
		}
		System.out.println();
		cmd.undoCode();
		
		for(Chemin ch : c.getTournee().getPlusCourteTournee()) {
			System.out.print(ch.getPremiere().getId() + " -> " + ch.getDerniere().getId() +", ");
		}
	}	
	
	
	
}
