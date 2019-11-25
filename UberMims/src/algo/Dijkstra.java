package algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Chemin;
import model.Intersection;
import model.Troncon;


public class Dijkstra {
	
	private final double vitesse = 20;
	//Retourne une map dont la clé est l'id de l'intersection considérée, et la valeur est une 
	//map qui contient en clé l'id de l'intersection vers laquelle on souhaite calculer le +
	//court chemin, et en valeur l'intersection précédente pour atteindre le + court chemin
	
	public Map<String, Map<String, String>> plusCourtsCheminsPlan(Map<String, Intersection> intersections) {
		Map<String, Map<String, String>> plusCourtsChemins = new HashMap<>();
		
		Map<String, String> precedence = new HashMap<>();
		Map<String, Double> distance = new HashMap<>(); 
		Map<String, Integer> couleurSommet = new HashMap<>();
		
		List<String> sommetsGris = new ArrayList();
		String sommetAEtudier = "";

		//On applique l'algo de Dikstra au depart de l'intersection "intersection"
		for(String intersectionId: intersections.keySet()) { 
			
			System.out.println("Plus courts sommets depuis " + intersectionId);
			
			//Tous les sommets sont blancs, précédence es
			initialiserMaps(intersections, precedence, distance, couleurSommet);
			 
			distance.put(intersectionId, 0.0);
			couleurSommet.put(intersectionId, 1);
			sommetsGris.add(intersectionId);
			
			while(!sommetsGris.isEmpty()) {
				//Récupérer le sommet gris avec la + courte distance
				double distanceMin = Double.MAX_VALUE; 
				for(String sommetGris: sommetsGris) {
					if(distance.get(sommetGris) < distanceMin) {
						sommetAEtudier = sommetGris;
						distanceMin = distance.get(sommetGris);
					} 
				}
				//System.out.println("Sommet a etudier: " + sommetAEtudier);
				for(Troncon troncon: intersections.get(sommetAEtudier).getTronconsSortants()) {
					String intersectionSuccId = troncon.getDestination().getId();
					//System.out.println("vers " + intersectionSuccId);
					if(couleurSommet.get(intersectionSuccId) == 0 ||couleurSommet.get(intersectionSuccId) == 1) {
						this.relacher(sommetAEtudier, intersectionSuccId, troncon.getLongueur(), precedence, distance);
						if(couleurSommet.get(intersectionSuccId) == 0) {
							couleurSommet.put(intersectionSuccId, 1);
							sommetsGris.add(intersectionSuccId);
						}
					}
				}
				couleurSommet.put(sommetAEtudier, 2); 
				sommetsGris.remove(sommetAEtudier);
			}
			plusCourtsChemins.put(intersectionId, new HashMap<>(precedence));
		}
		return plusCourtsChemins;
	}
	
	private void relacher(String debutArc, String finArc, double coutArc, Map<String, String> precedence, Map<String, Double> distance){
		System.out.println("relacher: arc " + debutArc + " arc " + finArc);

		if(distance.get(finArc) > distance.get(debutArc) + coutArc) {
			//System.out.println("plus court chemin: arc " + debutArc + " précède " + finArc);
			distance.put(finArc, distance.get(debutArc) + coutArc);
			precedence.put(finArc, debutArc);
		}
	}
	
	public void initialiserMaps(Map<String, Intersection> intersections, Map<String, String> precedence, Map<String, Double> distance, Map<String, Integer> couleurSommet) {
		precedence.clear();
		distance.clear();
		couleurSommet.clear();
		for(String intersectionId: intersections.keySet()) {
			precedence.put(intersectionId, "null");
			distance.put(intersectionId, Double.MAX_VALUE);
			couleurSommet.put(intersectionId, 0);
		}
	}
	
	public static void main(String[] args) {
		Dijkstra d = new Dijkstra();
		Intersection i1 = new Intersection("i1", 0.0, 0.0);
		Intersection i2 = new Intersection("i2", 0.0, 0.0);
		Intersection i3 = new Intersection("i3", 0.0, 0.0);
		Intersection i4 = new Intersection("i4", 0.0, 0.0);
		Intersection i5 = new Intersection("i5", 0.0, 0.0);
		
		i1.addTroncon(new Troncon(i2, "", 2.0));
		i1.addTroncon(new Troncon(i3, "", 4.0));
		i1.addTroncon(new Troncon(i4, "", 5.0));
		i2.addTroncon(new Troncon(i3, "", 3.0));
		i3.addTroncon(new Troncon(i5, "", 4.0));
		i4.addTroncon(new Troncon(i5, "", 2.0));
		
		Map<String, Intersection> intersections = new HashMap();
		intersections.put("i1", i1);
		intersections.put("i2", i2);
		intersections.put("i3", i3);
		intersections.put("i4", i4);
		intersections.put("i5", i5);
		
		Map<String, Map<String, String>> plusCourtsChemins = d.plusCourtsCheminsPlan(intersections);
		for(String interDepart: plusCourtsChemins.keySet()) {
			System.out.println("intersection " + interDepart +":");
			for(String interArrivee: plusCourtsChemins.get(interDepart).keySet()) {
				System.out.println("arrivee: " + interArrivee + " par " + plusCourtsChemins.get(interDepart).get(interArrivee));
			}
		}
	}
}