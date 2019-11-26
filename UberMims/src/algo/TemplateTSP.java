package algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import model.Chemin;
import model.Intersection;
import model.ContraintesTournee;
import model.PointEnlevement;
import model.PointLivraison;
import model.Tournee;

public abstract class TemplateTSP implements TSP{
	
	private String[] meilleureSolution;
	private int coutMeilleureSolution = 0;
	private Boolean tempsLimiteAtteint;
	
	//Constructeur par defaut
	public TemplateTSP() {
	}
	
	public Boolean getTempsLimiteAtteint(){
		return tempsLimiteAtteint;
	}
	
	public Tournee chercheSolution(int tpsLimite, ContraintesTournee contraintes, Map<String, Map<String, Chemin>> plusCourtsChemins){
		
//		tempsLimiteAtteint = false;
//		coutMeilleureSolution = Integer.MAX_VALUE;
//		meilleureSolution = new String[nbSommets];
		
//		Map<String, Map<String, Integer>> couts = extraitCouts(plusCourtsChemins,contraintes.getPointsLivraison(), contraintes.getPointsEnlevement()); //Pas frocement utile
		
		//HashMap avec l'id et le point pour pouvoir recuperer le point a partir de l'id
		HashMap<String, Intersection> intersections = new HashMap<String, Intersection>();
		Iterator<PointEnlevement> itEnlev = (Iterator<PointEnlevement>)contraintes.getPointsEnlevement().iterator();
		Iterator<PointLivraison> itLiv = (Iterator<PointLivraison>)contraintes.getPointsLivraison().iterator();
		
		//Remplissage HashMap
		intersections.put(contraintes.getDepot().getId(), contraintes.getDepot());
		int nbSommets = 1;
		while(itEnlev.hasNext()) {
			Intersection intersec = (Intersection) itEnlev.next();
			intersections.put(intersec.getId(), intersec);
			nbSommets++;
		}
		while(itLiv.hasNext()) {
			Intersection intersec = (Intersection) itLiv.next();
			intersections.put(intersec.getId(), intersec);
			nbSommets++;
		}
		
//		meilleureSolution = new String[nbSommets];
		
		//Initialisation de la HashMap vuDispo - contenant les attributs boolean vu et dispo
		HashMap<String, Paire> vuDispo = new HashMap<String, Paire>();
		
		for (HashMap.Entry<String, Intersection> iterator : intersections.entrySet()) {
		    if( iterator.getValue() instanceof PointEnlevement ) {
		    	vuDispo.put( iterator.getKey(), new Paire(true, false) );
		    }else {
		    	vuDispo.put( iterator.getKey(), new Paire(false, false) );
		    }
		}
		
		//Mettre premier noeud comme deja visite - ici le premier est l'entrepot
		HashMap.Entry<String,Paire> first = vuDispo.entrySet().iterator().next();
		first.getValue().setDispo(false);
		first.getValue().setVu(true);
//		if( intersections.get(first.getKey()) instanceof PointEnlevement ) {
//			String cleTmp = ((PointEnlevement)intersections.get(first.getKey())).getIdLivraison();
//			vuDispo.get(cleTmp).setDispo(true);
//		}
		
		
		Tournee tournee = new Tournee();
		calculerSimplementTournee(tournee, first.getKey(), (nbSommets-1), intersections, vuDispo, plusCourtsChemins);
//		testerCalcul(tournee, first.getKey(), (nbSommets-1), intersections, vuDispo, plusCourtsChemins);
		
		
		return tournee;
		
//		List<String> vus = new LinkedList<String>();
//		vus.add(contraintes.getDepot().getId()); // le premier sommet visite est le depot
//		branchAndBound(0, nonVus, vus, 0, cout, duree, System.currentTimeMillis(), tpsLimite);
		
	}
	
	
	protected void calculerSimplementTournee(Tournee tournee, String first, int restants, HashMap<String, Intersection> intersections, HashMap<String, Paire> vuDispo, Map<String, Map<String, Chemin>> plusCourtsChemins) {
		
		Iterator<String> it = iterator(restants, intersections, vuDispo);
		String noeudPreced = null;

		while(it.hasNext()) {
			String noeudSuivant = it.next();
			
			vuDispo.get(noeudSuivant).setDispo(false);
			vuDispo.get(noeudSuivant).setVu(true);
			
			//Mettre dispo le pt de livraison si celui actuel est un pt d'enlevement
			if( intersections.get(noeudSuivant) instanceof PointEnlevement ) {
				String cleTmp = ((PointEnlevement)intersections.get(noeudSuivant)).getIdLivraison();
				vuDispo.get(cleTmp).setDispo(true);
			}
			
			//Creation d'un chemin et l'ajout a la tournee
			Intersection depart;
			if(noeudPreced != null) {
				depart = intersections.get(noeudPreced);
			}else {
				depart = intersections.get(first);
			}
			Intersection arrivee = intersections.get(noeudSuivant);
			List<Intersection> listeInter =  plusCourtsChemins.get(depart.getId()).get(arrivee.getId()).getIntersections();
			int duree = plusCourtsChemins.get(depart.getId()).get(arrivee.getId()).getDuree();
			Chemin chemin = new Chemin(listeInter, duree);
			tournee.addChemin(chemin);
			
			noeudPreced = noeudSuivant;
		}
		
		Intersection depart = intersections.get(noeudPreced);
		List<Intersection> listeInter =  plusCourtsChemins.get(depart.getId()).get(first).getIntersections();
		int duree = plusCourtsChemins.get(depart.getId()).get(first).getDuree();
		Chemin chemin = new Chemin(listeInter, duree);
		tournee.addChemin(chemin);
		
	}
	
//	protected void testerCalcul(Tournee tournee, String first, int restants, HashMap<String, Intersection> intersections, HashMap<String, Paire> vuDispo, Map<String, Map<String, Chemin>> plusCourtsChemins) {
//			
//		Iterator<String> it = iterator(restants, intersections, vuDispo);
//		String noeudPreced = null;
//		
//		while(it.hasNext()) {
//			String noeudSuivant = it.next();
//			
//			vuDispo.get(noeudSuivant).setDispo(false);
//			vuDispo.get(noeudSuivant).setVu(true);
//			
//			if( intersections.get(noeudSuivant) instanceof PointEnlevement ) {
//				String cleTmp = ((PointEnlevement) intersections.get(noeudSuivant)).getIdLivraison();
//				vuDispo.get(cleTmp).setDispo(true);
//			}
//			
//			//Creation d'un chemin et l'ajout a la tournee
//			Intersection depart;
//			if(noeudPreced != null) {
//				System.out.println("preced="+noeudPreced+"; actuel="+noeudSuivant);
//			}else {
//				System.out.println("preced="+first+"; actuel="+noeudSuivant);
//			}
//			
//			noeudPreced = noeudSuivant;
//		}
//		
//	}
		
	
	public String getMeilleureSolution(int i){
		if ((meilleureSolution == null) || (i<0) || (i>=meilleureSolution.length))
			return null;
		return meilleureSolution[i];
	}
	
	public int getCoutMeilleureSolution(){
		return coutMeilleureSolution;
	}
	
	/**
	 * Methode devant etre redefinie par les sous-classes de TemplateTSP
	 * @param sommetCourant
	 * @param nonVus : tableau des sommets restant a visiter
	 * @param cout : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
	 * @param duree : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
	 * @return une borne inferieure du cout des permutations commencant par sommetCourant, 
	 * contenant chaque sommet de nonVus exactement une fois et terminant par le sommet 0
	 */
	protected abstract int bound();
	
	/**
	 * Methode devant etre redefinie par les sous-classes de TemplateTSP
	 * @param sommetCrt
	 * @param nonVus : tableau des sommets restant a visiter
	 * @param cout : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
	 * @param duree : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
	 * @return un iterateur permettant d'iterer sur tous les sommets de nonVus
	 */
	protected abstract Iterator<String> iterator(int restants, HashMap<String, Intersection> intersections, HashMap<String, Paire> vuDispo);
	
	/**
	 * Methode definissant le patron (template) d'une resolution par separation et evaluation (branch and bound) du TSP
	 * @param sommetCrt le dernier sommet visite
	 * @param nonVus la liste des sommets qui n'ont pas encore ete visites
	 * @param vus la liste des sommets visites (y compris sommetCrt)
	 * @param coutVus la somme des couts des arcs du chemin passant par tous les sommets de vus + la somme des duree des sommets de vus
	 * @param cout : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
	 * @param duree : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
	 * @param tpsDebut : moment ou la resolution a commence
	 * @param tpsLimite : limite de temps pour la resolution
	 */	
//	 void branchAndBound(int sommetCrt, ArrayList<Integer> nonVus, ArrayList<Integer> vus, int coutVus, int[][] cout, int[] duree, long tpsDebut, int tpsLimite){
//		 if (System.currentTimeMillis() - tpsDebut > tpsLimite){
//			 tempsLimiteAtteint = true;
//			 return;
//		 }
//	    if (nonVus.size() == 0){ // tous les sommets ont ete visites
//	    	coutVus += cout[sommetCrt][0];
//	    	if (coutVus < coutMeilleureSolution){ // on a trouve une solution meilleure que meilleureSolution
//	    		vus.toArray(meilleureSolution);
//	    		coutMeilleureSolution = coutVus;
//	    	}
//	    } else if (coutVus + bound(sommetCrt, nonVus, cout, duree) < coutMeilleureSolution){
//	        Iterator<Integer> it = iterator(sommetCrt, nonVus, cout, duree);
//	        while (it.hasNext()){
//	        	Integer prochainSommet = it.next();
//	        	vus.add(prochainSommet);
//	        	nonVus.remove(prochainSommet);
//	        	branchAndBound(prochainSommet, nonVus, vus, coutVus + cout[sommetCrt][prochainSommet] + duree[prochainSommet], cout, duree, tpsDebut, tpsLimite);
//	        	vus.remove(prochainSommet);
//	        	nonVus.add(prochainSommet);
//	        }	    
//	    }
//	}

}
