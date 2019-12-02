package algo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import model.Chemin;
import model.Intersection;
import model.Troncon;

class DijkstraTest {

	@Test
	void testPlusCourtsCheminsPlan1() {
		//exemple avec intersections simples
		Dijkstra d = new Dijkstra();
		Intersection i1 = new Intersection("i1", 0.0, 0.0);
		Intersection i2 = new Intersection("i2", 0.0, 0.0);
		Intersection i3 = new Intersection("i3", 0.0, 0.0);
		Intersection i4 = new Intersection("i4", 0.0, 0.0);
		Intersection i5 = new Intersection("i5", 0.0, 0.0);
		
		i1.addTroncon("i2", new Troncon(i2, "", 20.0));
		i1.addTroncon("i3", new Troncon(i3, "", 40.0));
		i1.addTroncon("i4", new Troncon(i4, "", 50.0));
		i2.addTroncon("i3", new Troncon(i3, "", 30.0));
		i3.addTroncon("i5", new Troncon(i5, "", 40.0));
		i4.addTroncon("i5", new Troncon(i5, "", 20.0));
		
		Map<String, Intersection> intersections = new HashMap<>();
		intersections.put("i1", i1);
		intersections.put("i2", i2);
		intersections.put("i3", i3);
		intersections.put("i4", i4);
		intersections.put("i5", i5);
		
		Map<String, Map<String, Chemin>> plusCourtsChemins = d.plusCourtsCheminsPlan(intersections);
		
		assertEquals(plusCourtsChemins.get("i1").get("i2").getDuree(),4);
		assertEquals(plusCourtsChemins.get("i1").get("i3").getDuree(),9);
		assertEquals(plusCourtsChemins.get("i1").get("i4").getDuree(),12);
		assertEquals(plusCourtsChemins.get("i1").get("i5").getDuree(),16);
	}

	@Test
	void testPlusCourtsCheminsPlan2() {
		//exemple avec une intersection à laquelle on ne peut pas accéder
		Dijkstra d = new Dijkstra();
		Intersection i1 = new Intersection("i1", 0.0, 0.0);
		Intersection i2 = new Intersection("i2", 0.0, 0.0);
		Intersection i3 = new Intersection("i3", 0.0, 0.0);
		Intersection i4 = new Intersection("i4", 0.0, 0.0);
		Intersection i5 = new Intersection("i5", 0.0, 0.0);
		
		i2.addTroncon("i3", new Troncon(i3, "", 100.0));
		i2.addTroncon("i4", new Troncon(i4, "", 200.0));
		i3.addTroncon("i4", new Troncon(i4, "", 50.0));
		i4.addTroncon("i5", new Troncon(i5, "", 300.0));
		i5.addTroncon("i2", new Troncon(i2, "", 100.0));
		
		Map<String, Intersection> intersections = new HashMap<>();
		intersections.put("i1", i1);
		intersections.put("i2", i2);
		intersections.put("i3", i3);
		intersections.put("i4", i4);
		intersections.put("i5", i5);
		
		Map<String, Map<String, Chemin>> plusCourtsChemins = d.plusCourtsCheminsPlan(intersections);
		
		assertEquals(plusCourtsChemins.get("i1").get("i2").getDuree(),2147483647);
		assertEquals(plusCourtsChemins.get("i1").get("i3").getDuree(),2147483647);
		assertEquals(plusCourtsChemins.get("i1").get("i4").getDuree(),2147483647);
		assertEquals(plusCourtsChemins.get("i1").get("i5").getDuree(),2147483647);
	}
	
	@Test
	void testPlusCourtsCheminsPlan3() {
		//exemple avec des troncons aller retour
		Dijkstra d = new Dijkstra();
		Intersection i1 = new Intersection("i1", 0.0, 0.0);
		Intersection i2 = new Intersection("i2", 0.0, 0.0);
		Intersection i3 = new Intersection("i3", 0.0, 0.0);
		Intersection i4 = new Intersection("i4", 0.0, 0.0);
		
		i1.addTroncon("i2", new Troncon(i2, "", 100.0));
		i2.addTroncon("i3", new Troncon(i3, "", 80.0));
		i2.addTroncon("i1", new Troncon(i1, "", 50.0));
		i3.addTroncon("i4", new Troncon(i4, "", 100.0));
		i3.addTroncon("i2", new Troncon(i2, "", 50.0));
		i3.addTroncon("i1", new Troncon(i1, "", 300.0));
		
		Map<String, Intersection> intersections = new HashMap<>();
		intersections.put("i1", i1);
		intersections.put("i2", i2);
		intersections.put("i3", i3);
		intersections.put("i4", i4);
		
		Map<String, Map<String, Chemin>> plusCourtsChemins = d.plusCourtsCheminsPlan(intersections);
		
		assertEquals(plusCourtsChemins.get("i3").get("i2").getDuree(),12);
		assertEquals(plusCourtsChemins.get("i3").get("i1").getDuree(),24);
		assertEquals(plusCourtsChemins.get("i1").get("i4").getDuree(),67);
		assertEquals(plusCourtsChemins.get("i4").get("i1").getDuree(),2147483647);
	}
	
	@Test
	void testPlusCourtsCheminsPlan4() {
		//exemple avec des troncons aller retour
		Dijkstra d = new Dijkstra();
		Intersection i1 = new Intersection("i1", 0.0, 0.0);
		Intersection i2 = new Intersection("i2", 0.0, 0.0);
		Intersection i3 = new Intersection("i3", 0.0, 0.0);
		Intersection i4 = new Intersection("i4", 0.0, 0.0);
		Intersection i5 = new Intersection("i5", 0.0, 0.0);
		
		i1.addTroncon("i2", new Troncon(i2, "", 100.0));
		i2.addTroncon("i3", new Troncon(i3, "", 80.0));
		i1.addTroncon("i3", new Troncon(i3, "", 50.0));
		i3.addTroncon("i1", new Troncon(i1, "", 100.0));
		i4.addTroncon("i5", new Troncon(i5, "", 50.0));
		
		Map<String, Intersection> intersections = new HashMap<>();
		intersections.put("i1", i1);
		intersections.put("i2", i2);
		intersections.put("i3", i3);
		intersections.put("i4", i4);
		intersections.put("i5", i5);
		
		Map<String, Map<String, Chemin>> plusCourtsChemins = d.plusCourtsCheminsPlan(intersections);
		
		assertEquals(plusCourtsChemins.get("i1").get("i4").getDuree(),2147483647);
		assertEquals(plusCourtsChemins.get("i2").get("i5").getDuree(),2147483647);
		assertEquals(plusCourtsChemins.get("i4").get("i3").getDuree(),2147483647);
	}

}
