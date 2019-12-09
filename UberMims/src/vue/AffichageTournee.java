package vue;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
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
        
        
        /*JScrollPane scrollPane = new JScrollPane(new JPanel());
        textArea.add(scrollPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);*/
        
        
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

        for (int i = 0; i < 15 ; i++) {
        	Button j = new Button("jjjjjjj");
        	j.setPreferredSize(new Dimension(400, 100));
            resultsPanel.add(j);
        }
        
        textArea.add(scrollpane, BorderLayout.CENTER);

        
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
