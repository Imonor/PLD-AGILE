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
	
	LinkedList<Triplet> dependences = new LinkedList<Triplet>();
	
	//ContraintesTournee - juste pour test
	//Constructeur sans parametres normalement
	public TemplateTSP(ContraintesTournee contraintes) {
		
		//A changer ici - prendre les contraintes depuis la classe singleton
		Iterator<Precedence> it = (Iterator<Precedence>) contraintes.getContraintes().iterator();
		while(it.hasNext()) {
			
			//Initialisation liste dependences
			Precedence p = (Precedence) it.next();
			boolean found = false;
			Iterator<Triplet> itDepend = (Iterator<Triplet>) dependences.iterator();
			while(itDepend.hasNext()&&!found) {
				if( ((Triplet) itDepend.next()).getIntersection() == p.getPointAvant()){
					found = true;
				}
			}
			if (!found) {
				dependences.add(new Triplet(p.getPointAvant(), new ArrayList<>(), false));
			}else {
				((Triplet) itDepend.next()).getPredecesseurs().add(p.getPointApres());
			}
			
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
