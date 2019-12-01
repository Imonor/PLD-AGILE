package algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Chemin;
import model.Intersection;
import model.Troncon;


public class Dijkstra {
	
	private final double vitesse = 1.0;//15.0/3.6; //Vitesse en m/s
	
	//Retourne une map dont la clé est l'id de l'intersection considérée, et la valeur est une 
	//map qui contient en clé l'id de l'intersection vers laquelle on souhaite calculer le +
	//court chemin, et en valeur le chemin correspondant
	public Map<String, Map<String, Chemin>> plusCourtsCheminsPlan(Map<String, Intersection> plan, Map<String, Intersection> intersectionsAVisiter) {
		Map<String, Map<String, Chemin>> plusCourtsChemins = new HashMap<>();
		
		Map<String, String> precedence = new HashMap<>();
		Map<String, Double> distance = new HashMap<>(); 
		Map<String, Integer> couleurSommet = new HashMap<>();
		
		List<String> sommetsGris = new ArrayList<>();
		String sommetAEtudier = "";

		//On applique l'algo de Dikstra au depart de l'intersection "intersectionId"
		for(String intersectionId: intersectionsAVisiter.keySet()) { 
					
			//Tous les sommets sont blancs, les précédences sont initialisées à null et les distances à +infini
			initialiserMaps(plan, precedence, distance, couleurSommet);
			
			//Initialisation du sommet de départ (distance nulle, couleur grise)
			distance.put(intersectionId, 0.0);
			couleurSommet.put(intersectionId, 1);
			sommetsGris.add(intersectionId);
			
			//Parcours du graphe
			while(!sommetsGris.isEmpty()) {
				//Récupérer le sommet gris avec la + courte distance. Il s'agira du sommet à étudier
				double distanceMin = Double.MAX_VALUE; 
				for(String sommetGris: sommetsGris) {
					if(distance.get(sommetGris) < distanceMin) {
						sommetAEtudier = sommetGris;
						distanceMin = distance.get(sommetGris);
					}
				}
				//Récupérer la liste des troncons sortant du sommet étudié
				Map<String, Troncon> troncons = plan.get(sommetAEtudier).getTronconsSortants();
				//Procédure de relâchement pour chaque arc partant du sommet étudié
				for(String idDestination: troncons.keySet()) {
					if(couleurSommet.get(idDestination) == 0 ||couleurSommet.get(idDestination) == 1) {
						this.relacher(sommetAEtudier, idDestination, troncons.get(idDestination).getLongueur(), precedence, distance);
						if(couleurSommet.get(idDestination) == 0) {
							couleurSommet.put(idDestination, 1);
							sommetsGris.add(idDestination);
						}
					}
				}
				//On colorie le sommet qui a été étudié en noir, et on le retire de la liste des gris
				couleurSommet.put(sommetAEtudier, 2); 
				sommetsGris.remove(sommetAEtudier);
			}
			
			//On crée la liste des + courts chemins depuis intersectionId vers les autres intersections
			Map<String, Chemin> chemins = new HashMap<>();
			for(String intersectionArrivee: intersectionsAVisiter.keySet()) {
				Chemin chemin = this.creerChemin(intersectionId, intersectionArrivee, precedence, plan);
				chemins.put(intersectionArrivee, chemin);
			}
			//On ajoute cette liste à la liste globale des + courts chemins
			plusCourtsChemins.put(intersectionId, chemins); 
		}
		return plusCourtsChemins;
	}
	
	
	//Procédure de relachement d'un arc
	private void relacher(String debutArc, String finArc, double coutArc, Map<String, String> precedence, Map<String, Double> distance){

		if(distance.get(finArc) > distance.get(debutArc) + coutArc) {
			distance.put(finArc, distance.get(debutArc) + coutArc);
			precedence.put(finArc, debutArc);
		}
	}
	
	
	//Initialisation des précédences, des couleurs et des distances
	private void initialiserMaps(Map<String, Intersection> intersections, Map<String, String> precedence, Map<String, Double> distance, Map<String, Integer> couleurSommet) {
		precedence.clear();
		distance.clear();
		couleurSommet.clear();
		for(String intersectionId: intersections.keySet()) {
			precedence.put(intersectionId, "null");
			distance.put(intersectionId, Double.MAX_VALUE);
			couleurSommet.put(intersectionId, 0);
		}
	}
	
	
	//Procédure de création d'un chemin à partir d'une intersection de départ vers une intersection d'arrivee
	//en fonction d'un tableau donnant les précédences entre intersections
	private Chemin creerChemin(String interDepart, String interArrivee, Map<String, String> precedence, Map<String, Intersection> intersections) {
		
		//Liste ordonnée des intersections qui composeront le chemin, si elle existe
		List<Intersection> cheminement = new ArrayList<>();
		//Distance totale du chemin
		double distance = 0;
		
		//Cas où  l'intersection de départ et d'arrivée sont les mêmes
		if(interDepart.equals(interArrivee)) {
			cheminement = null;

		} else {

			String idIntersectionActuelle = interArrivee;
			String idIntersectionPrecedente;
			
			//Test qui vérifie si l'intersection d'arrivée est atteignable depuis l'intersection de départ
			//(si ce n'est pas le cas l'intersection précédente a pour valeur "null)
			if(!precedence.get(idIntersectionActuelle).equals("null")) {
				Intersection intersectionActuelle = intersections.get(idIntersectionActuelle);
				
				//On remonte le cheminement jusqu'à trouver l'intersection de départ
				while(idIntersectionActuelle != interDepart) {
					cheminement.add(intersectionActuelle);
					idIntersectionPrecedente = precedence.get(idIntersectionActuelle);
					idIntersectionActuelle = idIntersectionPrecedente;
					intersectionActuelle = intersections.get(idIntersectionActuelle);
				}
				cheminement.add(intersections.get(interDepart));
				
				//Le cheminement est inversé pour bien partir de l'intersection de départ et non pas de 
				//l'intersection d'arrivée
				Collections.reverse(cheminement);
				
				//Calcul de la distance du chemin
				int indexDepart = 0;
				while(indexDepart < cheminement.size() - 1) {
					//Récupérer le troncon correspondant à l'étape actuelle du cheminement
					Intersection iDepart = cheminement.get(indexDepart);
					Intersection iNext = cheminement.get(indexDepart + 1);
					Troncon troncon = iDepart.getTronconsSortants().get(iNext.getId());
					//Ajouter la longueur du troncon à la distance totale du chemin
					distance += troncon.getLongueur();
					indexDepart++;
				}
				
			//Cas où l'intersection n'est pas atteignable: on considère que la distance depuis
			//l'intersection de départ est infinie
			} else {
				cheminement = null;
				distance = Double.MAX_VALUE;
			}
			
		}
		int duree = (int)(distance/this.vitesse);
		return new Chemin(cheminement, duree);
	}
	
	
	public static void main(String[] args) {
		Dijkstra d = new Dijkstra();
		Intersection i1 = new Intersection("i1", 0.0, 0.0);
		Intersection i2 = new Intersection("i2", 0.0, 0.0);
		Intersection i3 = new Intersection("i3", 0.0, 0.0);
		Intersection i4 = new Intersection("i4", 0.0, 0.0);
		Intersection i5 = new Intersection("i5", 0.0, 0.0);
		
		i1.addTroncon("i2", new Troncon(i2, "", 100.0));
		i2.addTroncon("i3", new Troncon(i3, "", 50.0));
		i2.addTroncon("i1", new Troncon(i1, "", 50.0));
		i3.addTroncon("i4", new Troncon(i4, "", 100.0));
		i3.addTroncon("i2", new Troncon(i2, "", 80.0));
		i3.addTroncon("i1", new Troncon(i1, "", 300.0));
		i4.addTroncon("i5", new Troncon(i5, "", 150.0));
		i2.addTroncon("i5", new Troncon(i5, "", 250.0));
		
		
		Map<String, Intersection> intersections = new HashMap<>();
		intersections.put("i1", i1);
		intersections.put("i2", i2);
		intersections.put("i3", i3);
		intersections.put("i4", i4);
		intersections.put("i5", i5);
		
		Map<String, Intersection> aVisiter = new HashMap<>();
		aVisiter.put("i1", i1);
		aVisiter.put("i2", i2);
		aVisiter.put("i3", i3);
		
		Map<String, Map<String, Chemin>> plusCourtsChemins = d.plusCourtsCheminsPlan(intersections, aVisiter);
		for(String interDepart: plusCourtsChemins.keySet()) {
			System.out.println("intersection " + interDepart +":");
			for(String interArrivee: plusCourtsChemins.get(interDepart).keySet()) {
				System.out.print("vers: " + interArrivee + ": ");
				if(plusCourtsChemins.get(interDepart).get(interArrivee).getIntersections() != null) {
					for(Intersection i: plusCourtsChemins.get(interDepart).get(interArrivee).getIntersections()) {
						System.out.print(i.getId() + " -> ");
					}
				}
				System.out.println("duree: " + (int) (plusCourtsChemins.get(interDepart).get(interArrivee).getDuree()));
			}
		}

	}
}