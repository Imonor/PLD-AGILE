package util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import model.ContraintesTournee;
import model.Plan;
import model.Troncon;

class XMLParserTest {

	@Test
	void testChargerPlanNormal() {
		Plan plan = XMLParser.chargerPlan("fichiersXML2019/petitPlan.xml", 1600, 1900);
		assertEquals(plan.getIntersections().get("25611760").getId(), "25611760");
		assertEquals(plan.getIntersections().get("25611760").getLatitude(), 964);
		assertEquals(plan.getIntersections().get("25611760").getLongitude(), 823);
		Map<String, Troncon> tronconsSortants = plan.getIntersections().get("25611760").getTronconsSortants();
		assertEquals(tronconsSortants.get("26317233").getDestination().getId(), "26317233");
		assertEquals(tronconsSortants.get("26317233").getNomRue(), "Avenue Félix Faure");
		assertEquals(tronconsSortants.get("26317233").getLongueur(), 26.020561);
		
		assertEquals(tronconsSortants.get("26317214").getDestination().getId(), "26317214");
		assertEquals(tronconsSortants.get("26317214").getNomRue(), "Avenue Lacassagne");
		assertEquals(tronconsSortants.get("26317214").getLongueur(), 73.66637);
		
		assertEquals(tronconsSortants.get("250042857").getDestination().getId(), "250042857");
		assertEquals(tronconsSortants.get("250042857").getNomRue(), "Avenue Félix Faure");
		assertEquals(tronconsSortants.get("250042857").getLongueur(), 214.0224);
	}
	
	@Test
	void testChargerPlanUNOWKN() {
		Plan plan = XMLParser.chargerPlan("fichiersXML2019/petitPlan.xml", 1600, 1900);
		assertEquals(plan.getIntersections().get("208769027").getId(), "208769027");
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
		assertEquals(tronconsSortants.get("208769069").getNomRue(), "Rue Saint-Eusèbe");
		assertEquals(tronconsSortants.get("208769069").getLongueur(), 59.16955);
	}
	
	/*@Test
	void testChargerPlanError() {
		Assertions.assertThrows(FileNotFoundException.class, () -> XMLParser.chargerPlan("fichiersXML2019/planFurtif.xml", 1600, 1900));

	}*/
	
	@Test
	void testChargerContraintesTourneeNormal() {
		Plan plan = XMLParser.chargerPlan("fichiersXML2019/petitPlan.xml", 1600, 1900);
		ContraintesTournee contraintesTournee = XMLParser.chargerContraintesTournee("fichiersXML2019/demandePetit2.xml", plan);
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

	/*@Test
	void testChargerContraintesTourneeError() {
		Plan plan = XMLParser.chargerPlan("fichiersXML2019/petitPlan.xml", 1600, 1900);
		Assertions.assertThrows(FileNotFoundException.class, () -> XMLParser.chargerContraintesTournee("fichiersXML2019/demandePetit10.xml", plan));

	}*/

}
