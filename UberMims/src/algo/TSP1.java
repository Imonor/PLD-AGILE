package algo;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import model.Chemin;
import model.ContraintesTournee;
import model.Intersection;
import model.Tournee;

/**
 * Classe qui implemente l'interface TSP avec un iterateur sequentiel
 *
 */
public class TSP1 extends TemplateTSP{

	public TSP1() {
		super();
	}
	
	/**
	 *  Cree un iterateur pour iterer sur l'ensemble des sommets de nonVus
	 * type - prochain neouds dans l'ordre de la liste initiale
	 * @param restants - nb de noeuds restants a visiter
	 * @param intersections - map avec l'id et les intersections qu'on a a visiter
	 * @param vuDispo - map de id et objet=(vu, dispo)
	 * @param plusCourtsChemins - map des plus courts chemins calcules
	 */
	@Override
	protected Iterator<String> iterator(int restants, HashMap<String, Intersection> intersections, HashMap<String, Paire> vuDispo, Map<String, Map<String, Chemin>> plusCourtsChemins) {
		return new IteratorSeq(restants, intersections, vuDispo, plusCourtsChemins);
	}

	/**
	 * Methode qui donne la solution finale - la tournee
	 * @param tpsLimite - temps accorde a l'algorithme
	 * @param contraintes - la liste des points de livraison et d'enlevement
	 * @param plusCourtsChemins - map des plus courts chemins calcules
	 */
	@Override
	public Tournee chercheSolution(int tpsLimite, ContraintesTournee contraintes, Map<String, Map<String, Chemin>> plusCourtsChemins) {
		// TODO Auto-generated method stub
		return super.chercheSolution(tpsLimite, contraintes, plusCourtsChemins);
	}


}
