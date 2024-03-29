package algo;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.Map;

import model.Chemin;
import model.Intersection;

/**
 * Classe implementant l'interface iterateur
 *
 */
public class IteratorMinFirst implements Iterator<String>{
	
	private HashMap<String, Intersection> intersections;
	private HashMap<String, Paire> vuDispo;
	Map<String, Map<String, Chemin>> plusCourtsChemins;
	private int restants;
	private String courant;
	
	/**
	 * Cree un iterateur pour iterer sur l'ensemble des sommets de nonVus - type : minimum en premier
	 * @param restants - nb de noeuds restants a visiter
	 * @param intersections - map avec l'id et les intersections qu'on a a visiter
	 * @param vuDispo - map de id et objet=(vu, dispo)
	 * @param plusCourtsChemins - map des plus courts chemins calcules
	 */
	public IteratorMinFirst(int restants, HashMap<String, Intersection> intersections, HashMap<String, Paire> vuDispo, Map<String, Map<String, Chemin>> plusCourtsChemins){
		this.intersections = intersections;
		this.vuDispo = vuDispo;
		this.plusCourtsChemins = plusCourtsChemins;
		this.restants = restants;
		courant = "";
	}
	
	/**
	 * Methode qui indique si c'est possible d'obtenir un iterateur suivant
	 */
	@Override
	public boolean hasNext() {
		return restants > 0;
	}
	
	/**
	 * Methode qui renvoit le noeud suivant (String)
	 */
	@Override
	public String next() {
		
		Iterator<Entry<String, Paire>> it = vuDispo.entrySet().iterator();
		String cle = "";
		int min = Integer.MAX_VALUE;
		
		String actual = "";
		if(courant.equals("")) {
			Map.Entry<String,Intersection> first = intersections.entrySet().iterator().next();
			actual = first.getKey();
		}else {
			actual = courant;
		}
		
		//Recherche du min
		for (HashMap.Entry<String, Intersection> entry : intersections.entrySet()) {
			if( plusCourtsChemins.get(actual).get(entry.getKey()).getDuree() < min && vuDispo.get(entry.getKey()).getDispo() == true && vuDispo.get(entry.getKey()).getVu() == false ) {
				cle = entry.getKey();
				min = plusCourtsChemins.get(actual).get(entry.getKey()).getDuree();
			}
		}
		
		restants--;
		courant = cle;
		return cle;
	}
	
	public void remove(String... args) {
	}

}
