package model;

import java.util.List;
import java.time.LocalTime;

public class ContraintesTournee {

	private LocalTime heureDepart;
	private Intersection depart;
	private List<Precedence> listePrecedences;

	public ContraintesTournee() {
	}

	public ContraintesTournee(LocalTime heureDepart, Intersection depart, List<Precedence> listePrecedences) {
		this.heureDepart = heureDepart;
		this.depart = depart;
		this.listePrecedences = listePrecedences;
	}

	public LocalTime getHeureDepart() {
		return heureDepart;
	}

	public void setHeureDepart(LocalTime heureDepart) {
		this.heureDepart = heureDepart;
	}

	public Intersection getDepart() {
		return depart;
	}

	public void setDepart(Intersection depart) {
		this.depart = depart;
	}

	public List<Precedence> getListePrecedences() {
		return listePrecedences;
	}

	public void setListePrecedences(List<Precedence> listePrecedences) {
		this.listePrecedences = listePrecedences;
	}

	public void addPrecedence(Precedence prec) {
		this.listePrecedences.add(prec);
	}

	public void removePrecedence(Precedence prec) {
		if (this.listePrecedences.contains(prec))
			this.listePrecedences.remove(prec);
	}

}
