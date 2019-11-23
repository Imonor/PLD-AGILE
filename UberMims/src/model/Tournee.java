package model;

import java.util.List;
import java.util.LinkedList;

public class Tournee {
	
	private int duree;
	private List<Chemin> plusCourteTournee;
	
	public Tournee(List<Chemin> plusCourteTournee) {
		for(Chemin currentChemin : plusCourteTournee) {
			duree+=currentChemin.getDuree();
		}
		this.plusCourteTournee=plusCourteTournee;
	}
	
	public Tournee(List<Chemin> plusCourteTournee, int duree) {
		this.duree=duree;
		this.plusCourteTournee=plusCourteTournee;
	}
	
	public Tournee() {
		plusCourteTournee = new LinkedList<Chemin>();
		duree = 0;
	}
	
	public int getDuree() {
		return duree;
	}
	
	public List<Chemin> getPlusCourteTournee () {
		return plusCourteTournee;
	}
	
	public void addChemin (Chemin chemin) {
		plusCourteTournee.add(chemin);
		duree+=chemin.getDuree();
	}
}
