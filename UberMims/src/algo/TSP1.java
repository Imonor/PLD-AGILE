package algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;

import model.Chemin;
import model.ContraintesTournee;
import model.Intersection;

public class TSP1 extends TemplateTSP{

	public TSP1() {
		super();
	}
	
	@Override
	protected Iterator<String> iterator(int restants, HashMap<String, Intersection> intersections, HashMap<String, Paire> vuDispo) {
		return new IteratorSeq(restants, intersections, vuDispo);
	}

	@Override
	protected int bound() {
		return 0;
	}

	@Override
	public void chercheSolution(int tpsLimite, int nbSommets, int[][] cout, int[] duree) {
		// TODO Auto-generated method stub
		
	}

}
