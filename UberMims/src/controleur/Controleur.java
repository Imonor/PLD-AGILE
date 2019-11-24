package controleur;

import util.XMLParser;
import model.*;

public class Controleur {
	
	public static Chemin[][] plusCourtsChemins;
	private XMLParser parser;
	private Tournee tournee;
	private ContraintesTournee contraintes;
	public static Plan plan;
	//private CmdList cmdList;
	//private Dijkstra uniteCalculChemins
	
	public Controleur (String filePathPlan, String filePathTournee) {
		parser = new XMLParser();
		tournee = new Tournee ();
		plan = parser.chargerPlan(filePathPlan);
		contraintes = parser.chargerContraintesTournee(filePathTournee, plan);
	}
	
	public void chargerPlan(String filePathPlan) {
		// à compléter modifier après implémentation de la CmdList
		plan = parser.chargerPlan(filePathPlan);
	}
	
	public void chargerTournee(String filePathTournee) {
		// à compléter modifier après implémentation de la CmdList
		contraintes = parser.chargerContraintesTournee(filePathTournee, plan); // vérifier que le plan est pas incohérent
	}
	
	public void calculerTournee() {
		// à implémenter
	}
	
	public void ajouterLivraison (Livraison livraison) {
		// à compléter modifier après implémentation de la CmdList
		contraintes.addPrecedence(livraison);
	}
	
	public void supprimerLivraison (Livraison livraison) {
		// à compléter modifier après implémentation de la CmdList
		contraintes.removePrecedence(livraison);
	}
	
	public void modifierOrdrePassage (Precedence precedence) {
		// à compléter modifier après implémentation de la CmdList
		contraintes.addPrecedence(precedence);
	}
	
	public void undo() {
		// à implémenter
	}
	
	public void redo () {
		// à implémenter
	}

}
