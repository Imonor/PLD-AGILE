package controleur;

import model.Tournee;
import model.PointEnlevement;
import model.PointLivraison;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.Chemin;
import model.Intersection;

public class CmdModifOrdre implements Commande {

	private Tournee tournee;
	private Intersection pointModif;
	private Intersection newSuiv;
	private Intersection newPrec;
	private Map<String, Map<String, Chemin>> plusCourtsChemins;

	public CmdModifOrdre(Tournee tournee, Intersection pointModif, Intersection prec, Intersection suiv,
			Map<String, Map<String, Chemin>> plusCourtsChemins) {
		super();
		this.tournee = tournee;
		this.pointModif = pointModif;
		this.newSuiv = suiv;
		this.newPrec = prec;
		this.plusCourtsChemins = plusCourtsChemins;
	}

	@Override
	public void doCode() {
		Chemin newCheminPrec, newCheminModif, newCheminSuiv;
		Intersection actuelPrec = null, actuelSuiv = null;
		Intersection depot = tournee.getPlusCourteTournee().get(0).getPremiere();
		int indexPrec = 0, indexModif = indexPrec= 0, indexSuiv = 0;
		for (int i = 0; i < tournee.getPlusCourteTournee().size(); ++i) {
			Chemin c = tournee.getPlusCourteTournee().get(i);
			if (c.getDerniere() == pointModif) {
				actuelPrec = c.getPremiere();
			}
			if (c.getPremiere() == pointModif) {
				actuelSuiv = c.getDerniere();
			}
		}
		
		newCheminModif = plusCourtsChemins.get(actuelPrec.getId()).get(actuelSuiv.getId());

		if(newPrec == null) newPrec = depot;
		newCheminPrec = plusCourtsChemins.get(newPrec.getId()).get(pointModif.getId());
		
		if(newSuiv == null) newSuiv = depot;
		newCheminSuiv = plusCourtsChemins.get(pointModif.getId()).get(newSuiv.getId());
		
		List<Chemin> newChemins = new LinkedList<>();
		

		for (int i = 0; i < tournee.getPlusCourteTournee().size(); ++i) {
			Chemin c = tournee.getPlusCourteTournee().get(i);
			
			if (c.getPremiere() == newPrec && c.getDerniere() == newSuiv) {
				newChemins.add(newCheminPrec);
				newChemins.add(newCheminSuiv);
			}
			else if (c.getPremiere() == actuelPrec && c.getDerniere() == pointModif) {
				newChemins.add(newCheminModif);
			}
			else if (c.getPremiere() == pointModif && c.getDerniere() == actuelSuiv) {
				continue;
			} else {
				newChemins.add(c);
			}
		}
		
		tournee.setPlusCourteTournee(newChemins);
	}
	
	public static void main(String[] args) {
		Controleur c = new Controleur("fichiersXML2019/petitPlan.xml", "fichiersXML2019/demandePetit2.xml", 600,
				800);
		c.calculerTournee();
		System.out.println("\n\n\n\n\n\n");
		for(Chemin ch : c.getTournee().getPlusCourteTournee())
			System.out.print(ch.getPremiere().getId() + " -> " + ch.getDerniere().getId() +", ");
		
		System.out.println();
		Intersection modif = Controleur.plan.getIntersections().get("208769457");
		Intersection suiv = Controleur.plan.getIntersections().get("208769120");
		
		CmdModifOrdre cmd = new CmdModifOrdre(c.getTournee(), modif, null, suiv, c.getPlusCourtsChemins());
		cmd.doCode();

		for(Chemin ch : c.getTournee().getPlusCourteTournee())
			System.out.print(ch.getPremiere().getId() + " -> " + ch.getDerniere().getId() +", ");
		
	}	

	@Override
	public void undoCode() {
	}



}
