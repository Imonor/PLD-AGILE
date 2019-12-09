package vue;

import java.awt.BasicStroke;
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
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
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

public class AffichageTournee extends JPanel {

	
	public AffichageTournee(Fenetre fenetre) {
		this.addMouseListener(new EcouteurSouris(this, fenetre));
	}

	public void afficherDetailTournee(Tournee tournee, ContraintesTournee contraintestournee) {
		System.out.println("OK");
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
 
        JPanel panelAll = new JPanel();
        panelAll.setBackground(Color.red);
        panelAll.setLayout(layout);
        
        JPanel panelDetail = new JPanel();
        panelDetail.setBackground(Color.yellow);
        panelDetail.setLayout(null);
        
        JPanel separation = new JPanel();
        separation.setBackground(Color.black);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty=2.5;
        gbc.fill = GridBagConstraints.BOTH;

        
        this.add(panelAll, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty=1.0;
        gbc.fill = GridBagConstraints.BOTH;
        
        this.add(panelDetail, gbc);
        
        JLabel textInfo = new JLabel();
        textInfo.setBounds(10,-50,400,200);
 		panelDetail.add(textInfo);
        
        //----------------------
        
        JPanel topMargin = new JPanel();
        panelAll.setBackground(Color.pink);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty=0.0;
        gbc.weightx=0.0;
        panelAll.add(topMargin, gbc);
        
        JPanel textArea = new JPanel();
        textArea.setLayout(new BorderLayout());
        textArea.setBackground(Color.cyan);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty= 1.5;
        gbc.weightx= 1.5;
        panelAll.add(textArea, gbc);
        
        
        
        JPanel bottomMargin = new JPanel();
        panelAll.setBackground(Color.magenta);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty=0.0;
        gbc.weightx= 0.0;
        panelAll.add(bottomMargin, gbc);
        
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollpane = new JScrollPane(resultsPanel);
        scrollpane.getVerticalScrollBar().setUnitIncrement(16);
        textArea.add(scrollpane, BorderLayout.CENTER);
        
        //------------------------------------------------------------------
        
        JLabel infoGeneral = new JLabel("<html> <left> Itineraire propose : </center> <left>  <br><br> Adresse de depart : ");
        infoGeneral.setFont(new Font("Arial",1,12));
        infoGeneral.setForeground(new Color(69,73,74));
		
		String adresseDepart = tournee.getPlusCourteTournee().get(0).getIntersections().get(0).getTronconsSortants().get(tournee.getPlusCourteTournee().get(0).getIntersections().get(1).getId()).getNomRue();
		infoGeneral.setText(infoGeneral.getText() + adresseDepart + ". <br>");
		
		int heure = contraintestournee.getHeureDepart().getHour();
		int minute = contraintestournee.getHeureDepart().getMinute();
		int seconde = contraintestournee.getHeureDepart().getSecond();
		
		String tempsDepart = heure + ":" + minute + ":" + seconde;
		infoGeneral.setText(infoGeneral.getText() + "Heure de depart : " + tempsDepart + "<br> <br> </left>");
		
		List<String> idPointsEnlevement = new ArrayList<>();
		List<String> idPointsLivraison = new ArrayList<>();

		HashMap<String, Integer> succession =  new HashMap<>();
		
		Map<String, PointEnlevement> ptEnlevement =  new HashMap<>();
		Map<String, PointLivraison> ptLivraison =  new HashMap<>();
		
		for(int i = 0; i < contraintestournee.getPointsEnlevement().size(); i++) {
			idPointsEnlevement.add(contraintestournee.getPointsEnlevement().get(i).getId());
			System.out.println(contraintestournee.getPointsEnlevement().get(i).getId());
			ptEnlevement.put(contraintestournee.getPointsEnlevement().get(i).getId(), contraintestournee.getPointsEnlevement().get(i));
		}
		
		for(int j = 0; j < contraintestournee.getPointsEnlevement().size(); j++) {
			idPointsLivraison.add(contraintestournee.getPointsLivraison().get(j).getId());
			System.out.println(contraintestournee.getPointsLivraison().get(j).getId());
			ptLivraison.put(contraintestournee.getPointsLivraison().get(j).getId(), contraintestournee.getPointsLivraison().get(j));
		}
		
		int compteurPickUp = 1;
		int compteurDelivery = 1;
		
		resultsPanel.add(infoGeneral);
        
		for (int k = 0; k < tournee.getPlusCourteTournee().size(); k++) {
			JLabel jlabel = new JLabel("<html> ");
			jlabel.setName(Integer.toString(k));
			
			jlabel.addMouseListener(new MouseAdapter() {
			    public void mouseClicked(MouseEvent e) {
			         
				 	textInfo.removeAll();
				 	String lab = (String) e.getSource().toString();
				 
				 	int index = Integer.parseInt(lab.substring(19, 20));
				 	Chemin current = tournee.getPlusCourteTournee().get(index);
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
				 	
				 	String itineraire = depart + " -> ";
				 	String tmp = depart;
				 		        
				 	for (int i = 0; i < current.getIntersections().size()-1; i++) {
				 		Intersection crntInters = current.getIntersections().get(i);
				 		Intersection nextInters = current.getIntersections().get(i+1);
					 	if (!tmp.equals(crntInters.getTronconsSortants().get(nextInters.getId()).getNomRue())) {
					 		itineraire = itineraire + crntInters.getTronconsSortants().get(nextInters.getId()).getNomRue() + " -> ";
					 	}
				 		tmp = crntInters.getTronconsSortants().get(nextInters.getId()).getNomRue();     	
				 	}
				 		        
				 	itineraire = itineraire.substring(0, itineraire.length()-3);
				 	textInfo.setText(("<html> Pour acc�der � " + arrivee + " � partir de " + depart + " : <br> " + itineraire + "</html>"));
			 		        
			    }
			});
		 		
		     
			System.out.println(k);
			Chemin c = tournee.getPlusCourteTournee().get(k);
			List<Intersection> inters =  c.getIntersections();
			int taille = inters.size();
			Intersection inter = inters.get(taille-1);
			Intersection interPrevious = inters.get(taille-2);

			Troncon tronc = interPrevious.getTronconsSortants().get(inter.getId());
			System.out.println(tronc.getNomRue());
			System.out.println(inter.getId());

			
			
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
					succession.put("Pick Up n� " + compteurPickUp, k);
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
				}else if (idPointsLivraison.contains(inter.getId())) {
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
					succession.put("Delivery n� " + compteurDelivery, k);
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
	        	jlabel.setPreferredSize(new Dimension(400, 100));
	            resultsPanel.add(jlabel);
		}
        
		int duree = tournee.getDuree() ;
		duree = (int) duree/60;
		JLabel time = new JLabel("<html> Duree totale : " + duree + " minutes. </center> </html>");
		resultsPanel.add(time);
		
		for (int i = 0; i < 4 ; i++) {
        	JLabel j = new JLabel("");
        	j.setPreferredSize(new Dimension(400, 100));
            resultsPanel.add(j);
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
	


}
