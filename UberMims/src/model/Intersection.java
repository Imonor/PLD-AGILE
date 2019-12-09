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
	
	public Intersection(Intersection i) {
		this.id = i.id;
		this.latitude = i.latitude;
		this.longitude = i.longitude;
		this.tronconsSortants = new HashMap<>();
		for(Map.Entry<String, Troncon> e : tronconsSortants.entrySet()) {
			this.tronconsSortants.put(e.getKey(), e.getValue());
		}
	}

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

	public boolean addTroncon(String idArrivee, Troncon tronc) {
		if(!tronconsSortants.containsKey(idArrivee)) {
			this.tronconsSortants.put(idArrivee, tronc);
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Intersection other = (Intersection) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
