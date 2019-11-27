package model;

import java.util.List;
import java.time.LocalTime;

public class ContraintesTournee {

	private LocalTime heureDepart;
	private Intersection depot;
	private List<PointEnlevement> pointsEnlevement;
	private List<PointLivraison> pointsLivraison;

	public ContraintesTournee() {
	}

	public ContraintesTournee(LocalTime heureDepart, Intersection depot, List<PointEnlevement> enls, List<PointLivraison> livs) {
		this.heureDepart = heureDepart;
		this.depot = depot;
		this.pointsEnlevement = enls;
		this.pointsLivraison = livs;
	}

	public LocalTime getHeureDepart() {
		return heureDepart;
	}

	public void setHeureDepart(LocalTime heureDepart) {
		this.heureDepart = heureDepart;
	}

	public Intersection getDepot() {
		return depot;
	}

	public void setDepot(Intersection depot) {
		this.depot = depot;
	}

	public List<PointEnlevement> getPointsEnlevement() {
		return pointsEnlevement;
	}

	public void setPointsEnlevement(List<PointEnlevement> pointsEnlevement) {
		this.pointsEnlevement = pointsEnlevement;
	}

	public List<PointLivraison> getPointsLivraison() {
		return pointsLivraison;
	}

	public void setPointsLivraison(List<PointLivraison> pointsLivraison) {
		this.pointsLivraison = pointsLivraison;
	}
	
	public void addLivraison(PointEnlevement e, PointLivraison l) {
		if(e.getIdLivraison() == l.getId() && l.getIdEnlevement() == e.getId()) {
			pointsEnlevement.add(e);
			pointsLivraison.add(l);
		} else {
			System.err.println("ERROR: PICKUP AND DELIVERY ARE NOT RELATED");
		}
	}
	
	public void removeLivraison(PointEnlevement e, PointLivraison l) {
		if(e.getIdLivraison() == l.getId() && l.getIdEnlevement() == e.getId()) {
			pointsEnlevement.remove(e);
			pointsLivraison.remove(l);
		} else {
			System.err.println("ERROR: PICKUP AND DELIVERY ARE NOT RELATED");
		}
	}

}
