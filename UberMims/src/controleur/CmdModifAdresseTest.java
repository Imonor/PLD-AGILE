package controleur;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Intersection;
import model.PointEnlevement;
import model.PointLivraison;
import model.Tournee;
import util.ExceptionChargement;


public class CmdModifAdresseTest {

	@Test
	public void testerModifAdresseEnlevementNormal() {
		
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
		
		PointEnlevement enlevARemplacer = controleur.getContraintes().getPointsEnlevement().get(2);
		PointLivraison saLivraison = controleur.getContraintes().getPointsLivraison().get(2);
		
		Intersection newInter = new Intersection("55474924", 45.75965, 4.882271);
		
		System.out.println("middle");
		
		int posEstDepart = -1;
		int posEstArrivee = -1;
		for(int count = 0; count< controleur.getTournee().getPlusCourteTournee().size(); ++count) {
			if( controleur.getTournee().getPlusCourteTournee().get(count).getPremiere().getId().equals(enlevARemplacer.getId()) ) {
				posEstDepart = count;
			}
			if( controleur.getTournee().getPlusCourteTournee().get(count).getDerniere().getId().equals(enlevARemplacer.getId()) ) {
				posEstArrivee = count;
			}
		}
		
		System.out.println("middle1");
		
		controleur.modifierAdresse( enlevARemplacer, newInter);
		
		System.out.println("middle2");
		
		System.out.println("depart="+controleur.getTournee().getPlusCourteTournee().get(posEstDepart).getPremiere().getId());
		System.out.println("arriv="+controleur.getTournee().getPlusCourteTournee().get(posEstArrivee).getDerniere().getId());	
		
		assertEquals("55474924", controleur.getTournee().getPlusCourteTournee().get(posEstDepart).getPremiere().getId());	
		assertEquals("55474924", controleur.getTournee().getPlusCourteTournee().get(posEstArrivee).getDerniere().getId());	
		
		System.out.println("fin");
	}
	
	
	@Test
	public void testerModifAdresseLivraisonNormal() {
		
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
		
		PointLivraison livraisonARemplacer = controleur.getContraintes().getPointsLivraison().get(2);
		PointEnlevement sonEnlev = controleur.getContraintes().getPointsEnlevement().get(2);
		
		Intersection newInter = new Intersection("55474924", 45.75965, 4.882271);
		
		System.out.println("middle");
		
		int posEstDepart = -1;
		int posEstArrivee = -1;
		for(int count = 0; count< controleur.getTournee().getPlusCourteTournee().size(); ++count) {
			if( controleur.getTournee().getPlusCourteTournee().get(count).getPremiere().getId().equals(livraisonARemplacer.getId()) ) {
				posEstDepart = count;
			}
			if( controleur.getTournee().getPlusCourteTournee().get(count).getDerniere().getId().equals(livraisonARemplacer.getId()) ) {
				posEstArrivee = count;
			}
		}
		
		System.out.println("middle1");
		
		controleur.modifierAdresse( livraisonARemplacer, newInter);
		
		System.out.println("middle2");
		
		System.out.println("depart="+controleur.getTournee().getPlusCourteTournee().get(posEstDepart).getPremiere().getId());
		System.out.println("arriv="+controleur.getTournee().getPlusCourteTournee().get(posEstArrivee).getDerniere().getId());	
		
		assertEquals("55474924", controleur.getTournee().getPlusCourteTournee().get(posEstDepart).getPremiere().getId());	
		assertEquals("55474924", controleur.getTournee().getPlusCourteTournee().get(posEstArrivee).getDerniere().getId());	
		
		System.out.println("fin");
	}
	
	
	@Test
	public void testerModifAdresseEnlevementSeul() {
		
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
		controleur.calculerTournee();
		
		PointEnlevement enlevARemplacer = controleur.getContraintes().getPointsEnlevement().get(2);
		PointLivraison saLivraison = controleur.getContraintes().getPointsLivraison().get(2);
		
		Intersection newInter = new Intersection("55474924", 45.75965, 4.882271);
		
		System.out.println("middle");
		
		int posEstDepart = -1;
		int posEstArrivee = -1;
		for(int count = 0; count< controleur.getTournee().getPlusCourteTournee().size(); ++count) {
			if( controleur.getTournee().getPlusCourteTournee().get(count).getPremiere().getId().equals(enlevARemplacer.getId()) ) {
				posEstDepart = count;
			}
			if( controleur.getTournee().getPlusCourteTournee().get(count).getDerniere().getId().equals(enlevARemplacer.getId()) ) {
				posEstArrivee = count;
			}
		}
		

		System.out.println("middle1");
		
		controleur.modifierAdresse( enlevARemplacer, newInter);
		
		System.out.println("middle2");
		
		System.out.println("depart="+controleur.getTournee().getPlusCourteTournee().get(posEstDepart).getPremiere().getId());
		System.out.println("arriv="+controleur.getTournee().getPlusCourteTournee().get(posEstArrivee).getDerniere().getId());	
		
		assertEquals("55474924", controleur.getTournee().getPlusCourteTournee().get(posEstDepart).getPremiere().getId());	
		assertEquals("55474924", controleur.getTournee().getPlusCourteTournee().get(posEstArrivee).getDerniere().getId());	
		
		System.out.println("fin");
	}
	
	
	@Test
	public void testerModifAdresseLivraisonSeul() {
		
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
		controleur.calculerTournee();
		
		PointLivraison livraisonARemplacer = controleur.getContraintes().getPointsLivraison().get(2);
		PointEnlevement sonEnlev = controleur.getContraintes().getPointsEnlevement().get(2);
		
		Intersection newInter = new Intersection("55474924", 45.75965, 4.882271);
		
		System.out.println("middle");
		
		int posEstDepart = -1;
		int posEstArrivee = -1;
		for(int count = 0; count< controleur.getTournee().getPlusCourteTournee().size(); ++count) {
			if( controleur.getTournee().getPlusCourteTournee().get(count).getPremiere().getId().equals(livraisonARemplacer.getId()) ) {
				posEstDepart = count;
			}
			if( controleur.getTournee().getPlusCourteTournee().get(count).getDerniere().getId().equals(livraisonARemplacer.getId()) ) {
				posEstArrivee = count;
			}
		}
		

		System.out.println("middle1");
		
		controleur.modifierAdresse( livraisonARemplacer, newInter);
		
		System.out.println("middle2");
		
		System.out.println("depart="+controleur.getTournee().getPlusCourteTournee().get(posEstDepart).getPremiere().getId());
		System.out.println("arriv="+controleur.getTournee().getPlusCourteTournee().get(posEstArrivee).getDerniere().getId());	
		
		assertEquals("55474924", controleur.getTournee().getPlusCourteTournee().get(posEstDepart).getPremiere().getId());	
		assertEquals("55474924", controleur.getTournee().getPlusCourteTournee().get(posEstArrivee).getDerniere().getId());	
		
		System.out.println("fin");
	}
	
}
