package controleur;

import java.util.LinkedList;

public class CmdListe {

	private LinkedList<Commande>liste;
	private int i;
	
	public CmdListe () {
		liste = new LinkedList<Commande>();
		i = -1;
	}
	
	public void addCommande(Commande commande) {
		liste.add(commande);
		i = liste.size()-1;
		commande.doCode();
	}
	
	public void undo() {
		if(i>=0)
			liste.get(i--).undoCode();
	}
	public void redo() {
		if(i<liste.size()-1)
			liste.get(++i).doCode();
	}	
}
