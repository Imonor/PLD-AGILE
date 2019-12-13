package controleur;

import java.util.LinkedList;

public class CmdListe {

	private Commande liste[];
	private int i;
	private int iLast;
	
	public CmdListe () {
		liste = new Commande[20];
		i = -1;
		iLast = -1;
	}
	
	public boolean isEmpty() {
		return (i < 0);
	}
	
	public boolean redoPossible() {
		return i < iLast;
	}
	
	public void addCommande(Commande commande) {
		liste[++i] = commande;
		iLast = i;
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
