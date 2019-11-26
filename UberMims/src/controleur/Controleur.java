package controleur;

import util.XMLParser;
import model.*;

public class Controleur {
	private final int LARGEUR_PLAN = 1200;
	private final int HAUTEUR_PLAN = 800;
	
	public Chemin[][] plusCourtsChemins;
	private Tournee tournee;
	private ContraintesTournee contraintes;
	public static Plan plan;
	private CmdListe cmdListe;
	//private Dijkstra uniteCalculChemins
	
	public Controleur (String filePathPlan, String filePathTournee) {
		tournee = new Tournee ();
		plan = XMLParser.chargerPlan(filePathPlan, HAUTEUR_PLAN, LARGEUR_PLAN);
		contraintes = XMLParser.chargerContraintesTournee(filePathTournee, plan);
		cmdListe = new CmdListe();
	}
	
	public void chargerPlan(String filePathPlan) {
		plan = XMLParser.chargerPlan(filePathPlan,HAUTEUR_PLAN, LARGEUR_PLAN);
	}
	
	public void chargerTournee(String filePathTournee) {
		contraintes = XMLParser.chargerContraintesTournee(filePathTournee, plan); // vérifier que le plan est pas incohérent
	}
	
	public void calculerTournee() {
		// à implémenter
	}
	
//	public void ajouterLivraison (Livraison livraison) {
//		CmdAjoutLivraison cmd = new CmdAjoutLivraison(contraintes, livraison);
//		cmdListe.addCommande(cmd);
//	}
//	
//	public void supprimerLivraison (Livraison livraison) {
//		CmdSupprimeLivraison cmd = new CmdSupprimeLivraison(contraintes, livraison);
//		cmdListe.addCommande(cmd);
//	}
//	
//	public void modifierOrdrePassage (Precedence precedence) {
//		CmdModifOrdre cmd = new CmdModifOrdre(contraintes, precedence);
//		cmdListe.addCommande(cmd);
//	}
	
	public void undo() {
		cmdListe.undo();
	}
	
	public void redo () {
		cmdListe.redo();
	}

}
