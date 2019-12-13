package controleur;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Intersection;
import model.PointEnlevement;
import model.PointLivraison;
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
		
		Intersection newInter = new Intersection("55474924", 45.75965, 4.882271);
		
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
		
		controleur.modifierAdresse( enlevARemplacer, newInter);
		
		assertEquals("55474924", controleur.getTournee().getPlusCourteTournee().get(posEstDepart).getPremiere().getId());	
		assertEquals("55474924", controleur.getTournee().getPlusCourteTournee().get(posEstArrivee).getDerniere().getId());	
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
		
		Intersection newInter = new Intersection("55474924", 45.75965, 4.882271);
		
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
		
		controleur.modifierAdresse( livraisonARemplacer, newInter);
		
		assertEquals("55474924", controleur.getTournee().getPlusCourteTournee().get(posEstDepart).getPremiere().getId());	
		assertEquals("55474924", controleur.getTournee().getPlusCourteTournee().get(posEstArrivee).getDerniere().getId());	
	}
	
	
}
