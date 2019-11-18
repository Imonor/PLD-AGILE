package util;

import org.w3c.dom.*;

import model.Course;
import model.Intersection;
import model.Troncon;
import model.Plan;
import model.Precedence;
import model.Tournee;

import javax.xml.parsers.*;
import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLParser {
	
	public static Plan chargerPlan(String filePathPlan) {	
		Map<String, Intersection> intersections = new HashMap<>();
		List<Troncon> troncons = new ArrayList<>();
		Plan plan = new Plan();
		try {
			File file = new File(filePathPlan);
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(file);
	         doc.getDocumentElement().normalize();
	         NodeList nInter = doc.getElementsByTagName("noeud");
	         NodeList nTronc = doc.getElementsByTagName("troncon");
	         
	         for(int i = 0; i < nInter.getLength(); ++i) {
	        	 Element elem = (Element)nInter.item(i);
	        	 String id = elem.getAttribute("id");
	        	 double latitude = Double.parseDouble(elem.getAttribute("latitude"));
	        	 double longitude = Double.parseDouble(elem.getAttribute("longitude"));
	        	 Intersection inter = new Intersection(id, latitude, longitude);
	        	 intersections.put(id, inter);
	         }

	         for(int i = 0; i < nTronc.getLength(); ++i) {
	        	 Element elem = (Element)nTronc.item(i);
	        	 long idOrig = Long.parseLong(elem.getAttribute("origine"));
	        	 long idDest = Long.parseLong(elem.getAttribute("destination"));
	        	 String nomRue = elem.getAttribute("nomRue");
	        	 double longueur = Double.parseDouble(elem.getAttribute("longueur"));
	        	 Troncon tronc = new Troncon(intersections.get(idOrig), intersections.get(idDest), nomRue, longueur);
	        	 troncons.add(tronc);
	         }
	         
	 		plan.setIntersections(intersections);
	 		plan.setTroncons(troncons);
	         
		} catch(Exception e) {
			e.printStackTrace();
		}
		return plan;
	}
	
	public static Tournee chargerTournee(String filePathTournee, Plan plan) {
		List<Precedence> precedences = new ArrayList<>();
		Tournee tournee = new Tournee();
		try {
			File file = new File(filePathTournee);
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(file);
	         doc.getDocumentElement().normalize();
	         Element entrepot = (Element)doc.getElementsByTagName("entrepot").item(0);
	         String[] heureTab = entrepot.getAttribute("heureDepart").split(":");
	         int heures = Integer.parseInt(heureTab[0]);
	         int minutes = Integer.parseInt(heureTab[1]);
	         int secondes = Integer.parseInt(heureTab[2]);
	         tournee.setHeureDepart(LocalTime.of(heures, minutes, secondes));
	         tournee.setDepart(plan.getIntersections().get(entrepot.getAttribute("adresse")));
	         
	         NodeList nLivraisons = doc.getElementsByTagName("livraison");
	         
	         for(int i = 0; i < nLivraisons.getLength(); ++i) {
	        	 Element elem = (Element)nLivraisons.item(i);
	        	 Intersection depart = plan.getIntersections().get(elem.getAttribute("adresseEnlevement"));
	        	 Intersection arrivee = plan.getIntersections().get(elem.getAttribute("adresseLivraison"));
	        	 int duree = Integer.parseInt(elem.getAttribute("dureeLivraison")) + Integer.parseInt(elem.getAttribute("dureeEnlevement"));
	        	 
	        	 Course course = new Course(depart, arrivee, duree);
	        	 precedences.add(course);
	         }
	         
	         tournee.setListePrecedences(precedences);

		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return tournee;
	}
	
	public static void main(String[] args) {
		Plan plan = chargerPlan("fichiersXML2019/grandPlan.xml");
		Tournee tournee = chargerTournee("fichiersXML2019/demandeGrand7.xml", plan);
		for(Precedence prec: tournee.getListePrecedences()) {
			System.out.println(prec.getDepart().getId() + " --> " + prec.getArrivee().getId());
		}
		
	}

}
