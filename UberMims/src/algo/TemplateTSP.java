package algo;

import java.util.ArrayList;
import java.util.Collections;
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

/**
 * Classe qui calcule la tournee
 */
public abstract class TemplateTSP implements TSP{
	
	/**
	 * Constructeur par defaut
	 */
	public TemplateTSP() {
	}
	
	/**
	 * Methode qui donne la solution finale - la tournee
	 * @param tpsLimite - temps accorde a l'algorithme
	 * @param contraintes - la liste des points de livraison et d'enlevement
	 * @param plusCourtsChemins - map des plus courts chemins calcules
	 */
	public Tournee chercheSolution(int tpsLimite, ContraintesTournee contraintes, Map<String, Map<String, Chemin>> plusCourtsChemins){
		//HashMap avec l'id et le point pour pouvoir recuperer le point a partir de l'id
		HashMap<String, Intersection> intersections = new HashMap<String, Intersection>();
		Iterator<PointEnlevement> itEnlev = (Iterator<PointEnlevement>)contraintes.getPointsEnlevement().iterator();
		Iterator<PointLivraison> itLiv = (Iterator<PointLivraison>)contraintes.getPointsLivraison().iterator();
		
		//Remplissage HashMap des intersections 
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
		
		//Initialisation de la HashMap vuDispo - contenant les attributs boolean vu et dispo
		HashMap<String, Paire> vuDispo = initVuDispo(contraintes, intersections);
		
		Tournee tournee = new Tournee(contraintes);
		//Sequentiel et MinFirst
//		calculerSimplementTournee(tournee, contraintes.getDepot().getId(), (nbSommets-1), intersections, vuDispo, plusCourtsChemins);		
		
		//MinFirst + 2-Opt
		calculerSimplementTournee(tournee, contraintes.getDepot().getId(), (nbSommets-1), intersections, vuDispo, plusCourtsChemins);		
		twoOpt(tpsLimite, System.currentTimeMillis(), tournee, plusCourtsChemins, intersections);
		
		return tournee;
	}
	
	/**
	 * Methode qui donne la solution finale - la tournee
	 * @param tournee - objet aui va representer la tournee finale
	 * @param restants - nb de noeuds restants a visiter
	 * @param first - premier noeud a visiter
	 * @param intersections - map avec l'id et les intersections qu'on a a visiter
	 * @param vuDispo - map de id et objet=(vu, dispo)
	 * @param plusCourtsChemins - map des plus courts chemins calcules
	 * 
	 */
	protected void calculerSimplementTournee(Tournee tournee, String first, int restants, HashMap<String, Intersection> intersections, HashMap<String, Paire> vuDispo, Map<String, Map<String, Chemin>> plusCourtsChemins) {
		
		Iterator<String> it = iterator(restants, intersections, vuDispo, plusCourtsChemins);
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
	
	/**
	 * Methode qui donne la solution finale - la tournee
	 * @param tournee - objet aui va representer la tournee finale
	 * @param tpsLimite - temps accorde a l'algorithme
	 * @param tpsDebut - moment du debut de l'appel de l'algo
	 * @param intersections - map avec l'id et les intersections qu'on a a visiter
	 * @param plusCourtsChemins - map des plus courts chemins calcules
	 * 
	 */
	protected void twoOpt(int tpsLimite, long tpsDebut, Tournee tournee, Map<String, Map<String, Chemin>> plusCourtsChemins, HashMap<String, Intersection> intersections) {
		int nbChemins = tournee.getPlusCourteTournee().size();
		
		while(System.currentTimeMillis() - tpsDebut < tpsLimite){
			Iterator<Chemin> it = tournee.getPlusCourteTournee().iterator();
			
			//Initialisation nouvelle map ordreNoeuds pour le 2-opt - ordre dans la tournee qu'on calcule
			//Pour chaque chemin c'est le noeuds de depart qui est mis ici
			HashMap<String, Integer> ordreNoeuds = new HashMap<String, Integer>();
			for (HashMap.Entry<String, Intersection> iterator : intersections.entrySet()) {
		    	ordreNoeuds.put( iterator.getKey(), -1 );
			}
			
			//Algo 2-Opt
			int i = 0 ;
			while(it.hasNext() && i<(nbChemins - 2)) {
				Chemin chemin = it.next();
				ordreNoeuds.replace(chemin.getPremiere().getId(), i);
				ArrayList<String> noeudsUpdate = new ArrayList<String>();
				
				int j = 0 ;
				Iterator<Chemin> it2 = tournee.getPlusCourteTournee().iterator();
				while(it2.hasNext() && j<nbChemins) {
					Chemin chemin2 = it2.next();
					if(j>i) {
						ordreNoeuds.replace(chemin2.getPremiere().getId(), j);
						noeudsUpdate.add(chemin2.getPremiere().getId());
					}
					if(j>=i+2) {
						//Pour tous les chemins qu'on va mettre dans le sens inverse, on s'assure
						//que pour tous les points de livraison, leur pt d'enlevement est vu avant
						//ou au noeds de depart du premier chemin du swap
						boolean possible = true;
						for(int count = noeudsUpdate.size()-1; count>0; --count) {
							if( intersections.get(noeudsUpdate.get(count)) instanceof PointLivraison ) {
								String sonPtEnlev = ((PointLivraison)intersections.get(noeudsUpdate.get(count))).getIdEnlevement();
								if( ordreNoeuds.get(sonPtEnlev) > ordreNoeuds.get(chemin.getPremiere().getId()) ) {
									possible = false;
								}
							}
						}
						
						int duree = 0;
						int newDuree = 0;
						if(possible) {
							//Calculer la duree standarde
							for(int count = 0; count < noeudsUpdate.size()-1; ++count) {
								duree += plusCourtsChemins.get(noeudsUpdate.get(count)).get(noeudsUpdate.get(count+1)).getDuree();
							}
							duree += plusCourtsChemins.get(chemin.getPremiere().getId()).get(chemin.getDerniere().getId()).getDuree();
							duree += plusCourtsChemins.get(chemin2.getPremiere().getId()).get(chemin2.getDerniere().getId()).getDuree();
							
							//Calculer la duree du nouvel ensemble de chemins inverses
							for(int count = 0; count < noeudsUpdate.size()-1; ++count) {
								newDuree += plusCourtsChemins.get(noeudsUpdate.get(count+1)).get(noeudsUpdate.get(count)).getDuree();
							}
							newDuree += plusCourtsChemins.get(chemin.getPremiere().getId()).get(chemin2.getPremiere().getId()).getDuree();
							newDuree += plusCourtsChemins.get(chemin.getDerniere().getId()).get(chemin2.getDerniere().getId()).getDuree();
						}
						
						
						if( possible && newDuree < duree ) {
							twoOptSwap(i, j, chemin, chemin2, noeudsUpdate, ordreNoeuds, tournee, plusCourtsChemins);
							chemin = tournee.getPlusCourteTournee().get(i);
							chemin2 = tournee.getPlusCourteTournee().get(j);
							System.out.println("possible="+possible+"; oldDuree="+duree+"; newDuree="+newDuree);
						}
					}
					++j;
				}
				
				//Remet les noeuds qu'on a visite dans la 2eme boucle a l'etat non vus
				for(int count = 0; count<noeudsUpdate.size(); ++count) {
					ordreNoeuds.replace( noeudsUpdate.get(count), -1 );
				}
				++i;
			}
		}
	}
	
	/**
	 * Methode qui fait le swap de 2 noeuds et des chemins
	 * qui sont entre eux d'un cote
	 * @param i - l'index de debut du swap
	 * @param j - l'index de fin du swap
	 * @param chemin - le premier chemin du swap
	 * @param chemin2 - le dernier chemin du swap
	 * @param noeudsUpdate - list des noeuds qu'on devra change
	 * @param ordreNoeuds - map avec la map contenant l'ordre de passage de chaque noeud
	 * @param tournee - objet aui va representer la tournee finale
	 * @param plusCourtsChemins - map des plus courts chemins calcules
	 * 
	 */
	private void twoOptSwap(int i, int j, Chemin chemin, Chemin chemin2, ArrayList<String> noeudsUpdate, HashMap<String, Integer> ordreNoeuds, Tournee tournee, Map<String, Map<String, Chemin>> plusCourtsChemins ) {
		//On mets les chemins compris entre i et j dans l'ordre inverse de parcours pour connecter la tournee
		//On met aussi les 2 chemins avec les noeuds extremes inter-changes
		List<Chemin> listeTmp = new ArrayList<Chemin>();
		for(int count = 0; count < noeudsUpdate.size()-1; ++count) {
			int ascending = i+count+1;
			listeTmp.add(tournee.getPlusCourteTournee().get(ascending));
		}
		for(int count = 0; count< noeudsUpdate.size()-1; ++count) {
			int ascending = i+count+1;
			int descending = i+noeudsUpdate.size()-count-1;
			String premiere = listeTmp.get(count).getPremiere().getId();
			String derniere = listeTmp.get(count).getDerniere().getId();
			Chemin newChemin = plusCourtsChemins.get(derniere).get(premiere);
			tournee.getPlusCourteTournee().set(descending, newChemin);
			ordreNoeuds.replace(derniere, ascending);
			System.out.print(count+"."+tournee.getPlusCourteTournee().get(ascending).getPremiere().getId()+"->"+tournee.getPlusCourteTournee().get(ascending).getDerniere().getId()+" / ");
		}
		Chemin newChemin1 = plusCourtsChemins.get(chemin.getPremiere().getId()).get(chemin2.getPremiere().getId());
		Chemin newChemin2 = plusCourtsChemins.get(chemin.getDerniere().getId()).get(chemin2.getDerniere().getId());
		tournee.getPlusCourteTournee().set(i, newChemin1);
		tournee.getPlusCourteTournee().set(j, newChemin2);
		ordreNoeuds.replace(chemin.getDerniere().getId(), j);
	}
	
	/**
	 * Methode qui initialise la map VuDispo
	 * @param contraintes - la liste des points de livraison et d'enlevement
	 * @param intersections - map avec l'id et les intersections qu'on a a visiter
	 * 
	 */
	private HashMap<String, Paire> initVuDispo(ContraintesTournee contraintes, HashMap<String, Intersection> intersections){
		HashMap<String, Paire> vuDispo = new HashMap<String, Paire>();
		for (HashMap.Entry<String, Intersection> iterator : intersections.entrySet()) {
		    if( iterator.getValue() instanceof PointEnlevement ) {
		    	vuDispo.put( iterator.getKey(), new Paire(true, false) );
		    }else {
		    	vuDispo.put( iterator.getKey(), new Paire(false, false) );
		    }
		}
		
		//Mettre premier noeud comme deja visite - ici le premier est l'entrepot
		vuDispo.get(contraintes.getDepot().getId()).setDispo(false);
		vuDispo.get(contraintes.getDepot().getId()).setVu(true);
		
		return vuDispo;
	}
	
	/**
	 * Cree un iterateur pour iterer sur l'ensemble des sommets de nonVus
	 * type - prochain neouds dans l'ordre de la liste initiale
	 * @param restants - nb de noeuds restants a visiter
	 * @param intersections - map avec l'id et les intersections qu'on a a visiter
	 * @param vuDispo - map de id et objet=(vu, dispo)
	 * @param plusCourtsChemins - map des plus courts chemins calcules
	 */
	protected abstract Iterator<String> iterator(int restants, HashMap<String, Intersection> intersections, HashMap<String, Paire> vuDispo, Map<String, Map<String, Chemin>> plusCourtsChemins);
	
}