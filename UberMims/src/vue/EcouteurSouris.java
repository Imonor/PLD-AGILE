package vue;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.Intersection;

public class EcouteurSouris implements MouseListener {

	private AffichagePlan affichagePlan;

	public EcouteurSouris(AffichagePlan affichagePlan) {
		this.affichagePlan = affichagePlan;
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
}
