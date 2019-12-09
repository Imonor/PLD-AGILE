package util;

import org.w3c.dom.*;

import algo.TSP1;
import model.PointEnlevement;
import model.Intersection;
import model.Troncon;
import model.Plan;
import model.PointLivraison;
import model.Tournee;
import model.Chemin;
import model.ContraintesTournee;

import javax.xml.parsers.*;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class XMLParser {

	public static Plan chargerPlan(String filePathPlan, int screenHeight, int screenWidth) throws ExceptionChargement {
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

			double latMax = 0, longMax = 0, latMin = Double.MAX_VALUE, longMin = Double.MAX_VALUE;
			
			//Calcul des dimensions du plan
			for (int i = 0; i < nInter.getLength(); ++i) {
				Element elem = (Element) nInter.item(i);
				double latitude = Double.parseDouble(elem.getAttribute("latitude"));
				double longitude = Double.parseDouble(elem.getAttribute("longitude"));

				latMax = (latitude > latMax) ? latitude : latMax;
				longMax = (longitude > longMax) ? longitude : longMax;
				latMin = (latitude < latMin) ? latitude : latMin;
				longMin = (longitude < longMin) ? longitude : longMin;
			}
			double ratioHauteur = screenHeight / (latMax - latMin);
			double ratioLargeur = screenWidth / (longMax - longMin);
			double ratio = (ratioLargeur < ratioHauteur) ? ratioLargeur : ratioHauteur;

			//Chargement des intersections
			for (int i = 0; i < nInter.getLength(); ++i) {
				Element elem = (Element) nInter.item(i);
				String id = elem.getAttribute("id");
				double latitude = Double.parseDouble(elem.getAttribute("latitude"));
				double longitude = Double.parseDouble(elem.getAttribute("longitude"));

				int longitudeEcran = (int) ((longitude - longMin) * ratioLargeur);
				int latitudeEcran = (int) (screenHeight - (latitude - latMin) * ratioHauteur);

				Intersection inter = new Intersection(id, latitudeEcran, longitudeEcran);
				intersections.put(id, inter);
			}
			
			//Chargement des troncons 
			for (int i = 0; i < nTronc.getLength(); ++i) {
				Element elem = (Element) nTronc.item(i);
				String idOrig = elem.getAttribute("origine");
				String idDest = elem.getAttribute("destination");
				String nomRue = elem.getAttribute("nomRue");
				double longueur = Double.parseDouble(elem.getAttribute("longueur"));
				Troncon tronc = new Troncon(intersections.get(idDest), nomRue, longueur);
				intersections.get(idOrig).addTroncon(idDest, tronc);
			}

			//Suppression des intersections sans troncon sortant
			for (Iterator<Map.Entry<String, Intersection>> iterator = intersections.entrySet().iterator(); iterator
					.hasNext();) {
				Intersection inter = iterator.next().getValue();
				if (inter.getTronconsSortants().isEmpty()) {
					iterator.remove();
				}
			}

			//Suppression des intersections sans troncon entrant
			List<String> idInters = new ArrayList<String>(intersections.keySet()); // on copie les ID de tous les troncons
			for (int i = 0; i < nTronc.getLength(); ++i) {
				Element elem = (Element) nTronc.item(i);
				String idDest = elem.getAttribute("destination");
				if (idInters.contains(idDest)) //Pour chaque troncon on supprime son intersection de destination dans la copie des ID
					idInters.remove(idDest);
			}
			for (String id : idInters) { 
				intersections.remove(id); //On supprime les intersections dont l'ID apparait dans la liste des copies d'ID
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

			
		plan.setIntersections(intersections);
		if (intersections.isEmpty()) throw new ExceptionChargement("Aucun plan Charge");
		return plan;
	}

	public static ContraintesTournee chargerContraintesTournee(String filePathTournee, Plan plan) throws ExceptionChargement {
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

				PointEnlevement enlevement = new PointEnlevement(interEnlevement, interLivraison.getId(),
						dureeEnlevement);
				PointLivraison livraison = new PointLivraison(interLivraison, interEnlevement.getId(), dureeLivraison);

				livraisons.add(livraison);
				enlevements.add(enlevement);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		tournee.setPointsEnlevement(enlevements);
		tournee.setPointsLivraison(livraisons);
		
		if(tournee.getPointsEnlevement().isEmpty()) throw new ExceptionChargement ("Pas de contraintes CHargees");
		
		return tournee;
	}

//	public static void main(String[] args) {
//		
//	}
}