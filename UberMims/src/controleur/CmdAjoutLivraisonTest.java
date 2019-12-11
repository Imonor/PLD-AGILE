package controleur;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.Test;

import model.Chemin;
import model.ContraintesTournee;
import model.Intersection;
import model.PointEnlevement;
import model.PointLivraison;
import model.Tournee;
import util.ExceptionChargement;
import vue.EcouteurBoutons;
import vue.Fenetre;

public class CmdAjoutLivraisonTest {

	@Test
	public void testerAjoutLivraisonNormal() {
		
		Fenetre fenetre = new Fenetre();
		Controleur controleur = new Controleur();
		EcouteurBoutons ecouteurBoutons = new EcouteurBoutons(controleur, fenetre);
		
		try {
			controleur.chargerPlan("./fichiersXML2019/moyenPlan.xml",1000,1000);
		} catch (ExceptionChargement e) {
			e.printStackTrace();
		}
		
		try {
			controleur.chargerTournee("./fichiersXML2019/demandeMoyen5.xml");
		} catch (ExceptionChargement e) {
			e.printStackTrace();
		}
		
		controleur.calculerTournee();
		Tournee tournee = controleur.getTournee();
		
		Intersection inter1 = new Intersection("55474924", 45.75965, 4.882271);
		Intersection inter2 = new Intersection("60491706", 45.76259, 4.8819356);
		
		PointEnlevement ptEnlev = new PointEnlevement(inter1, "60491706", 10);
		PointLivraison ptLiv = new PointLivraison(inter2, "55474924", 10);

		controleur.ajouterLivraison(ptEnlev, ptLiv);
		
		System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\");
		System.out.print( tournee.getPlusCourteTournee().get(tournee.getPlusCourteTournee().size()-2).getPremiere().getId() );
		System.out.print( tournee.getPlusCourteTournee().get(tournee.getPlusCourteTournee().size()-2).getDerniere().getId() );
		
		assertEquals( ptEnlev.getId(), tournee.getPlusCourteTournee().get(tournee.getPlusCourteTournee().size()-2).getPremiere().getId());
		assertEquals( ptLiv.getId(), tournee.getPlusCourteTournee().get(tournee.getPlusCourteTournee().size()-2).getDerniere().getId());	
		
		System.out.println("end");
	}
	
	
	@Test
	public void testerAjoutLivraisonListeVide() {
		
		Controleur controleur = new Controleur();
		
		Intersection inter1 = new Intersection("55474924", 45.75965, 4.882271);
		Intersection inter2 = new Intersection("60491706", 45.76259, 4.8819356);
		Intersection depot = new Intersection("4150019167", 45.752956, 4.8982544);
		
		PointEnlevement ptEnlev = new PointEnlevement(inter1, "60491706", 10);
		PointLivraison ptLiv = new PointLivraison(inter2, "55474924", 10);
		
		ContraintesTournee contraintes = new ContraintesTournee(LocalTime.now(), depot, new ArrayList<PointEnlevement>(), new ArrayList<PointLivraison>() );
		Tournee tournee = new Tournee( new ArrayList<Chemin>(), contraintes );
		controleur.setTournee(tournee);
		
		controleur.ajouterLivraison(ptEnlev, ptLiv);
		
		System.out.println("/////////////////////////");
		for(int count = 0; count < tournee.getPlusCourteTournee().size(); ++count) {
			System.out.print(count+"."+tournee.getPlusCourteTournee().get(count).getPremiere().getId()+"->"+tournee.getPlusCourteTournee().get(count).getDerniere().getId()+" | ");
		}
		
//		System.out.print( tournee.getPlusCourteTournee().get(tournee.getPlusCourteTournee().size()-2).getPremiere().getId() );
//		System.out.print( tournee.getPlusCourteTournee().get(tournee.getPlusCourteTournee().size()-2).getDerniere().getId() );
//		
		assertEquals( ptEnlev.getId(), tournee.getPlusCourteTournee().get(tournee.getPlusCourteTournee().size()-2).getPremiere().getId());
		assertEquals( ptLiv.getId(), tournee.getPlusCourteTournee().get(tournee.getPlusCourteTournee().size()-2).getDerniere().getId());	
	}
}
