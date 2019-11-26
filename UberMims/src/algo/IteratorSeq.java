package algo;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Iterator;

import model.Intersection;
import model.PointEnlevement;

public class IteratorSeq implements Iterator<String>{
	
	HashMap<String, Intersection> intersections;
	HashMap<String, Paire> vuDispo;
	private int restants;
	
	/**
	 * Cree un iterateur pour iterer sur l'ensemble des sommets de nonVus
	 * @param nonVus
	 * @param sommetCrt
	 */
	public IteratorSeq(int restants, HashMap<String, Intersection> intersections, HashMap<String, Paire> vuDispo){
		this.intersections = intersections;
		this.vuDispo = vuDispo;
		this.restants = restants;
	}
	
	@Override
	public boolean hasNext() {
		return restants > 0;
	}
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
		
//		System.out.println("---ITERATEUR---");
//		for (HashMap.Entry<String, Paire> iterator : vuDispo.entrySet()) {
//		    System.out.println( iterator.getKey()+" : dispo="+iterator.getValue().getDispo() + "- vu=" + iterator.getValue().getVu() );
//		}
//		System.out.println("---FIN ITERATEUR---");
		
		if(trouve) {
			return cle;
		}else {
			return "";
		}
	}
	
	
	public void remove(String... args) {
	}
	
	

}
