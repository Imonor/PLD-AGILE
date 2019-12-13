package model;

import static org.junit.Assert.*;

import java.util.List;
import java.time.LocalTime;
import java.util.LinkedList;

import org.junit.Test;

public class CheminTest {
	
	@Test
	public void TestContructeur() {
		Intersection i1 = new Intersection ("e1", 0.0,0.0);
		Intersection i2 = new Intersection ("l1", 0.0,0.0);
		Intersection i3 = new Intersection ("e2", 0.0,0.0);
		Intersection i4 = new Intersection ("l2", 0.0,0.0);
		List<Intersection> i = new LinkedList<Intersection>();
		
		i.add(i1);
		i.add(i2);
		i.add(i3);
		i.add(i4);
		
		//Constructeur par défaut
		Chemin c1 = new Chemin();
		assertTrue(c1.getIntersections().isEmpty());
		assertEquals(0, c1.getDuree());
		
		//Constructeur avec List<Intersection> et entier
		Chemin c2 = new Chemin(i, 10);
		for(Intersection intersection : i) {
			assertTrue(listContains(c2.getIntersections(),intersection));
		}
		assertEquals(10, c2.getDuree());	
		
	}
	
	@Test
	public void TestGetExtremites () {
		Intersection i1 = new Intersection ("e1", 0.0,0.0);
		Intersection i2 = new Intersection ("l1", 0.0,0.0);
		Intersection i3 = new Intersection ("e2", 0.0,0.0);
		Intersection i4 = new Intersection ("l2", 0.0,0.0);
		List<Intersection> i = new LinkedList<Intersection>();
		
		i.add(i1);
		i.add(i2);
		i.add(i3);
		i.add(i4);
		
		Chemin c = new Chemin(i,10);
		
		assertEquals(i1.getId(), c.getPremiere().getId());
		assertEquals(i4.getId(), c.getDerniere().getId());
	}
	
	@Test
	public void TestAddIntersection () {
		
		Intersection i1 = new Intersection ("e1", 0.0,0.0);
		Intersection i2 = new Intersection ("l1", 0.0,0.0);
		Intersection i3 = new Intersection ("e2", 0.0,0.0);
		Intersection i4 = new Intersection ("l2", 0.0,0.0);
		List<Intersection> i = new LinkedList<Intersection>();
		
		i.add(i1);
		i.add(i2);
		i.add(i3);
		
		Chemin c = new Chemin(i,10);
	
		//ajout d'une intersection absente
		assertTrue(c.addIntersection(i4));
		assertTrue(listContains(c.getIntersections(), i4));
		
		//Verification que l'ajout se fait a la fin de la liste
		assertEquals(i4.getId(),c.getDerniere().getId());
		
		//ajout d'une intersection egale a la derniere ajoutee
		assertFalse(c.addIntersection(i4));
		
		//ajout d'une intersection deja presente mais pas egale a la derniere
		assertTrue(c.addIntersection(i1));
		assertEquals(i1.getId(), c.getDerniere().getId());
		
	}
	
	@Test
	public void TestEquals() { //Chaque test verifie la commutitavite de la fonction
		Intersection i1 = new Intersection ("e1", 0.0,0.0);
		Intersection i2 = new Intersection ("l1", 0.0,0.0);
		Intersection i3 = new Intersection ("e2", 0.0,0.0);
		List<Intersection> is1 = new LinkedList<Intersection>();
		
		is1.add(i1);
		is1.add(i2);
		is1.add(i3);
		
		Chemin c1 = new Chemin(is1,10);
		
		Chemin c2 = new Chemin(is1,10);
		
		//Test de deux chemins egaux
		assertTrue(c1.equals(c2));
		assertTrue(c2.equals(c1));
		
		
		List<Intersection> is2 = new LinkedList<Intersection>();
		is2.add(i2);
		is2.add(i3);
		
		Chemin c3 = new Chemin(is2,10);
		
		//Test avec 2 chemins de longueur differente
		assertFalse(c1.equals(c3));
		assertFalse(c3.equals(c1));
		
		List<Intersection> is3 = new LinkedList<Intersection>();		
		is3.add(i3);
		is3.add(i1);
		is3.add(i2);
		
		Chemin c4 = new Chemin(is3, 10);
		
		//Test avec de chimins de meme taille avec ordre different
		assertFalse(c1.equals(c4));
		assertFalse(c4.equals(c1));

		
	}
	
	private boolean listContains (List<Intersection> toCheck, Intersection toFind) {
		for (Intersection i : toCheck) {
			if(i.getId().equals(toFind.getId())) return true;
		}
		return false;
	}
}