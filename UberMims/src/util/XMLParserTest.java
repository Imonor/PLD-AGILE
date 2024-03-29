package util;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.Map;

import org.junit.Test;

import model.ContraintesTournee;
import model.Plan;
import model.Troncon;

class XMLParserTest {

	@Test
	public void testChargerPlanNormal() {
		Plan plan = new Plan();
		try {
		plan = XMLParser.chargerPlan("fichiersXML2019/petitPlan.xml", 1600, 1900);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		assertEquals(plan.getIntersections().get("25611760").getId(), "25611760");
		assertEquals(plan.getIntersections().get("25611760").getLatitude(), 964);
		assertEquals(plan.getIntersections().get("25611760").getLongitude(), 823);
		Map<String, Troncon> tronconsSortants = plan.getIntersections().get("25611760").getTronconsSortants();
		assertEquals(tronconsSortants.get("26317233").getDestination().getId(), "26317233");
		assertEquals(tronconsSortants.get("26317233").getNomRue(), "Avenue F�lix Faure");
		assertEquals(tronconsSortants.get("26317233").getLongueur(), 26.020561);
		
		assertEquals(tronconsSortants.get("26317214").getDestination().getId(), "26317214");
		assertEquals(tronconsSortants.get("26317214").getNomRue(), "Avenue Lacassagne");
		assertEquals(tronconsSortants.get("26317214").getLongueur(), 73.66637);
		
		assertEquals(tronconsSortants.get("250042857").getDestination().getId(), "250042857");
		assertEquals(tronconsSortants.get("250042857").getNomRue(), "Avenue F�lix Faure");
		assertEquals(tronconsSortants.get("250042857").getLongueur(), 214.0224);
	}
	
	@Test
	public void testChargerPlanUNOWKN() {
		Plan plan = new Plan();
		try {
		plan = XMLParser.chargerPlan("fichiersXML2019/petitPlan.xml", 1600, 1900);
		} catch (Exception e)
		{
			e.printStackTrace();
		}		assertEquals(plan.getIntersections().get("208769027").getId(), "208769027");
		assertEquals(plan.getIntersections().get("208769027").getLatitude(), 609);
		assertEquals(plan.getIntersections().get("208769027").getLongitude(), 1439);
		Map<String, Troncon> tronconsSortants = plan.getIntersections().get("208769027").getTronconsSortants();
		assertEquals(tronconsSortants.get("208769047").getDestination().getId(), "208769047");
		assertEquals(tronconsSortants.get("208769047").getNomRue(), "Rue Pascal");
		assertEquals(tronconsSortants.get("208769047").getLongueur(), 109.713776);
		
		assertEquals(tronconsSortants.get("55475052").getDestination().getId(), "55475052");
		assertEquals(tronconsSortants.get("55475052").getNomRue(),"UNKNOWN");
		assertEquals(tronconsSortants.get("55475052").getLongueur(), 109.26013);
		
		assertEquals(tronconsSortants.get("208769069").getDestination().getId(), "208769069");
		assertEquals(tronconsSortants.get("208769069").getNomRue(), "Rue Saint-Eus�be");
		assertEquals(tronconsSortants.get("208769069").getLongueur(), 59.16955);
	}
	
	/*@Test
	void testChargerPlanError() {
		Assertions.assertThrows(FileNotFoundException.class, () -> XMLParser.chargerPlan("fichiersXML2019/planFurtif.xml", 1600, 1900));

	}*/
	
	@Test
	public void testChargerContraintesTourneeNormal() {
		Plan plan = new Plan();
		try {
		plan = XMLParser.chargerPlan("fichiersXML2019/petitPlan.xml", 1600, 1900);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		ContraintesTournee contraintesTournee = new ContraintesTournee();
		try {
			contraintesTournee = XMLParser.chargerContraintesTournee("fichiersXML2019/demandePetit2.xml", plan);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		assertEquals(contraintesTournee.getHeureDepart(), LocalTime.of(8, 0, 0));
		assertEquals(contraintesTournee.getDepot().getId(), "2835339774");
		assertEquals(contraintesTournee.getPointsEnlevement().get(0).getId(), "1679901320");
		assertEquals(contraintesTournee.getPointsEnlevement().get(1).getId(), "208769120");
		assertEquals(contraintesTournee.getPointsLivraison().get(0).getId(), "208769457");
		assertEquals(contraintesTournee.getPointsLivraison().get(1).getId(), "25336179");

		assertEquals(contraintesTournee.getPointsEnlevement().get(0).getTempsEnlevement(), 420);
		assertEquals(contraintesTournee.getPointsEnlevement().get(1).getTempsEnlevement(), 420);
		assertEquals(contraintesTournee.getPointsLivraison().get(0).getTempsLivraison(), 600);
		assertEquals(contraintesTournee.getPointsLivraison().get(1).getTempsLivraison(), 480);
		
		
	}
	
	@Test
	public void testChargerTourneeSansDepot() {
		Plan plan = new Plan();
		boolean thrown = false;
		try {
			plan = XMLParser.chargerPlan("fichiersXML2019/petitPlan.xml", 1600, 1900);
		} catch (ExceptionChargement e)
		{
			thrown = true;
			e.printStackTrace();
		}
		assertTrue(thrown);
	}
	
	@Test
	public void testChargerTourneeIncoherenteAvecPlan() {
		Plan plan = new Plan();
		boolean thrown = false;
		try {
			plan = XMLParser.chargerPlan("fichiersXML2019/petitPlan.xml", 1600, 1900);
		} catch (ExceptionChargement e)
		{
			thrown = true;
			e.printStackTrace();
		}
		assertTrue(thrown);		
	}
	
	@Test
	public void testChargerFichiersAutresQueXML() {
		Plan plan = new Plan();
		boolean thrown = false;
		try {
			XMLParser.chargerPlan("test/Random.csv", 1600, 1900);
		} catch (ExceptionChargement e) {
			e.printStackTrace();
			thrown = true;
		}
		assertTrue(thrown);
		thrown = false;
		try{
			XMLParser.chargerContraintesTournee("test/Random.csv", plan);
		} catch (ExceptionChargement e) {
			e.printStackTrace();
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void testChargerFichierInexistant() {
		Plan plan = new Plan();
		boolean thrown = false;
		try {
			XMLParser.chargerPlan("test/Unknown.xml", 1600, 1900);
		} catch (ExceptionChargement e) {
			e.printStackTrace();
			thrown = true;
		}
		assertTrue(thrown);
		thrown = false;
		try{
			XMLParser.chargerContraintesTournee("test/Unknown.xml", plan);
		} catch (ExceptionChargement e) {
			e.printStackTrace();
			thrown = true;
		}
		assertTrue(thrown);
		
	}

	/*@Test
	void testChargerContraintesTourneeError() {
		Plan plan = XMLParser.chargerPlan("fichiersXML2019/petitPlan.xml", 1600, 1900);
		Assertions.assertThrows(FileNotFoundException.class, () -> XMLParser.chargerContraintesTournee("fichiersXML2019/demandePetit10.xml", plan));

	}*/

}
