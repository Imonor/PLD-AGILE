package model;

import java.util.List;
import java.util.LinkedList;
/**
 * Classe representant un chemin, soit une liste ordonnee d'intersection.
 *
 */
public class Chemin {
	
	private int duree;
	private List<Intersection> intersections;
	
	public Chemin (List<Intersection> intersections, int duree) {
		this.duree = duree;
		this.intersections = intersections;
	}
	
	public Chemin() {
		duree = 0;
		intersections = new LinkedList<Intersection>();
	}
	
	public int getDuree () {
		return duree;
	}
	
	public List<Intersection> getIntersections(){
		return intersections;
	}
	
	public boolean addIntersection(Intersection intersection) {
		if(!getDerniere().getId().equals(intersection.getId())) {
			intersections.add(intersection);
			return true;
		}
		return false;
	}
	
	public Intersection getPremiere() {
		return intersections.get(0);
	}

	public Intersection getDerniere() {
		return intersections.get(intersections.size()-1);
	}
	
	public boolean equals(Chemin toTest) {
		if(duree!=toTest.getDuree()) return false;
		if(intersections.size()!=toTest.getIntersections().size()) return false;
		for (int i = 0; i < intersections.size(); i++) {
			if(!intersections.get(i).getId().equals(toTest.getIntersections().get(i).getId())) return false;
		}
		
		return true;
	}
	
}
