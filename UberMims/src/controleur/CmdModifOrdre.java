package controleur;

import model.ContraintesTournee;
import model.Precedence;

public class CmdModifOrdre implements Commande {
	
	private ContraintesTournee tournee;
	private Precedence precedence;
	
	public CmdModifOrdre(ContraintesTournee t, Precedence p) {
		this.tournee = t;
		this.precedence = p;
	}

	@Override
	public void doCode() {
		tournee.addPrecedence(precedence);
	}

	@Override
	public void undoCode() {
		tournee.removePrecedence(precedence);
	}

}
