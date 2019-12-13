package controleur;

import util.ExceptionChargement;
import util.XMLParser;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algo.Dijkstra;
import algo.TSP2;
import model.*;

public class Controleur {

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
		tournee = tsp.chercheSolution(2000, contraintes, plusCourtsChemins);
		int dureeEnlevementLivraison = 0;
		
		for(PointEnlevement p : contraintes.getPointsEnlevement()) {
			dureeEnlevementLivraison += p.getTempsEnlevement();
		}
		tournee.calculDuree();
		
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

	/**
	 * Fonction appelee par la fenetre pour ajouter une livraison a la tournee
	 * 
	 * @param e Point d'enlevement a ajouter
	 * @param l Point de livraison a  ajouter
	 */
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

	/**
	 * Fonction appelee par la fenetre pour supprimer une livraison de la tournee
	 * @param e Point d'enlevement de la Livraison a supprimer
	 */
	public void supprimerLivraison (PointEnlevement e) {
		 CmdSupprimeLivraison cmd = new CmdSupprimeLivraison(contraintes, tournee, e, plusCourtsChemins);
		 cmdListe.addCommande(cmd);
	 }
	 /**
	  * Fonction appelee par la fenetre pour supprimer une livraison de la tournee
	  * @param l Point de livraison de la livraison a supprimer
	  */
	public void supprimerLivraison (PointLivraison l) {
		 CmdSupprimeLivraison cmd = new CmdSupprimeLivraison(contraintes, tournee, l, plusCourtsChemins);
		 cmdListe.addCommande(cmd);
	}

	/**
	 * Fonction appelee par la fenetre pour modifier l'ordre des livraisons de la tournee
	 * @param pointModif Le point dont on veut modifier la place dans la liste
	 * @param newPrec Le nouveau point precedent (si le precedent est le depot, mettre a null)
	 * @param newSuiv Le nouveau point suivant (si le suivant est le depot, mettre a null)
	 */
	public void modifierOrdrePassage (Intersection pointModif, Intersection newPrec, Intersection newSuiv) {
		 CmdModifOrdre cmd = new CmdModifOrdre(tournee, pointModif, newPrec, newSuiv, plusCourtsChemins);
		 cmdListe.addCommande(cmd);
	}
	/**
	 * Fonction appelee par la fenetre pour modifier l'emplacement d'un point d'enlevement
	 * @param e L'ancien point d'enlevement
	 * @param newI L'intersection representant le nouvel emplacement
	 */
	public void modifierAdresse(PointEnlevement e, Intersection newI) {
		Map<String, Intersection> intersectionsAVisiter = new HashMap<>();
		
		intersectionsAVisiter.put(contraintes.getDepot().getId(), contraintes.getDepot());
		for(Intersection i: contraintes.getPointsEnlevement()) {
			intersectionsAVisiter.put(i.getId(), i);
		}
		for(Intersection i: contraintes.getPointsLivraison()) {
			intersectionsAVisiter.put(i.getId(), i);
		}
		intersectionsAVisiter.put(newI.getId(), newI);
		plusCourtsChemins = uniteCalculChemins.plusCourtsCheminsPlan(plan.getIntersections(), intersectionsAVisiter);
		CmdModifAdresse cmd = new CmdModifAdresse(contraintes, tournee, e, newI, plusCourtsChemins);
		cmdListe.addCommande(cmd);
	}

	/**
	 * Fonction appelee par la fenetre pour modifier l'emplacement d'un point de livraison
	 * @param l L'ancien point de livraison
	 * @param newI L'intersection representant le nouvel emplacement
	 */
	public void modifierAdresse(PointLivraison l, Intersection newI) {
		Map<String, Intersection> intersectionsAVisiter = new HashMap<>();
		
		intersectionsAVisiter.put(contraintes.getDepot().getId(), contraintes.getDepot());
		for(Intersection i: contraintes.getPointsEnlevement()) {
			intersectionsAVisiter.put(i.getId(), i);
		}
		for(Intersection i: contraintes.getPointsLivraison()) {
			intersectionsAVisiter.put(i.getId(), i);
		}
		intersectionsAVisiter.put(newI.getId(), newI);
		plusCourtsChemins = uniteCalculChemins.plusCourtsCheminsPlan(plan.getIntersections(), intersectionsAVisiter);
		CmdModifAdresse cmd = new CmdModifAdresse(contraintes, tournee, l, newI, plusCourtsChemins);
		cmdListe.addCommande(cmd);
	}
	
	public void modifierTemps(PointEnlevement e, int duree) {
		CmdModifTemps cmd = new CmdModifTemps(e, duree);
		cmdListe.addCommande(cmd);
	}
	
	public void modifierTemps(PointLivraison l, int duree) {
		CmdModifTemps cmd = new CmdModifTemps(l, duree);
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
	
	public CmdListe getCmdListe() {
		return this.cmdListe;
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
