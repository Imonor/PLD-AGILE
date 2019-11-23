package model;

import java.util.List;
import java.util.LinkedList;

public class Tournee {
	
	private int duree;
	private List<Chemin> plusCourtChemin;
	
	public Tournee(List<Chemin> plusCourtChemin) {
		for(Chemin currentChemin : plusCourtChemin) {
			duree+=currentChemin.getDuree();
		}
		this.plusCourtChemin=plusCourtChemin;
	}
	
	public Tournee(List<Chemin> plusCourtChemin, int duree) {
		this.duree=duree;
		this.plusCourtChemin=plusCourtChemin;
	}
	
	public Tournee() {
		plusCourtChemin = new LinkedList<Chemin>();
		duree = 0;
	}
	
	public int getDuree() {
		return duree;
	}
	
	public List<Chemin> getPlusCourtChemin () {
		return plusCourtChemin;
	}
	
	public void addChemlin (Chemin chemin) {
		plusCourtChemin.add(chemin);
		duree+=chemin.getDuree();
	}
}
