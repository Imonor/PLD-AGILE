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
	
	public void addIntersection(Intersection intersection) {
		intersections.add(intersection);
	}

}
