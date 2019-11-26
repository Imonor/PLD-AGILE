package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import controleur.Controleur;
import model.Intersection;
import model.Plan;

import util.XMLParser;

public class Fenetre extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color backgroundColor1 = new Color(191, 252, 251);
	private Color backgroundColor2 = new Color(254, 201, 172);
	private Color backgroundColor3 = new Color(252, 151, 107);

	private final int LARGEUR_PLAN = 1200;
	private final int HAUTEUR_PLAN = 800;

	private double coefX;
	private double coefY;

	private Plan plan;

	private Controleur controleur;
	// Panels
	private JPanel panAccueil = new JPanel();
	private JPanel panPrincipal = new JPanel();
	private JPanel panGauche = new JPanel();
	private JPanel panDroite = new JPanel();
	private JPanel panHautGauche = new JPanel();
	private JPanel panPlan = new JPanel();

	public Fenetre() {

		// Page globale
		this.setTitle("Accueil UberMims");
		this.setSize(1200, 800);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		this.setLayout(new BorderLayout());

		// BOUTONS
		EcouteurBoutons ecouteurBoutons = new EcouteurBoutons(controleur, this);
		JButton boutonChargementPlan = new JButton("Charger le plan de la ville");
		JButton boutonChargementLivraison = new JButton("Charger une livraison");

		// Panel Accueil : affichage du bouton "Chargement plan"
		panAccueil.setLayout(null);
		panAccueil.setBackground(backgroundColor1);
		panAccueil.setSize(1200, 800);
		panAccueil.setVisible(true);
		boutonChargementPlan.setBounds(400, 350, 400, 200);
		panAccueil.add(boutonChargementPlan);
		boutonChargementPlan.addActionListener(ecouteurBoutons);
		this.setContentPane(panAccueil);

		// Panel Presentation : affichage de la page globale apres chargement de plan
		panPrincipal.setVisible(false);
		panPrincipal.setLayout(null);
		panPrincipal.setBackground(backgroundColor2);
		panPrincipal.setSize(1200, 800);

		// Panel de Droite : partie qui contiendra le chargement de livraison et les
		// informations de la livraison
		panDroite.setVisible(true);
		panDroite.setLayout(new FlowLayout());
		panDroite.setBounds(700, 0, 500, 800);
		panDroite.setBackground(Color.red);
		// boutonChargementLivraison.setBounds(400,350,400,200);
		// panDroite.add(boutonChargementLivraison);
		// this.setContentPane(panPrincipal);
		// boutonChargementLivraison.addActionListener(ecouteurBoutons);*/
		panPrincipal.add(panDroite);

		// Panel de Gauche : partie qui contiendra le plan et le nom du plan+ bouton
		// chargement d'un autre plan
		panGauche.setVisible(true);
		panGauche.setLayout(null);
		panGauche.setBackground(backgroundColor3);
		panGauche.setBounds(0, 100, 700, 700);
		panPrincipal.add(panGauche);

		// Panel haut dessus du plan : partie qui contiendra le plan et le nom du plan+
		// bouton chargement d'un autre plan
		panHautGauche.setVisible(true);
		panHautGauche.setLayout(null);
		panHautGauche.setBackground(backgroundColor2);
		panHautGauche.setBounds(0, 0, 700, 35);
		panGauche.add(panHautGauche);

		// Panel de plan : partie qui contiendra le plan et le nom du plan+ bouton
		// chargement d'un autre plan
		panPlan.setVisible(true);
		panPlan.setLayout(null);
		panPlan.setBackground(backgroundColor1);
		panPlan.setBounds(0, 35, 700, 765);
		panGauche.add(panPlan);

	}

	public JPanel getPanAccueil() {
		return panAccueil;
	}

	public JPanel getPanPrincipal() {
		return panPrincipal;
	}

	// Passage a la page principale apres le chargement d'un plan
	public void afficherPanPrincipal() {
		panAccueil.setVisible(false);
		panPrincipal.setVisible(true);
		// panGauche.setVisible(true);
		// panDroite.setVisible(true);
		this.setContentPane(panPrincipal);

		this.repaint();
	}

	// Passage a la page principale apres le chargement d'un plan
	public void afficherDetailLivraison() {
		// this.setContentPane(nouveau pan);
		// fenetre.repaint();
	}

	/*
	 * //***************************************************************************
	 * *********************$ //CREATION PLAN
	 * 
	 * 
	 * 
	 * public void creerPlan(JFrame frame){ String path =
	 * "H:\\Mes documents\\PLDAgile\\PLD-AGILE\\UberMims\\fichiersXML2019\\moyenPlan.xml"
	 * ; plan = XMLParser.chargerPlan(path);
	 * 
	 * //Creation du panel plan JPanel panel = new JPanel(); panel.setSize(500,
	 * 500); Border blackline = BorderFactory.createLineBorder(Color.black);
	 * panel.setBorder(blackline);
	 * 
	 * //this.setContentPane(pan); frame.getContentPane().add(panel,
	 * BorderLayout.CENTER); }
	 * 
	 * public void dessinerIntersections(Graphics g){ Graphics2D g2d = (Graphics2D)
	 * g; miseALEchelle(); int i = 10; for (Intersection intersection :
	 * plan.getIntersections().values()) { //g2d.drawLine(120+i, 50, 360, 50);
	 * Ellipse2D.Double shape = new Ellipse2D.Double(intersection.getLatitude(),
	 * intersection.getLongitude(), 2, 2); g2d.draw(shape); g2d.fill(shape); i+=10;
	 * }
	 * 
	 * //g2d.draw(new Line2D.Double(59.2d, 99.8d, 419.1d, 99.8d));
	 * 
	 * //g2d.draw(new Line2D.Float(21.50f, 132.50f, 459.50f, 132.50f)); }
	 * 
	 * 
	 * public void miseALEchelle(){
	 * 
	 * double lol = plan.getLattitudeMax(); double lol2 = plan.getLattitudeMin();
	 * if(plan != null){ coefX = (double) (LARGEUR_PLAN) /
	 * (double)(plan.getLattitudeMax() - plan.getLattitudeMin()); coefY = (double)
	 * (HAUTEUR_PLAN) / (double)(plan.getLongitudeMax() - plan.getLongitudeMin()); }
	 * }
	 * 
	 * 
	 * 
	 * @Override public void paint(Graphics g) { super.paint(g);
	 * dessinerIntersections(g); }
	 * 
	 * @Override public void update( Graphics g ) { paint( g ); }
	 */

	public static void main(String[] args) {
		Fenetre fen = new Fenetre();
	}
}