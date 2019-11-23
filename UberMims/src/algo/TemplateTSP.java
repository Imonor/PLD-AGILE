package algo;

import java.util.LinkedList;

import model.Chemin;

public abstract class TemplateTSP implements TSP{

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
