package model;

public class Livraison extends Precedence {

	private int duree;

	public Livraison(Intersection depart, Intersection arrivee, int duree) {
		super(depart, arrivee);
		this.duree = duree;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

}
