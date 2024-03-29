package vue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import javax.swing.JScrollPane;
import model.Chemin;
import model.ContraintesTournee;
import model.Intersection;
import model.Plan;
import model.PointEnlevement;
import model.PointLivraison;
import model.Tournee;
import model.Troncon;
/**
 * Concerne le bon d�roulement de l'affichage du plan
 *
 */
public class AffichagePlan extends JScrollPane {

	private static final long serialVersionUID = 1L;

	/**
	 * Page d'accueil : chargement du plan
	 */
	public enum Etat {
		LIVRAISON, ENLEVEMENT, MODIF_ADRESSE, MODIF_TEMPS;
	}

	private Etat etat;

	// Plan charg� via le fichier XML
	private Plan plan;

	// Contraintes charg�es via le fichier XML
	private ContraintesTournee contraintes;

	// Trajet de livraison
	private Tournee tournee;

	// Liste de couleurs pour les points
	private List<Color> couleurs;

	// Determine s'il est possible de cliquer sur le plan
	private boolean planClickable;

	// Point de pickUp ajoute
	private Intersection nouveauPickUp;

	// Point de livraison ajoute
	private Intersection nouvelleLivraison;
	
	// Nouvelle intersection pour un pick-up/delivery
	private Intersection nouvelleAdresse;
	

	// InterSection du chemin � mettre en surbrillance
	private Intersection intersectionSelectionne;

	private int nouveauTempsPickUp;
	private int nouveauTempsDelivery;

	// Ecouteur de la souris
	private EcouteurSouris ecouteurSouris;

	// Zoom
	private static double FACTEUR_ZOOM = 1.1f;
	private double zoom;
	private double zoomPrecedent;
	private double xOffset = 0;
	private double yOffset = 0;
	private boolean zoomIn;
	private boolean zoomOut;
	private Stack<Double> xOldMouseX;
	private Stack<Double> yOldMouseY;
	private double mouseX;
	private double mouseY;

	// Drag and drop
	private int xDiff;
	private int yDiff;
	private int newxDiff;
	private int newyDiff;
	private boolean mouseReleased;
	
	private double largeurPlan;
	private double hauteurPlan;

	
//////////////////////////// CONSTRUCTEURS ////////////////////////////
	
	
	/**
     * Constructeur de la classe Affichage Plan
     * 
     * @param plan
     * 		Le plan � afficher
     * @param
     * 		La fenetre qui contient l'instance de la classe
     */
	public AffichagePlan(Plan plan, Fenetre fenetre) {
		this.plan = plan;
		chargementCouleurs();
		this.planClickable = false;
		this.etat = etat.LIVRAISON;
		
		//Ajout des �couteurs souris
		this.ecouteurSouris = new EcouteurSouris(this, fenetre);
		this.addMouseListener(ecouteurSouris);
		this.addMouseWheelListener(ecouteurSouris);
		this.addMouseMotionListener(ecouteurSouris);
		
		//Initialisation des variables li�es au zoom et au drag & drop
		this.zoom = 1f;
		this.zoomPrecedent = 1f;
		zoomIn = false;
		zoomOut = false;
		xOldMouseX = new Stack<Double>();
		yOldMouseY = new Stack<Double>();
		xDiff = 0;
		yDiff = 0;
		newxDiff = 0;
		newyDiff =0;
		mouseReleased = true;
		
		largeurPlan = Fenetre.LARGEUR_PLAN;
		hauteurPlan = Fenetre.HAUTEUR_PLAN;
		
		nouveauTempsPickUp = 0;
		nouveauTempsDelivery = 0;
	}

	
//////////////////////////// GETTERS ET SETTERS ////////////////////////////	
	
	public double getxOffset() {
		return xOffset;
	}

	public double getyOffset() {
		return yOffset;
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

	public int getNouveauTempsPickUp() {
		return nouveauTempsPickUp;
	}

	public void setNouveauTempsPickUp(int nouveauTempsPickUp) {
		this.nouveauTempsPickUp = nouveauTempsPickUp;
	}

	public int getNouveauTempsDelivery() {
		return nouveauTempsDelivery;
	}

	public void setNouveauTempsDelivery(int nouveauTempsDelivery) {
		this.nouveauTempsDelivery = nouveauTempsDelivery;
	}

	public int getxDiff() {
		return xDiff;
	}

	public void setxDiff(int xDiff) {
		this.xDiff = xDiff;
	}

	public int getyDiff() {
		return yDiff;
	}

	public void setyDiff(int yDiff) {
		this.yDiff = yDiff;
	}
	
	public int getnewxDiff() {
		return newxDiff;
	}

	public void setnewxDiff(int newxDiff) {
		this.newxDiff = newxDiff;
	}

	public int getnewyDiff() {
		return yDiff;
	}

	public void setnewyDiff(int newyDiff) {
		this.newyDiff = newyDiff;
	}

	public double getZoom() {
		return zoom;
	}

	public void setZoom(float zoom) {
		this.zoom = zoom;
	}

	public double getZoomPrecedent() {
		return zoomPrecedent;
	}

	public void setMouseX(double mouseX) {
		this.mouseX = mouseX;
	}

	public void setMouseY(double mouseY) {
		this.mouseY = mouseY;
	}

	public boolean isRelease() {
		return mouseReleased;
	}

	public void setRelease(boolean release) {
		this.mouseReleased = release;
	}
	
	public double getLargeurPlan() {
		return largeurPlan;
	}


	public double getHauteurPlan() {
		return hauteurPlan;
	}
	
	public void setIntersectionSelectionne(Intersection intersectionSelectionne) {
		this.intersectionSelectionne = intersectionSelectionne;
	}
	
	public Intersection getNouvelleAdresse() {
		return nouvelleAdresse;
	}

	public void setNouvelleAdresse(Intersection nouvelleAdresse) {
		this.nouvelleAdresse = nouvelleAdresse;
	}

	
//////////////////////////// METHODES DE LA CLASSE ////////////////////////////	
	
	/**
     * Fonction qui applique un zoom vers l'avant en augmentant
     * le zoom et les dimensions du plan.
     * 
     */	
	public void ZoomIn() {
		this.zoom = this.zoom * FACTEUR_ZOOM;
		zoomIn = true;
		zoomOut = false;
		this.largeurPlan *= FACTEUR_ZOOM;
		this.hauteurPlan *= FACTEUR_ZOOM;
		this.repaint();
	}
	
	/**
     * Fonction qui applique un zoom vers l'arri�re en diminuant
     * le zoom et les dimensions du plan.
     * 
     */	
	public void ZoomOut() {
		this.zoom = this.zoom / FACTEUR_ZOOM;
		zoomIn = false;
		zoomOut = true;
		this.largeurPlan /= FACTEUR_ZOOM;
		this.hauteurPlan /= FACTEUR_ZOOM;
		this.repaint();
	}
	
	/**
     * Charge les couleurs al�atoires � utiliser pour attribuer
     * des couleurs aux points de livraison et d'enl�vement.
     */
	public void chargementCouleurs() {
		couleurs = new ArrayList<Color>();
		Random rand = new Random();
		for (int i = 0; i < 100; i++) {
			couleurs.add(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
		}
	}

	
	// Le code de la fonction ci-dessous a �t� fortement inspir� par le lien suivant
	// https://stackoverflow.com/questions/6543453/zooming-in-and-zooming-out-within-a-panel
	
	/**
     * Ajuste la position du plan en fonction du zoom et du d�placement � la souris
     * 
     * @param g2d
     * 		Composant graphique sur lequel est dessin� le plan
     */
	public void ajusterZoom(Graphics2D g2d) {
		AffineTransform at = new AffineTransform();

		double zoomDiv = zoom / zoomPrecedent;
		if (zoomIn) {
			xOffset = (zoomDiv) * xOffset + (1 - zoomDiv) * mouseX;
			yOffset = (zoomDiv) * yOffset + (1 - zoomDiv) * mouseY;
			xOldMouseX.push(mouseX);
			yOldMouseY.push(mouseY);

		} else if (zoomOut && !xOldMouseX.isEmpty() && !yOldMouseY.isEmpty()) {
			xOffset = (zoomDiv) * xOffset + (1 - zoomDiv) * (xOldMouseX.pop() - xDiff);
			yOffset = (zoomDiv) * yOffset + (1 - zoomDiv) * (yOldMouseY.pop() - yDiff);
		}
		if (zoom == 1f) {
			xOffset = 0;
			yOffset = 0;
			xDiff = 0;
			yDiff = 0;
			largeurPlan = Fenetre.LARGEUR_PLAN;
			hauteurPlan = Fenetre.HAUTEUR_PLAN;
		}

		if (mouseReleased) {
			xDiff += newxDiff;
			yDiff += newyDiff;
			xOffset += newxDiff;
			yOffset += newyDiff;
			yDiff += newyDiff;
			newxDiff = 0;
			newyDiff = 0;
		}

		at.translate(xOffset + newxDiff, yOffset + newyDiff);
		at.scale(zoom, zoom);
		zoomPrecedent = zoom;
		g2d.transform(at);
	}
	
	
	/**
     * M�thode surcharg� qui redessine les �l�ments graphiques de la classe.
     * 
     * @param g
     * 		Param�tre par d�faut de la m�thode surcharg�
     */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Random rand = new Random();
		Graphics2D g2d = (Graphics2D) g;

		ajusterZoom(g2d);		
		
		// AFFiCHAGE DU PLAN
		if (plan != null) {
			for (Intersection intersection : plan.getIntersections().values()) {
				for (Troncon troncon : intersection.getTronconsSortants().values()) {
					Intersection destination = troncon.getDestination();
					g2d.drawLine((int) intersection.getLongitude(), (int) intersection.getLatitude(),
							(int) destination.getLongitude(), (int) destination.getLatitude());
				}
			}
			
			//AFFICHAGE DE LA TOURNEE
			if (tournee != null) {
				List<Chemin> plusCourtChemin = tournee.getPlusCourteTournee();
				int cptColor = 0;
				Color couleurLigne;
				boolean trouve = false;
				for (Chemin c : plusCourtChemin) {
					List<Intersection> inters = c.getIntersections();
					int epaisseur = 2;
					if(intersectionSelectionne != null && inters.contains(intersectionSelectionne) && !trouve){
						couleurLigne = Color.RED;
						epaisseur = 4;
						trouve = true;
					}else{
						couleurLigne = getArrowColor(cptColor);
					}
					
					int k = 0;
					for (int i = 0; i < inters.size() - 1; ++i) {
						Intersection inter = inters.get(i);

						if (inter.getTronconsSortants().size() > 3 || k == 3) {
							LineArrow line = new LineArrow((int) inter.getLongitude(), (int) inter.getLatitude(),
									(int) inters.get(i + 1).getLongitude(), (int) inters.get(i + 1).getLatitude(),
									couleurLigne, epaisseur);
							line.draw(g2d);
							k = 0;
						} else {
							g2d.setStroke(new BasicStroke(epaisseur));
							g2d.setPaint(couleurLigne);
							g2d.draw(new Line2D.Float((int) inter.getLongitude(), (int) inter.getLatitude(),
									(int) inters.get(i + 1).getLongitude(), (int) inters.get(i + 1).getLatitude()));
							k++;
						}
					}
					cptColor++;
				}
			}
			
			//AFFICHAGE DES CONTRAINTES
			if (contraintes != null) {
				Intersection depot = contraintes.getDepot();
				Rectangle2D.Double depotg = new Rectangle2D.Double(depot.getLongitude() - 5, depot.getLatitude() - 5,
						15, 15);
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

			if (nouvelleLivraison != null) {
				Ellipse2D.Double pointLivraison = new Ellipse2D.Double(nouvelleLivraison.getLongitude() - 5,
						nouvelleLivraison.getLatitude() - 5, 10, 10);
				g2d.setPaint(Color.RED);
				g2d.draw(pointLivraison);
			}

			if (nouveauPickUp != null) {
				Rectangle2D.Double pointEnlevement = new Rectangle2D.Double(nouveauPickUp.getLongitude() - 5,
						nouveauPickUp.getLatitude() - 5, 10, 10);
				g2d.setPaint(Color.RED);
				g2d.draw(pointEnlevement);
			}
			
			if (nouvelleAdresse != null) {
				Ellipse2D.Double pointNouvelleAdresse = new Ellipse2D.Double(nouvelleAdresse.getLongitude() - 5,
						nouvelleAdresse.getLatitude() - 5, 10, 10);
				g2d.setPaint(Color.RED);
				g2d.draw(pointNouvelleAdresse);
			}
		}
	}
	
	/**
     * Retourne la couleur du chemin � dessiner.
     * 
     * @param i
     * 		Position du chemin sur la tourn�e.
     * 
     * @return une instance de classe Color
     * 		Retourne la couleur du chemin
     */
	public Color getArrowColor(int i) {
		int k = i % 3;
		if (k == 0) {
			return new Color(226, 226, 72);
		} else if (k == 1) {
			return new Color(229, 138, 86);
		} else if (k == 2) {
			return new Color(150, 120, 57);
		}
		return new Color(0, 0, 0);
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

			// Desenha a linha. Corta 10 pixels na ponta para a ponta nï¿½o

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