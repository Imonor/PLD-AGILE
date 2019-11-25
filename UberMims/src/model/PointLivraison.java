package model;

public class PointLivraison extends Intersection {
	
	private String idEnlevement;
	private int tempsLivraison;

	public PointLivraison(String id, double latitude, double longitude, String idEnlevement, int tempsLivraison) {
		super(id, latitude, longitude);
		this.idEnlevement = idEnlevement;
		this.setTempsLivraison(tempsLivraison);
	}
	
	public PointLivraison(Intersection i, String idEnlevement, int tempsLivraison) {
		this.setId(i.getId());
		this.setLatitude(i.getLatitude());
		this.setLongitude(i.getLongitude());
		this.idEnlevement = idEnlevement;
		this.setTempsLivraison(tempsLivraison);
	}

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
		this.tempsLivraison = tempsLivraison;
	}

}
