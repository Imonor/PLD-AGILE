package model;

import java.util.List;
import java.util.LinkedList;
import java.time.LocalTime;

import model.ContraintesTournee;

public class Tournee {
	
	private int duree;
	private List<Chemin> plusCourteTournee;
	private ContraintesTournee contraintes;
	
	public Tournee(List<Chemin> plusCourteTournee, ContraintesTournee contraintes) {
		for(Chemin currentChemin : plusCourteTournee) {
			duree+=currentChemin.getDuree();
		}
		this.plusCourteTournee=plusCourteTournee;
		for(PointEnlevement e : contraintes.getPointsEnlevement()) duree+=e.getTempsEnlevement();
		for(PointLivraison l : contraintes.getPointsLivraison()) duree+=l.getTempsLivraison();
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
	}
	
	public void calculDuree(int tempsElevementLivraison) {
		for(Chemin currentChemin : plusCourteTournee) {
			duree+=currentChemin.getDuree();
		}
		duree+=tempsElevementLivraison;
	}
	
	public LocalTime getHeureDePassage (String intersection) {		
		LocalTime heurePassage = contraintes.getHeureDepart();
		int secondes = 0;
		int i = 0;
		Chemin currentChemin =  plusCourteTournee.get(i);
		String currentInt = currentChemin.getPremiere().getId();
		
		//Tant qu'on a pas atteint l'intersection voulue
		while (!currentInt.equals(intersection)) { 
			
			secondes+=currentChemin.getDuree(); //Ajout du temps pour aller à la suivante
			
			boolean trouve = false;
			for(PointEnlevement e : contraintes.getPointsEnlevement()) { //Recherche de l'intersection parmis les points d'enlèvement pour ajouter son temps d'enlèvement
				if (e.getId().equals(currentInt)) {
					secondes+=e.getTempsEnlevement();
					trouve = true;
					break;
				}
			}
			if (!trouve) {
				for(PointLivraison l : contraintes.getPointsLivraison()) { //Recherche de l'intersection parmis les points de livraison pour ajouter son temps de livraison
					if (l.getId().equals(currentInt)) {
						secondes+=l.getTempsLivraison();
						break;
					}	
				}	
			}
			// Si on le trouve pas, c'est le dépôt donc on ajoute pas de temps, normal...
			currentInt = currentChemin.getPremiere().getId();
		}
		currentChemin = plusCourteTournee.get(++i); //Chemin pour aller à l'intersection suivante
		heurePassage.plusSeconds(secondes);
		return heurePassage;
	}
}
