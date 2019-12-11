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

public class EcouteurSouris implements MouseListener, MouseWheelListener, MouseMotionListener  {

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
		if (affichagePlan.getPlanClickable()) {
		    int xClic=e.getX();
		    int yClic=e.getY();
		    System.out.println("posX:" + xClic + " posY:" + yClic);
		    
		    Intersection interLaPlusProche = new Intersection();
		    double distanceMin = Double.MAX_VALUE;
		    for(String intersectionId: affichagePlan.getPlan().getIntersections().keySet()) {
		    	Intersection i = affichagePlan.getPlan().getIntersections().get(intersectionId);
		    	double distance = (xClic-i.getLongitude())*(xClic-i.getLongitude()) + (yClic-i.getLatitude())*(yClic-i.getLatitude());
		    	if(distance < distanceMin) {
		    		distanceMin = distance;
		    		interLaPlusProche = i;
		    	}
		    }
		  
		    switch(affichagePlan.getEtat()) {
		    case LIVRAISON  :
		    	affichagePlan.setNouvelleLivraison(interLaPlusProche);
		    	fenetre.afficherAjoutLivraison3();

		    	break;
		    case ENLEVEMENT :
		    	affichagePlan.setNouveauPickUp(interLaPlusProche);
		    	fenetre.afficherAjoutLivraison2();
		    	break;
		    }
		        
		    affichagePlan.repaint();
		    System.out.println("Intersection la plus proche: " + interLaPlusProche.getLongitude() + " "+ interLaPlusProche.getLatitude());
		    
		}
			
			double zoom = affichagePlan.getZoom();
			
			double xClic = e.getX()+ Math.abs(affichagePlan.getxOffset());
			double yClic = e.getY()+ Math.abs(affichagePlan.getyOffset());
			System.out.println("posX:" + xClic + " posY:" + yClic);
			System.out.println("offsetX:" + affichagePlan.getxOffset() + " offSetY:" + affichagePlan.getyOffset());
			//System.out.println("posX:" + xClic + " posY:" + yClic);
			
	
			Intersection interLaPlusProche = new Intersection();
			double distanceMin = Double.MAX_VALUE;
			for (String intersectionId : affichagePlan.getPlan().getIntersections().keySet()) {
				Intersection i = affichagePlan.getPlan().getIntersections().get(intersectionId);
				double longitude = i.getLongitude() ;// + affichagePlan.getxOffset();
				double latitude = i.getLatitude() ;//+ affichagePlan.getyOffset();
				double distance = (xClic - longitude) * (xClic - longitude)
						+ (yClic - latitude) * (yClic - latitude);
				if (distance < distanceMin) {
					distanceMin = distance;
					interLaPlusProche = i;
				}
			}

			affichagePlan.repaint();
			System.out.println("Intersection la plus proche: " + interLaPlusProche.getLongitude() + " "
					+ interLaPlusProche.getLatitude());

		}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		pointDepart.setLocation(e.getX(), e.getY());
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
		
		int lol = affichagePlan.getxDiff();
		int xDiff = affichagePlan.getxDiff();
		int yDiff = affichagePlan.getyDiff();
		double xOffset = affichagePlan.getxOffset() - affichagePlan.getxDiff();
		double yOffset = affichagePlan.getyOffset() - affichagePlan.getyDiff();
		if(affichagePlan.getZoom() >1 )			
		{
			int clicX = e.getX();
			int clicY = e.getY();
			
			int deplacementX = clicX - pointDepart.x;
			int deplacementY = clicY - pointDepart.y;
			
			if( (xDiff + deplacementX<= Math.abs(xOffset) && deplacementX >0 ) ||
				( xDiff + deplacementX >= xOffset - Fenetre.LARGEUR_PLAN/2  && deplacementX <0)	){
				affichagePlan.setnewxDiff(deplacementX);
			}
			if(yDiff + deplacementY < Math.abs(yOffset) ){
				affichagePlan.setnewyDiff(deplacementY);
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
		System.out.println(cptZoom);
		int rotationScroll = e.getWheelRotation();
		if (rotationScroll < 0) {
			// Zoom avant
			if(cptZoom <20){
				affichagePlan.setMouseX(x);
				affichagePlan.setMouseY(y);
				affichagePlan.ZoomIn();
				cptZoom++;
			}
			
		} else {
			// Zoom arri�re			
			if(cptZoom == 1){
				affichagePlan.setZoom(1);
				cptZoom =0;
				affichagePlan.repaint();
			}
			if(affichagePlan.getZoom() > 1){
				affichagePlan.ZoomOut();
				cptZoom --;
			}			
		}
	}
}
