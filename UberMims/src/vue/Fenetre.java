package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.Intersection;
import model.Plan;

import util.XMLParser;
public class Fenetre extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color backgroundColor = new Color(191,252,251);
	private final int LARGEUR_FENETRE = 1200;
	private final int HAUTEUR_FENETRE = 800;
	
	
	
	private final int LARGEUR_PLAN = 800;
	private final int HAUTEUR_PLAN = 600;

	private JButton boutonChargementPlan = new JButton("Charger le plan de la ville" );
	private Plan plan;
	private AffichagePlan affichagePlan;

	public Fenetre(){
		this.setTitle("Accueil UberMims");
	    this.setSize(1200, 800);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

	    this.setVisible(true);
	    //this.setResizable(false);
	    this.setLayout(new BorderLayout());
	    
	    //Instanciation d'un objet JPanel
	    JPanel pan = new JPanel();
	    pan.setLayout(null);
	    pan.setBackground(backgroundColor);
	    pan.setSize(1200, 800);
	    boutonChargementPlan.setBounds(400,350,400,200);
	    pan.add(boutonChargementPlan);
		this.setContentPane(pan);    
		boutonChargementPlan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == boutonChargementPlan) {
					//Affichage du navigateur de choix de fichier
					String planFileName;
					String planPath;
					JFileChooser choix = new JFileChooser();
					int boiteDialogue = choix.showOpenDialog(boutonChargementPlan);
					
					//Si un fichier a ete choisi (utilisateur a cliqué sur OK)
					if (boiteDialogue == JFileChooser.APPROVE_OPTION) { 
						planFileName = choix.getSelectedFile().getName();
						planPath = choix.getSelectedFile().getAbsolutePath();
						
						//Chargement du fichier
						// A RAJOUTER A LA DEUXIEME ITERATION : VERIFICATION DU FICHIER 
						plan = XMLParser.chargerPlan(planPath,HAUTEUR_PLAN, LARGEUR_PLAN);
						
						boutonChargementPlan.setVisible(false);

						//Affichage du plan :
						//creerPlan();	
						affichagePlan = new AffichagePlan(Fenetre.this,plan);
						repaint();
					}
				}
			}
		});
	    
	}	
	
	@Override
	public void paint(Graphics g) {
        super.paint(g);
        if(plan != null)
        	affichagePlan.dessinerPlan(g);
    }
	
	@Override
	public void update( Graphics g )
	{
		paint( g );
	}
	
	public static void main(String[] args){   
	    Fenetre fen = new Fenetre();	    
	}       
}