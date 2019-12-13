package model;
/**
 * Classe representant un Troncon, soit un lien entre deux intersections
 *
 */
public class Troncon {

	private Intersection destination;
	private String nomRue;
	private double longueur;

	public Troncon(Intersection destination, String nomRue, double longueur) {
		if ("".equals(nomRue))
			nomRue = "UNKNOWN";
		this.destination = destination;
		this.nomRue = nomRue;
		this.longueur = longueur;
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
