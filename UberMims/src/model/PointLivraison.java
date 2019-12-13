package model;

/**
 * Classe heritant de la classe Intersection, representant un point de livraison
 *
 */
public class PointLivraison extends Intersection {
	
	private String idEnlevement;
	private int tempsLivraison;

	public PointLivraison(String id, double latitude, double longitude, String idEnlevement, int tempsLivraison) {
		super(id, latitude, longitude);
		this.idEnlevement = idEnlevement;
		if (tempsLivraison >=0)
			this.tempsLivraison = tempsLivraison;
		else
			this.tempsLivraison = 0;
	}
	
	public PointLivraison(Intersection i, String idEnlevement, int tempsLivraison) {
		super(i.getId(), i.getLatitude(), i.getLongitude());
		this.idEnlevement = idEnlevement;
		if (tempsLivraison >=0)
			this.tempsLivraison = tempsLivraison;
		else
			this.tempsLivraison = 0;	}

	public String getIdEnlevement() {
		return idEnlevement;
	}

	public void setIEnlevement(String idPointEnlevement) {
		this.idEnlevement = idPointEnlevement;
	}

	public int getTempsLivraison() {
		return tempsLivraison;
	}

	public void setTempsLivraison(int tempsLivraison) {
		if (tempsLivraison >=0)
			this.tempsLivraison = tempsLivraison;
		else
			this.tempsLivraison = 0;	}

}
