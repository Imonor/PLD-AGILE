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
		this.contraintes = contraintes;
		for(Chemin currentChemin : plusCourteTournee) {
			duree+=currentChemin.getDuree();
		}
		this.plusCourteTournee=plusCourteTournee;
		for(PointEnlevement e : contraintes.getPointsEnlevement()) duree+=e.getTempsEnlevement();
		for(PointLivraison l : contraintes.getPointsLivraison()) duree+=l.getTempsLivraison();
	}
	
	public Tournee(List<Chemin> plusCourteTournee, ContraintesTournee contraintes, int duree) {
		this.duree=Math.abs(duree);
		this.plusCourteTournee=plusCourteTournee;
		this.contraintes = contraintes;
	}
	
	public Tournee(ContraintesTournee contraintes) {
		this.contraintes = contraintes;
		plusCourteTournee = new LinkedList<Chemin>();
		duree = 0;
	}
	
	public Tournee() {
		contraintes = new ContraintesTournee();
		plusCourteTournee = new LinkedList<Chemin>();
		duree = 0;
	}
	
	public int getDuree() {
		return duree;
	}
	
	public ContraintesTournee getContraintes() {
		return contraintes;
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
	
	public LocalTime getHeureArrivee() {
		return contraintes.getHeureDepart().plusSeconds(duree);
	}
	
	public LocalTime getHeureDePassage (String intersection) {	
		int secondes = 0;
		int i = 0;
		Chemin currentChemin =  plusCourteTournee.get(i);
		String currentInt = currentChemin.getPremiere().getId();
		boolean trouve = (intersection.equals(currentInt));
		
		//Tant qu'on a pas atteint l'intersection voulue et qu'on est pas au bout de la tournee
		while ((!trouve) && (i<plusCourteTournee.size()-1)) {
			secondes+=currentChemin.getDuree(); //Ajout du temps pour aller à la suivante
			boolean currentIntTrouvee = false;
			for(PointEnlevement e : contraintes.getPointsEnlevement()) { //Recherche de l'intersection parmis les points d'enlèvement pour ajouter son temps d'enlèvement
				if (e.getId().equals(currentInt)) {
					secondes+=e.getTempsEnlevement();
					currentIntTrouvee = true;
					break;
				}
			}
			if (!currentIntTrouvee) {
				for(PointLivraison l : contraintes.getPointsLivraison()) { //Recherche de l'intersection parmis les points de livraison pour ajouter son temps de livraison
					if (l.getId().equals(currentInt)) {
						secondes+=l.getTempsLivraison();
						break;
					}	
				}	
			}
			// Si on le trouve pas, c'est le dépôt donc on ajoute pas de temps, normal...
			
			currentChemin = plusCourteTournee.get(++i); //Chemin pour aller à l'intersection suivante
			currentInt = currentChemin.getPremiere().getId();
			trouve = (intersection.equals(currentInt));
		}
		if (trouve) return contraintes.getHeureDepart().plusSeconds(secondes);
		return LocalTime.MAX;
	}

	public void setPlusCourteTournee(List<Chemin> plusCourteTournee) {
		this.plusCourteTournee = plusCourteTournee;
	}
}
