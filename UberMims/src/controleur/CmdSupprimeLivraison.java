package controleur;

import model.ContraintesTournee;
import model.Intersection;
import model.PointEnlevement;
import model.PointLivraison;
import model.Tournee;
import model.Chemin;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;

/**
 * Commande de suppression d'une Livraison d'une tournée
 *
 */
public class CmdSupprimeLivraison implements Commande {
	
	private ContraintesTournee contraintes;
	private Tournee tournee;
	private PointEnlevement enlevement;
	private PointLivraison livraison;
	private Intersection ePrec, eSuiv, lPrec, lSuiv;
	private Map<String, Map<String, Chemin>> plusCourtsChemins;
	
	public CmdSupprimeLivraison(ContraintesTournee c, Tournee t, PointEnlevement e, Map<String, Map<String, Chemin>> ch) {
		this.contraintes = c;
		this.tournee = t;
		this.enlevement = e;
		for(PointLivraison l : c.getPointsLivraison())
		{
			if(l.getId() == e.getIdLivraison()) 
				this.livraison = l;
		}
		this.plusCourtsChemins = ch;
		this.ePrec = null;
		this.eSuiv = null;
		this.lPrec = null;
		this.lSuiv = null;
				
	}

	public CmdSupprimeLivraison(ContraintesTournee c, Tournee t, PointLivraison l, Map<String, Map<String, Chemin>> ch) {
		this.contraintes = c;
		this.tournee = t;
		this.livraison = l;
		for(PointEnlevement e : c.getPointsEnlevement())
		{
			if(e.getId() == l.getIdEnlevement()) 
				this.enlevement = e;
		}
		this.plusCourtsChemins = ch;
		this.ePrec = null;
		this.eSuiv = null;
		this.lPrec = null;
		this.lSuiv = null;
	}

	@Override
	public void doCode() {
		contraintes.removeLivraison(enlevement, livraison);
		
		List<Chemin> chemins = tournee.getPlusCourteTournee();

		chemins.removeAll(Collections.singleton(null));
		Chemin newPrec = null, newSuiv = null;
		for(ListIterator<Chemin> it = chemins.listIterator(); it.hasNext();) {
			Chemin c = it.next();
			if(c.getDerniere().equals(enlevement)) {
				this.ePrec = c.getPremiere();
				it.remove();
			}
			if(c.getPremiere().equals(enlevement)) {
				if(c.getDerniere().equals(livraison)) {
					this.eSuiv = livraison;
					this.lPrec = enlevement;
					it.remove();
					this.lSuiv = it.next().getDerniere();
					it.remove();
					newPrec = plusCourtsChemins.get(ePrec.getId()).get(lSuiv.getId());
					break;
				} else {
					this.eSuiv = c.getDerniere();
					newPrec = plusCourtsChemins.get(ePrec.getId()).get(eSuiv.getId());
					it.remove();
				}
			}
			if(c.getDerniere().equals(livraison)) {
				this.lPrec = c.getPremiere();
				it.remove();
				this.lSuiv = it.next().getDerniere();
				it.remove();
				newSuiv = plusCourtsChemins.get(lPrec.getId()).get(lSuiv.getId());
				break;
			}
		}
		chemins.removeIf(Objects::isNull);
		if(!chemins.isEmpty()) {
			if(ePrec.equals(contraintes.getDepot())) {
				chemins.add(0, newPrec);
			}
			for(ListIterator<Chemin> it = chemins.listIterator(); it.hasNext();) {
				Chemin c = it.next();
				if(c != null && c.getDerniere().equals(newPrec.getPremiere())) {
					it.add(newPrec);
					if(newSuiv == null)
						break;
				}
				if(c != null  && newSuiv != null && !lSuiv.equals(contraintes.getDepot()) && c.getDerniere().equals(newSuiv.getPremiere())) {
					it.add(newSuiv);
					break;
				}
			}
			
			if(lSuiv.equals(contraintes.getDepot())) {
				chemins.add(newSuiv);
			}
		}
		
		chemins.removeAll(Collections.singleton(null));
		tournee.setPlusCourteTournee(chemins);
		for(Chemin c : tournee.getPlusCourteTournee()) {
			System.out.println(c);
		}
		
	}

	@Override
	public void undoCode() {
		CmdAjoutLivraison cmdA = new CmdAjoutLivraison(tournee, contraintes, enlevement, livraison, plusCourtsChemins);
		cmdA.doCode();
		tournee.getPlusCourteTournee().removeIf(Objects::isNull);
		if(tournee.getPlusCourteTournee().size() > 3) {
			Intersection ePrecCmd = null, eSuivCmd = null, lPrecCmd = null, lSuivCmd = null;
			if(ePrec.equals(contraintes.getDepot())) {
				ePrecCmd = null;
			}else{
				ePrecCmd = new Intersection(ePrec);
			}
			
			if(lSuiv.equals(contraintes.getDepot())) {
				lSuivCmd = null;
			} else {
				lSuivCmd = new Intersection(lSuiv);
			}
			
			if(eSuiv.equals(livraison)) {
				for(Chemin c : tournee.getPlusCourteTournee()) {
					if(c.getPremiere().equals(ePrec)) {
						eSuivCmd = c.getDerniere();
					}
				}
				if(eSuivCmd.equals(contraintes.getDepot())) {
					eSuivCmd = null;
				}
				lSuivCmd = eSuivCmd;
				
			} else {
				eSuivCmd = new Intersection(eSuiv);
			}
	
			lPrecCmd = new Intersection(lPrec);
			
			
			
			CmdModifOrdre cmdMO1 = new CmdModifOrdre(tournee, enlevement, ePrecCmd, eSuivCmd, plusCourtsChemins);
			cmdMO1.doCode();
	
			CmdModifOrdre cmdMO2 = new CmdModifOrdre(tournee, livraison, lPrecCmd, lSuivCmd, plusCourtsChemins);
			cmdMO2.doCode();
		}

	}

}
