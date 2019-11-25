package algo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import model.Chemin;
import model.Intersection;

public class IteratorSeq implements Iterator<Triplet>{
	
	private Intersection current;
	
	private LinkedList<Triplet> dependences;
	
	public IteratorSeq(LinkedList<Triplet> dependeces) {
		this.dependences = dependences;
		current = dependeces.getFirst().getIntersection();
		
	}
	
	
	@Override
	public boolean hasNext() {
		return current != null;
	}

	@Override
	public Iterator<Triplet> next() {
		
		//found1 et found2 sont utilises pour avoir l'iterateur a it.next().next()
		Iterator<Triplet> it = (Iterator<Triplet>) dependences.iterator();
		boolean found1 = false;
		boolean found2 = false;
		while(!found1&&!found2) {
			if(found1) {
				found2 = true;
			}
			if( ((Triplet) it.next()).getIntersection() == current){
				found1 = true;
				((Triplet) it.next()).setVisite(true);
			}
		}
		
		return it.next();
	}
	
	@Override
	public void remove(Triplet... triplet) {
		Iterator<Triplet> it = (Iterator<Triplet>) dependences.iterator();
		boolean found = false;
		while(!found) {
			if( ((Triplet) it.next()).getIntersection() == current){
				found = true;
				
			}
		}
	}

}