package controleur;

import model.Tournee;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.Chemin;
import model.Intersection;

public class CmdModifOrdre implements Commande {

	private Tournee tournee;
	private Intersection pointModif;
	private Intersection newPrec;
	private Intersection newSuiv;
	private Intersection actuelPrec;
	private Intersection actuelSuiv;
	private Map<String, Map<String, Chemin>> plusCourtsChemins;

	public CmdModifOrdre(Tournee tournee, Intersection pointModif, Intersection prec, Intersection suiv,
			Map<String, Map<String, Chemin>> plusCourtsChemins) {
		super();
		this.tournee = tournee;
		this.pointModif = pointModif;
		this.newSuiv = suiv;
		this.newPrec = prec;
		this.plusCourtsChemins = plusCourtsChemins;
		for (Chemin c : tournee.getPlusCourteTournee()) {
			if (c.getDerniere() == pointModif) {
				this.actuelPrec = c.getPremiere();
			}
			if (c.getPremiere() == pointModif) {
				this.actuelSuiv = c.getDerniere();
			}
		}
	}

	@Override
	public void doCode() {
		modifOrdre(pointModif, newPrec, newSuiv, actuelPrec, actuelSuiv);
	}	
	
	@Override
	public void undoCode() {
		modifOrdre(pointModif, actuelPrec, actuelSuiv, newPrec, newSuiv);
	}
	
	public static void main(String[] args) {
		Controleur c = new Controleur("fichiersXML2019/moyenPlan.xml", "fichiersXML2019/demandeMoyen3.xml", 600,
				800);
		c.calculerTournee();
		System.out.println("\n\n\n\n\n\n");
		for(Chemin ch : c.getTournee().getPlusCourteTournee())
			System.out.print(ch.getPremiere().getId() + " -> " + ch.getDerniere().getId() +", ");
		
		System.out.println();
		Intersection prec = Controleur.plan.getIntersections().get("26470086");
		Intersection modif = Controleur.plan.getIntersections().get("505061101");
		Intersection suiv = Controleur.plan.getIntersections().get("27362899");
		
		CmdModifOrdre cmd = new CmdModifOrdre(c.getTournee(), modif, prec, suiv, c.getPlusCourtsChemins());
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


	
	private void modifOrdre(Intersection pointModif, Intersection newPrec, Intersection newSuiv, Intersection actuelPrec, Intersection actuelSuiv) {
		Chemin newCheminPrec, newCheminModif, newCheminSuiv;
		Intersection depot = tournee.getPlusCourteTournee().get(0).getPremiere();
		boolean newPrecApres = false;		
		for (Chemin c : tournee.getPlusCourteTournee()) {
			if(c.getDerniere() == pointModif) {
				newPrecApres = true;
				break;
			}
			if(c.getDerniere() == newPrec) {
				break;
			}
		}
		
		newCheminModif = plusCourtsChemins.get(actuelPrec.getId()).get(actuelSuiv.getId());
		List<Chemin> newChemins = new LinkedList<>();
		
		if(newSuiv == null) {
			newCheminSuiv = plusCourtsChemins.get(pointModif.getId()).get(depot.getId());
		} else {
			newCheminSuiv = plusCourtsChemins.get(pointModif.getId()).get(newSuiv.getId());
		}

		if(newPrec == null) {
			newCheminPrec = plusCourtsChemins.get(depot.getId()).get(pointModif.getId());
		} else {
			newCheminPrec = plusCourtsChemins.get(newPrec.getId()).get(pointModif.getId());
		}
		
		if(newPrec == null) {
			newChemins.add(newCheminPrec);
			newChemins.add(newCheminSuiv);
			for(int i = 1; i< tournee.getPlusCourteTournee().size(); i++) {
				Chemin c = tournee.getPlusCourteTournee().get(i);
				if(c.getDerniere() == pointModif) {
					newChemins.add(newCheminModif);
				} else if(c.getPremiere() == pointModif) {
					continue;
				} else {
					newChemins.add(c);
				}
			}
		} else if(newSuiv == null) {
			for(int i = 0; i< tournee.getPlusCourteTournee().size() - 1; i++) {
				Chemin c = tournee.getPlusCourteTournee().get(i);
				if(c.getDerniere() == pointModif) {
					continue;
				} else if(c.getPremiere() == pointModif) {
					newChemins.add(newCheminModif);
				} else {
					newChemins.add(c);
				}
			}
			newChemins.add(newCheminPrec);
			newChemins.add(newCheminSuiv);
		} else if(newPrecApres){
			for(int i = 0; i< tournee.getPlusCourteTournee().size(); i++) {
				Chemin c = tournee.getPlusCourteTournee().get(i);
				if(c.getDerniere() == pointModif) {
					newChemins.add(newCheminModif);
				} else if(c.getPremiere() == pointModif) {
					if(c.getDerniere() == newPrec) {
						newChemins.add(newCheminPrec);
						newChemins.add(newCheminSuiv);
						i++;
					} else continue;
				} else if(c.getPremiere() == newPrec && c.getDerniere() == newSuiv) {
					newChemins.add(newCheminPrec);
					newChemins.add(newCheminSuiv);
				} else {
					newChemins.add(c);
				}
			}
		} else {
			for(int i = 0; i< tournee.getPlusCourteTournee().size(); i++) {
				Chemin c = tournee.getPlusCourteTournee().get(i);
				if(c.getPremiere() == newPrec && c.getDerniere() == newSuiv) {
					newChemins.add(newCheminPrec);
					newChemins.add(newCheminSuiv);
				} else if(c.getDerniere() == pointModif) {
					newChemins.add(newCheminModif);
				} else if(c.getPremiere() == pointModif) {
					continue;
				} else {
					newChemins.add(c);
				}
			}
			
		}		
		tournee.setPlusCourteTournee(newChemins);
	}
}
