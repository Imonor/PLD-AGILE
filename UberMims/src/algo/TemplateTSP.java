package algo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	
	/*public TemplateTSP(ContraintesTournee contraintes) {
		
	}*/
	
	public Boolean getTempsLimiteAtteint(){
		return tempsLimiteAtteint;
	}
	
	public void chercheSolution(int tpsLimite, ContraintesTournee contraintes, Map<String, Map<String, Chemin>> plusCourtsChemins, int[] duree){
		
		tempsLimiteAtteint = false;
		coutMeilleureSolution = Integer.MAX_VALUE;
		
//		int nbSommets = contraintes.getPointsEnlevement().size()+contraintes.getPointsLivraison().size();//initialisation du nombre de sommets a visiter
//		meilleureSolution = new String[nbSommets];
		
		Map<String, Map<String, Integer>> couts = extraitCouts(plusCourtsChemins,contraintes.getPointsLivraison(), contraintes.getPointsEnlevement()); //Pas frocement utile
		
		//HashMap avec la position dans le tableau et l'id de l'intersection correspondantes
		HashMap<Integer, String> positions = new HashMap<Integer, String>();
		Iterator<PointEnlevement> itEnlev = (Iterator<PointEnlevement>)contraintes.getPointsEnlevement().iterator();
		Iterator<PointLivraison> itLiv = (Iterator<PointLivraison>)contraintes.getPointsLivraison().iterator();
		
		//Remplissage HashMap
		int nbSommets = 0;
		while(itEnlev.hasNext()) {
			Intersection intersec = (Intersection) itEnlev.next();
			positions.put(nbSommets, intersec.getId());
			nbSommets++;
		}
		while(itLiv.hasNext()) {
			Intersection intersec = (Intersection) itLiv.next();
			positions.put(nbSommets, intersec.getId());
			nbSommets++;
		}
		
		meilleureSolution = new String[nbSommets];
		
		//Initialisation tableaux avec les elements aux positions attribuees plus haut
		boolean[] dispos = new boolean[nbSommets];
		boolean[] vus = new boolean[nbSommets];
		
		for(int i = 0; i<nbSommets; ++i) {
			vus[i] = false;
			
			//Les points d'enlevement sont les premiers dans le tableau
			if( i < (nbSommets/2) ) {
				dispos[i] = true;
			}else {
				dispos[i] = false;
			}
		}
		
		
			
//		List<String> dispos = new LinkedList<String>();
//		ArrayList<String> indispos = new ArrayList<String>(nbSommets);
//		for(PointEnlevement intersectionDispo : contraintes.getPointsEnlevement()) dispos.add(intersectionDispo.getId()); // Marquage des points d'enlevement comme potentiellement visitables
//		for(PointLivraison intersectionIndispo : contraintes.getPointsLivraison()) indispos.add(intersectionIndispo.getId()); // Marqueges des point de livraison comme non visitables
//		List<String> vus = new LinkedList<String>();
//		vus.add(contraintes.getDepot().getId()); // le premier sommet visite est le depot
		
		//branchAndBound(0, nonVus, vus, 0, cout, duree, System.currentTimeMillis(), tpsLimite);
		
//		cheminDebile(contraintes.getDepot().getId(), indispos, dispos, vus, plusCourtsChemins);
	}
	
	//Peut-etre inutile
	private Map<String, Map<String, Integer>> extraitCouts(Map<String,Map<String, Chemin>> aTraiter, List<PointLivraison> aLivrer, List<PointEnlevement> aEnlever)	{
		Map<String, Map<String, Integer>> couts = new HashMap();
		for(PointEnlevement currentEnlevement : aEnlever) {
			Map<String, Integer> coutsIntermediaraies = new HashMap();
			for (PointLivraison currentLivraison : aLivrer) {
				coutsIntermediaraies.put(currentLivraison.getId(),
						aTraiter.get(currentEnlevement.getId()).get(currentLivraison.getId()).getDuree());
			}
			couts.put(currentEnlevement.getId(),
					coutsIntermediaraies);
		}
		return couts;
	}
	
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
	protected abstract int bound(Integer sommetCourant, ArrayList<Integer> nonVus, int[][] cout, int[] duree);
	
	/**
	 * Methode devant etre redefinie par les sous-classes de TemplateTSP
	 * @param sommetCrt
	 * @param nonVus : tableau des sommets restant a visiter
	 * @param cout : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
	 * @param duree : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
	 * @return un iterateur permettant d'iterer sur tous les sommets de nonVus
	 */
	protected abstract Iterator<Integer> iterator(Integer sommetCrt, ArrayList<Integer> nonVus, int[][] cout, int[] duree);
	
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
	 
//	 Tournee cheminDebile(String sommetCrt, LinkedList<String> dispos, ArrayList<String> indispos, LinkedList<String> vus, Map<String,Map<String, Chemin>> cout){
//		 Tournee tourneeDebile = new Tournee();
//		 while(!dispos.isEmpty()) {
//			 String current = dispos.poll();
//			 
//		 }
//		 return tourneeDebile;		 
//	}
}
