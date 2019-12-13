package vue;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import model.Intersection;
import java.awt.geom.Point2D;

public class EcouteurSouris implements MouseListener, MouseWheelListener, MouseMotionListener {

	private AffichagePlan affichagePlan;
	private AffichageTournee affichageTournee;
	private Fenetre fenetre;
	private int cptZoom;
	private Point pointDepart;

	public EcouteurSouris(AffichagePlan affichagePlan, Fenetre fenetre) {
		this.affichagePlan = affichagePlan;
		this.fenetre = fenetre;
		cptZoom = 0;
		pointDepart = new Point();
	}

	public EcouteurSouris(AffichageTournee affichageTournee, Fenetre fenetre) {
		this.affichageTournee = affichageTournee;
		this.fenetre = fenetre;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		// Récuperation de la position du curseur en y soustrayant les offset
		double xClic = e.getX() - affichagePlan.getxOffset();
		double yClic = e.getY() - affichagePlan.getyOffset();
		
		// Passage de la position du clic à la bonne échelle
		double xPos = (xClic * Fenetre.LARGEUR_PLAN )/affichagePlan.getLargeurPlan();
		double yPos = (yClic * Fenetre.HAUTEUR_PLAN )/affichagePlan.getHauteurPlan();
		
		// Recherche de l'intersection la plus proche
		Intersection interLaPlusProche = trouverIntersectionLaPlusProche(xPos, yPos);
		
		if (affichagePlan.getPlanClickable()) {
			affichagePlan.setIntersectionSelectionne(null);
			fenetre.getAffichageTournee().setIntersectionClique(null);
			switch (affichagePlan.getEtat()) {
			case LIVRAISON:
				affichagePlan.setNouvelleLivraison(interLaPlusProche);
				fenetre.afficherAjoutLivraison3();

				break;
			case ENLEVEMENT:
				affichagePlan.setNouveauPickUp(interLaPlusProche);
				fenetre.afficherAjoutLivraison2();
				break;
				
			case MODIF_ADRESSE:
				affichagePlan.setNouvelleAdresse(interLaPlusProche);
				fenetre.getPanModificationTournee().afficherValidationModifAdresse();
			}
			

			affichagePlan.repaint();
			System.out.println("Intersection la plus proche: " + interLaPlusProche.getLongitude()+ " "
					+ interLaPlusProche.getLatitude());
		} 
		else{
			affichagePlan.setIntersectionSelectionne(interLaPlusProche);
			fenetre.getAffichageTournee().setIntersectionClique(interLaPlusProche);
			fenetre.getAffichageTournee().repaint();
			//fenetre.apresModifOrdre();
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		pointDepart.setLocation(e.getX() - affichagePlan.getxOffset(), e.getY() - affichagePlan.getyOffset());
		affichagePlan.setRelease(false);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		affichagePlan.setRelease(true);
		affichagePlan.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

		int xDiff = affichagePlan.getxDiff();
		int yDiff = affichagePlan.getyDiff();
		
		double xOffset = affichagePlan.getxOffset();
		double yOffset = affichagePlan.getyOffset();
		
		double largeurPlan = affichagePlan.getLargeurPlan();
		double hauteurPlan = affichagePlan.getHauteurPlan();
		
		if (affichagePlan.getZoom() > 1) {
			double clicX = e.getX() - xOffset;
			double clicY = e.getY() - yOffset;
			System.out.println(" X depart : " +pointDepart.x);

			double deplacementX = clicX - pointDepart.x;
			double deplacementY = clicY - pointDepart.y;

			if( (Math.abs(deplacementX) <= Math.abs(xOffset) && deplacementX > 0) ||
				(Math.abs(deplacementX) <= largeurPlan + xOffset - Fenetre.LARGEUR_PLAN) && deplacementX <0){
				affichagePlan.setnewxDiff((int)deplacementX);
			}
			
			if( (Math.abs(deplacementY) <= Math.abs(yOffset) && deplacementY > 0)||
					(Math.abs(deplacementY) <= hauteurPlan + yOffset - Fenetre.HAUTEUR_PLAN) && deplacementY <0){
				affichagePlan.setnewyDiff((int)deplacementY);
			}

			affichagePlan.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		int rotationScroll = e.getWheelRotation();
		if (rotationScroll < 0) {
			// Zoom avant
			if (cptZoom < 20) {
				affichagePlan.setMouseX(x);
				affichagePlan.setMouseY(y);
				affichagePlan.ZoomIn();
				cptZoom++;
			}

		} else {
			// Zoom arrière
			if (cptZoom == 1) {
				affichagePlan.setZoom(1);
				cptZoom = 0;
				affichagePlan.repaint();
			}
			if (affichagePlan.getZoom() > 1) {
				affichagePlan.ZoomOut();
				cptZoom--;
			}
		}
	}
	
	public Intersection trouverIntersectionLaPlusProche(double xPos, double yPos){
		Intersection interLaPlusProche = new Intersection();
		double distanceMin = Double.MAX_VALUE;
		for (String intersectionId : affichagePlan.getPlan().getIntersections().keySet()) {
			Intersection i = affichagePlan.getPlan().getIntersections().get(intersectionId);
			double longitude = i.getLongitude();
			double latitude = i.getLatitude();	
			double distance = Point2D.distanceSq(xPos, yPos, longitude, latitude);
	
			if (distance < distanceMin) {
				distanceMin = distance;
				interLaPlusProche = i;
			}
		}
		return interLaPlusProche;
	}
	
	
}
