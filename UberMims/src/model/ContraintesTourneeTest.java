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
