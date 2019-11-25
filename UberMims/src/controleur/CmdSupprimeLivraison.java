//package controleur;
//
//import model.ContraintesTournee;
//import model.Livraison;
//
//public class CmdSupprimeLivraison implements Commande {
//	
//	private ContraintesTournee tournee;
//	private Livraison livraison;
//	
//	public CmdSupprimeLivraison(ContraintesTournee t, Livraison l) {
//		this.tournee = t;
//		this.livraison = l;
//	}
//	@Override
//	public void doCode() {
//		tournee.removePrecedence(livraison);
//	}
//
//	@Override
//	public void undoCode() {
//		tournee.addPrecedence(livraison);
//	}
//
//}
