package model;

import java.util.List;
import java.util.Map;

public class Plan {
	
	
	
	
	private Map<String, Intersection> intersections;
	private List<Troncon> troncons;
	
	
	
	public Plan() {
		
	}
	
	public Plan(Map<String, Intersection> intersections, List<Troncon> troncons) {
		this.intersections = intersections;
		this.troncons = troncons;
	}

	
	public Map<String, Intersection> getIntersections() {
		return intersections;
	}
	public void setIntersections(Map<String, Intersection> intersections) {
		this.intersections = intersections;
	}
	public List<Troncon> getTroncons() {
		return troncons;
	}
	public void setTroncons(List<Troncon> troncons) {
		this.troncons = troncons;
	}

}
