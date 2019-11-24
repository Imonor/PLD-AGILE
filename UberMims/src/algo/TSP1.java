package algo;

import java.util.ArrayList;
import java.util.List;

import model.Chemin;
import model.ContraintesTournee;

public class TSP1 extends TemplateTSP{

	//ContraintesTournee - juste pour test
	//Constructeur sans parametres normalement
	public TSP1(ContraintesTournee contraintes) {
		super(contraintes);
		// TODO Auto-generated constructor stub
	}

	//Bound - premiere approche : 0
	@Override
	public int bound() {
		return 0;
	}

	@Override
	public Chemin iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
