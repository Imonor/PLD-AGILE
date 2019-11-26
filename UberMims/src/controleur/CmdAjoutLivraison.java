//package controleur;
//
//import model.ContraintesTournee;
//import model.Livraison;
//
//public class CmdAjoutLivraison implements Commande {
//	
//	private ContraintesTournee tournee;
//	private Livraison livraison;
//	
//	public CmdAjoutLivraison(ContraintesTournee t, Livraison l) {
//		this.tournee = t;
//		this.livraison = l;
//	}
//
//	@Override
//	public void doCode() {
//		tournee.addPrecedence(livraison);
//	}
//
//	@Override
//	public void undoCode() {
//		tournee.removePrecedence(livraison);
//	}
//
//}
