package model;

import java.util.ArrayList;
import java.util.Collections;
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

	public double getLattitudeMin(){
		List<Double> lattitudes = new ArrayList<Double>();
		for (Intersection it : intersections.values()) {
			lattitudes.add(it.getLatitude());
		}	
		return Collections.min(lattitudes);
	}
	
	public double getLattitudeMax(){
		List<Double> lattitudes = new ArrayList<Double>();
		for (Intersection it : intersections.values()) {
			lattitudes.add(it.getLatitude());
		}	
		return Collections.max(lattitudes);
	}
	
	public double getLongitudeMin(){
		List<Double> longitudes = new ArrayList<Double>();
		for (Intersection it : intersections.values()) {
			longitudes.add(it.getLongitude());
		}	
		return Collections.min(longitudes);
	}
	
	public double getLongitudeMax(){
		List<Double> longitudes = new ArrayList<Double>();
		for (Intersection it : intersections.values()) {
			longitudes.add(it.getLongitude());
		}	
		return Collections.max(longitudes);
	}
}
