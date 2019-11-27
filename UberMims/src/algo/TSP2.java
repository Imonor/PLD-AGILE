package algo;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import model.Chemin;
import model.ContraintesTournee;
import model.Intersection;
import model.Tournee;

public class TSP2 extends TemplateTSP{

	public TSP2() {
		super();
	}
	
	@Override
	protected Iterator<String> iterator(int restants, HashMap<String, Intersection> intersections, HashMap<String, Paire> vuDispo, Map<String, Map<String, Chemin>> plusCourtsChemins, HashMap<String, Integer> couts) {
		return new IteratorMinFirst(restants, intersections, vuDispo, couts);
	}

	@Override
	protected int bound() {
		return 0;
	}

	@Override
	public Tournee chercheSolution(int tpsLimite, ContraintesTournee contraintes, Map<String, Map<String, Chemin>> plusCourtsChemins) {
		// TODO Auto-generated method stub
		return super.chercheSolution(tpsLimite, contraintes, plusCourtsChemins);
	}


}
