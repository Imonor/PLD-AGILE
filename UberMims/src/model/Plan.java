package model;

import java.util.Map;
/**
 * Classe representant le plan, soit la liste des intersections
 *
 */
public class Plan {

	private Map<String, Intersection> intersections;

	public Plan() {}
	
	public Plan(Map<String, Intersection> intersections) {
		this.intersections = intersections;
	}

	public Map<String, Intersection> getIntersections() {
		return intersections;
	}

	public void setIntersections(Map<String, Intersection> intersections) {
		this.intersections = intersections;
	}

}
