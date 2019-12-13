package vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;

import algo.Dijkstra;
import algo.TSP1;
import algo.TemplateTSP;
import controleur.Controleur;
import model.Chemin;
import model.ContraintesTournee;
import model.Intersection;
import model.Plan;
import model.PointEnlevement;
import model.PointLivraison;
import model.Tournee;
import util.ExceptionChargement;
import util.XMLParser;


public class EcouteurBoutons implements ActionListener{
	private Controleur controleur;
	private Fenetre fenetre;
	private JPanel panAccueil;
	private JPanel panPrincipal;
	private JPanel panPlan;
	private String cheminFichierPlan ;
	private String nomFichierPlan;
	private String cheminFichierPlan2 ;
	private String nomFichierPlan2;
	private String cheminFichierTournee ;
	private String nomFichierTournee;
	//Constructeur
	public EcouteurBoutons(Controleur controleur, Fenetre fenetre) {
		this.controleur = controleur;
		this.fenetre = fenetre;
		panAccueil = new JPanel();
		panPrincipal = new JPanel();
		panPlan = new JPanel();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		panAccueil = fenetre.getPanAccueil();
		
		
		//panPrincipal = fenetre.getPanPrincipal();
		switch (e.getActionCommand()) {
			case "Charger le plan de la ville":
				System.out.println("Chargement plan de la ville");
				
				
				JFileChooser choixPlan = new JFileChooser();
				int boiteDialogue = choixPlan.showOpenDialog(bouton);
				// 2EME ITERATION : FILTRER LES FICHIERS XML
				//NE MARCHE PAS : choixPlan.setFileFilter(new FileNameExtensionFilter("*.xml", "xml"));
				
				if (boiteDialogue == JFileChooser.APPROVE_OPTION) { 
					nomFichierPlan = choixPlan.getSelectedFile().getName();
					cheminFichierPlan = choixPlan.getSelectedFile().getAbsolutePath();
					try {
					controleur.chargerPlan(cheminFichierPlan,Fenetre.HAUTEUR_PLAN, Fenetre.LARGEUR_PLAN);
					fenetre.setPlan(Controleur.plan);
					fenetre.afficherPanPrincipal();
					} catch (ExceptionChargement exception) {
						exception.printStackTrace();
						JOptionPane.showMessageDialog(null, "Impossible de charger un plan a partir du fichier fourni");
					} catch (Exception exception) {
						exception.printStackTrace();
					}
				}
			break;
			
			case "Charger un autre plan de la ville":
				System.out.println("Chargement d'un autre plan de la ville");
				fenetre.getAffichageTournee().removeAll();
				JFileChooser choixPlan2 = new JFileChooser();
				int boiteDialogue3 = choixPlan2.showOpenDialog(bouton);
				// 2EME ITERATION : FILTRER LES FICHIERS XML
				//NE MARCHE PAS : choixPlan.setFileFilter(new FileNameExtensionFilter("*.xml", "xml"));
				
				if (boiteDialogue3 == JFileChooser.APPROVE_OPTION) { 
					nomFichierPlan2 = choixPlan2.getSelectedFile().getName();
					cheminFichierPlan2 = choixPlan2.getSelectedFile().getAbsolutePath();
					//controleur.creerPlan(cheminFichierPlan)
					//Plan plan = controleur.chargerPlan(cheminFichierPlan);
					try {
						controleur.chargerPlan(cheminFichierPlan2,Fenetre.HAUTEUR_PLAN, Fenetre.LARGEUR_PLAN);						
						fenetre.setPlan(Controleur.plan);
						fenetre.setContraintes(null);
						fenetre.setTournee(null);
						fenetre.afficherPanPrincipal();
					} catch (ExceptionChargement exception) {
						exception.printStackTrace();
						JOptionPane.showMessageDialog(null, "Impossible de charger un plan a partir du fichier fourni");
					} catch (Exception exception) {
						exception.printStackTrace();
					}
				}
			break;
			
			
			case "Charger une demande de tournee":
				System.out.println("Chargement d'une demande de tournee");
				fenetre.getAffichageTournee().removeAll();
				JFileChooser choixTournee = new JFileChooser();
				int boiteDialogue2 = choixTournee.showOpenDialog(bouton);
				// 2EME ITERATION : FILTRER LES FICHIERS XML
				//NE MARCHE PAS : choixPlan.setFileFilter(new FileNameExtensionFilter("*.xml", "xml"));
				
				if (boiteDialogue2 == JFileChooser.APPROVE_OPTION) { 
					nomFichierTournee = choixTournee.getSelectedFile().getName();
					cheminFichierTournee = choixTournee.getSelectedFile().getAbsolutePath();
					//controleur.creerPlan(cheminFichierPlan);
					try {
					controleur.chargerTournee(cheminFichierTournee);
					fenetre.setTournee(null);
					fenetre.setContraintes(controleur.getContraintes());
					fenetre.afficherBoutonCalcul();
					} catch (ExceptionChargement exception) {
						exception.printStackTrace();
						JOptionPane.showMessageDialog(null, "Impossible de charger une tournee sur le plan a partir du fichier fourni");
					} catch (Exception exception) {
						exception.printStackTrace();
					}
					
				}
			break;
			
			case "Calculer une tournee":
				System.out.println("Calculer une tournee");
					//controleur.chargerTournee(cheminFichierTournee);
					controleur.calculerTournee();
					fenetre.setTournee(controleur.getTournee());
					
					System.out.println("Affichage des rues d'une demande de tournee");
					fenetre.afficherInfos();
					fenetre.getAffichageTournee().afficherDetailTournee(fenetre.getTournee(),fenetre.getContraintes());
					
			break;
			
			case "Ajouter une livraison a la tournee":
				System.out.println("Ajouter une livraison");
					fenetre.afficherAjoutLivraison();
					fenetre.getAffichagePlan().setPlanClickable(true);
					
					
			break;
			
			case "Annuler l'ajout d'une livraison":
				System.out.println("Annuler ajout d'une livraison");
				fenetre.getAffichagePlan().setNouveauPickUp(null);
				fenetre.getAffichagePlan().setNouvelleLivraison(null);
				fenetre.getAffichagePlan().setPlanClickable(false);
				fenetre.afficherInfos();
				fenetre.getAffichageTournee().afficherDetailTournee(controleur.getTournee(),controleur.getContraintes());
			break;
			
			case "Valider l'ajout d'une livraison":
				System.out.println("Valider ajout d'une livraison");
				int nouveauTempsPickUp = ((Number) fenetre.getChampPickUp().getValue()).intValue() * 60;
				int nouveauTempsDelivery = ((Number) fenetre.getChampDelivery().getValue()).intValue() * 60;
				System.out.println(nouveauTempsPickUp);
				System.out.println(nouveauTempsDelivery);

				Intersection nouveauPointPickUp = fenetre.getAffichagePlan().getNouveauPickUp();
				Intersection nouveauPointLivraison = fenetre.getAffichagePlan().getNouvelleLivraison();
				PointEnlevement pointEnlevement = new PointEnlevement(nouveauPointPickUp, nouveauPointLivraison.getId(), nouveauTempsPickUp);
				PointLivraison pointLivraison = new PointLivraison(nouveauPointLivraison, nouveauPointPickUp.getId(), nouveauTempsDelivery);
				System.out.println("id enlevement: " + pointEnlevement.getId() + "id livraison associé: " + pointEnlevement.getIdLivraison() + "temps" + pointEnlevement.getTempsEnlevement());
				System.out.println("id livraison: " + pointLivraison.getId() + "id enlevement associé: " + pointLivraison.getIdEnlevement() + "temps" + pointLivraison.getTempsLivraison());

				fenetre.getAffichagePlan().setNouveauPickUp(null);
				fenetre.getAffichagePlan().setNouvelleLivraison(null);
				
				//System.out.println(pointEnlevement.getId());
				//System.out.println(pointLivraison.getId());

				controleur.ajouterLivraison(pointEnlevement, pointLivraison);
				System.out.println(controleur.getContraintes().getPointsEnlevement().get(controleur.getContraintes().getPointsEnlevement().size()-1).getTempsEnlevement());
				fenetre.setTournee(controleur.getTournee());
				fenetre.setContraintes(controleur.getContraintes());
				fenetre.apresAjoutLivraison();
				fenetre.getAffichageTournee().afficherDetailTournee(controleur.getTournee(),controleur.getContraintes());
				fenetre.afficherInfos();
			break;

			case "⟲":
				System.out.println("Annuler derniere modification");
				controleur.undo();
				fenetre.getAffichageTournee().afficherDetailTournee(controleur.getTournee(), controleur.getContraintes());
				fenetre.afficherInfos();
				
			break;
			case "redo":
				System.out.println("Refaire la derniere modification");
				controleur.redo();
				fenetre.getAffichageTournee().afficherDetailTournee(controleur.getTournee(), controleur.getContraintes());
				fenetre.afficherInfos();
				
			break;
			case "Modifier la tournee":
				System.out.println("Modifier tournee");
				fenetre.afficherModificationTournee();
			break;
		}
	}
}

