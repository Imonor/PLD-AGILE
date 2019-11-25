package algo;

import java.util.ArrayList;
import java.util.List;

import model.Chemin;
import model.ContraintesTournee;

public class TSP1 extends TemplateTSP{

	public TSP1(ContraintesTournee contraintes) {
		super(contraintes);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected Iterator<Integer> iterator(Integer sommetCrt, ArrayList<Integer> nonVus, int[][] cout, int[] duree) {
		return new IteratorSeq(nonVus, sommetCrt);
	}

	@Override
	protected int bound(Integer sommetCourant, ArrayList<Integer> nonVus, int[][] cout, int[] duree) {
		return 0;
	}

}
