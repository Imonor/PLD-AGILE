package controleur;

import model.ContraintesTournee;
import model.PointEnlevement;
import model.PointLivraison;

public class CmdAjoutLivraison implements Commande {
	
	private ContraintesTournee tournee;
	private PointEnlevement enlevement;
	private PointLivraison livraison;
	
	public CmdAjoutLivraison(ContraintesTournee t, PointEnlevement e, PointLivraison l) {
		this.tournee = t;
		this.enlevement = e;
		this.livraison = l;
	}

	@Override
	public void doCode() {
		tournee.addLivraison(enlevement, livraison);
	}

	@Override
	public void undoCode() {
		tournee.removeLivraison(enlevement, livraison);
	}
}
