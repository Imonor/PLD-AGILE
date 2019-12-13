package algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Chemin;
import model.Intersection;
import model.Troncon;

/**
 * Classe de gestion de calcul des plus courts chemins par l'algorithme de Dijkstra
 *
 */
public class Dijkstra {
	
	private final double vitesse = 15.0/3.6; //Vitesse en m/s
	
	//Retourne une map dont la cl� est l'id de l'intersection consid�r�e, et la valeur est une 
	//map qui contient en cl� l'id de l'intersection vers laquelle on souhaite calculer le +
	//court chemin, et en valeur le chemin correspondant
	/**
	 * 								calcule les plus courts chemins entre les points que doit visiter le livreur
	 * @param plan					le plan choisi par l'utilisateur
	 * @param intersectionsAVisiter	la liste des intersections du plan qui doivent être visitées par le livreur (pick-ups, deliveries et depot)
	 * @return						une map dont la clé est l'id de l'intersection d'où on calcule les plus courts chemins (une intersection à 
	 * 								visiter) et la valeur est une map, dont la clé est l'id de intersection vers laquelle on a trouvé le plus court
	 * 								chemin (une intersection à visiter), et la valeur le chemin pour y accéder
	 */
	public Map<String, Map<String, Chemin>> plusCourtsCheminsPlan(Map<String, Intersection> plan, Map<String, Intersection> intersectionsAVisiter) {
		Map<String, Map<String, Chemin>> plusCourtsChemins = new HashMap<>();
		
		Map<String, String> precedence = new HashMap<>();
		Map<String, Double> distance = new HashMap<>(); 
		Map<String, Integer> couleurSommet = new HashMap<>();
		
		List<String> sommetsGris = new ArrayList<>();
		String sommetAEtudier = "";

		//On applique l'algo de Dikstra au depart de l'intersection "intersectionId"
		for(String intersectionId: intersectionsAVisiter.keySet()) { 
					
			//Tous les sommets sont blancs, les pr�c�dences sont initialis�es � null et les distances � +infini
			initialiserMaps(plan, precedence, distance, couleurSommet);
			
			//Initialisation du sommet de d�part (distance nulle, couleur grise)
			distance.put(intersectionId, 0.0);
			couleurSommet.put(intersectionId, 1);
			sommetsGris.add(intersectionId);
			
			//Parcours du graphe
			while(!sommetsGris.isEmpty()) {
				//R�cup�rer le sommet gris avec la + courte distance. Il s'agira du sommet � �tudier
				double distanceMin = Double.MAX_VALUE; 
				for(String sommetGris: sommetsGris) {
					if(distance.get(sommetGris) < distanceMin) {
						sommetAEtudier = sommetGris;
						distanceMin = distance.get(sommetGris);
					}
				}
				//R�cup�rer la liste des troncons sortant du sommet �tudi�
				Map<String, Troncon> troncons = plan.get(sommetAEtudier).getTronconsSortants();
				//Proc�dure de rel�chement pour chaque arc partant du sommet �tudi�
				for(String idDestination: troncons.keySet()) {
					if(couleurSommet.containsKey(idDestination) && (couleurSommet.get(idDestination) == 0 ||couleurSommet.get(idDestination) == 1)) {
						this.relacher(sommetAEtudier, idDestination, troncons.get(idDestination).getLongueur(), precedence, distance);
						if(couleurSommet.get(idDestination) == 0) {
							couleurSommet.put(idDestination, 1);
							sommetsGris.add(idDestination);
						}
					}
				}
				//On colorie le sommet qui a �t� �tudi� en noir, et on le retire de la liste des gris
				couleurSommet.put(sommetAEtudier, 2); 
				sommetsGris.remove(sommetAEtudier);
			}
			
			//On cr�e la liste des + courts chemins depuis intersectionId vers les autres intersections
			Map<String, Chemin> chemins = new HashMap<>();
			for(String intersectionArrivee: intersectionsAVisiter.keySet()) {
				Chemin chemin = this.creerChemin(intersectionId, intersectionArrivee, precedence, plan);
				chemins.put(intersectionArrivee, chemin);
			}
			//On ajoute cette liste � la liste globale des + courts chemins
			plusCourtsChemins.put(intersectionId, chemins); 
		}
		return plusCourtsChemins;
	}
	
	
	//Proc�dure de relachement d'un arc
	private void relacher(String debutArc, String finArc, double coutArc, Map<String, String> precedence, Map<String, Double> distance){

		if(distance.get(finArc) > distance.get(debutArc) + coutArc) {
			distance.put(finArc, distance.get(debutArc) + coutArc);
			precedence.put(finArc, debutArc);
		}
	}
	
	
	//Initialisation des pr�c�dences, des couleurs et des distances
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
	
	
	//Proc�dure de cr�ation d'un chemin � partir d'une intersection de d�part vers une intersection d'arrivee
	//en fonction d'un tableau donnant les pr�c�dences entre intersections
	private Chemin creerChemin(String interDepart, String interArrivee, Map<String, String> precedence, Map<String, Intersection> intersections) {
		
		//Liste ordonn�e des intersections qui composeront le chemin, si elle existe
		List<Intersection> cheminement = new ArrayList<>();
		//Distance totale du chemin
		double distance = 0;
		
		//Cas o�  l'intersection de d�part et d'arriv�e sont les m�mes
		if(interDepart.equals(interArrivee)) {
			cheminement = null;

		} else {

			String idIntersectionActuelle = interArrivee;
			String idIntersectionPrecedente;
			
			//Test qui v�rifie si l'intersection d'arriv�e est atteignable depuis l'intersection de d�part
			//(si ce n'est pas le cas l'intersection pr�c�dente a pour valeur "null)
			if(!precedence.get(idIntersectionActuelle).equals("null")) {
				Intersection intersectionActuelle = intersections.get(idIntersectionActuelle);
				
				//On remonte le cheminement jusqu'� trouver l'intersection de d�part
				while(idIntersectionActuelle != interDepart) {
					cheminement.add(intersectionActuelle);
					idIntersectionPrecedente = precedence.get(idIntersectionActuelle);
					idIntersectionActuelle = idIntersectionPrecedente;
					intersectionActuelle = intersections.get(idIntersectionActuelle);
				}
				cheminement.add(intersections.get(interDepart));
				
				//Le cheminement est invers� pour bien partir de l'intersection de d�part et non pas de 
				//l'intersection d'arriv�e
				Collections.reverse(cheminement);
				
				//Calcul de la distance du chemin
				int indexDepart = 0;
				while(indexDepart < cheminement.size() - 1) {
					//R�cup�rer le troncon correspondant � l'�tape actuelle du cheminement
					Intersection iDepart = cheminement.get(indexDepart);
					Intersection iNext = cheminement.get(indexDepart + 1);
					Troncon troncon = iDepart.getTronconsSortants().get(iNext.getId());
					//Ajouter la longueur du troncon � la distance totale du chemin
					distance += troncon.getLongueur();
					indexDepart++;
				}
				
			//Cas o� l'intersection n'est pas atteignable: on consid�re que la distance depuis
			//l'intersection de d�part est infinie
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