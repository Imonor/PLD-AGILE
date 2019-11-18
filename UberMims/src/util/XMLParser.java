package util;

import org.w3c.dom.*;

import model.Intersection;
import model.Troncon;

import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLParser {
	
	public static List<Object> readFilePlan(String filePathPlan) {	

		Map<Long, Intersection> intersections = new HashMap<>();
		List<Troncon> troncons = new ArrayList<>();
		List<Object> result = new ArrayList<>();
		try {
			File file = new File(filePathPlan);
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(file);
	         doc.getDocumentElement().normalize();
	         System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	         NodeList nInter = doc.getElementsByTagName("noeud");
	         NodeList nTronc = doc.getElementsByTagName("troncon");
	         
	         for(int i = 0; i < nInter.getLength(); ++i) {
	        	 Element elem = (Element)nInter.item(i);
	        	 long id = Long.parseLong(elem.getAttribute("id"));
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
	         
	         result.add(intersections);
	         result.add(troncons);
	         
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		List<Object> liste = readFilePlan("fichiersXML2019/grandPlan.xml");
		List<Troncon> troncons = (List<Troncon>)liste.get(1);
		Map< Long, Intersection> inters = (HashMap<Long, Intersection>)liste.get(0);
		
		for(Troncon tronc : troncons) {
			System.out.println(tronc.getNomRue());
		}
	}

}
