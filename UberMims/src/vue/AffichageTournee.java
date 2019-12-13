package vue;

import java.awt.BasicStroke;
import static javax.swing.ScrollPaneConstants.*;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.*;

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
import vue.AffichagePlan.LineArrow;

public class AffichageTournee extends JPanel {
	
	private Fenetre fenetre;
	private Plan plan;
	private Color backgroundBleuCiel = new Color(191, 252, 251);
	private Color backgroundTurquoiseClair = new Color(135, 216, 217);
	private Color backgroundTurquoise = new Color(25, 174, 186);
	private Color backgroundJaune = new Color(226, 179, 72);
	private Color backgroundOrange = new Color(229, 138, 86);
	private Color backgroundRougeClair = new Color(184, 64, 57);
	private Font police = new Font("Avenir", 0, 15);
	
	private Intersection intersectionClique;
	
	private Tournee tournee;
	private ContraintesTournee contraintestournee;
	
	List<JLabel> jlabels;
	JLabel textInfo; 
	JPanel resultsPanel;
	
	private List<String> idPointsEnlevement;
	private List<String> idPointsLivraison;
	
	Map<String, PointEnlevement> ptEnlevement;
	Map<String, PointLivraison> ptLivraison;
	
	Map<String, Integer> indexationPointsE = new HashMap<>();
	Map<String, Integer> indexationPointsL = new HashMap<>();

	Map<String, Color> colorPointsE = new HashMap<>();
	Map<String, Color> colorPointsL = new HashMap<>();
	
	int heure; 
	int minute;
	int seconde;


	public Intersection getIntersectionClique() {
		return intersectionClique;
	}

	public void setIntersectionClique(Intersection intersectionClique) {
		this.intersectionClique = intersectionClique;
	}
	
	

	private List<Color> listColors = new ArrayList();
	public AffichageTournee(Plan plan, Fenetre fenetre) {
		this.fenetre = fenetre;
		this.plan = plan;
		listColors.add(Color.decode("#2F4F4F"));
		listColors.add(Color.decode("#808080"));
		listColors.add(Color.decode("#800000"));
		listColors.add(Color.decode("#8B4513"));
		listColors.add(Color.decode("#D2691E"));
		listColors.add(Color.decode("#191970"));
		listColors.add(Color.decode("#4169E1"));
		listColors.add(Color.decode("#556B2F"));
		listColors.add(Color.decode("#006400"));
		listColors.add(Color.decode("#3CB371"));
		listColors.add(Color.decode("#32CD32"));
		listColors.add(Color.decode("#BDB76B"));
		listColors.add(Color.decode("#FF4500"));
		listColors.add(Color.decode("#DC143C"));
		listColors.add(Color.decode("#FA8072"));
		listColors.add(Color.decode("#4B0082"));
		listColors.add(Color.decode("#8B008B"));
		listColors.add(Color.decode("#C71585"));
		listColors.add(Color.decode("#9400D3"));
		listColors.add(Color.decode("#6365ff"));
	}

	public void afficherDetailTournee(Tournee tournee, ContraintesTournee contraintestournee) {
        this.tournee = tournee;
        this.contraintestournee = contraintestournee;
		
		this.setFont(new Font("Avenir",1,15));
		
		jlabels = new ArrayList<JLabel>();
		
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        this.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
 
        JPanel panelAll = new JPanel();
        panelAll.setBackground(Color.red);
        panelAll.setLayout(layout);
        
        JPanel panelDetail = new JPanel();
        panelDetail.setBackground(backgroundTurquoiseClair);
        panelDetail.setLayout(null);
        
        JPanel separation = new JPanel();
        separation.setBackground(backgroundTurquoiseClair);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty=3.5;
        gbc.fill = GridBagConstraints.BOTH;

        
        this.add(panelAll, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty=0.2;
        gbc.fill = GridBagConstraints.BOTH;

        
        this.add(separation, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.5;
        gbc.weighty=1.5;
        gbc.fill = GridBagConstraints.BOTH;
        
        this.add(panelDetail, gbc);
        
        textInfo = new JLabel();
        textInfo.setBounds(10,-20,400,200);
 		panelDetail.add(textInfo);
        
        //----------------------
        
        /*JPanel topMargin = new JPanel();
        panelAll.setBackground(Color.pink);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty=0.0;
        gbc.weightx=0.0;
        panelAll.add(topMargin, gbc);
        */
        JPanel textArea = new JPanel();
        textArea.setLayout(new BorderLayout());
        textArea.setBackground(backgroundTurquoiseClair);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty= 1.5;
        gbc.weightx= 1.5;
        panelAll.add(textArea, gbc);
        
        
        
        /*JPanel bottomMargin = new JPanel();
        panelAll.setBackground(Color.magenta);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty=0.0;
        gbc.weightx= 0.0;
        panelAll.add(bottomMargin, gbc);
        */
        
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(backgroundTurquoiseClair);

        JScrollPane scrollpane = new JScrollPane(resultsPanel);
        scrollpane.getVerticalScrollBar().setUnitIncrement(16);
        scrollpane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        resultsPanel.setBackground(backgroundBleuCiel);
        scrollpane.setBackground(backgroundBleuCiel);
        scrollpane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        textArea.add(scrollpane, BorderLayout.CENTER);
       
        
        //------------------------------------------------------------------
        
        JLabel infoGeneral = new JLabel("<html> <center> <BLOCKQUOTE> <BLOCKQUOTE> <b>ITINERAIRE PROPOSE : </center> <br><br> Adresse de depart : </b>");
        infoGeneral.setForeground(new Color(69,73,74));
		
        if(!tournee.getPlusCourteTournee().isEmpty()) {
			String adresseDepart = tournee.getPlusCourteTournee().get(0).getIntersections().get(0).getTronconsSortants().get(tournee.getPlusCourteTournee().get(0).getIntersections().get(1).getId()).getNomRue();
			infoGeneral.setText(infoGeneral.getText() + adresseDepart + ". <br>");
			
			LocalTime tempsDepart = contraintestournee.getHeureDepart();
			infoGeneral.setText(infoGeneral.getText() + "<b>Heure de depart : </b>" + tempsDepart + "<br> <br> </left>");
			
			idPointsEnlevement = new ArrayList<>();
			idPointsLivraison = new ArrayList<>();
	
			
			ptEnlevement =  new HashMap<>();
			ptLivraison =  new HashMap<>();
			
			for(int i = 0; i < contraintestournee.getPointsEnlevement().size(); i++) {
				idPointsEnlevement.add(contraintestournee.getPointsEnlevement().get(i).getId());
				ptEnlevement.put(contraintestournee.getPointsEnlevement().get(i).getId(), contraintestournee.getPointsEnlevement().get(i));
			}
			
			for(int j = 0; j < contraintestournee.getPointsLivraison().size(); j++) {
				idPointsLivraison.add(contraintestournee.getPointsLivraison().get(j).getId());
				ptLivraison.put(contraintestournee.getPointsLivraison().get(j).getId(), contraintestournee.getPointsLivraison().get(j));
			}
			
			indexationPointsE =  new HashMap<>();
			indexationPointsL =  new HashMap<>();
			
			colorPointsE =  new HashMap<>();
			colorPointsL =  new HashMap<>();
			Random rand = new Random();

	
			int i = 1;
			for(PointEnlevement crntPointE: contraintestournee.getPointsEnlevement() ) {
				indexationPointsE.put(crntPointE.getId(), i);
				colorPointsE.put(crntPointE.getId(), listColors.get(i));
				for(PointLivraison crntPointL: contraintestournee.getPointsLivraison() ) {
					if(crntPointL.getIdEnlevement().equals(crntPointE.getId())){
						indexationPointsL.put(crntPointL.getId(), i);
						colorPointsL.put(crntPointL.getId(), listColors.get(i));
					}
		    	}
				i++;
	    	}
			
			resultsPanel.add(infoGeneral);
			
			//setdDetailsTournee();
	        
			for (int k = 0; k < tournee.getPlusCourteTournee().size(); k++) {
				JLabel jlabel = new JLabel("<html> ");
				jlabel.setName(Integer.toString(k));
				
				jlabel.addMouseListener(new MouseAdapter() {
				    public void mouseClicked(MouseEvent e) {
				    	//Affichage Details Texte
					 	textInfo.removeAll();
					 	String lab = (String) e.getSource().toString();

				    	String indBeforeTest = lab.substring(19, 21);
				    	if (indBeforeTest.contains(",")) {
				    		indBeforeTest = indBeforeTest.substring(0, 1);
				    	}
					 	int index = Integer.parseInt(indBeforeTest);
					 	Chemin current = tournee.getPlusCourteTournee().get(index);
					 	
					 	intersectionClique = current.getIntersections().get(1);
					 	fenetre.getAffichagePlan().setIntersectionSelectionne(intersectionClique);
					 	int tailleC = current.getIntersections().size();
					 	Chemin previous;
					 	String depart;
					 	String arrivee = current.getIntersections().get(tailleC-2).getTronconsSortants().get(current.getIntersections().get(tailleC-1).getId()).getNomRue();
					 	if(index != 0) {
					 		previous = tournee.getPlusCourteTournee().get(index-1);
					 		int tailleInters = previous.getIntersections().size();
					 		depart = previous.getIntersections().get(tailleInters - 2).getTronconsSortants().get(previous.getIntersections().get(tailleInters - 1).getId()).getNomRue();
					 	} else {
					 		previous = null;
					 		depart = tournee.getPlusCourteTournee().get(0).getIntersections().get(0).getTronconsSortants().get(tournee.getPlusCourteTournee().get(0).getIntersections().get(1).getId()).getNomRue();;
					 	}
					 	
					 	String itineraire = "<html> <font color=\"424242\"> "+depart + " &rarr; ";
					 	String tmp = depart;
					 		        
					 	for (int i = 0; i < current.getIntersections().size()-1; i++) {
					 		Intersection crntInters = current.getIntersections().get(i);
					 		Intersection nextInters = current.getIntersections().get(i+1);
						 	if (!tmp.equals(crntInters.getTronconsSortants().get(nextInters.getId()).getNomRue())) {
						 		itineraire = itineraire + crntInters.getTronconsSortants().get(nextInters.getId()).getNomRue() + " &rarr; ";
						 	}
					 		tmp = crntInters.getTronconsSortants().get(nextInters.getId()).getNomRue();     	
					 	}
					 		        
					 	itineraire = itineraire.substring(0, itineraire.length()-7);
					 	textInfo.setText(("<html> <b> <font color=\"424242\"> Pour acceder a " + arrivee + " a partir de " + depart + " : </b><br> " + itineraire + "</font> </html>"));
				 		AffichageTournee.this.repaint();
				 		fenetre.getAffichagePlan().repaint();
				    }
				});
			 		
				Chemin c = tournee.getPlusCourteTournee().get(k);
				List<Intersection> inters =  c.getIntersections();
				int taille = inters.size();
				Intersection inter = inters.get(taille-1);
				Intersection interPrevious = inters.get(taille-2);
	
				Troncon tronc = interPrevious.getTronconsSortants().get(inter.getId());
				
					if (idPointsEnlevement.contains(inter.getId())) {
						int duree = c.getDuree();
						int livraison = ptEnlevement.get(inter.getId()).getTempsEnlevement();
						
						int tempsChemin[] = traitementTempsChemin(duree);
						int tempsLivraison[] = traitementTempsLivraison(livraison);
	
						LocalTime heurePassage = tournee.getHeureDePassage(inter.getId());
						
					    int indexation = indexationPointsE.get(inter.getId());
					    jlabel.setForeground(colorPointsE.get(inter.getId()));
						jlabel.setText(jlabel.getText() + "Pick Up numero " + indexation + " :   <br>");
						jlabel.setText(jlabel.getText() + "&rarr; Adresse : " + tronc.getNomRue() +"<br>");	
						jlabel.setText(jlabel.getText() + "&rarr; Heure de passage : " + heurePassage +"<br>");
						jlabel.setText(jlabel.getText() + "&rarr; Temps de pick up : " + tempsLivraison[1] + " minutes.<br><br>");

					} else if (idPointsLivraison.contains(inter.getId())) {
						int duree = c.getDuree();
						int livraison = ptLivraison.get(inter.getId()).getTempsLivraison();
						int tempsChemin[] = traitementTempsChemin(duree);
						int tempsLivraison[] = traitementTempsLivraison(livraison);
						
						LocalTime heurePassage = tournee.getHeureDePassage(inter.getId());						
					    int indexation = indexationPointsL.get(inter.getId());
					    jlabel.setForeground(colorPointsL.get(inter.getId()));
						jlabel.setText(jlabel.getText() + "Delivery numero " + indexation + " :   <br>");
						jlabel.setText(jlabel.getText() + "&rarr; Adresse : " + tronc.getNomRue() +"<br>");	
						jlabel.setText(jlabel.getText() + "&rarr; Heure de passage : " + heurePassage +"<br>");
						jlabel.setText(jlabel.getText() + "&rarr; Temps de delivery : " + tempsLivraison[1] + " minutes.<br><br>");
						
						
					} 
		        	jlabel.setPreferredSize(new Dimension(400, 100));
		            resultsPanel.add(jlabel);
		            jlabels.add(jlabel);
			}
	        tournee.calculDuree();
			int duree = tournee.getDuree() ;
			duree = (int) duree/60;
			JLabel time = new JLabel("<html> Duree totale : " + duree + " minutes. </center> </html>");
			resultsPanel.add(time);
			
			for (int extra = 0; extra < 4 ; extra++) {
	        	JLabel j = new JLabel("");
	        	j.setPreferredSize(new Dimension(400, 100));
	            resultsPanel.add(j);
	        }
        }
        
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
	
	
	public void setdDetailsTournee(){
		boolean trouve = false;
		for (int k = 0; k < tournee.getPlusCourteTournee().size(); k++) {			
			JLabel jlabel = jlabels.get(k);
			
		    boolean miseEnAvant = false; 
			Chemin c = tournee.getPlusCourteTournee().get(k);
			List<Intersection> inters =  c.getIntersections();
			
			if( intersectionClique != null && inters.contains(intersectionClique) && !trouve ){
				miseEnAvant = true;
				trouve = true;
			}
			int taille = inters.size();
			Intersection inter = inters.get(taille-1);
			Intersection interPrevious = inters.get(taille-2);

			Troncon tronc = interPrevious.getTronconsSortants().get(inter.getId());
				
			if(miseEnAvant){
				jlabel.setText("<html> <div style=\"color:Red;\">");
			}
			else{
				jlabel.setText("<html>");
			}
				if (idPointsEnlevement.contains(inter.getId())) {
					int duree = c.getDuree();
					int livraison = ptEnlevement.get(inter.getId()).getTempsEnlevement();
					
					int tempsChemin[] = traitementTempsChemin(duree);
					int tempsLivraison[] = traitementTempsLivraison(livraison);

					LocalTime heurePassage = tournee.getHeureDePassage(inter.getId());
					
				    int indexation = indexationPointsE.get(inter.getId());
				    jlabel.setForeground(colorPointsE.get(inter.getId()));
					jlabel.setText(jlabel.getText() + "Pick Up numero " + indexation + " :   <br>");
					jlabel.setText(jlabel.getText() + "&rarr; Adresse : " + tronc.getNomRue() +"<br>");	
					jlabel.setText(jlabel.getText() + "&rarr; Heure de passage : " + heurePassage +"<br>");
					jlabel.setText(jlabel.getText() + "&rarr; Temps de pick up : " + tempsLivraison[1] + " minutes.<br><br>");

				} else if (idPointsLivraison.contains(inter.getId())) {
					int duree = c.getDuree();
					int livraison = ptLivraison.get(inter.getId()).getTempsLivraison();
					int tempsChemin[] = traitementTempsChemin(duree);
					int tempsLivraison[] = traitementTempsLivraison(livraison);
					
					LocalTime heurePassage = tournee.getHeureDePassage(inter.getId());						
				    int indexation = indexationPointsL.get(inter.getId());
				    jlabel.setForeground(colorPointsL.get(inter.getId()));
					jlabel.setText(jlabel.getText() + "Delivery numero " + indexation + " :   <br>");
					jlabel.setText(jlabel.getText() + "&rarr; Adresse : " + tronc.getNomRue() +"<br>");	
					jlabel.setText(jlabel.getText() + "&rarr; Heure de passage : " + heurePassage +"<br>");
					jlabel.setText(jlabel.getText() + "&rarr; Temps de delivery : " + tempsLivraison[1] + " minutes.<br><br>");
					
					
				}
				if(miseEnAvant){
					jlabel.setText(jlabel.getText() + "</div>");
				}
		}
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(intersectionClique != null){
			setdDetailsTournee();
		}
		
	}
}