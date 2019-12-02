package controleur;

import model.ContraintesTournee;
import model.Intersection;
import model.Plan;
import model.PointEnlevement;
import model.PointLivraison;
import util.XMLParser;

public class TestCmdAjoutLivraison {
	public static void main(String[] args) {
		Plan plan = XMLParser.chargerPlan("fichiersXML2019/petitPlan.xml", 0, 0);
		ContraintesTournee tournee = XMLParser.chargerContraintesTournee("fichiersXML2019/demandePetit1.xml", plan);
		for(PointEnlevement e: tournee.getPointsEnlevement()) {
			System.out.println(e.getId() + " --> " + e.getIdLivraison());
		}
		PointEnlevement e1 = new PointEnlevement("1", 123, 456, "2", 20);
		PointLivraison l1 = new PointLivraison("2", 456, 123, "1", 5);
		CmdAjoutLivraison cmd = new CmdAjoutLivraison(tournee, e1, l1);
		cmd.doCode();
		System.out.println("\n\n");
		for(PointEnlevement e: tournee.getPointsEnlevement()) {
			System.out.println(e.getId() + " --> " + e.getIdLivraison());
		}
		cmd.undoCode();
		System.out.println("\n\n");
		for(PointEnlevement e: tournee.getPointsEnlevement()) {
			System.out.println(e.getId() + " --> " + e.getIdLivraison());
		}
	}

}
