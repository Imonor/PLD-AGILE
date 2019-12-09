package algo;

import model.*;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

public class Secours {
	
	public static Tournee calculTourneeBete (ContraintesTournee contraintes, Map<String, Map<String, Chemin>> plusCourtsChemins) {
		Tournee tournee = new Tournee(contraintes);
		int tempsEnlevement=0, tempsLivraison = 0;
		LinkedList<String> dispos = new LinkedList<String>();
		HashMap<String, String> indispos = new HashMap<String,String>();
		LinkedList<String> vus = new LinkedList<String>();
		
		for(PointEnlevement enlevement : contraintes.getPointsEnlevement()) {
			dispos.add(enlevement.getId());
			tempsEnlevement+=enlevement.getTempsEnlevement();
		}
		for(PointLivraison livraison : contraintes.getPointsLivraison()) {
			indispos.put(livraison.getIdEnlevement(),livraison.getId());
			tempsLivraison+=livraison.getTempsLivraison();
		}
		vus.add(contraintes.getDepot().getId());
		//Calcul de l'ordre de passage
		while(!dispos.isEmpty()) {
			String current = dispos.poll();
			vus.add(current);
			if(indispos.containsKey(current)) {
				dispos.add(indispos.get(current));
				indispos.remove(current);
			}
		}
		
		vus.add(contraintes.getDepot().getId());
		
		//Contruction de la tournee
		String last = vus.poll();
		while(!vus.isEmpty()) {
			String current = vus.poll();
			tournee.addChemin(plusCourtsChemins.get(last).get(current));
			last = current;
		}
		
		tournee.calculDuree(tempsLivraison+tempsEnlevement);
		
		return tournee;
	}

}
