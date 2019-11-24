package algo;

import java.util.List;

import model.Intersection;

public class Triplet {
	
	private Intersection intersection;
	private List<Intersection> predecesseurs;
	private boolean visite;
	
	public Triplet(Intersection intersection, List<Intersection> predecesseurs, boolean visite) {
		this.intersection = intersection;
		this.predecesseurs = predecesseurs;
		this.visite = visite;
	}
	
	public Intersection getIntersection() {
		return intersection;
	}

	public void setIntersection(Intersection intersection) {
		this.intersection = intersection;
	}

	public List<Intersection> getPredecesseurs() {
		return predecesseurs;
	}

	public void setPredecesseurs(List<Intersection> predecesseurs) {
		this.predecesseurs = predecesseurs;
	}

	public boolean getVisite() {
		return visite;
	}

	public void setVisite(boolean visite) {
		this.visite = visite;
	}
}
