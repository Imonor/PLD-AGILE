package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class PointEnlevementTest {
	
	@Test
	void TestContructeurs() {
		Intersection i1 = new Intersection ("e1", 0.0,0.0);
		Intersection i2 = new Intersection ("l1", 0.0,0.0);
		
		//Constructeur avec intersection, id point de livraison et temps d'enlevement
		PointEnlevement e1 = new PointEnlevement(i1,i2.getId(), 10);		
		assertEquals(e1.getId(), "e1");
		assertEquals(e1.getLatitude(), 0.0);
		assertEquals(e1.getLongitude(), 0.0);
		assertEquals(e1.getIdLivraison(), i2.getId());
		assertEquals(e1.getTempsEnlevement(), 10);
		
		//Constructeur avec id, lat, long, id point de livraison et temps d'enlement
		PointEnlevement e2 = new PointEnlevement("e2", 0.0, 0.0, i2.getId(), 10);
		assertEquals(e2.getId(), "e2");
		assertEquals(e2.getLatitude(), 0.0);
		assertEquals(e2.getLongitude(), 0.0);
		assertEquals(e2.getIdLivraison(), i2.getId());
		assertEquals(e2.getTempsEnlevement(), 10);
		
		}
	
	@Test
	void TestTempsEnlevmentNegatif() {
		Intersection i1 = new Intersection ("e1", 0.0,0.0);
		Intersection i2 = new Intersection ("l1", 0.0,0.0);
		
		//Construire un point d'enlevement avec temps négatif
		PointEnlevement e1 = new PointEnlevement(i1,i2.getId(), -10);
		assertEquals(e1.getTempsEnlevement(), 0);
		
		//Actualiser son temps a une valeure négative
		e1.setTempsEnlevement(-10);
		assertEquals(e1.getTempsEnlevement(), 0);
		
		//Actualiser son temps a une valeure nulle
		e1.setTempsEnlevement(0);
		assertEquals(e1.getTempsEnlevement(), 0);

		//Actualiser son temps a une valeure positive
		e1.setTempsEnlevement(10);
		assertEquals(e1.getTempsEnlevement(), 10);
	}
}