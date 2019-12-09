package vue;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import model.Intersection;

public class EcouteurSouris implements MouseListener, MouseWheelListener {

	private AffichagePlan affichagePlan;
	private Fenetre fenetre;
	private int cptZoom;

	public EcouteurSouris(AffichagePlan affichagePlan, Fenetre fenetre) {
		this.affichagePlan = affichagePlan;
		this.fenetre = fenetre;
		cptZoom = 0;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (affichagePlan.getPlanClickable()) {
			int xClic = e.getX();
			int yClic = e.getY();
			System.out.println("posX:" + xClic + " posY:" + yClic);

			Intersection interLaPlusProche = new Intersection();
			double distanceMin = Double.MAX_VALUE;
			for (String intersectionId : affichagePlan.getPlan().getIntersections().keySet()) {
				Intersection i = affichagePlan.getPlan().getIntersections().get(intersectionId);
				double distance = (xClic - i.getLongitude()) * (xClic - i.getLongitude())
						+ (yClic - i.getLatitude()) * (yClic - i.getLatitude());
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

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

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
