package model;

public class PointEnlevement extends Intersection {

	private String idLivraison;
	private int tempsEnlevement;

	public PointEnlevement(String id, double latitude, double longitude, String idLivraison, int tempsEnlevement) {
		super(id, latitude, longitude);
		this.idLivraison = idLivraison;
		this.tempsEnlevement = tempsEnlevement;
	}
	
	public PointEnlevement(Intersection i, String idLivraison, int tempsEnlevement) {
		this.setId(i.getId());
		this.setLatitude(i.getLatitude());
		this.setLongitude(i.getLongitude());
		this.idLivraison = idLivraison;
		this.tempsEnlevement = tempsEnlevement;
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
		this.tempsEnlevement = tempsEnlevement;
	}
}
