package algo;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import controleur.Controleur;
import util.ExceptionChargement;

public class TemplateTSPTest {

	@Test
	public void testerVisteUniqueNormal() {
		
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
		
		HashMap<String, Integer> frequence = new HashMap<String, Integer>();
		for(int i = 0; i < controleur.getContraintes().getPointsEnlevement().size(); ++i) {
			frequence.put( controleur.getContraintes().getPointsEnlevement().get(i).getId() , 0);
		}
		for(int i = 0; i < controleur.getContraintes().getPointsLivraison().size(); ++i) {
			frequence.put( controleur.getContraintes().getPointsLivraison().get(i).getId() , 0);
		}
		frequence.put( controleur.getContraintes().getDepot().getId(), 0 );
		
		controleur.calculerTournee();
		
		for(int i = 0; i < controleur.getTournee().getPlusCourteTournee().size(); ++i) {
			int nb = frequence.get( controleur.getTournee().getPlusCourteTournee().get(i).getDerniere().getId() );
			frequence.put( controleur.getTournee().getPlusCourteTournee().get(i).getDerniere().getId() , ++nb);
		}
		
		boolean unique = true;
		for(int i = 0; i < controleur.getTournee().getPlusCourteTournee().size(); ++i) {
			if(frequence.get( controleur.getTournee().getPlusCourteTournee().get(i).getDerniere().getId() ) != 1) {
				unique = false;
			}
		}
		
		assertTrue("tous les noeuds des contraintes doivent etre vu une seul fois", unique);
		
	}

}

