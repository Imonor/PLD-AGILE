package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Intersection {

	private String id;
	private double longitude;
	private double latitude;
	private Map<String, Troncon> tronconsSortants;
	
	public Intersection() {}

	public Intersection(String id, double latitude, double longitude) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		tronconsSortants = new HashMap<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public Map<String, Troncon> getTronconsSortants() {
		return tronconsSortants;
	}

	public void addTroncon(String idArrivee, Troncon tronc) {
		if(!tronconsSortants.containsKey(idArrivee))
			this.tronconsSortants.put(idArrivee, tronc);
	}

}
