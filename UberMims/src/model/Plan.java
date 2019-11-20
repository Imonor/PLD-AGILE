package model;

import java.util.List;
import java.util.Map;

public class Plan {

	private Map<String, Intersection> intersections;

	public Plan() {
	}

	public Plan(Map<String, Intersection> intersections, List<Troncon> troncons) {
		this.intersections = intersections;
	}

	public Map<String, Intersection> getIntersections() {
		return intersections;
	}

	public void setIntersections(Map<String, Intersection> intersections) {
		this.intersections = intersections;
	}

}
