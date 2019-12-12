package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import controleur.Controleur;
import model.Intersection;
import model.Plan;
import model.Tournee;
import model.Troncon;
import model.ContraintesTournee;
import vue.AffichagePlan.Etat;

public class Fenetre extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Color backgroundBleuCiel = new Color(191, 252, 251);
	private Color backgroundTurquoiseClair = new Color(135, 216, 217);
	private Color backgroundTurquoise = new Color(25, 174, 186);
	private Color backgroundJaune = new Color(226, 179, 72);
	private Color backgroundOrange = new Color(229, 138, 86);
	private Color backgroundRougeClair = new Color(184, 64, 57);

	public static final int LARGEUR_PLAN = 750;
	public static final int HAUTEUR_PLAN = 750;

	private double coefX;
	private double coefY;
	private final int LARGEUR_FENETRE = 1200;
	private final int HAUTEUR_FENETRE = 800;
	private int nouveauTempsPickUp=0;
	private int nouveauTempsDelivery=0 ;

	private Plan plan;
	private Tournee tournee;
	private ContraintesTournee contraintes;

	private Controleur controleur;
	// Panels
	private JPanel panAccueil = new JPanel();
	private JComboBox c1;
	private JPanel panPrincipal = new JPanel();
	private JPanel panGauche = new JPanel();
	private JLabel textInfo = new JLabel();
	private JPanel panDroite = new JPanel();
	private JPanel panCalculTournee = new JPanel();
	private JPanel panHautDroite = new JPanel();
	private JPanel panChargePlan = new JPanel();
	private JPanel panChargeTournee = new JPanel();
	private JPanel panInfoLivraison = new JPanel();
	private JPanel panHautGauche = new JPanel();
	private AffichagePlan affichagePlan = new AffichagePlan(plan, this);
	private AffichageTournee affichageTournee = new AffichageTournee(plan);
	private JPanel panAjoutLivraisonGlobal = new JPanel();
	private JPanel panAnnulerAjoutLivraison = new JPanel();
	private JPanel panAjoutLivraison1 = new JPanel();
	private JPanel panAjoutLivraison2 = new JPanel();
	private JPanel panAjoutLivraison3 = new JPanel();

	private ModificationTournee panModificationTournee;
	private JFormattedTextField champDelivery = new JFormattedTextField(NumberFormat.getIntegerInstance());
	private JFormattedTextField champPickUp = new JFormattedTextField(NumberFormat.getIntegerInstance());
	private Font police = new Font("Avenir", 0, 15);
	private JLabel textePickUp = new JLabel();
	private JLabel texteDelivery = new JLabel();
	private JLabel texteBienvenue = new JLabel ();



	public Fenetre() {

		controleur = new Controleur();
		champPickUp.setValue(0);
		champDelivery.setValue(0);
		
		panModificationTournee = new ModificationTournee(this, controleur);


		// Page globale
		this.setTitle("Accueil UberMims");
		this.setSize(1200, 800);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(true);
		this.setLayout(new BorderLayout());

		// BOUTONS
		EcouteurBoutons ecouteurBoutons = new EcouteurBoutons(controleur, this);
		JButton boutonChargementPlan = new JButton("Charger le plan de la ville");
		JButton boutonChargementTournee = new JButton("Charger une demande de tournee");
		JButton boutonChargementPlan2 = new JButton("Charger un autre plan de la ville");
		JButton boutonCalculTournee = new JButton("Calculer une tournee");
		JButton boutonAjouterLivraison = new JButton("Ajouter une livraison a la tournee");
		JButton boutonAnnulerAjoutLivraison = new JButton("Annuler l'ajout d'une livraison");
		JButton boutonValiderAjoutLivraison = new JButton("Valider l'ajout d'une livraison");
		JButton boutonModifierTournee = new JButton("Modifier la tournee");
		JButton boutonAnnulerModification = new JButton("Annuler la derniere modification");

//************** ACCUEIL ****************//
		// Panel Accueil : affichage du bouton "Chargement plan"
		panAccueil.setLayout(null);
		panAccueil.setBackground(backgroundBleuCiel);
		panAccueil.setSize(1200, 800);
		panAccueil.setVisible(true);
		// Logo

		// Bouton Chargement plan
		boutonChargementPlan.setBounds(450, 500, 300, 50);
		panAccueil.add(boutonChargementPlan);
		boutonChargementPlan.addActionListener(ecouteurBoutons);
		this.setContentPane(panAccueil);

//***************************************//

		// Panel Presentation : affichage de la page globale apres chargement de plan
		panPrincipal.setVisible(false);
		panPrincipal.setLayout(null);
		panPrincipal.setBackground(backgroundBleuCiel);
		panPrincipal.setSize(1200, 800);

//************** DROITE ****************//
		// Panel de DROITE : partie principale de droite
		panDroite.setVisible(true);
		panDroite.setLayout(null);
		panDroite.setBounds(750, 0, 450, 800);
		panDroite.setBackground(backgroundTurquoiseClair);
		texteBienvenue.setText("BIENVENUE !");
		texteBienvenue.setVisible(true);
		texteBienvenue.setFont(new Font("Avenir", 1, 30));
		texteBienvenue.setForeground(backgroundTurquoise);
		texteBienvenue.setBounds(120,300,200,200);
		panDroite.add(texteBienvenue);
		panPrincipal.add(panDroite);

		// Panel HAUT DROITE : haut tout a droite
		panHautDroite.setVisible(false);
		panHautDroite.setLayout(null);
		panHautDroite.setBackground(backgroundRougeClair);
		panHautDroite.setBounds(0, 0, 450, 100);
		// bouton ajouter livraison
		boutonAjouterLivraison.setVisible(true);
		boutonAjouterLivraison.setBounds(95,7, 260, 30);
		panHautDroite.add(boutonAjouterLivraison);
		boutonAjouterLivraison.addActionListener(ecouteurBoutons);
		//bouton annuler la derniere modification
		boutonAnnulerModification.setVisible(true);
		boutonAnnulerModification.setEnabled(false);
		boutonAnnulerModification.setBounds(95, 71, 260, 30);
		panHautDroite.add(boutonAnnulerModification);
		boutonAnnulerModification.addActionListener(ecouteurBoutons);
		panDroite.add(panHautDroite);
		// bouton modifier ordre livraison
		boutonModifierTournee.setVisible(true);
		boutonModifierTournee.setBounds(95, 39, 260, 30);
		panHautDroite.add(boutonModifierTournee);
		boutonModifierTournee.addActionListener(ecouteurBoutons);
		
		panDroite.add(panHautDroite);

		// Panel de CALCUL TOURNEE : partie qui contient le bouton Calculer Tournee
		panCalculTournee.setVisible(false);
		panCalculTournee.setLayout(null);
		panCalculTournee.setBounds(0, 0, 450, 800);
		panCalculTournee.setBackground(backgroundTurquoise);
		// bouton chargement autre plan
		boutonCalculTournee.setVisible(true);
		boutonCalculTournee.setBounds(75, 350, 300, 75);
		panCalculTournee.add(boutonCalculTournee);
		boutonCalculTournee.addActionListener(ecouteurBoutons);
		panDroite.add(panCalculTournee);

		// Panel INFORMATIONS : partie à droite qui contient les infos de la Tournee, apparait quand bouton calul de tournee cliqué
		affichageTournee.setVisible(false);
		affichageTournee.setLayout(null);
		affichageTournee.setBounds(22, 120, 410, 638);
		affichageTournee.setBackground(backgroundTurquoiseClair);
		panDroite.add(affichageTournee);

		// Panel AJOUT LIVRAISON
		panAjoutLivraisonGlobal.setVisible(false);
		panAjoutLivraisonGlobal.setLayout(null);
		panAjoutLivraisonGlobal.setBackground(backgroundRougeClair);
		panAjoutLivraisonGlobal.setBounds(0, 0, 450, 800);
		panDroite.add(panAjoutLivraisonGlobal);
		
		// Panel Annuler livraison
		panAnnulerAjoutLivraison.setVisible(true);
		panAnnulerAjoutLivraison.setLayout(null);
		panAnnulerAjoutLivraison.setBackground(backgroundRougeClair);
		panAnnulerAjoutLivraison.setBounds(0, 0, 450, 100);
		// bouton annuler ajout de livraison
		boutonAnnulerAjoutLivraison.setVisible(true);
		boutonAnnulerAjoutLivraison.setBounds(95, 10, 260, 30);
		boutonAnnulerAjoutLivraison.addActionListener(ecouteurBoutons);
		panAnnulerAjoutLivraison.add(boutonAnnulerAjoutLivraison);
		panAjoutLivraisonGlobal.add(panAnnulerAjoutLivraison);

		// Panel DETAILS AJOUT LIVRAISON 1
		panAjoutLivraison1.setVisible(true);
		panAjoutLivraison1.setLayout(null);
		panAjoutLivraison1.setBackground(backgroundTurquoiseClair);
		panAjoutLivraison1.setBounds(0, 100, 450, 700);
		panAjoutLivraisonGlobal.add(panAjoutLivraison1);
		// Texte Ajout Livraison Debut
		JLabel CliquezSurCarte1 = new JLabel(
				"<html> <center> Cliquez sur la carte pour selectionner le point d'<b>enlevement </b> de la nouvelle livraison<br>");
		CliquezSurCarte1.setFont(police);
		CliquezSurCarte1.setBounds(75, 200, 300, 200);
		panAjoutLivraison1.add(CliquezSurCarte1);
		panAjoutLivraisonGlobal.add(panAjoutLivraison1);

		// Panel DETAILS AJOUT LIVRAISON 2
		panAjoutLivraison2.setVisible(false);
		panAjoutLivraison2.setLayout(null);
		panAjoutLivraison2.setBackground(backgroundTurquoiseClair);
		panAjoutLivraison2.setBounds(0, 100, 450, 700);
		// Texte Ajout Livraison 2
		JLabel CliquezSurCarte2 = new JLabel(
				"<html> <center> Cliquez sur la carte pour selectionner le point de <b>livraison </b> de la nouvelle livraison<br>");
		CliquezSurCarte2.setFont(police);
		CliquezSurCarte2.setBounds(75, 200, 300, 200);
		panAjoutLivraison2.add(CliquezSurCarte2);
		panAjoutLivraisonGlobal.add(panAjoutLivraison2);

		// Panel DETAILS AJOUT LIVRAISON 3
		panAjoutLivraison3.setVisible(false);
		panAjoutLivraison3.setLayout(null);
		panAjoutLivraison3.setBackground(backgroundTurquoiseClair);
		panAjoutLivraison3.setBounds(0, 100, 450, 700);
		panAjoutLivraisonGlobal.add(panAjoutLivraison3);
		// CHAMP ENLEVEMENT

		JLabel texteMinutes1 = new JLabel("<html> minutes<br> ");
		texteMinutes1.setVisible(true);
		texteMinutes1.setFont(police);
		texteMinutes1.setBounds(205, 155, 100, 30);
		panAjoutLivraison3.add(texteMinutes1);

		JLabel texteMinutes2 = new JLabel("<html> minutes<br> ");
		texteMinutes2.setVisible(true);
		texteMinutes2.setFont(police);
		texteMinutes2.setBounds(205, 405, 100, 30);
		panAjoutLivraison3.add(texteMinutes2);
		// bouton Valider
		boutonValiderAjoutLivraison.setVisible(true);
		boutonValiderAjoutLivraison.setBounds(75, 600, 300, 30);
		boutonValiderAjoutLivraison.addActionListener(ecouteurBoutons);
		panAjoutLivraison3.add(boutonValiderAjoutLivraison);
		
		
		//PANEL MODIFICATION DE TOURNEE
		panModificationTournee.setVisible(false);
		panAjoutLivraison3.setLayout(null);
		panModificationTournee.setBounds(20, 120, 400, 600);
		panModificationTournee.setBackground(Color.white);
		panDroite.add(panModificationTournee);

//***************************************//

//************** GAUCHE ****************//
		// Panel de GAUCHE : partie qui contiendra le plan et le bouton chargement
		// tournee + bouton
		// chargement d'un autre plan
		panGauche.setVisible(true);
		panGauche.setLayout(null);
		panGauche.setBounds(0, 0, 800, 800);
		panPrincipal.add(panGauche);

		// Panel CHARGEMENT PLAN : haut dessus du plan : partie qui contiendra le plan
		// et le nom du plan+ bouton chargement d'un autre plan
		panChargePlan.setVisible(true);
		panChargePlan.setLayout(null);
		panChargePlan.setBackground(backgroundOrange);
		panChargePlan.setBounds(375, 0, 375, 50);
		// bouton chargement autre plan
		boutonChargementPlan2.setVisible(true);
		boutonChargementPlan2.setBounds(60, 10, 255, 30);
		panChargePlan.add(boutonChargementPlan2);
		boutonChargementPlan2.addActionListener(ecouteurBoutons);
		panGauche.add(panChargePlan);

		// Panel de CHARGEMENT TOURNEE : partie qui contiendra le bouton de chargement
		// d'une livraison
		panChargeTournee.setVisible(true);
		panChargeTournee.setLayout(null);
		panChargeTournee.setBounds(0, 0, 375, 50);
		panChargeTournee.setBackground(backgroundJaune);
		// bouton chargement tournee
		boutonChargementTournee.setVisible(true);
		boutonChargementTournee.setBounds(60, 10, 255, 30);
		panChargeTournee.add(boutonChargementTournee);
		boutonChargementTournee.addActionListener(ecouteurBoutons);
		panGauche.add(panChargeTournee);

		// Panel PLAN
		affichagePlan.setVisible(true);
		affichagePlan.setLayout(null);
		affichagePlan.setBackground(backgroundBleuCiel);
		affichagePlan.setBounds(0, 50, LARGEUR_PLAN, HAUTEUR_PLAN);
		panGauche.add(affichagePlan);
//***************************************//

	}

	public JPanel getPanAccueil() {
		return panAccueil;
	}

	public JPanel getPanPrincipal() {
		return panPrincipal;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
		this.affichagePlan.setPlan(plan);
	}

	public Plan getPlan() {
		return this.plan;
	}

	// Passage a la page principale apres le chargement d'un plan
	public void afficherPanPrincipal() {
		panAjoutLivraisonGlobal.setVisible(false);
		affichageTournee.setVisible(false);
		affichageTournee.removeAll();
		panAccueil.setVisible(false);
		panPrincipal.setVisible(true);
		this.setContentPane(panPrincipal);

		this.repaint();
	}

	// Affichage du bouton calculer tournee apres le chargement d'une tournee
	public void afficherBoutonCalcul() {
		texteBienvenue.setVisible(false);
		panAjoutLivraisonGlobal.setVisible(false);
		affichageTournee.setVisible(false);
		affichageTournee.removeAll();
		panCalculTournee.setVisible(true);
		this.setContentPane(panPrincipal);
		this.repaint();
	}

		// Affichage des informations apres avoir clique sur bouton calculer tournee

		public void afficherInfos() {
			panAjoutLivraison1.setVisible(false);
			panAjoutLivraison2.setVisible(false);
			panAjoutLivraison3.setVisible(false);
			panAjoutLivraisonGlobal.setVisible(false);
			panCalculTournee.setVisible(false);
			panModificationTournee.setVisible(false);
			panHautDroite.setVisible(true);
			//panInformation.setVisible(true);
			affichageTournee.setVisible(true);
			this.setContentPane(panPrincipal);
			// this.repaint();
		}

	// Affichage des champs d'ajout de livraison
	public void afficherAjoutLivraison() {
		affichageTournee.setVisible(false);
		panHautDroite.setVisible(false);
		panAjoutLivraisonGlobal.setVisible(true);
		panAjoutLivraison1.setVisible(true);
		affichagePlan.setEtat(Etat.ENLEVEMENT);
		this.setContentPane(panPrincipal);
		// this.repaint();
	}

	// Passage aux details d'ajout de livraison 2
	public void afficherAjoutLivraison2() {
		panAjoutLivraison1.setVisible(false);
		panAjoutLivraison2.setVisible(true);
		affichagePlan.setEtat(Etat.LIVRAISON);
		this.setContentPane(panPrincipal);
	}

	// Passage aux details d'ajout de livraison 3
	public void afficherAjoutLivraison3() {
		panAjoutLivraison2.setVisible(false);
		infosPointsAjout();
		panAjoutLivraison3.setVisible(true);
		affichagePlan.setPlanClickable(false);
		this.setContentPane(panPrincipal);
	}

	public void apresAjoutLivraison() {
		panAjoutLivraison3.setVisible(false);
		this.setContentPane(panPrincipal);
	}
	
	public void apresModifOrdre() {
		affichageTournee.afficherDetailTournee(controleur.getTournee(), controleur.getContraintes());
	}

	// Affichage Infos du point ajoute avant clic sur bouton valider ajout
	public void infosPointsAjout() {
		System.out.println("Infos points Ajout");
		// Infos pick-up
		Intersection nouveauPickUp = affichagePlan.getNouveauPickUp();

		String idTronconNouveauPickUp = nouveauPickUp.getTronconsSortants().keySet().iterator().next();
		Troncon tronconNouveauPickUp = nouveauPickUp.getTronconsSortants().get(idTronconNouveauPickUp);
		textePickUp.removeAll();
		texteDelivery.removeAll();
		textePickUp.setText("<html><center><b>Point d'enlevement : </b></center><br> ");
		textePickUp.setVisible(true);
		textePickUp.setFont(police);
		textePickUp.setBounds(75, 0, 350, 200);
		textePickUp.setText(textePickUp.getText() + "<font color=\"B84039\"> &rarr; Adresse : "
				+ tronconNouveauPickUp.getNomRue() + "</font><br><br>Duree au point d'enlevement : <br>");
		panAjoutLivraison3.add(textePickUp);
		// Champ pickup
		champPickUp.setVisible(true);
		champPickUp.setText("0");
		champPickUp.setFont(police);
		champPickUp.setBounds(100, 155, 100, 30);
		champPickUp.setBackground(Color.white);
		panAjoutLivraison3.add(champPickUp);
		nouveauTempsPickUp = ((Number) champPickUp.getValue()).intValue();
		// Champ livraison
		champDelivery.setVisible(true);
		champDelivery.setText("0");
		champDelivery.setFont(police);
		champDelivery.setBounds(100, 405, 100, 30);
		champDelivery.setBackground(Color.white);
		panAjoutLivraison3.add(champDelivery);

		Intersection nouveauDelivery = affichagePlan.getNouvelleLivraison();
		String idTronconNouveauDelivery = nouveauDelivery.getTronconsSortants().keySet().iterator().next();
		Troncon tronconNouveauDelivery = nouveauDelivery.getTronconsSortants().get(idTronconNouveauDelivery);
		texteDelivery.setText("<html><center><b>Point de livraison : </b> </center><br> ");
		texteDelivery.setVisible(true);
		texteDelivery.setFont(police);
		texteDelivery.setBounds(75, 250, 350, 200);
		// textePickUp.setForeground(new Color(69, 73, 74));
		texteDelivery.setText(texteDelivery.getText() + "<font color=\"B84039\"> &rarr; Adresse : "
				+ tronconNouveauDelivery.getNomRue() + "</font><br><br>Duree au point de livraison :<br>");
		panAjoutLivraison3.add(texteDelivery);
	}

	
	public void afficherModificationTournee() {
		panModificationTournee.ajouterTournee(plan);
		panModificationTournee.afficherTournee();
		affichageTournee.setVisible(false);
		panModificationTournee.setVisible(true);
	}

	
	// ***** INFOS TOURNEE *****
	// Passage a la page principale apres le chargement d'un plan

	
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

	public JFormattedTextField getChampPickUp() {
		return champPickUp;
	}
	
	public JFormattedTextField getChampDelivery() {
		return champDelivery;
	}
	
	public Tournee getTournee() {
		return tournee;
	}
	
	public ContraintesTournee getContraintes() {
		return contraintes;
	}
	
	public AffichagePlan getAffichagePlan() {
		return this.affichagePlan;
	}
	
	public AffichageTournee getAffichageTournee() {
		return this.affichageTournee;
	}

	public void setTournee(Tournee tournee) {
		this.tournee = tournee;
		this.affichagePlan.setTournee(tournee);
		
	}

	public void setContraintes(ContraintesTournee contraintes) {
		this.contraintes = contraintes;
		this.affichagePlan.setContraintes(contraintes);
	}
	
	public ModificationTournee getPanModificationTournee() {
		return panModificationTournee;
	}
}