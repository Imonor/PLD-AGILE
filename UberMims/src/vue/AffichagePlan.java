package vue;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import controleur.Controleur;
import model.Chemin;
import model.ContraintesTournee;
import model.Intersection;
import model.Plan;
import model.PointEnlevement;
import model.PointLivraison;
import model.Tournee;
import model.Troncon;
import util.XMLParser;

public class AffichagePlan extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Page d'accueil : chargement du plan
	 */

	// Endroit de placement du plan dans la page
	private Point placementPlan;

	// Plan chargé via le fichier XML
	private Plan plan;

	// Contraintes chargées via le fichier XML
	private ContraintesTournee contraintes;

	// Trajet de livraison
	private Tournee tournee;

	public AffichagePlan(Plan plan) {
		this.plan = plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public void setTournee(Tournee tournee) {
		this.tournee = tournee;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		if (plan != null) {
			for (Intersection intersection : plan.getIntersections().values()) {
				Ellipse2D.Double shape = new Ellipse2D.Double(intersection.getLongitude(), intersection.getLatitude(),
						2, 2);
				g2d.draw(shape);
				g2d.fill(shape);
				for (Troncon troncon : intersection.getTronconsSortants().values()) {
					Intersection destination = troncon.getDestination();
					g2d.drawLine((int) intersection.getLongitude(), (int) intersection.getLatitude(),
							(int) destination.getLongitude(), (int) destination.getLatitude());
				}

			}
			if (contraintes != null) {
				Intersection depot = contraintes.getDepot();
				Rectangle2D.Double depotg = new Rectangle2D.Double(depot.getLongitude(),depot.getLatitude(),10, 10);
				g2d.setPaint(Color.black);
				g2d.fill(depotg);
				
				List<PointEnlevement> pointsEnlevement = contraintes.getPointsEnlevement();
				List<PointLivraison> pointsLivraison = contraintes.getPointsLivraison();
				
				for (int i = 0; i < pointsEnlevement.size(); i++) {
					Rectangle2D.Double pointEnlevement = new Rectangle2D.Double(pointsEnlevement.get(i).getLongitude(),pointsEnlevement.get(i).getLatitude(),10, 10);
					Ellipse2D.Double pointLivraison = new Ellipse2D.Double(pointsLivraison.get(i).getLongitude(),pointsLivraison.get(i).getLatitude(),10, 10);
			
					Random rand = new Random();
					
					g2d.setPaint(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
					g2d.fill(pointEnlevement);
					g2d.fill(pointLivraison);
				}
				
				if(tournee != null){
					List<Chemin> plusCourtChemin = tournee.getPlusCourteTournee();
					for (Chemin c : plusCourtChemin) {
						List<Intersection> inters = c.getIntersections();
						for (int i = 0; i < inters.size() - 1; ++i) {
							Intersection inter = inters.get(i);
							g2d.setStroke(new BasicStroke(2));
							g2d.setPaint(Color.ORANGE);
							g2d.draw(new Line2D.Float((int) inter.getLongitude(), (int) inter.getLatitude(),
									(int)  inters.get(i+1).getLongitude(), (int)  inters.get(i+1).getLatitude()));
						}
					}
				}

			}
		}

	}

	public void miseALEchelle() {
		if (plan != null) {
			// coefX = (double) (LARGEUR_PLAN) / (double)(plan.getLattitudeMax()
			// - plan.getLattitudeMin());
			// coefY = (double) (HAUTEUR_PLAN) / (double)(plan.getLongitudeMax()
			// - plan.getLongitudeMin());
		}
	}

	public void setContraintes(ContraintesTournee contraintes) {
		this.contraintes = contraintes;
	}

}
