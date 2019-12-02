package controleur;

import util.XMLParser;

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

	public Map<String, Map<String, Chemin>> plusCourtsChemins;
	private Tournee tournee;
	private ContraintesTournee contraintes;
	public static Plan plan;
	private CmdListe cmdListe;
	private Dijkstra uniteCalculChemins;

	public Controleur(String filePathPlan, String filePathTournee, int screenHeight, int screenWidth) {
		tournee = new Tournee();
		uniteCalculChemins = new Dijkstra();
		plan = XMLParser.chargerPlan(filePathPlan, screenHeight, screenWidth);
		contraintes = XMLParser.chargerContraintesTournee(filePathTournee, plan);
		plusCourtsChemins = uniteCalculChemins.plusCourtsCheminsPlan(plan.getIntersections());
		cmdListe = new CmdListe();
	}

	public Controleur() {
		tournee = new Tournee();
		uniteCalculChemins = new Dijkstra();
		cmdListe = new CmdListe();
	}

	public void chargerPlan(String filePathPlan, int screenHeight, int screenWidth) {
		plan = XMLParser.chargerPlan(filePathPlan, screenHeight, screenWidth);
	}

	public void chargerTournee(String filePathTournee) {
		contraintes = XMLParser.chargerContraintesTournee(filePathTournee, plan);
		plusCourtsChemins = uniteCalculChemins.plusCourtsCheminsPlan(plan.getIntersections()); 
																					// vérifier
																					// que
																					// le
																					// plan
																					// est
																					// pas
																					// incohérent
	}

	public void calculerTournee() {
		TSP2 tsp = new TSP2();
		tournee = tsp.chercheSolution(0, contraintes, plusCourtsChemins);
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

	// public void ajouterLivraison (Livraison livraison) {
	// CmdAjoutLivraison cmd = new CmdAjoutLivraison(contraintes, livraison);
	// cmdListe.addCommande(cmd);
	// }
	//
	// public void supprimerLivraison (Livraison livraison) {
	// CmdSupprimeLivraison cmd = new CmdSupprimeLivraison(contraintes,
	// livraison);
	// cmdListe.addCommande(cmd);
	// }
	//
	// public void modifierOrdrePassage (Precedence precedence) {
	// CmdModifOrdre cmd = new CmdModifOrdre(contraintes, precedence);
	// cmdListe.addCommande(cmd);
	// }

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

}
