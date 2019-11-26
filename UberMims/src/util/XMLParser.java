package util;

import org.w3c.dom.*;

import model.PointEnlevement;
import model.Intersection;
import model.Troncon;
import model.Plan;
import model.PointLivraison;
import model.ContraintesTournee;

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
		Plan plan = new Plan();
		try {
			File file = new File(filePathPlan);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nInter = doc.getElementsByTagName("noeud");
			NodeList nTronc = doc.getElementsByTagName("troncon");

			for (int i = 0; i < nInter.getLength(); ++i) {
				Element elem = (Element) nInter.item(i);
				String id = elem.getAttribute("id");
				double latitude = Double.parseDouble(elem.getAttribute("latitude"));
				double longitude = Double.parseDouble(elem.getAttribute("longitude"));
				Intersection inter = new Intersection(id, latitude, longitude);
				intersections.put(id, inter);
			}

			for (int i = 0; i < nTronc.getLength(); ++i) {
				Element elem = (Element) nTronc.item(i);
				String idOrig = elem.getAttribute("origine");
				String idDest = elem.getAttribute("destination");
				String nomRue = elem.getAttribute("nomRue");
				double longueur = Double.parseDouble(elem.getAttribute("longueur"));
				Troncon tronc = new Troncon(intersections.get(idDest), nomRue, longueur);
				intersections.get(idOrig).addTroncon(idDest, tronc);
			}

			plan.setIntersections(intersections);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return plan;
	}

	public static ContraintesTournee chargerContraintesTournee(String filePathTournee, Plan plan) {
		List<PointEnlevement> enlevements = new ArrayList<>();
		List<PointLivraison> livraisons = new ArrayList<>();
		ContraintesTournee tournee = new ContraintesTournee();
		try {
			File file = new File(filePathTournee);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			Element entrepot = (Element) doc.getElementsByTagName("entrepot").item(0);
			String[] heureTab = entrepot.getAttribute("heureDepart").split(":");
			int heures = Integer.parseInt(heureTab[0]);
			int minutes = Integer.parseInt(heureTab[1]);
			int secondes = Integer.parseInt(heureTab[2]);
			tournee.setHeureDepart(LocalTime.of(heures, minutes, secondes));
			tournee.setDepot(plan.getIntersections().get(entrepot.getAttribute("adresse")));

			NodeList nLivraisons = doc.getElementsByTagName("livraison");

			for (int i = 0; i < nLivraisons.getLength(); ++i) {
				Element elem = (Element) nLivraisons.item(i);
				Intersection interEnlevement = plan.getIntersections().get(elem.getAttribute("adresseEnlevement"));
				Intersection interLivraison = plan.getIntersections().get(elem.getAttribute("adresseLivraison"));
				int dureeEnlevement = Integer.parseInt(elem.getAttribute("dureeEnlevement"));
				int dureeLivraison = Integer.parseInt(elem.getAttribute("dureeLivraison"));

				PointEnlevement enlevement = new PointEnlevement(interEnlevement, interLivraison.getId(), dureeEnlevement);
				PointLivraison livraison = new PointLivraison(interLivraison, interEnlevement.getId(), dureeLivraison);

				livraisons.add(livraison);
				enlevements.add(enlevement);
			}

			tournee.setPointsEnlevement(enlevements);
			tournee.setPointsLivraison(livraisons);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return tournee;
	}
	
	public static void main(String[] args) {
		Plan plan = XMLParser.chargerPlan("fichiersXML2019/grandPlan.xml");
		ContraintesTournee tournee = XMLParser.chargerContraintesTournee("fichiersXML2019/demandeGrand7.xml", plan);
		for(PointEnlevement enl: tournee.getPointsEnlevement()) {
			System.out.println(enl.getId() + " --> " + enl.getIdLivraison());
		}
	}

}
