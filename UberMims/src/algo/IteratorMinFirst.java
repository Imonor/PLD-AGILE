package algo;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.Map;

import model.Chemin;
import model.Intersection;

public class IteratorMinFirst implements Iterator<String>{
	
	HashMap<String, Intersection> intersections;
	HashMap<String, Paire> vuDispo;
	Map<String, Map<String, Chemin>> plusCourtsChemins;
	HashMap<String, Integer> couts;
	private int restants;
	String courant;
	
	/**
	 * Cree un iterateur pour iterer sur l'ensemble des sommets de nonVus
	 * @param nonVus
	 * @param sommetCrt
	 */
	public IteratorMinFirst(int restants, HashMap<String, Intersection> intersections, HashMap<String, Paire> vuDispo, Map<String, Map<String, Chemin>> plusCourtsChemins){
		this.intersections = intersections;
		this.vuDispo = vuDispo;
		this.plusCourtsChemins = plusCourtsChemins;
		this.restants = restants;
		this.calculerCouts();
		courant = "";
	}
	
	@Override
	public boolean hasNext() {
		return restants > 0;
	}
	
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
		for (Map.Entry<String, Integer> entry : couts.entrySet()) {
			String idid = actual.concat(entry.getKey());
			if( couts.get(idid) < min ) {
				cle = entry.getKey();
			}
		}
		
		restants--;
		courant = cle;
		return cle;
	}
	
	public void remove(String... args) {
	}
	
	private void calculerCouts(){
		
		this.couts = new HashMap<String, Integer>();
		
		HashMap<String, Integer> couts = new HashMap<String, Integer>();
		for (Map.Entry<String, Intersection> entry : intersections.entrySet()) {
			for (Map.Entry<String, Intersection> entry2 : intersections.entrySet()) {
				String idid = ((String)entry.getKey()).concat(entry2.getKey());
				int duree = plusCourtsChemins.get(entry.getKey()).get(entry2.getKey()).getDuree();
				couts.put(idid, duree);
			}
		}
	}

}
