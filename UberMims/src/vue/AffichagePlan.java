package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import controleur.Controleur;
import model.Intersection;
import model.Plan;
import model.Tournee;
import model.Troncon;
import util.XMLParser;

public class AffichagePlan extends JFrame{

	/**
	 * Page d'accueil : chargement du plan
	 */
	
	//Fenetre sur laquelle s'affiche le plan
	private Fenetre fenetre;
	
	//Taille du plan
	private int sideLength;
	
	//Endroit de placement du plan dans la page
	private Point placementPlan;

	//Plan chargé via le fichier XML
	private Plan plan;
	
	//Controleur du plan
	private Controleur controleur;
	
	public AffichagePlan(Fenetre fenetre, Plan plan) {
		 //String path = "H:\\Mes documents\\PLDAgile\\PLD-AGILE\\UberMims\\fichiersXML2019\\moyenPlan.xml";
		 //plan = XMLParser.chargerPlan(path);
		    
		 this.plan = plan;
		 this.fenetre = fenetre;
	}
	
	public void dessinerPlan(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
	    for (Intersection intersection : plan.getIntersections().values()) {
	    	Ellipse2D.Double shape = new Ellipse2D.Double(intersection.getLongitude(),intersection.getLatitude(),2, 2);
	    	g2d.draw(shape);
	    	g2d.fill(shape);
	    	for (Troncon troncon : intersection.getTronconsSortants().values()) {
	    		Intersection destination = troncon.getDestination();
				g2d.drawLine((int)intersection.getLongitude(),(int)intersection.getLatitude() ,
						(int)destination.getLongitude(),(int)destination.getLatitude());
			}
	    	
		}
	    
	}
	
	public void miseALEchelle(){
		if(plan != null){
			//coefX = (double) (LARGEUR_PLAN) / (double)(plan.getLattitudeMax() - plan.getLattitudeMin());
			//coefY = (double) (HAUTEUR_PLAN) / (double)(plan.getLongitudeMax() - plan.getLongitudeMin());
		}
	}
	
	
	
}
