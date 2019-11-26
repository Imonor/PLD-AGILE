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

	private Color backgroundBleuCiel = new Color(191, 252, 251);
	private Color backgroundTurquoise = new Color(25, 174, 186);
	private Color backgroundJaune = new Color(226, 179, 72);
	private Color backgroundOrange = new Color(229, 138, 86);

	private final int LARGEUR_PLAN = 1200;
	private final int HAUTEUR_PLAN = 800;

	private double coefX;
	private double coefY;

	private Color backgroundColor = new Color(191, 252, 251);
	private final int LARGEUR_FENETRE = 1200;
	private final int HAUTEUR_FENETRE = 800;

	private Plan plan;
	private AffichagePlan affichagePlan;

	private Controleur controleur;
	// Panels
	private JPanel panAccueil = new JPanel();
	private JPanel panPrincipal = new JPanel();
	private JPanel panGauche = new JPanel();
	private JPanel panDroite = new JPanel();
	private JPanel panChargePlan = new JPanel();
	private JPanel panPlan = new JPanel();
	private JPanel panChargeLivraison = new JPanel();
	private JPanel panInfoLivraison = new JPanel();

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
		panAccueil.setBackground(backgroundBleuCiel);
		panAccueil.setSize(1200, 800);
		panAccueil.setVisible(true);
		boutonChargementPlan.setBounds(400, 350, 400, 200);
		panAccueil.add(boutonChargementPlan);
		boutonChargementPlan.addActionListener(ecouteurBoutons);
		this.setContentPane(panAccueil);

		// Panel Presentation : affichage de la page globale apres chargement de plan
		panPrincipal.setVisible(false);
		panPrincipal.setLayout(null);
		panPrincipal.setBackground(backgroundBleuCiel);
		panPrincipal.setSize(1200, 800);

		// Panel de DROITE : partie qui contiendra le chargement de livraison et les
		// informations de la livraison
		panDroite.setVisible(true);
		panDroite.setLayout(null);
		panDroite.setBounds(800, 0, 400, 800);
		panDroite.setBackground(backgroundTurquoise);
		panPrincipal.add(panDroite);

		// Panel de GAUCHE : partie qui contiendra le plan et le nom du plan+ bouton
		// chargement d'un autre plan
		panGauche.setVisible(true);
		panGauche.setLayout(null);
		panGauche.setBackground(Color.white);
		panGauche.setBounds(0, 0, 800, 800);
		panPrincipal.add(panGauche);

		// Panel CHARGEMENT PLAN : haut dessus du plan : partie qui contiendra le plan
		// et le nom du plan+ bouton chargement d'un autre plan
		panChargePlan.setVisible(true);
		panChargePlan.setLayout(null);
		panChargePlan.setBackground(backgroundJaune);
		panChargePlan.setBounds(0, 0, 400, 200);
		panGauche.add(panChargePlan);

		// Panel de CHARGEMENT LIVRAISON : partie qui contiendra le bouton de chargement
		// d'une livraison
		panChargeLivraison.setVisible(true);
		panChargeLivraison.setLayout(null);
		panChargeLivraison.setBounds(400, 0, 400, 200);
		panChargeLivraison.setBackground(backgroundOrange);
			//bouton chargement livraison
		boutonChargementLivraison.setVisible(true);
		boutonChargementLivraison.setBounds(40,50,200,50);
		panChargeLivraison.add(boutonChargementLivraison);
		boutonChargementLivraison.addActionListener(ecouteurBoutons);
		panGauche.add(panChargeLivraison);

		// Panel de PLAN : partie qui contiendra le plan et le nom du plan+ bouton
		// chargement d'un autre plan
		panPlan.setVisible(true);
		panPlan.setLayout(null);
		panPlan.setBackground(backgroundBleuCiel);
		panPlan.setBounds(0, 200, 800, 600);
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

	public static void main(String[] args) {
		Fenetre fen = new Fenetre();

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (plan != null)
			affichagePlan.dessinerPlan(g);
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}
}