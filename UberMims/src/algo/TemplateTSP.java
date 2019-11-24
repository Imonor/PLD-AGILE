package algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import model.Chemin;
import model.Intersection;
import model.Precedence;
import model.ContraintesTournee;

public abstract class TemplateTSP implements TSP{
	
	HashMap<Intersection, List<Intersection>> predecesseurs = new HashMap<Intersection, List<Intersection>>();
	
	//ContraintesTournee - juste pour test
	//Constructeur sans parametres normalement
	public TemplateTSP(ContraintesTournee contraintes) {
		
		//A changer ici - prendre les contraintes depuis la classe singleton
		Iterator<Precedence> it = (Iterator<Precedence>) contraintes.getContraintes().iterator();
		while(it.hasNext()) {
			Precedence p = it.next();
			if (!predecesseurs.containsKey(p.getPointAvant())) {
				predecesseurs.put(p.getPointAvant(), new ArrayList<>());
			}
			predecesseurs.get(p.getPointAvant()).add(p.getPointApres());
		}
		
	}
	
	
	@Override
	public boolean chercherSolution() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LinkedList<Chemin> getSolution() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCoutSolution() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public abstract int bound();
	
	public abstract Chemin iterator();

}
