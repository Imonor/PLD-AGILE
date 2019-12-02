package algo;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.Map;

import model.Chemin;
import model.Intersection;

public class IteratorMinFirst implements Iterator<String>{
	
	private HashMap<String, Intersection> intersections;
	private HashMap<String, Paire> vuDispo;
	private HashMap<String, Integer> couts;
	private HashMap<String, Integer> testMap;
	private int restants;
	private String courant;
	
	/**
	 * Cree un iterateur pour iterer sur l'ensemble des sommets de nonVus
	 * @param nonVus
	 * @param sommetCrt
	 */
	public IteratorMinFirst(int restants, HashMap<String, Intersection> intersections, HashMap<String, Paire> vuDispo, HashMap<String, Integer> couts){
		this.intersections = intersections;
		this.vuDispo = vuDispo;
		this.couts = couts;
		this.restants = restants;
		courant = "";
	}
	
	@Override
	public boolean hasNext() {
		return restants > 0;
	}
	
	@Override
	public String next() {
		System.out.println("debut next");
		
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
		
		System.out.println("actual=" + actual);
		
		
		//Recherche du min
		for (HashMap.Entry<String, Intersection> entry : intersections.entrySet()) {
			String idid = actual.concat(entry.getKey());
			System.out.println("idid=" + idid + "; dureeAssociee=" + couts.get(idid));
			if( couts.get(idid) < min && vuDispo.get(entry.getKey()).getDispo() == true && vuDispo.get(entry.getKey()).getVu() == false ) {
				cle = entry.getKey();
				min = couts.get(idid);
			}
			System.out.println("inside-fin");
		}
		
		System.out.println("+++++ITERATEUR++++");
		System.out.println("cle=" + cle);
		restants--;
		courant = cle;
		return cle;
	}
	
	public void remove(String... args) {
	}
	
//	private void calculerCouts(){
////		
////		this.couts = new HashMap<String, Integer>();
//		
//		HashMap<String, Integer> couts = new HashMap<String, Integer>();
//		for (Map.Entry<String, Intersection> entry : intersections.entrySet()) {
//			for (Map.Entry<String, Intersection> entry2 : intersections.entrySet()) {
//				String idid = ((String)entry.getKey()).concat(entry2.getKey());
//				int duree;
//				if(entry.getKey().equals(entry2.getKey())) {
//					duree = Integer.MAX_VALUE;
//				}else {
//					duree = plusCourtsChemins.get(entry.getKey()).get(entry2.getKey()).getDuree();
//				}
//				couts.put(idid, duree);
//			}
//		}
//		
//		for (Map.Entry<String, Integer> entry : couts.entrySet()) {
//			System.out.println("idid= "+ entry.getKey() + "; duree=" + entry.getValue());
//		}
//	}

}
