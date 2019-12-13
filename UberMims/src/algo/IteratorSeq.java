package algo;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.Map;

import model.Chemin;
import model.Intersection;
import model.PointEnlevement;

/**
 * Classe implementant l'interface iterateur
 */
public class IteratorSeq implements Iterator<String>{
	
	private HashMap<String, Intersection> intersections;
	private HashMap<String, Paire> vuDispo;
	private int restants;
	
	/**
	 *  Cree un iterateur pour iterer sur l'ensemble des sommets de nonVus
	 * type - prochain neouds dans l'ordre de la liste initiale
	 * @param restants - nb de noeuds restants a visiter
	 * @param intersections - map avec l'id et les intersections qu'on a a visiter
	 * @param vuDispo - map de id et objet=(vu, dispo)
	 * @param plusCourtsChemins - map des plus courts chemins calcules
	 */
	public IteratorSeq(int restants, HashMap<String, Intersection> intersections, HashMap<String, Paire> vuDispo, Map<String, Map<String, Chemin>> plusCourtsChemins){
		this.intersections = intersections;
		this.vuDispo = vuDispo;
		this.restants = restants;
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
		
		boolean trouve = false;
		Iterator<Entry<String, Paire>> it = vuDispo.entrySet().iterator();
		String cle = "";
		
		//Recherche du premier noeud dans la HashMap qui est disponible et non vu
		while (it.hasNext() && !trouve) {
			HashMap.Entry<String, Paire> entry = (HashMap.Entry<String, Paire>) it.next();
			if( entry.getValue().getDispo() == true && entry.getValue().getVu() == false) {
				trouve = true;
				cle = entry.getKey();
				restants--;
			}
		}
		
		if(trouve) {
			return cle;
		}else {
			return "";
		}
	}
	
	
	public void remove(String... args) {
	}
	
	

}
