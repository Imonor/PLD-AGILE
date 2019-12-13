package controleur;

import model.PointEnlevement;
import model.PointLivraison;

public class CmdModifTemps implements Commande {
	
	private PointEnlevement enlevement;
	private PointLivraison livraison;
	private int newDuree, oldDuree;

	public CmdModifTemps (PointEnlevement e, int duree) {
		this.enlevement=e;
		this.livraison =null;
		this.newDuree=duree;
		this.oldDuree=e.getTempsEnlevement();
	}
	public CmdModifTemps (PointLivraison l, int duree) {
		this.enlevement=null;
		this.livraison =l;
		this.newDuree=duree;
		this.oldDuree=l.getTempsLivraison();
	}
	
	@Override
	public void doCode() {
		if(enlevement!=null) {
			enlevement.setTempsEnlevement(newDuree);
		} else {
			livraison.setTempsLivraison(newDuree);
		}
	}

	@Override
	public void undoCode() {
		if(enlevement!=null) {
			enlevement.setTempsEnlevement(oldDuree);
		} else {
			livraison.setTempsLivraison(oldDuree);
		}
	}
	
}
