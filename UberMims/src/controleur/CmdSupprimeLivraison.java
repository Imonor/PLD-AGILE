package controleur;

import model.ContraintesTournee;
import model.PointEnlevement;
import model.PointLivraison;

public class CmdSupprimeLivraison implements Commande {
	
	private ContraintesTournee tournee;
	private PointEnlevement enlevement;
	private PointLivraison livraison;
	
	public CmdSupprimeLivraison(ContraintesTournee t, PointEnlevement e, PointLivraison l) {
		this.tournee = t;
		this.enlevement = e;
		this.livraison = l;
	}
	
	public CmdSupprimeLivraison(ContraintesTournee t, PointEnlevement e) {
		this.tournee = t;
		this.enlevement = e;
		for(PointLivraison l : t.getPointsLivraison())
		{
			if(l.getId() == e.getIdLivraison()) 
				this.livraison = l;
		}
				
	}

	public CmdSupprimeLivraison(ContraintesTournee t, PointLivraison l) {
		this.tournee = t;
		this.livraison = l;
		for(PointEnlevement e : t.getPointsEnlevement())
		{
			if(e.getId() == l.getIdEnlevement()) 
				this.enlevement = e;
		}
	}

	@Override
	public void doCode() {
		tournee.removeLivraison(enlevement, livraison);
	}

	@Override
	public void undoCode() {
		tournee.addLivraison(enlevement, livraison);
	}
}
