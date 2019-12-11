package controleur;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Chemin;
import model.ContraintesTournee;
import model.Intersection;
import model.PointEnlevement;
import model.PointLivraison;
import model.Tournee;
import util.ExceptionChargement;

public class CmdAjoutLivraisonTest {

	@Test
	public void testerAjoutLivraisonNormal() {
		
		Controleur controleur = new Controleur();
		
		try {
			controleur.chargerPlan("./fichiersXML2019/moyenPlan.xml",1000,1000);
		} catch (ExceptionChargement e) {
			e.printStackTrace();
		}
		
		try {
			controleur.chargerTournee("./fichiersXML2019/demandeMoyen5.xml");
		} catch (ExceptionChargement e) {
			e.printStackTrace();
		}
		
		controleur.calculerTournee();
		Tournee tournee = controleur.getTournee();
		
		Intersection inter1 = new Intersection("55474924", 45.75965, 4.882271);
		Intersection inter2 = new Intersection("60491706", 45.76259, 4.8819356);
		
		PointEnlevement ptEnlev = new PointEnlevement(inter1, "60491706", 10);
		PointLivraison ptLiv = new PointLivraison(inter2, "55474924", 10);

		controleur.ajouterLivraison(ptEnlev, ptLiv);
		
		System.out.print( tournee.getPlusCourteTournee().get(tournee.getPlusCourteTournee().size()-2).getPremiere().getId() );
		System.out.print( tournee.getPlusCourteTournee().get(tournee.getPlusCourteTournee().size()-2).getDerniere().getId() );
		
		assertEquals( ptEnlev.getId(), tournee.getPlusCourteTournee().get(tournee.getPlusCourteTournee().size()-2).getPremiere().getId());
		assertEquals( ptLiv.getId(), tournee.getPlusCourteTournee().get(tournee.getPlusCourteTournee().size()-2).getDerniere().getId());	
		
		System.out.println("end");
	}
	
	
	@Test
	public void testerAjoutLivraisonSurListeVide() {
		System.out.println("debut");
		Controleur controleur = new Controleur();
		
		try {
			controleur.chargerPlan("./fichiersXML2019/moyenPlan.xml",1000,1000);
		} catch (ExceptionChargement e) {
			e.printStackTrace();
		}
		
		try {
			controleur.chargerTournee("./fichiersXML2019/demandeMoyen1.xml");
		} catch (ExceptionChargement e) {
			e.printStackTrace();
		}

		for(int count = 0; count< controleur.getContraintes().getPointsEnlevement().size(); ++count ) {
			System.out.println("countEnlev"+count+": "+controleur.getContraintes().getPointsEnlevement().get(count).getId());
		}
		
		Intersection interTmp1 = new Intersection("1400900990", 45.742126, 4.8623857);
		Intersection interTmp2 = new Intersection("208769083", 45.75947, 4.870945);
		PointEnlevement enlevASupp = new PointEnlevement(interTmp1, "208769083", 10);
		PointLivraison livASupp = new PointLivraison(interTmp2, "1400900990", 10);
		controleur.supprimerLivraison(enlevASupp, livASupp);
		
		for(int count = 0; count< controleur.getContraintes().getPointsEnlevement().size(); ++count ) {
			System.out.println("EnlevRestant"+count+": "+controleur.getContraintes().getPointsEnlevement().get(count).getId());
		}
		
		System.out.println("fin init");
		
		Intersection inter1 = new Intersection("55474924", 45.75965, 4.882271);
		Intersection inter2 = new Intersection("60491706", 45.76259, 4.8819356);
		PointEnlevement ptEnlev = new PointEnlevement(inter1, "60491706", 10);
		PointLivraison ptLiv = new PointLivraison(inter2, "55474924", 10);
		
		System.out.println("middle");

		Tournee tournee = controleur.getTournee();

		System.out.println("get tournee");
		
		controleur.ajouterLivraison(ptEnlev, ptLiv);
		
		System.out.println("ajout ici");
		
		for(int count = 0; count< controleur.getContraintes().getPointsEnlevement().size(); ++count ) {
			System.out.println("EnlevRestant"+count+": "+controleur.getContraintes().getPointsEnlevement().get(count).getId());
		}
		
		System.out.println("apres ajout");
		
//		System.out.print( tournee.getPlusCourteTournee().get(tournee.getPlusCourteTournee().size()-2).getPremiere().getId() );
//		System.out.print( tournee.getPlusCourteTournee().get(tournee.getPlusCourteTournee().size()-2).getDerniere().getId() );
//		
//		assertEquals( ptEnlev.getId(), tournee.getPlusCourteTournee().get(tournee.getPlusCourteTournee().size()-2).getPremiere().getId());
//		assertEquals( ptLiv.getId(), tournee.getPlusCourteTournee().get(tournee.getPlusCourteTournee().size()-2).getDerniere().getId());	
	}
	
}
