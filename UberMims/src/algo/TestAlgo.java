//package algo;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Iterator;
//
//import model.Chemin;
//import model.ContraintesTournee;
//import model.Intersection;
//import model.PointEnlevement;
//import model.PointLivraison;
//import model.Tournee;
////import model.Precedence;
//import model.Troncon;
//
//public class TestAlgo {
//	
////	public static void main(String[] args) {
////		
////		Intersection depot = new Intersection("000",10.6,12.1);
////		Intersection enlev1 = new Intersection("001",11.6,12.1);
////		Intersection liv1 = new Intersection("002",12.6,12.1);
////		Intersection enlev2 = new Intersection("003",13.6,12.1);
////		Intersection liv2 = new Intersection("004",14.6,12.1);
////		
////		Precedence preced1 = new Precedence(enlev1,liv1);
////		Precedence preced2 = new Precedence(enlev2,liv2);
////		Precedence preced3 = new Precedence(liv2,liv1);
////		
////		List<Precedence> precedences = new ArrayList<Precedence>();
////		precedences.add(preced1);
////		precedences.add(preced2);
////		precedences.add(preced3);
////		
////		ContraintesTournee contraintes = new ContraintesTournee(null, depot, precedences);
////	}
//	
//	public static void main(String[] args) {
//		Dijkstra d = new Dijkstra();
//		Intersection i1 = new Intersection("i1", 0.0, 0.0);
//		Intersection i2 = new Intersection("i2", 0.0, 0.0);
//		Intersection i3 = new Intersection("i3", 0.0, 0.0);
//		Intersection i4 = new Intersection("i4", 0.0, 0.0);
//		Intersection i5 = new Intersection("i5", 0.0, 0.0);
//		Intersection i6 = new Intersection("i6", 0.0, 0.0);
//		Intersection i7 = new Intersection("i7", 0.0, 0.0);
//		
//		i1.addTroncon("i2", new Troncon(i2, "", 2.0));
//		i1.addTroncon("i3", new Troncon(i3, "", 4.0));
//		i1.addTroncon("i4", new Troncon(i4, "", 5.0));
//		i2.addTroncon("i3", new Troncon(i3, "", 3.0));
//		i3.addTroncon("i5", new Troncon(i5, "", 4.0));
//		i4.addTroncon("i5", new Troncon(i5, "", 2.0));
//		i5.addTroncon("i6", new Troncon(i6, "", 2.0));
//		i5.addTroncon("i7", new Troncon(i7, "", 2.0));
//		i7.addTroncon("i3", new Troncon(i3, "", 2.0));
//		i6.addTroncon("i2", new Troncon(i2, "", 2.0));
//		i7.addTroncon("i2", new Troncon(i2, "", 2.0));
//		
//		Map<String, Intersection> intersections = new HashMap<>();
//		intersections.put("i1", i1);
//		intersections.put("i2", i2);
//		intersections.put("i3", i3);
//		intersections.put("i4", i4);
//		intersections.put("i5", i5);
//		intersections.put("i6", i6);
//		intersections.put("i7", i7);
//		
//		Map<String, Map<String, Chemin>> plusCourtsChemins = d.plusCourtsCheminsPlan(intersections);
//		for(String interDepart: plusCourtsChemins.keySet()) {
//			System.out.println("intersection " + interDepart +":");
//			for(String interArrivee: plusCourtsChemins.get(interDepart).keySet()) {
//				System.out.print("vers: " + interArrivee + ": ");
//				if(plusCourtsChemins.get(interDepart).get(interArrivee).getIntersections() != null) {
//					for(Intersection i: plusCourtsChemins.get(interDepart).get(interArrivee).getIntersections()) {
//						System.out.print(i.getId() + " -> ");
//					}
//				}
//				System.out.println("duree: " + plusCourtsChemins.get(interDepart).get(interArrivee).getDuree());
//			}
//		}
//		
//		System.out.println("--------------------------");
//		
//		//CREER DES POINTS D'ENLEVEMENT ET LIVRAISON
//		PointEnlevement enlev1 = new PointEnlevement(i1, "i2", 1);
//		PointLivraison livr1 = new PointLivraison(i2, "i1", 1);
//		
//		PointEnlevement enlev2 = new PointEnlevement(i3, "i4", 1);
//		PointLivraison livr2 = new PointLivraison(i4, "i3", 1);
//		
//		PointEnlevement enlev3 = new PointEnlevement(i6, "i7", 1);
//		PointLivraison livr3 = new PointLivraison(i7, "i6", 1);
//		
//		List<PointEnlevement> listeEnlev = new ArrayList<PointEnlevement>();
//		listeEnlev.add(enlev1);
//		listeEnlev.add(enlev2);
//		listeEnlev.add(enlev3);
//		
//		List<PointLivraison> listeLiv = new ArrayList<PointLivraison>();
//		listeLiv.add(livr1);
//		listeLiv.add(livr2);
//		listeLiv.add(livr3);
//		
//		ContraintesTournee contraintes = new ContraintesTournee(null, i5, listeEnlev, listeLiv);
//		
//		int tpsLimite = 10;
//		TSP1 tsp1 = new TSP1();
//		Tournee tournee = tsp1.chercheSolution(tpsLimite, contraintes, plusCourtsChemins);
//		
//		for (int i = 0; i < tournee.getPlusCourteTournee().size(); i++) {
//			String noeud = tournee.getPlusCourteTournee().get(i).getIntersections().get(0).getId();
//			System.out.println(noeud+ "->");
//		}
//		
////		System.out.println("++++++++++++++++++++++++++");
////		
////		HashMap<String, Intersection> hashIntersections = new HashMap<String, Intersection>();
////		Iterator<PointEnlevement> itEnlev = (Iterator<PointEnlevement>)contraintes.getPointsEnlevement().iterator();
////		Iterator<PointLivraison> itLiv = (Iterator<PointLivraison>)contraintes.getPointsLivraison().iterator();
////		
////		int nbSommets = 0;
////		while(itEnlev.hasNext()) {
////			Intersection intersec = (Intersection) itEnlev.next();
////			hashIntersections.put(intersec.getId(), intersec);
////			nbSommets++;
////		}
////		while(itLiv.hasNext()) {
////			Intersection intersec = (Intersection) itLiv.next();
////			hashIntersections.put(intersec.getId(), intersec);
////			nbSommets++;
////		}
////		
////		HashMap<String, Paire> vuDispo = new HashMap<String, Paire>();	
////		for (HashMap.Entry<String, Intersection> iterator : hashIntersections.entrySet()) {
////		    if( iterator.getValue() instanceof PointEnlevement ) {
////		    	vuDispo.put( iterator.getKey(), new Paire(true, false) );
////		    }else {
////		    	vuDispo.put( iterator.getKey(), new Paire(false, false) );
////		    }
////		}
//		
////		for (HashMap.Entry<String, Intersection> iterator : hashIntersections.entrySet()) {
////		    System.out.println( iterator.getValue().getId() + "->" );
////		}
////		
////		System.out.println("-------------");
////		
////		for (HashMap.Entry<String, Paire> iterator : vuDispo.entrySet()) {
////		    System.out.println( iterator.getValue().getDispo() + "-" + iterator.getValue().getVu() );
////		}
//		
//		
//	}
//
//	
//	
//	
//}
