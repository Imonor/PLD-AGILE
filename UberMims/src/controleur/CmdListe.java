package controleur;

import java.util.LinkedList;

public class CmdListe {

	private Commande liste[];
	private int i;
	
	public CmdListe () {
		liste = new Commande[20];
		i = -1;
	}
	
	public boolean isEmpty() {
		return (i < 0);
	}
	
	public void addCommande(Commande commande) {
		liste[++i] = commande;
		commande.doCode();
	}
	
	public void undo() {
		if(i>=0)
			liste[i--].undoCode();
	}
	public void redo() {
		if(i<20)
			liste[++i].doCode();
	}	
}
