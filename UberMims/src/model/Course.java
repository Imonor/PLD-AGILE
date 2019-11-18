package model;

public class Course extends Precedence {
	
	private int duree;

	public Course(Intersection depart, Intersection arrivee, int duree) {
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
