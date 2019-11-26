package vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import controleur.Controleur;
import util.XMLParser;


public class EcouteurBoutons implements ActionListener{
	private Controleur controleur;
	private Fenetre fenetre;
	private JPanel panAccueil = new JPanel();
	private JPanel panPrincipal = new JPanel();
	
	//Constructeur
	public EcouteurBoutons(Controleur controleur, Fenetre fenetre) {
		this.controleur = controleur;
		this.fenetre = fenetre;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		panAccueil = fenetre.getPanAccueil();
		//panPrincipal = fenetre.getPanPrincipal();
		switch (e.getActionCommand()) {
			case "Charger le plan de la ville":
				System.out.println("Chargement plan de la ville");
				
				String cheminFichierPlan ;
				String nomFichierPlan;
				//JFileChooser choixPlan = new JFileChooser();
				//int boiteDialogue = choixPlan.showOpenDialog(bouton);
				// 2EME ITERATION : FILTRER LES FICHIERS XML
				//NE MARCHE PAS : choixPlan.setFileFilter(new FileNameExtensionFilter("*.xml", "xml"));
				
				//if (boiteDialogue == JFileChooser.APPROVE_OPTION) { 
					//nomFichierPlan = choixPlan.getSelectedFile().getName();
					//cheminFichierPlan = choixPlan.getSelectedFile().getAbsolutePath();
					//controleur.creerPlan(cheminFichierPlan)
					
					fenetre.afficherPanPrincipal();
				//}
			break;
		
			case "Charger une livraison":
				String cheminFichierLivraison ;
				String nomFichierLivraison;
				JFileChooser choixLivraison = new JFileChooser();
				int boiteDialogue2 = choixLivraison.showOpenDialog(bouton);
				// 2EME ITERATION : FILTRER LES FICHIERS XML
				//NE MARCHE PAS : choixPlan.setFileFilter(new FileNameExtensionFilter("*.xml", "xml"));
				
				if (boiteDialogue2 == JFileChooser.APPROVE_OPTION) { 
					//nomFichierPlan = choixLivraison.getSelectedFile().getName();
					cheminFichierPlan = choixLivraison.getSelectedFile().getAbsolutePath();
					//controleur.creerPlan(cheminFichierPlan);
					fenetre.afficherDetailLivraison();

				}
			break;
			}
		}
	}

