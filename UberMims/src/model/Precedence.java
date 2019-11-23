package model;

public class Precedence {

	public Precedence(Intersection pointAvant, Intersection pointApres) {
		super();
		this.pointAvant = pointAvant;
		this.pointApres = pointApres;
	}

	private Intersection pointAvant;
	private Intersection pointApres;

	public Intersection getPointAvant() {
		return pointAvant;
	}

	public void setPointAvant(Intersection pointAvant) {
		this.pointAvant = pointAvant;
	}

	public Intersection getPointApres() {
		return pointApres;
	}

	public void setpointApres(Intersection pointApres) {
		this.pointApres = pointApres;
	}

}
