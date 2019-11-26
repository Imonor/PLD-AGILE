package algo;

import java.util.LinkedList;
import java.util.Map;

import model.Chemin;
import model.ContraintesTournee;
import model.Tournee;

public interface TSP {
	
	/**
	 * @return true si chercheSolution() s'est terminee parce que la limite de temps avait ete atteinte, avant d'avoir pu explorer tout l'espace de recherche,
	 */
	public Boolean getTempsLimiteAtteint();
	
	/**
	 * Cherche un circuit de duree minimale passant par chaque sommet (compris entre 0 et nbSommets-1)
	 * @param tpsLimite : limite (en millisecondes) sur le temps d'execution de chercheSolution
	 * @param nbSommets : nombre de sommets du graphe
	 * @param cout : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
	 * @param duree : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
	 */
	public Tournee chercheSolution(int tpsLimite, ContraintesTournee contraintes, Map<String, Map<String, Chemin>> plusCourtsChemins);
	
	/**
	 * @param i
	 * @return le sommet visite en i-eme position dans la solution calculee par chercheSolution
	 */
	public String getMeilleureSolution(int i);
	
	/** 
	 * @return la duree de la solution calculee par chercheSolution
	 */
	public int getCoutMeilleureSolution();
}
