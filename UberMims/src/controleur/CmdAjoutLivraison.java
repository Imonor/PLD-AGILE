package controleur;

import java.util.List;
import java.util.Map;

import model.Chemin;
import model.ContraintesTournee;
import model.PointEnlevement;
import model.PointLivraison;
import model.Tournee;

public class CmdAjoutLivraison implements Commande {
	
	private Tournee tournee;
	private ContraintesTournee contraintes;
	private PointEnlevement enlevement;
	private PointLivraison livraison;
	private Map<String, Map<String, Chemin>> plusCourtsChemins;
	
	public CmdAjoutLivraison(Tournee t, ContraintesTournee c, PointEnlevement e, PointLivraison l, Map<String, Map<String, Chemin>> ch) {
		this.tournee = t;
		this.contraintes = c;
		this.enlevement = e;
		this.livraison = l;
		this.plusCourtsChemins = ch;
	}

	@Override
	public void doCode() {
		contraintes.addLivraison(enlevement, livraison);
		Chemin newCheminPrec, newCheminModif, newCheminSuiv;
		List<Chemin> chemins = tournee.getPlusCourteTournee();
		Chemin cheminFin = chemins.get(chemins.size()-1);
		chemins.remove(chemins.size()-1);
		
		newCheminPrec = plusCourtsChemins.get(cheminFin.getPremiere().getId()).get(enlevement.getId());		
		newCheminModif = plusCourtsChemins.get(enlevement.getId()).get(livraison.getId());		
		newCheminSuiv = plusCourtsChemins.get(livraison.getId()).get(cheminFin.getDerniere().getId());
		
		chemins.add(newCheminPrec);
		chemins.add(newCheminModif);
		chemins.add(newCheminSuiv);
		
		tournee.setPlusCourteTournee(chemins);
	}

	@Override
	public void undoCode() {
		contraintes.removeLivraison(enlevement, livraison);
		List<Chemin> chemins = tournee.getPlusCourteTournee();
		Chemin cheminFin = chemins.get(chemins.size()-1);

		chemins.remove(chemins.size()-1);
		chemins.remove(chemins.size()-1);
		chemins.remove(chemins.size()-1);
		
		Chemin newCheminFin = plusCourtsChemins.get(chemins.get(chemins.size()-1).getDerniere().getId()).get(cheminFin.getDerniere().getId());
		
		chemins.add(newCheminFin);
		
		tournee.setPlusCourteTournee(chemins);
		
	}
}
