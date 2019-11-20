package model;

import java.util.ArrayList;
import java.util.List;

public class Intersection {

	private String id;
	private double longitude;
	private double latitude;
	private List<Troncon> tronconsSortants;

	public Intersection(String id, double latitude, double longitude) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		tronconsSortants = new ArrayList<>();
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

	public List<Troncon> getTronconsSortants() {
		return tronconsSortants;
	}

	public void addTroncon(Troncon tronc) {
		this.tronconsSortants.add(tronc);
	}

}
