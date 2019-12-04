package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class PlanTest {
	
	@Test
	void TestContructeurs() {
		
		Intersection i1 = new Intersection("i1", 0.0,0.0);
		Intersection i2 = new Intersection("i2", 0.0,0.0);
		Intersection i3 = new Intersection("i3", 0.0,0.0);
		Intersection i4 = new Intersection("i4", 0.0,0.0);
		
		Map<String, Intersection> i = new HashMap <String, Intersection>();
		
		i.put(i1.getId(), i1);
		i.put(i2.getId(), i2);
		i.put(i3.getId(), i3);
		i.put(i4.getId(), i4);
		
		Plan p = new Plan(i);
		
		assertEquals(p.getIntersections().size(), i.size());
		
		
		for (String s : p.getIntersections().keySet()) {
			assertEquals(p.getIntersections().get(s).getId(), i.get(s).getId());
		}
	}
	
	@Test
	void TestSetIntersections() {
		
		Intersection i1 = new Intersection("i1", 0.0,0.0);
		Intersection i2 = new Intersection("i2", 0.0,0.0);
		Intersection i3 = new Intersection("i3", 0.0,0.0);
		Intersection i4 = new Intersection("i4", 0.0,0.0);
		
		Map<String, Intersection> i = new HashMap <String, Intersection>();
		
		i.put(i1.getId(), i1);
		i.put(i2.getId(), i2);
		i.put(i3.getId(), i3);
		i.put(i4.getId(), i4);
		
		Plan p = new Plan();
		p.setIntersections(i);
		
		assertEquals(p.getIntersections().size(), i.size());
		
		
		for (String s : p.getIntersections().keySet()) {
			assertEquals(p.getIntersections().get(s).getId(), i.get(s).getId());
		}
	}
	
	
}