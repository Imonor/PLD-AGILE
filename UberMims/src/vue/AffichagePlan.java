package vue;

import java.awt.Color;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import controleur.Controleur;
import model.Livraison;
import model.Plan;
import model.Tournee;

public class AffichagePlan extends JFrame{

	/**
	 * Page d'accueil : chargement du plan
	 */
	
	private Plan plan;
	private Livraison livraison;
	private Tournee tournee;
	
	
	//Taille du plan
	private int sideLength;
	
	//Endroit de placement du plan dans la page
	private Point placementPlan;

	public AffichagePlan(Fenetre fenetre, Plan plan, Livraison livraison, Tournee tournee,
			Controleur controleur) {
		this.plan = plan;
		this.livraison = livraison;
		this.tournee = tournee;


		//setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	
	
	
}
