package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import controleur.Controleur;
import model.Chemin;
import model.Intersection;
import model.Plan;
import model.Tournee;
import model.Troncon;
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

	private final int LARGEUR_PLAN = 800;
	private final int HAUTEUR_PLAN = 600;

	private double coefX;
	private double coefY;

	private Color backgroundColor = new Color(191, 252, 251);
	private final int LARGEUR_FENETRE = 1200;
	private final int HAUTEUR_FENETRE = 800;

	private Plan plan;

	private Controleur controleur;
	// Panels
	private JPanel panAccueil = new JPanel();
	private JPanel panPrincipal = new JPanel();
	private JPanel panGauche = new JPanel();
	private JPanel panDroite = new JPanel();
	private JPanel panInformation = new JPanel();
	private JPanel panChargePlan = new JPanel();
	private JPanel panChargeTournee = new JPanel();
	private JPanel panInfoLivraison = new JPanel();

	private JPanel panHautGauche = new JPanel();
	private AffichagePlan affichagePlan = new AffichagePlan(plan);

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
		JButton boutonChargementTournee = new JButton("Charger une demande de tournee");	
		JButton boutonChargementPlan2 = new JButton("Charger un autre plan de la ville");	

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
		
		panInformation.setVisible(false);
		panInformation.setLayout(null);
		panInformation.setBounds(800, 0, 400, 800);
		panInformation.setBackground(Color.pink);
		panPrincipal.add(panInformation);

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
		//bouton chargement autre plan
		boutonChargementPlan2.setVisible(true);
		boutonChargementPlan2.setBounds(50,75,300,75);
		panChargePlan.add(boutonChargementPlan2);
		boutonChargementPlan2.addActionListener(ecouteurBoutons);
		panGauche.add(panChargePlan);

		// Panel de CHARGEMENT TOURNEE : partie qui contiendra le bouton de chargement
		// d'une livraison
		panChargeTournee.setVisible(true);
		panChargeTournee.setLayout(null);
		panChargeTournee.setBounds(400, 0, 400, 200);
		panChargeTournee.setBackground(backgroundOrange);
			//bouton chargement tournee
		boutonChargementTournee.setVisible(true);
		boutonChargementTournee.setBounds(50,75,300,75);
		panChargeTournee.add(boutonChargementTournee);
		boutonChargementTournee.addActionListener(ecouteurBoutons);
		panGauche.add(panChargeTournee);
		
		//Panel PLAN
		affichagePlan.setVisible(true);
		affichagePlan.setLayout(null);
		affichagePlan.setBackground(backgroundBleuCiel);	
		affichagePlan.setBounds(0, 200, 800, 600);
		panGauche.add(affichagePlan);

	}

	public JPanel getPanAccueil() {
		return panAccueil;
	}

	public JPanel getPanPrincipal() {
		return panPrincipal;
	}
	
	public void setPlan(Plan plan){
		this.plan = plan;
		this.affichagePlan.SetPlan(plan);
	}
	
	public Plan getPlan(){
		return this.plan;
	}
	
	// Passage a la page principale apres le chargement d'un plan
	public void afficherPanPrincipal() {
		panAccueil.setVisible(false);
		panPrincipal.setVisible(true);
		this.setContentPane(panPrincipal);

		this.repaint();
	}

	// Passage a la page principale apres le chargement d'un plan
	public void afficherDetailTournee(Tournee tournee) {
		JLabel jlabel = new JLabel("<html> Itinéraire proposé : <br>");
		jlabel.setFont(new Font("Verdana",1,10));
		panDroite.setVisible(false);
		panInformation.setVisible(true);
		panInformation.add(jlabel);
		panInformation.setLayout(null);
		jlabel.setBounds(100, 150, 200, 600);
		String previous = "x";
		
		for (Chemin c : tournee.getPlusCourteTournee()) {
			List<Intersection> inters =  c.getIntersections();
			for(int i = 0; i< inters.size() -1; ++i) {
				Intersection inter = inters.get(i);
				Troncon tronc = inter.getTronconsSortants().get(inters.get(i+1).getId());
				if(!previous.equals(tronc.getNomRue())) {
					jlabel.setText(jlabel.getText() + tronc.getNomRue() + ", <br> ");
					previous = tronc.getNomRue();
				}
			}
		}
		
		jlabel.setText(jlabel.getText()+" <br> Durée totale : " + tournee.getDuree() + " secondes. </html>");
	}

	public static void main(String[] args) {
		Fenetre fen = new Fenetre();
	}

	
	@Override
	public void paint(Graphics g) {
        super.paint(g);
    }
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
}