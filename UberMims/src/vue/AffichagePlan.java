package vue;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
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
	public enum Etat{
		LIVRAISON,
		ENLEVEMENT;
		}
	
	private Etat etat;
	// Endroit de placement du plan dans la page
	private Point placementPlan;

	// Plan charg� via le fichier XML
	private Plan plan;

	// Contraintes charg�es via le fichier XML
	private ContraintesTournee contraintes;

	// Trajet de livraison
	private Tournee tournee;

	// Liste de couleurs pour les points
	private List<Color> couleurs;
	

	private boolean planClickable;

	//Point de pickUp ajoute
	private Intersection nouveauPickUp;
	
	//Point de livraison ajoute
	private Intersection nouvelleLivraison;

	
	public AffichagePlan(Plan plan, Fenetre fenetre) {
		this.plan = plan;
		chargementCouleurs();
		this.planClickable = false;
		this.addMouseListener(new EcouteurSouris(this, fenetre));
		this.etat = etat.LIVRAISON;
	}
	
	public void setPlanClickable(boolean planClickable) {
		this.planClickable = planClickable;
	}
	
	public boolean getPlanClickable() {
		return this.planClickable;
	}
	
	public Etat getEtat() {
		return etat;
	}

	public void setEtat(Etat etat) {
		this.etat = etat;
	}
	
	
	public Plan getPlan() {
		return this.plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public void setTournee(Tournee tournee) {
		this.tournee = tournee;
	}
	
	public void setContraintes(ContraintesTournee contraintes) {
		this.contraintes = contraintes;
	}
	
	public Intersection getNouveauPickUp() {
		return nouveauPickUp;
	}

	public Intersection getNouvelleLivraison() {
		return nouvelleLivraison;
	}

	public void setNouveauPickUp(Intersection nouveauPickUp) {
		this.nouveauPickUp = nouveauPickUp;
	}

	public void setNouvelleLivraison(Intersection nouvelleLivraison) {
		this.nouvelleLivraison = nouvelleLivraison;
	}

	public void chargementCouleurs() {
		couleurs = new ArrayList<Color>();
		Random rand = new Random();
		for (int i = 0; i < 100; i++) {
			couleurs.add(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Random rand = new Random();
		Graphics2D g2d = (Graphics2D) g;
		if (plan != null) {
			for (Intersection intersection : plan.getIntersections().values()) {
				Ellipse2D.Double shape = new Ellipse2D.Double(intersection.getLongitude() - 1,
						intersection.getLatitude() - 1, 2, 2);
				g2d.draw(shape);
				g2d.fill(shape);
				for (Troncon troncon : intersection.getTronconsSortants().values()) {
					Intersection destination = troncon.getDestination();
					g2d.drawLine((int) intersection.getLongitude(), (int) intersection.getLatitude(),
							(int) destination.getLongitude(), (int) destination.getLatitude());
				}
			}

			if (tournee != null) {
				List<Chemin> plusCourtChemin = tournee.getPlusCourteTournee();
				for (Chemin c : plusCourtChemin) {
					List<Intersection> inters = c.getIntersections();
					int k = 0;
					for (int i = 0; i < inters.size() - 1; ++i) {
						Intersection inter = inters.get(i);

						if (inter.getTronconsSortants().size() > 3 || k == 3) {
							LineArrow line = new LineArrow((int) inter.getLongitude(), (int) inter.getLatitude(),
									(int) inters.get(i + 1).getLongitude(), (int) inters.get(i + 1).getLatitude(),
									Color.ORANGE, 2);
							line.draw(g2d);
							k = 0;
						} else {
							g2d.setStroke(new BasicStroke(2));
							g2d.setPaint(Color.ORANGE);
							g2d.draw(new Line2D.Float((int) inter.getLongitude(), (int) inter.getLatitude(),
									(int) inters.get(i + 1).getLongitude(), (int) inters.get(i + 1).getLatitude()));
							k++;
						}
					}
				}
			}

			if (contraintes != null) {
				Intersection depot = contraintes.getDepot();
				Rectangle2D.Double depotg = new Rectangle2D.Double(depot.getLongitude() - 5, depot.getLatitude() - 5,
						10, 10);
				g2d.setPaint(Color.black);
				g2d.fill(depotg);

				List<PointEnlevement> pointsEnlevement = contraintes.getPointsEnlevement();
				List<PointLivraison> pointsLivraison = contraintes.getPointsLivraison();

				for (int i = 0; i < pointsEnlevement.size(); i++) {
					Rectangle2D.Double pointEnlevement = new Rectangle2D.Double(
							pointsEnlevement.get(i).getLongitude() - 5, pointsEnlevement.get(i).getLatitude() - 5, 10,
							10);
					Ellipse2D.Double pointLivraison = new Ellipse2D.Double(pointsLivraison.get(i).getLongitude() - 5,
							pointsLivraison.get(i).getLatitude() - 5, 10, 10);

					g2d.setPaint(couleurs.get(i));
					g2d.fill(pointEnlevement);
					g2d.fill(pointLivraison);
				}
			}
			
			if(nouvelleLivraison != null){
				Ellipse2D.Double pointLivraison = new Ellipse2D.Double(nouvelleLivraison.getLongitude() - 5,
						nouvelleLivraison.getLatitude() - 5, 10, 10);
				g2d.setPaint(Color.RED);
				g2d.draw(pointLivraison);
			}
			
			if(nouveauPickUp != null){
				Rectangle2D.Double pointEnlevement = new Rectangle2D.Double(nouveauPickUp.getLongitude() - 5,
						nouveauPickUp.getLatitude() - 5, 10, 10);
				g2d.setPaint(Color.RED);
				g2d.draw(pointEnlevement);
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

	// The code snippet below was found on the forum
	// https://itqna.net/questions/3389/how-draw-arrow-using-java2d

	private static final Polygon ARROW_HEAD = new Polygon();

	static {
		ARROW_HEAD.addPoint(0, 0);
		ARROW_HEAD.addPoint(-5, -10);
		ARROW_HEAD.addPoint(5, -10);
	}

	public static class LineArrow {

		private final int x;
		private final int y;
		private final int endX;
		private final int endY;
		private final Color color;
		private final int thickness;

		public LineArrow(int x, int y, int x2, int y2, Color color, int thickness) {
			super();
			this.x = x;
			this.y = y;
			this.endX = x2;
			this.endY = y2;
			this.color = color;
			this.thickness = thickness;
		}

		public void draw(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;

			// Calcula o �ngulo da seta.
			double angle = Math.atan2(endY - y, endX - x);

			g2.setColor(color);
			g2.setStroke(new BasicStroke(thickness));

			// Desenha a linha. Corta 10 pixels na ponta para a ponta n�o ficar
			// grossa.
			g2.drawLine(x, y, (int) (endX - 10 * Math.cos(angle)), (int) (endY - 10 * Math.sin(angle)));

			// Obt�m o AffineTransform original.
			AffineTransform tx1 = g2.getTransform();

			// Cria uma c�pia do AffineTransform.
			AffineTransform tx2 = (AffineTransform) tx1.clone();

			// Translada e rotaciona o novo AffineTransform.
			tx2.translate(endX, endY);
			tx2.rotate(angle - Math.PI / 2);

			// Desenha a ponta com o AffineTransform transladado e rotacionado.
			g2.setTransform(tx2);
			g2.fill(ARROW_HEAD);

			// Restaura o AffineTransform original.
			g2.setTransform(tx1);
		}
	}

}
