package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class PointLivraisonTest {
	
	@Test
	void TestContructeurs() {
		Intersection i1 = new Intersection ("e1", 0.0,0.0);
		Intersection i2 = new Intersection ("l1", 0.0,0.0);
		
		//Constructeur avec intersection, id point de livraison et temps de livraison
		PointLivraison e1 = new PointLivraison(i1,i2.getId(), 10);		
		assertEquals(e1.getId(), "e1");
		assertEquals(e1.getLatitude(), 0.0);
		assertEquals(e1.getLongitude(), 0.0);
		assertEquals(e1.getIdEnlevement(), i2.getId());
		assertEquals(e1.getTempsLivraison(), 10);
		
		//Constructeur avec id, lat, long, id point de livraison et temps de livraison
		PointLivraison e2 = new PointLivraison("e2", 0.0, 0.0, i2.getId(), 10);
		assertEquals(e2.getId(), "e2");
		assertEquals(e2.getLatitude(), 0.0);
		assertEquals(e2.getLongitude(), 0.0);
		assertEquals(e2.getIdEnlevement(), i2.getId());
		assertEquals(e2.getTempsLivraison(), 10);
		
		}
	
	@Test
	void TestTempsEnlevmentNegatif() {
		Intersection i1 = new Intersection ("e1", 0.0,0.0);
		Intersection i2 = new Intersection ("l1", 0.0,0.0);
		
		//Construire un point de livraison avec temps négatif
		PointLivraison e1 = new PointLivraison(i1,i2.getId(), -10);
		assertEquals(e1.getTempsLivraison(), 0);
		
		//Actualiser son temps a une valeure négative
		e1.setTempsLivraison(-10);
		assertEquals(e1.getTempsLivraison(), 0);
		
		//Actualiser son temps a une valeure nulle
		e1.setTempsLivraison(0);
		assertEquals(e1.getTempsLivraison(), 0);

		//Actualiser son temps a une valeure positive
		e1.setTempsLivraison(10);
		assertEquals(e1.getTempsLivraison(), 10);
	}
}