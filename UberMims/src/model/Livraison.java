package model;

public class Livraison extends Precedence {

	private int dureeEnlevement;
	private int dureeLivraison;

	public Livraison(Intersection pointAvant, Intersection pointAprès, int dureeEnlevement, int dureeLivraison) {
		super(pointAvant, pointAprès);
		this.dureeEnlevement = dureeEnlevement;
		this.dureeLivraison = dureeLivraison;
	}

	public int getDureeEnlevement() {
		return dureeEnlevement;
	}
	
	public int getDureeLivraison() {
		return dureeLivraison;
	}

	public void setDureeLivraions(int dureeLivraison) {
		this.dureeEnlevement = dureeLivraison;
	}

	public void setDureeEnlevement(int dureeEnlevement) {
		this.dureeEnlevement = dureeEnlevement;
	}
	
}
