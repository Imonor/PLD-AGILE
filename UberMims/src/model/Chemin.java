package model;

import java.util.List;
import java.util.LinkedList;

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
	
}
