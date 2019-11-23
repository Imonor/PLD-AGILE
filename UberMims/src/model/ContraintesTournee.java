package model;

import java.util.List;
import java.time.LocalTime;

public class ContraintesTournee {

	private LocalTime heureDepart;
	private Intersection depot;
	private List<Precedence> contraintes;

	public ContraintesTournee() {
	}

	public ContraintesTournee(LocalTime heureDepart, Intersection depot, List<Precedence> contraintes) {
		this.heureDepart = heureDepart;
		this.depot = depot;
		this.contraintes = contraintes;
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

	public List<Precedence> getContraintes() {
		return contraintes;
	}

	public void setContraintes(List<Precedence> contraintes) {
		this.contraintes = contraintes;
	}

	public void addPrecedence(Precedence prec) {
		this.contraintes.add(prec);
	}

	public void removePrecedence(Precedence prec) {
		if (this.contraintes.contains(prec))
			this.contraintes.remove(prec);
	}

}
