package model;

import static org.junit.Assert.*;

import java.util.List;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.ArrayList;

import org.junit.Test;

public class TourneeTest {
	
	@Test
	public void TestContructeur() {
		Intersection depot = new Intersection ("depot", 0.0,0.0);
		Intersection i1 = new Intersection ("e1", 0.0,0.0);
		Intersection i2 = new Intersection ("l1", 0.0,0.0);
		Intersection i3 = new Intersection ("e2", 0.0,0.0);
		Intersection i4 = new Intersection ("l2", 0.0,0.0);

		List<Intersection> is1 = new LinkedList<Intersection>();
		is1.add(depot);
		is1.add(i1);
		Chemin c1 = new Chemin (is1,10);
		
		List<Intersection> is2 = new LinkedList<Intersection>();
		is2.add(i1);
		is2.add(i2);
		Chemin c2 = new Chemin(is2,10);
		
		List<Intersection> is3 = new LinkedList<Intersection>();
		is3.add(i2);
		is3.add(i3);
		Chemin c3 = new Chemin(is3,10);
		
		List<Intersection> is4 = new LinkedList<Intersection>();
		is4.add(i3);
		is4.add(i4);
		Chemin c4 = new Chemin(is4,10);
		
		List<Intersection> is5 = new LinkedList<Intersection>();
		is5.add(i4);
		is5.add(depot);
		Chemin c5 = new Chemin(is5,10);
		
		List<Chemin> cs = new LinkedList<Chemin>();
		cs.add(c1);
		cs.add(c2);
		cs.add(c3);
		cs.add(c4);
		cs.add(c5);
		
		PointEnlevement e1 = new PointEnlevement(i1, "l1", 10);
		PointLivraison l1 = new PointLivraison(i2, "e1", 10);

		PointEnlevement e2 = new PointEnlevement(i3, "l2", 10);
		PointLivraison l2 = new PointLivraison(i4, "e2", 10);

		List<PointEnlevement> e = new ArrayList<PointEnlevement>();
		e.add(e1);
		e.add(e2);

		List<PointLivraison> l = new ArrayList<PointLivraison>();
		l.add(l1);
		l.add(l2);
		
		ContraintesTournee ct = new ContraintesTournee(LocalTime.MIN, depot, e, l);
		
		//TestConstructeurs
		Tournee t1 = new Tournee(cs, ct);
		assertEquals(90, t1.getDuree());
		assertTrue(ct.equals(t1.getContraintes()));
		assertEquals(cs.size(), t1.getPlusCourteTournee().size());
		for(int i = 0; i< cs.size(); i++) {
			assertTrue(cs.get(i).equals(t1.getPlusCourteTournee().get(i)));
		}
		
		Tournee t2 = new Tournee(cs, ct, 90);
		assertEquals(90, t2.getDuree());
		assertTrue(ct.equals(t2.getContraintes()));
		assertEquals(cs.size(), t2.getPlusCourteTournee().size());
		for(int i = 0; i< cs.size(); i++) {
			assertTrue(cs.get(i).equals(t2.getPlusCourteTournee().get(i)));
		}
		
		
		Tournee t3 = new Tournee(cs, ct, -90);
		assertEquals(90, t3.getDuree());
		assertTrue(ct.equals(t3.getContraintes()));
		assertEquals(cs.size(), t3.getPlusCourteTournee().size());
		for(int i = 0; i< cs.size(); i++) {
			assertTrue(cs.get(i).equals(t3.getPlusCourteTournee().get(i)));
		}
		
		Tournee t4 = new Tournee(ct);
		assertEquals(0, t4.getDuree());
		assertEquals(0, t4.getPlusCourteTournee().size());
		assertTrue(ct.equals(t4.getContraintes()));
		
	}
	
	@Test
	public void TestGetHeureDePassage() {
		Intersection depot = new Intersection ("depot", 0.0,0.0);
		Intersection i1 = new Intersection ("e1", 0.0,0.0);
		Intersection i2 = new Intersection ("l1", 0.0,0.0);
		Intersection i3 = new Intersection ("e2", 0.0,0.0);
		Intersection i4 = new Intersection ("l2", 0.0,0.0);

		List<Intersection> is1 = new LinkedList<Intersection>();
		is1.add(depot);
		is1.add(i1);
		Chemin c1 = new Chemin (is1,10);
		
		List<Intersection> is2 = new LinkedList<Intersection>();
		is2.add(i1);
		is2.add(i2);
		Chemin c2 = new Chemin(is2,10);
		
		List<Intersection> is3 = new LinkedList<Intersection>();
		is3.add(i2);
		is3.add(i3);
		Chemin c3 = new Chemin(is3,10);
		
		List<Intersection> is4 = new LinkedList<Intersection>();
		is4.add(i3);
		is4.add(i4);
		Chemin c4 = new Chemin(is4,10);
		
		List<Intersection> is5 = new LinkedList<Intersection>();
		is5.add(i4);
		is5.add(depot);
		Chemin c5 = new Chemin(is5,10);
		
		List<Chemin> cs = new LinkedList<Chemin>();
		cs.add(c1);
		cs.add(c2);
		cs.add(c3);
		cs.add(c4);
		cs.add(c5);
		
		PointEnlevement e1 = new PointEnlevement(i1, i2.getId(), 10);
		PointLivraison l1 = new PointLivraison(i2, i1.getId(), 10);

		PointEnlevement e2 = new PointEnlevement(i3, i4.getId(), 10);
		PointLivraison l2 = new PointLivraison(i4, i3.getId(), 10);

		List<PointEnlevement> e = new ArrayList<PointEnlevement>();
		e.add(e1);
		e.add(e2);

		List<PointLivraison> l = new ArrayList<PointLivraison>();
		l.add(l1);
		l.add(l2);
		
		LocalTime time = LocalTime.now();
		
		ContraintesTournee ct = new ContraintesTournee(time, depot, e, l);
		
		Tournee t = new Tournee(cs, ct);
		
		//Test Pour le départ
		assertEquals(time, t.getHeureDePassage(depot.getId()));
		
		time = time.plusSeconds(10);
		
		//Test pour tous les points de passage
		assertEquals(time, t.getHeureDePassage(i1.getId()));
		
		time = time.plusSeconds(20);
		
		assertEquals(time, t.getHeureDePassage(i2.getId()));
		
		time = time.plusSeconds(20);
		
		assertEquals(time, t.getHeureDePassage(i3.getId()));	
		
		time = time.plusSeconds(20);
		
		assertEquals(time, t.getHeureDePassage(i4.getId()));
		
		time = time.plusSeconds(20);
		
		//Heure de passage à l'arrivee
		assertEquals(time, t.getHeureArrivee());
		
		
		//Heure de passage a un point absent de la tournee
		assertTrue(t.getHeureDePassage("point absent").equals(LocalTime.MAX));
	}
	
	
}