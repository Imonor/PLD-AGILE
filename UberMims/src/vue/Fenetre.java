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
import java.awt.event.ItemEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import controleur.Controleur;
import model.Chemin;
import model.Intersection;
import model.Plan;
import model.PointEnlevement;
import model.PointLivraison;
import model.Tournee;
import model.Troncon;
import model.ContraintesTournee;
import model.Intersection;
import model.Plan;
import model.Tournee;
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
	private Color backgroundRougeClair = new Color(184, 64, 57);

	public static final int LARGEUR_PLAN = 750;
	public static final int HAUTEUR_PLAN = 750;

	private double coefX;
	private double coefY;

	private Color backgroundColor = new Color(191, 252, 251);
	private final int LARGEUR_FENETRE = 1200;
	private final int HAUTEUR_FENETRE = 800;

	private Plan plan;
	private Tournee tournee;
	private ContraintesTournee contraintes;

	private Controleur controleur;
	// Panels
	private JPanel panAccueil = new JPanel();
	private JComboBox c1; 
	private JPanel panPrincipal = new JPanel();
	private JPanel panGauche = new JPanel();
	private JPanel panInformation = new JPanel();
	private JPanel panInformationAll = new JPanel();
	private JPanel panInformationDetail = new JPanel();
	private JLabel textInfo = new JLabel();
	private JPanel panDroite = new JPanel();
	private JPanel panCalculTournee = new JPanel();
	private JPanel panHautDroite = new JPanel();
	private JPanel panChargePlan = new JPanel();
	private JPanel panChargeTournee = new JPanel();
	private JPanel panInfoLivraison = new JPanel();
	private JPanel panHautGauche = new JPanel();
	private AffichagePlan affichagePlan = new AffichagePlan(plan);
	
	
	public Fenetre() {

		controleur = new Controleur();

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
		JButton boutonCalculTournee = new JButton("Calculer une tournee");
		JButton boutonAjouterLivraison = new JButton("Ajouter une livraison");

		
//************** ACCUEIL ****************//
		// Panel Accueil : affichage du bouton "Chargement plan"
		panAccueil.setLayout(null);
		panAccueil.setBackground(backgroundBleuCiel);
		panAccueil.setSize(1200, 800);
		panAccueil.setVisible(true);
		//Logo
	

		//Bouton Chargement plan
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
		panDroite.setBackground(Color.CYAN);
		panPrincipal.add(panDroite);
		
		// Panel HAUT DROITE : haut tout a droite
		panHautDroite.setVisible(false);
		panHautDroite.setLayout(null);
		panHautDroite.setBackground(backgroundRougeClair);
		panHautDroite.setBounds(0, 0, 450, 200);
		JLabel titreLegende = new JLabel("<html> <center> Legende <br>");
		titreLegende.setFont(new Font("Verdana", 1, 10));
		panHautDroite.setLayout(null);
		titreLegende.setBounds(10, 10, 100, 30);
		panHautDroite.add(titreLegende);
		panDroite.add(panHautDroite);
		

		// Panel de CALCUL TOURNEE : partie qui contient le bouton Calculer Tournee
		panCalculTournee.setVisible(false);
		panCalculTournee.setLayout(null);
		panCalculTournee.setBounds(0, 0, 450, 800);
		panCalculTournee.setBackground(backgroundTurquoise);
		// bouton chargement autre plan
		boutonCalculTournee.setVisible(true);
		boutonCalculTournee.setBounds(75, 300, 300, 75);
		panCalculTournee.add(boutonCalculTournee);
		boutonCalculTournee.addActionListener(ecouteurBoutons);
		panDroite.add(panCalculTournee);

		// Panel INFORMATIONS : partie à droite qui contient les infos de la Tournee, apparait quand bouton calul de tournee cliqué
		panInformation.setVisible(false);
		panInformation.setLayout(null);
		panInformation.setBounds(0, 200, 450, 800);
		panInformation.setBackground(Color.white);
		panDroite.add(panInformation);
		
		
		panInformationAll.setVisible(false);
		//panInformationAll.setLayout(null);
		panInformationAll.setBounds(0, 200, 450, 400);
		panInformationAll.setBackground(Color.green);
		panDroite.add(panInformationAll);
		
		panInformationDetail.setVisible(false);
		panInformationDetail.setLayout(null);
		panInformationDetail.setBounds(0, 600, 450, 300);
		panInformationDetail.setBackground(Color.red);
		panDroite.add(panInformationDetail);
//***************************************//
		

//************** GAUCHE ****************//
		// Panel de GAUCHE : partie qui contiendra le plan et le bouton chargement tournee + bouton
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
		panChargePlan.setBackground(backgroundOrange);
		panChargePlan.setBounds(375, 0, 375, 50);
		// bouton chargement autre plan
		boutonChargementPlan2.setVisible(true);
		boutonChargementPlan2.setBounds(50, 10, 300, 30);
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
				boutonChargementTournee.setBounds(50, 10, 300, 30);
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
		panInformation.setVisible(false);
		panInformation.removeAll();
		panAccueil.setVisible(false);
		panPrincipal.setVisible(true);
		this.setContentPane(panPrincipal);

		this.repaint();
	}
	
	// Affichage du bouton calculer tournee apres le chargement d'une tournee
		public void afficherBoutonCalcul() {
			panInformation.setVisible(false);
			panInformation.removeAll();
			panCalculTournee.setVisible(true);
			this.setContentPane(panPrincipal);
			this.repaint();
		}

		// Affichage des informations apres avoir clique sur bouton calculer tournee
		public void afficherInfos() {
			panCalculTournee.setVisible(false);
			panHautDroite.setVisible(true);
			//panInformation.setVisible(true);
			panInformationAll.setVisible(true);
			panInformationDetail.setVisible(true);
			this.setContentPane(panPrincipal);
			// this.repaint();
		}

	// ***** INFOS TOURNEE *****
	// Passage a la page principale apres le chargement d'un plan
	public void afficherDetailTournee(Tournee tournee, ContraintesTournee contraintestournee) {

		/*------------------------------- Partie All --------------------------------*/
		JLabel jlabel = new JLabel("<html> <center> Itineraire propose : </center> <left>  <br><br> Adresse de d�part : ");
		jlabel.setFont(new Font("Arial",1,12));
		jlabel.setForeground(new Color(69,73,74));
		
		String adresseDepart = tournee.getPlusCourteTournee().get(0).getIntersections().get(0).getTronconsSortants().get(tournee.getPlusCourteTournee().get(0).getIntersections().get(1).getId()).getNomRue();
		jlabel.setText(jlabel.getText() + adresseDepart + ". <br>");
		
		int heure = contraintestournee.getHeureDepart().getHour();
		int minute = contraintestournee.getHeureDepart().getMinute();
		int seconde = contraintestournee.getHeureDepart().getSecond();
		
		String tempsDepart = heure + ":" + minute + ":" + seconde;
		jlabel.setText(jlabel.getText() + "Heure de d�part : " + tempsDepart + "<br> <br>");
		
		List<String> idPointsEnlevement = new ArrayList<>();
		List<String> idPointsLivraison = new ArrayList<>();
		

		Map<String, Chemin> caminos =  new HashMap<>();
		
		Map<String, PointEnlevement> ptEnlevement =  new HashMap<>();
		Map<String, PointLivraison> ptLivraison =  new HashMap<>();
		
		for(int i = 0; i < contraintestournee.getPointsEnlevement().size(); i++) {
			idPointsEnlevement.add(contraintestournee.getPointsEnlevement().get(i).getId());
			ptEnlevement.put(contraintestournee.getPointsEnlevement().get(i).getId(), contraintestournee.getPointsEnlevement().get(i));
		}
		
		for(int j = 0; j < contraintestournee.getPointsEnlevement().size(); j++) {
			idPointsLivraison.add(contraintestournee.getPointsLivraison().get(j).getId());
			ptLivraison.put(contraintestournee.getPointsLivraison().get(j).getId(), contraintestournee.getPointsLivraison().get(j));
		}
		
		int compteurPickUp = 1;
		int compteurDelivery = 1;
		for (int k = 0; k < tournee.getPlusCourteTournee().size(); k++) {
			Chemin c = tournee.getPlusCourteTournee().get(k);
			List<Intersection> inters =  c.getIntersections();
			Intersection inter = inters.get(0);
			Troncon tronc = inter.getTronconsSortants().get(inters.get(1).getId());
			
			if (k!=0) {
				if (idPointsEnlevement.contains(inter.getId())) {
					int duree = c.getDuree();
					int livraison = ptEnlevement.get(inter.getId()).getTempsEnlevement();
					
					int tempsChemin[] = traitementTempsChemin(duree);
					int tempsLivraison[] = traitementTempsLivraison(livraison);

					heure = heure + tempsChemin[0];
				    minute = minute + tempsChemin[1];
				    seconde = seconde + tempsChemin[2];
				    if (seconde >= 60) {
				    	minute ++;
				      seconde = seconde % 60;
				    }
				    if (minute >= 60) {
				    	minute ++;
				    	minute = minute % 60;
				    }
							
					jlabel.setText(jlabel.getText() + "Pick Up n� " + compteurPickUp + " :   <br>");	
					caminos.put("Pick up n�" + compteurPickUp, c);
					jlabel.setText(jlabel.getText() + "&rarr; Adresse : " + tronc.getNomRue() +"<br>");	
					jlabel.setText(jlabel.getText() + "&rarr; Heure de passage : " + heure + ":" + minute + ":" + seconde +"<br>");
					jlabel.setText(jlabel.getText() + "&rarr; Temps de pick up : " + tempsLivraison[1] + " minutes.<br><br>");
					
					compteurPickUp++;
					
					heure = heure + tempsLivraison[0];
				    minute = minute + tempsLivraison[1];
				    seconde = seconde + tempsLivraison[2];
				    if (seconde >= 60) {
				    	minute ++;
				      seconde = seconde % 60;
				    }
				    if (minute >= 60) {
				    	minute ++;
				    	minute = minute % 60;
				    }
				}else {
					int duree = c.getDuree();
					int livraison = ptLivraison.get(inter.getId()).getTempsLivraison();
					
					int tempsChemin[] = traitementTempsChemin(duree);
					int tempsLivraison[] = traitementTempsLivraison(livraison);

					heure = heure + tempsChemin[0];
				    minute = minute + tempsChemin[1];
				    seconde = seconde + tempsChemin[2];
				    if (seconde >= 60) {
				    	minute ++;
				      seconde = seconde % 60;
				    }
				    if (minute >= 60) {
				    	minute ++;
				    	minute = minute % 60;
				    }
							
					jlabel.setText(jlabel.getText() + "Delivery n� " + compteurDelivery + " :   <br>");	
					caminos.put("Delivery n�" + compteurPickUp, c);
					jlabel.setText(jlabel.getText() + "&rarr; Adresse : " + tronc.getNomRue() +"<br>");	
					jlabel.setText(jlabel.getText() + "&rarr; Heure de passage : " + heure + ":" + minute + ":" + seconde +"<br>");
					jlabel.setText(jlabel.getText() + "&rarr; Temps de delivery : " + tempsLivraison[1] + " minutes.<br><br>");
					
					compteurDelivery++;
					
					heure = heure + tempsLivraison[0];
				    minute = minute + tempsLivraison[1];
				    seconde = seconde + tempsLivraison[2];
				    if (seconde >= 60) {
				    	minute ++;
				      seconde = seconde % 60;
				    }
				    if (minute >= 60) {
				    	minute ++;
				    	minute = minute % 60;
				    }
				}
			}
			
		}

		int duree = tournee.getDuree() ;
		jlabel.setText(jlabel.getText()+" <br> Dur�e totale : " + duree + " minutes. </center> </html>");
		
		JScrollPane scrollPane = new JScrollPane(jlabel);
		scrollPane.setPreferredSize(new Dimension(300, 350));
		scrollPane.getViewport().setBackground(new Color(232,246,248));
		panInformationAll.add(scrollPane, BorderLayout.CENTER);
		
		/*------------------------------- Partie D�tail --------------------------------*/
		
		
		c1 = new JComboBox();
		/*for (int k = 0; k < tournee.getPlusCourteTournee().size(); k++) {
			Chemin c = tournee.getPlusCourteTournee().get(k);
			List<Intersection> inters =  c.getIntersections();
			Intersection inter = inters.get(0);
			Troncon tronc = inter.getTronconsSortants().get(inters.get(1).getId());
			
			if (k!=0) {
				Chemin cprecedent = tournee.getPlusCourteTournee().get(k-1);
				List<Intersection> listeInter =  cprecedent.getIntersections();
				for (int i = 0; i< listeInter.size()-1; i++) {
					Intersection crntInter = listeInter.get(i);
					Troncon crntTronc = crntInter.getTronconsSortants().get(listeInter.get(i+1).getId());
					text = text + " - " + crntTronc.getNomRue();
				}
				
				
				
				
				if (idPointsEnlevement.contains(inter.getId())) {
					c1.addItem("Pick Up - " + tronc.getNomRue() );
					
				}else {
					c1.addItem("Delivery - " + tronc.getNomRue() );
				}
			}
		}*/
	
		
		for ( String key : caminos.keySet() ) {
			c1.addItem( key );
		}

		c1.setBounds(20, 20, 250, 20);
		panInformationDetail.add(c1);

		
		
		textInfo = new JLabel("Afficher le details");
		textInfo.setBounds(20, -30, 350, 200);
		textInfo.setForeground(Color.LIGHT_GRAY);
		panInformationDetail.add(textInfo);
		
		c1.addActionListener(new ActionListener() {
			 
		    @Override
		    public void actionPerformed(ActionEvent event) {
		        JComboBox<String> combo = (JComboBox<String>) event.getSource();
		        String selected = (String) combo.getSelectedItem();
		 
		        if (selected.equals("Effective Java")) {
		            textInfo.setText("hihi mdr c bien");
		        } else  {
		        	textInfo.setText("Nice pick, too!");
		        }
		    }
		});
		
		//panInformationDetail.add(button);
	}
	
	
	public int[] traitementTempsLivraison(int livraison) {
		int livraisonHeure = (int) livraison / 3600;
	    int remainder1 = (int) livraison - livraisonHeure * 3600;
	    int livraisonMinute = remainder1 / 60;
	    remainder1 = remainder1 - livraisonMinute * 60;
	    int livraisonSecond = remainder1;
	    
	    int ret[] = {livraisonHeure, livraisonMinute, livraisonSecond};
	    
	    return ret;
	}
	
	public int[] traitementTempsChemin(int duree) {
		int trajetHeure = (int) duree / 3600;
	    int remainder = (int) duree - trajetHeure * 3600;
	    int trajetMinute = remainder / 60;
	    remainder = remainder - trajetMinute * 60;
	    int trajetSecond = remainder;
	    
	    int ret[] = {trajetHeure, trajetMinute, trajetSecond};
	    
	    return ret;
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

	public Tournee getTournee() {
		return tournee;
	}

	public void setTournee(Tournee tournee) {
		this.tournee = tournee;
		this.affichagePlan.setTournee(tournee);
	}

	public void setContraintes(ContraintesTournee contraintes) {
		this.contraintes = contraintes;
		this.affichagePlan.setContraintes(contraintes);
	}
}