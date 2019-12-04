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
		
		tempsLimiteAtteint = false;
//		coutMeilleureSolution = Integer.MAX_VALUE;
//		meilleureSolution = new String[nbSommets];

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
		
		//Calcul des couts des combinaisons des noeuds
		HashMap<String, Integer> couts = new HashMap<String, Integer>();
		for (Map.Entry<String, Intersection> entry : intersections.entrySet()) {
			for (Map.Entry<String, Intersection> entry2 : intersections.entrySet()) {
				String idid = ((String)entry.getKey()).concat(entry2.getKey());
				int duree;
				if(entry.getKey().equals(entry2.getKey())) {
					duree = Integer.MAX_VALUE;
				}else {
					duree = plusCourtsChemins.get(entry.getKey()).get(entry2.getKey()).getDuree();
				}
				couts.put(idid, duree);
			}
		}
		
		//Initialisation de la HashMap vuDispo - contenant les attributs boolean vu et dispo
		HashMap<String, Paire> vuDispo = initVuDispo(contraintes, intersections);
		
		Tournee tournee = new Tournee();
		//Sequentiel et MinFirst
//		calculerSimplementTournee(tournee, contraintes.getDepot().getId(), (nbSommets-1), intersections, vuDispo, plusCourtsChemins, couts);		
		
		//MinFirst + 2-Opt
		calculerSimplementTournee(tournee, contraintes.getDepot().getId(), (nbSommets-1), intersections, vuDispo, plusCourtsChemins, couts);		
		twoOpt(tpsLimite, System.currentTimeMillis(), tournee, plusCourtsChemins, intersections, couts);
		
		return tournee;
	}
	
	
	protected void calculerSimplementTournee(Tournee tournee, String first, int restants, HashMap<String, Intersection> intersections, HashMap<String, Paire> vuDispo, Map<String, Map<String, Chemin>> plusCourtsChemins, HashMap<String, Integer> couts) {
		
		Iterator<String> it = iterator(restants, intersections, vuDispo, plusCourtsChemins, couts);
		String noeudPreced = null;

		while(it.hasNext()) {
			String noeudSuivant = it.next();
			System.out.println("noeudSuiv=" + noeudSuivant);
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
	
	protected void twoOpt(int tpsLimite, long tpsDebut, Tournee tournee, Map<String, Map<String, Chemin>> plusCourtsChemins, HashMap<String, Intersection> intersections, HashMap<String, Integer> couts) {
		
		Iterator<Chemin> it = tournee.getPlusCourteTournee().iterator();
		Iterator<Chemin> it2 = tournee.getPlusCourteTournee().iterator();
		int nbChemins = tournee.getPlusCourteTournee().size();
		tempsLimiteAtteint = false;
		Tournee tourneeReponse = new Tournee(tournee.getPlusCourteTournee(), tournee.getContraintes());
		
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
			String depart1 = chemin.getPremiere().getId();
			String fin1 = chemin.getDerniere().getId();
			ordreNoeuds.replace(chemin.getPremiere().getId(), i);
			
			ArrayList<String> noeudsUpdate = new ArrayList<String>();
			
			int j = 0 ;
			while(it2.hasNext() && j<nbChemins) {
				Chemin chemin2 = it2.next();
				ordreNoeuds.replace(chemin2.getPremiere().getId(), j);
				noeudsUpdate.add(chemin2.getPremiere().getId());
				
				//Pour ne pas depasser le temps limite
				if (System.currentTimeMillis() - tpsDebut > tpsLimite){
					 tempsLimiteAtteint = true;
					 return;
				}
				
				if(j>i+2) {
					String depart2 = chemin2.getPremiere().getId();
					String fin2 = chemin2.getDerniere().getId();
					int dureeIni = chemin.getDuree() + chemin2.getDuree();
					
					//On met l'ordre inverse aux noeuds qu'on va inverser pour tester les precedences
					for(int count = 0; count<noeudsUpdate.size(); ++count) {
						ordreNoeuds.replace(noeudsUpdate.get(count), j+(noeudsUpdate.size()-count));
					}

					//Pour tous les chemins qu'on va mettre dans le sens inverse, on s'assure
					//que pour tous les points de livraison, leur pt d'enlevement est vu avant
					//ou au noeds de depart du premier chemin du swap
					boolean possible = true;
					for(int count = noeudsUpdate.size()-1; count>0; --count) {
						if( intersections.get(noeudsUpdate.get(count)) instanceof PointLivraison ) {
							String sonPtEnlev = ((PointLivraison)intersections.get(noeudsUpdate.get(count))).getIdEnlevement();
							if( ordreNoeuds.get(sonPtEnlev) < ordreNoeuds.get(noeudsUpdate.get(count)) ) {
								possible = false;
							}
						}
					}
					
					//Calculer la duree du nouvel ensemble de chemins inverses
					int newDuree = 0;
					for(int count = noeudsUpdate.size(); count>1; ++count) {
						newDuree += couts.get(noeudsUpdate.get(count)+noeudsUpdate.get(count-1));
					}
					newDuree = couts.get(noeudsUpdate.get(i)+noeudsUpdate.get(0));
					newDuree = couts.get(noeudsUpdate.get(noeudsUpdate.size())+noeudsUpdate.get(chemin2.getDerniere().getId()));
					
					
					//Il faut calculer le couts de l'ensemble de chemins recalcules et le comparer a celui existant
//					if( (couts.get(depart1+depart2) + couts.get(fin1+fin2)) < dureeIni && enlevVu) {
//						//Trouver les chemins extremes + on met les 2 chemins extremes dans la tournee dans l'ordre change
//						Chemin newChemin1 = plusCourtsChemins.get(chemin.getPremiere().getId()).get(chemin2.getPremiere().getId());
//						Chemin newChemin2 = plusCourtsChemins.get(chemin.getDerniere().getId()).get(chemin2.getDerniere().getId());
//						tournee.getPlusCourteTournee().set(index1, newChemin1);
//						tournee.getPlusCourteTournee().set(index2, newChemin2);
//						
//						tournee = twoOptSwap(index1, index2, tournee, plusCourtsChemins);
//					}
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
	
	private Tournee twoOptSwap(int index1, int index2, Tournee tournee, Map<String, Map<String, Chemin>> plusCourtsChemins ) {
		
		//On mets les chemins compris entre index1 et index2 dans l'ordre inverse de parcours pour connecter la tournee
		Tournee reponse = new Tournee(tournee.getPlusCourteTournee(), tournee.getContraintes());
		int j = index1+1;
		for(int i = index2-1; i>index1; --i) {
			Chemin cheminTmp = tournee.getPlusCourteTournee().get(i);
			Chemin newChemin = plusCourtsChemins.get(cheminTmp.getDerniere().getId()).get(cheminTmp.getPremiere().getId());
			reponse.getPlusCourteTournee().set(j, newChemin);
			++j;
		}
		
		return reponse;
	}
	
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
	protected abstract Iterator<String> iterator(int restants, HashMap<String, Intersection> intersections, HashMap<String, Paire> vuDispo, Map<String, Map<String, Chemin>> plusCourtsChemins, HashMap<String, Integer> couts);
	
}
