package model;

import static org.junit.Assert.*;



import org.junit.Test;

public class TronconTest {
	
	@Test
	public void TestContructeur() {
		Intersection i = new Intersection ("i", 0.0,0.0);
		
		Troncon t = new Troncon (i, "t", 10.0);
		
		
		assertEquals(t.getDestination().getId(),"i");
		assertEquals(t.getLongueur(), 10.0);
		assertEquals(t.getNomRue(), "t");
	}
}