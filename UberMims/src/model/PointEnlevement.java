package model;

/**
 * Classe heritant de la classe Intersection, representant un point d'enlevement
 *
 */
public class PointEnlevement extends Intersection {

	private String idLivraison;
	private int tempsEnlevement;

	public PointEnlevement(String id, double latitude, double longitude, String idLivraison, int tempsEnlevement) {
		super(id, latitude, longitude);
		this.idLivraison = idLivraison;
		if (tempsEnlevement >=0)
			this.tempsEnlevement = tempsEnlevement;
		else
			this.tempsEnlevement = 0;
	}
	
	public PointEnlevement(Intersection i, String idLivraison, int tempsEnlevement) {
		super(i.getId(),i.getLatitude(),i.getLongitude());
		this.idLivraison = idLivraison;
		if (tempsEnlevement >=0)
			this.tempsEnlevement = tempsEnlevement;
		else
			this.tempsEnlevement = 0;
	}

	public String getIdLivraison() {
		return idLivraison;
	}

	public void setIdLivraison(String idLivraison) {
		this.idLivraison = idLivraison;
	}

	public int getTempsEnlevement() {
		return tempsEnlevement;
	}

	public void setTempsEnlevement(int tempsEnlevement) {
		if (tempsEnlevement >=0)
			this.tempsEnlevement = tempsEnlevement;
		else
			this.tempsEnlevement = 0;
	}
}
