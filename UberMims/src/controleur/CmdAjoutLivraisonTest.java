package controleur;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Intersection;
import model.PointEnlevement;
import model.PointLivraison;
import model.Tournee;
import util.ExceptionChargement;

/**
 * Classe qui teste la classe CmdAjoutLivraison 
 */
public class CmdAjoutLivraisonTest {

	/**
	 * Methode qui test l'ajout d'une livraison dans une tournee avec
	 * une taille normale
	 */
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
	
}
