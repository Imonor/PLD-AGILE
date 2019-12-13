package model;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class IntersectionTest {
	
	@Test
	public void TestContructeur() {
		Intersection i = new Intersection ("i", 0.0,0.0);
		
		assertEquals(i.getId(),"i");
		assertEquals(i.getLatitude(), 0.0);
		assertEquals(i.getLongitude(), 0.0);
	}
	
	@Test
	public void TestAddTroncon() {
		Intersection i1 = new Intersection ("i1", 0.0,0.0);
		Intersection i2 = new Intersection ("i2", 0.0,0.0);
		Intersection i3 = new Intersection ("i3", 0.0,0.0);

		Troncon t1 = new Troncon(i2,"Troncon", 10.0);
		Troncon t2 = new Troncon(i3,"Troncon", 10.0);
		Troncon t3 = new Troncon(i1,"Troncon", 10.0);

		
		//tentative d'ajout de 2 troncons
		assertEquals(i1.addTroncon(t1.getDestination().getId(), t1),true);
		assertEquals(i1.addTroncon(t2.getDestination().getId(), t2),true);
		
		//tentative d'ajout du même troncon que le premier
		assertEquals(i1.addTroncon(t1.getDestination().getId(), t1),false);
		
		//tentative d'ajout d'un troncon vers l'intersection elle meme
		assertEquals(i1.addTroncon(t3.getDestination().getId(), t3), true);
	}
}
