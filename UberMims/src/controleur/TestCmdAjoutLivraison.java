package controleur;

import model.ContraintesTournee;
import model.Intersection;
import model.Livraison;
import model.Plan;
import model.Precedence;
import util.XMLParser;

public class TestCmdAjoutLivraison {
	public static void main(String[] args) {
		Plan plan = XMLParser.chargerPlan("fichiersXML2019/grandPlan.xml");
		ContraintesTournee tournee = XMLParser.chargerContraintesTournee("fichiersXML2019/demandePetit1.xml", plan);
		for(Precedence prec: tournee.getContraintes()) {
			System.out.println(prec.getPointAvant().getId() + " --> " + prec.getPointApres().getId());
		}
		Livraison l = new Livraison(new Intersection("1", 123, 456), new Intersection("2", 456, 123), 12, 5);
		CmdAjoutLivraison cmd = new CmdAjoutLivraison(tournee, l);
		cmd.doCode();
		System.out.println("\n\n");
		for(Precedence prec: tournee.getContraintes()) {
			System.out.println(prec.getPointAvant().getId() + " --> " + prec.getPointApres().getId());
		}
		cmd.undoCode();
		System.out.println("\n\n");
		for(Precedence prec: tournee.getContraintes()) {
			System.out.println(prec.getPointAvant().getId() + " --> " + prec.getPointApres().getId());
		}
	}

}
