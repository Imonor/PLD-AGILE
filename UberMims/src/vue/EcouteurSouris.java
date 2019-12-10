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
	private Fenetre fenetre;
	private int cptZoom;
	private Point pointDepart;
	
	public EcouteurSouris(AffichagePlan affichagePlan, Fenetre fenetre) {
		this.affichagePlan = affichagePlan;
		this.fenetre = fenetre;
		cptZoom = 0;
		pointDepart = new Point();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (affichagePlan.getPlanClickable()) {
			
			double zoom = affichagePlan.getZoom();
			
			double xClic = e.getX();
			double yClic = e.getY();
			System.out.println("posX:" + xClic + " posY:" + yClic);
			e.translatePoint((int)affichagePlan.getxOffset(),(int)affichagePlan.getyOffset() );
			xClic = e.getX();
			yClic = e.getY();
			System.out.println("offsetX:" + affichagePlan.getxOffset() + " offSetY:" + affichagePlan.getyOffset());
			System.out.println("posX:" + xClic + " posY:" + yClic);
			
	
			Intersection interLaPlusProche = new Intersection();
			double distanceMin = Double.MAX_VALUE;
			for (String intersectionId : affichagePlan.getPlan().getIntersections().keySet()) {
				Intersection i = affichagePlan.getPlan().getIntersections().get(intersectionId);
				double longitude = i.getLongitude() + affichagePlan.getxOffset();
				double latitude = i.getLatitude() + affichagePlan.getyOffset();
				double distance = (xClic - longitude) * (xClic - longitude)
						+ (yClic - latitude) * (yClic - latitude);
				if (distance < distanceMin) {
					distanceMin = distance;
					interLaPlusProche = i;
				}
			}

			switch (affichagePlan.getEtat()) {
			case LIVRAISON:
				affichagePlan.setNouvelleLivraison(interLaPlusProche);
				fenetre.afficherAjoutLivraison3();

				break;
			case ENLEVEMENT:
				affichagePlan.setNouveauPickUp(interLaPlusProche);
				fenetre.afficherAjoutLivraison2();
				break;
			}

			affichagePlan.repaint();
			System.out.println("Intersection la plus proche: " + interLaPlusProche.getLongitude() + " "
					+ interLaPlusProche.getLatitude());

		}

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
		
		if(affichagePlan.getZoom() >1)
		{
			int clicX = e.getX();
			int clicY = e.getY();
			affichagePlan.setxDiff( clicX - pointDepart.x );
			affichagePlan.setyDiff( clicY - pointDepart.y);
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
			if(cptZoom <20){
				affichagePlan.setMouseX(x);
				affichagePlan.setMouseY(y);
				affichagePlan.ZoomIn();
				cptZoom++;
			}
			
		} else {
			// Zoom arrière			
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
