package model;

public class Precedence {

	public Precedence(Intersection depart, Intersection arrivee) {
		super();
		this.depart = depart;
		this.arrivee = arrivee;
	}

	private Intersection depart;
	private Intersection arrivee;

	public Intersection getDepart() {
		return depart;
	}

	public void setDepart(Intersection depart) {
		this.depart = depart;
	}

	public Intersection getArrivee() {
		return arrivee;
	}

	public void setArrivee(Intersection arrivee) {
		this.arrivee = arrivee;
	}

}
