package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class ContraintesTourneeTest {
	
	@Test
	void TestContructeur() {
		Intersection depot = new Intersection ("depot", 0.0,0.0);
		
		Intersection i1 = new Intersection ("e1", 0.0,0.0);
		Intersection i2 = new Intersection ("l1", 0.0,0.0);
		
		PointEnlevement e1 = new PointEnlevement(i1, i2.getId(), 10);
		PointLivraison l1 = new PointLivraison (i2, i1.getId(), 10);
		
		Intersection i3 = new Intersection ("e2", 0.0,0.0);
		Intersection i4 = new Intersection ("l2", 0.0,0.0);
		
		PointEnlevement e2 = new PointEnlevement(i3, i4.getId(), 10);
		PointLivraison l2 = new PointLivraison (i4, i3.getId(), 10);
		
		List<PointEnlevement> e = new ArrayList<PointEnlevement>();
		List<PointLivraison> l = new ArrayList<PointLivraison>();
		
		e.add(e1);
		e.add(e2);
		l.add(l1);
		l.add(l2);
		
		ContraintesTournee ct = new ContraintesTournee(LocalTime.MIN, depot, e, l);
		
		assertEquals(LocalTime.MIN, ct.getHeureDepart());
		assertEquals(depot.getId(), ct.getDepot().getId());
		assertEquals(e.size(), ct.getPointsEnlevement().size());
		
		for (PointEnlevement elv : ct.getPointsEnlevement()) {
			assertTrue(ListContains(e,elv));
		}
		
		for (PointLivraison lvr : ct.getPointsLivraison()) {
			assertTrue(ListContains(l,lvr));
		}
		
	}
	
	
	@Test
	void TestsAddLivraison() {
		Intersection depot = new Intersection ("depot", 0.0,0.0);
		
		Intersection i1 = new Intersection ("e1", 0.0,0.0);
		Intersection i2 = new Intersection ("l1", 0.0,0.0);
		
		PointEnlevement e1 = new PointEnlevement(i1, i2.getId(), 10);
		PointLivraison l1 = new PointLivraison (i2, i1.getId(), 10);
		
		Intersection i3 = new Intersection ("e2", 0.0,0.0);
		Intersection i4 = new Intersection ("l2", 0.0,0.0);
		
		PointEnlevement e2 = new PointEnlevement(i3, i4.getId(), 10);
		PointLivraison l2 = new PointLivraison (i4, i3.getId(), 10);
		
		List<PointEnlevement> e = new ArrayList<PointEnlevement>();
		List<PointLivraison> l = new ArrayList<PointLivraison>();
		
		e.add(e1);
		e.add(e2);
		l.add(l1);
		l.add(l2);
		
		ContraintesTournee ct = new ContraintesTournee(LocalTime.MIN, depot, e, l);
		
		Intersection i5 = new Intersection("e3", 0.0,0.0);
		Intersection i6 = new Intersection("l3", 0.0,0.0);
		PointEnlevement e3 = new PointEnlevement(i5,"l3",10);
		PointLivraison l3 = new PointLivraison(i6,"e3",10);
		
		//Ajout livrason inexistante sans point commun
		assertTrue(ct.addLivraison(e3, l3));
		
		//Ajout livraisons avec points non référencés l'un à l'autre
		assertFalse(ct.addLivraison(e3, l1));
		assertFalse(ct.addLivraison(e1, l3));
		
		//Ajout livraisons deja presente
		assertFalse(ct.addLivraison(e3, l3));
		
		//Ajout livraison avec un point commun
		PointEnlevement e4 = new PointEnlevement(i5,"l4",10);
		PointLivraison l4 = new PointLivraison("l4", 0.0,0.0,e4.getId(),10);
		
		PointLivraison l5 = new PointLivraison(i6, "e5",10);
		PointEnlevement e5 = new PointEnlevement("e5",0.0,0.0,l5.getId(),10);
		
		assertTrue(ct.addLivraison(e4, l4));
		assertTrue(ct.addLivraison(e5, l5));
		
	}
	
	@Test
	void TestRemoveLivrasons() {
		Intersection depot = new Intersection ("depot", 0.0,0.0);
		
		Intersection i1 = new Intersection ("e1", 0.0,0.0);
		Intersection i2 = new Intersection ("l1", 0.0,0.0);
		
		PointEnlevement e1 = new PointEnlevement(i1, i2.getId(), 10);
		PointLivraison l1 = new PointLivraison (i2, i1.getId(), 10);
		
		Intersection i3 = new Intersection ("e2", 0.0,0.0);
		Intersection i4 = new Intersection ("l2", 0.0,0.0);
		
		PointEnlevement e2 = new PointEnlevement(i3, i4.getId(), 10);
		PointLivraison l2 = new PointLivraison (i4, i3.getId(), 10);
		
		List<PointEnlevement> e = new ArrayList<PointEnlevement>();
		List<PointLivraison> l = new ArrayList<PointLivraison>();
		
		e.add(e1);
		e.add(e2);
		l.add(l1);
		l.add(l2);
		
		ContraintesTournee ct = new ContraintesTournee(LocalTime.MIN, depot, e, l);
		
		//Supprimer une livraison présente
		assertTrue(ct.removeLivraison(e1, l1));
		
		//Supprimer une livraison absente
		assertFalse(ct.removeLivraison(e1, l1));
		
		//Ajout livraisons avec points non référencés l'un à l'autre
		assertFalse(ct.addLivraison(e1, l2));
		assertFalse(ct.addLivraison(e2, l1));
		
	}
	
	
	private boolean ListContains (List<PointEnlevement> toTest, PointEnlevement toFind) {
		for (Intersection i : toTest) {
			if(i.getId().equals(toFind.getId())) return true;
		}		
		return false;
	}
	
	private boolean ListContains (List<PointLivraison> toTest, PointLivraison toFind) {
		for (Intersection i : toTest) {
			if(i.getId().equals(toFind.getId())) return true;
		}		
		return false;
	}
	
}
