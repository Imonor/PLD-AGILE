package controleur;

import util.ExceptionChargement;
import util.XMLParser;


import java.util.HashMap;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import algo.Dijkstra;
import algo.TSP1;
import algo.TSP2;
import model.*;

public class Controleur {
	private final int LARGEUR_PLAN = 800;
	private final int HAUTEUR_PLAN = 600;

	private Map<String, Map<String, Chemin>> plusCourtsChemins;
	private Tournee tournee;
	private ContraintesTournee contraintes;
	public static Plan plan;
	private CmdListe cmdListe;
	private Dijkstra uniteCalculChemins;

//	public Controleur(String filePathPlan, String filePathTournee, int screenHeight, int screenWidth) {
//		tournee = new Tournee();
//		uniteCalculChemins = new Dijkstra();
//		plan = XMLParser.chargerPlan(filePathPlan, screenHeight, screenWidth);
//		chargerTournee(filePathTournee);
//		cmdListe = new CmdListe();
//	}

	public Controleur() {
		tournee = new Tournee();
		uniteCalculChemins = new Dijkstra();
		cmdListe = new CmdListe();
	}

	public void chargerPlan(String filePathPlan, int screenHeight, int screenWidth) throws ExceptionChargement{
		plan = XMLParser.chargerPlan(filePathPlan, screenHeight, screenWidth);
	}

	public void chargerTournee(String filePathTournee) throws ExceptionChargement {
		contraintes = XMLParser.chargerContraintesTournee(filePathTournee, plan); // v�rifier que le plan nest pas incoh�rent
		Map<String, Intersection> intersectionsAVisiter = new HashMap<>();
		
		intersectionsAVisiter.put(contraintes.getDepot().getId(), contraintes.getDepot());
		for(Intersection i: contraintes.getPointsEnlevement()) {
			intersectionsAVisiter.put(i.getId(), i);
		}
		for(Intersection i: contraintes.getPointsLivraison()) {
			intersectionsAVisiter.put(i.getId(), i);
		}
		plusCourtsChemins = uniteCalculChemins.plusCourtsCheminsPlan(plan.getIntersections(), intersectionsAVisiter);																			// que
	}


	public void calculerTournee() {
		TSP2 tsp = new TSP2();
		tournee = tsp.chercheSolution(500, contraintes, plusCourtsChemins);
		int dureeEnlevementLivraison = 0;
		
		for(PointEnlevement p : contraintes.getPointsEnlevement()) {
			dureeEnlevementLivraison += p.getTempsEnlevement();
		}
		
		for(PointLivraison p : contraintes.getPointsLivraison()) {
			dureeEnlevementLivraison += p.getTempsLivraison();
		}
		
		tournee.calculDuree(dureeEnlevementLivraison);
		
		for (Chemin c : tournee.getPlusCourteTournee()) {
			List<Intersection> inters = c.getIntersections();
			for (int i = 0; i < inters.size() - 1; ++i) {
				Intersection inter = inters.get(i);
				Troncon tronc = inter.getTronconsSortants().get(inters.get(i + 1).getId());
				System.out.print(tronc.getNomRue() + ", ");
			}
			System.out.println("; duree= " + c.getDuree());
		}
	}


	public void ajouterLivraison (PointEnlevement e, PointLivraison l) {
		Map<String, Intersection> intersectionsAVisiter = new HashMap<>();
		
		intersectionsAVisiter.put(contraintes.getDepot().getId(), contraintes.getDepot());
		for(Intersection i: contraintes.getPointsEnlevement()) {
			intersectionsAVisiter.put(i.getId(), i);
		}
		for(Intersection i: contraintes.getPointsLivraison()) {
			intersectionsAVisiter.put(i.getId(), i);
		}
		intersectionsAVisiter.put(e.getId(), e);
		intersectionsAVisiter.put(l.getId(), l);
		plusCourtsChemins = uniteCalculChemins.plusCourtsCheminsPlan(plan.getIntersections(), intersectionsAVisiter);																			// que
		CmdAjoutLivraison cmd = new CmdAjoutLivraison(tournee, contraintes, e, l, plusCourtsChemins);
		cmdListe.addCommande(cmd);
	}

	 public void supprimerLivraison (PointEnlevement e, PointLivraison l) {
		 CmdSupprimeLivraison cmd = new CmdSupprimeLivraison(contraintes, tournee, e, l, plusCourtsChemins);
		 cmdListe.addCommande(cmd);
	 }

	/**
	 * 
	 * @param pointModif
	 * @param newPrec Si le pr�c�dent est le d�p�t, mettre 'null'
	 * @param newSuiv Si le suivant est le d�p�t, mettre 'null'
	 */
	 public void modifierOrdrePassage (Intersection pointModif, Intersection newPrec, Intersection newSuiv) {
		 CmdModifOrdre cmd = new CmdModifOrdre(tournee, pointModif, newPrec, newSuiv, plusCourtsChemins);
		 cmdListe.addCommande(cmd);
	 }
	
	public void modifierAdresse(PointEnlevement e, Intersection newI) {
		Map<String, Intersection> intersectionAVisiter = new HashMap<>();
		Map<String, Chemin> newChemins = new HashMap<String, Chemin>();
		intersectionAVisiter.put(newI.getId(), newI);
		newChemins = uniteCalculChemins.plusCourtsCheminsPlan(plan.getIntersections(), intersectionAVisiter).get(newI.getId());
		plusCourtsChemins.put(newI.getId(), newChemins);
		CmdModifAdresse cmd = new CmdModifAdresse(contraintes, tournee, e, newI, plusCourtsChemins);
		cmdListe.addCommande(cmd);
	}
	
	public void modifierAdresse(PointLivraison l, Intersection newI) {
		Map<String, Intersection> intersectionAVisiter = new HashMap<>();
		Map<String, Chemin> newChemins = new HashMap<String, Chemin>();
		intersectionAVisiter.put(newI.getId(), newI);
		newChemins = uniteCalculChemins.plusCourtsCheminsPlan(plan.getIntersections(), intersectionAVisiter).get(newI.getId());
		plusCourtsChemins.put(newI.getId(), newChemins);
		CmdModifAdresse cmd = new CmdModifAdresse(contraintes, tournee, l, newI, plusCourtsChemins);
		cmdListe.addCommande(cmd);
	}

	public void undo() {
		cmdListe.undo();
	}

	public void redo() {
		cmdListe.redo();
	}

//	public static void main(String[] args) {
//		Controleur contr = new Controleur("fichiersXML2019/petitPlan.xml", "fichiersXML2019/demandePetit1.xml", 600,
//				800);
//		contr.calculerTournee();
//	}

	public Tournee getTournee() {
		return tournee;
	}

	public ContraintesTournee getContraintes() {
		return contraintes;
	}

	public Map<String, Map<String, Chemin>> getPlusCourtsChemins() {
		return plusCourtsChemins;
	}

	public void setTournee(Tournee tournee) {
		this.tournee = tournee;
	}

	public void setContraintes(ContraintesTournee contraintes) {
		this.contraintes = contraintes;
	}

}
