package algo;

import java.util.LinkedList;

import model.Chemin;

public interface TSP {
	public boolean chercherSolution();
	public LinkedList<Chemin> getSolution();
	public int getCoutSolution();
}
