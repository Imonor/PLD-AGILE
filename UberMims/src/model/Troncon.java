package model;

public class Troncon {

	private Intersection origine;
	private Intersection destination;
	private String nomRue;
	private double longueur;

	public Troncon(Intersection origine, Intersection destination, String nomRue, double longueur) {
		if ("".equals(nomRue))
			nomRue = "UNKNOWN";
		this.origine = origine;
		this.destination = destination;
		this.nomRue = nomRue;
		this.longueur = longueur;
	}

	public Intersection getOrigine() {
		return origine;
	}

	public void setOrigine(Intersection origine) {
		this.origine = origine;
	}

	public Intersection getDestination() {
		return destination;
	}

	public void setDestination(Intersection destination) {
		this.destination = destination;
	}

	public String getNomRue() {
		return nomRue;
	}

	public void setNomRue(String nomRue) {
		this.nomRue = nomRue;
	}

	public double getLongueur() {
		return longueur;
	}

	public void setLongueur(double longueur) {
		this.longueur = longueur;
	}

}
