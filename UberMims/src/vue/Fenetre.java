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
	private final int LARGEUR_PLAN = 1200;
	private final int HAUTEUR_PLAN = 800;
	
	
	private double coefX;
	private double coefY;
	
	private final int mapHeight = 600;
	private final int mapWidth = 1000;

	private JButton boutonChargementPlan = new JButton("Charger le plan de la ville" );
	private Plan plan;

	public Fenetre(){
		this.setTitle("Accueil UberMims");
	    this.setSize(1200, 800);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

	    this.setVisible(true);
	    this.setResizable(false);
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
						plan = XMLParser.chargerPlan(planPath);
						
						boutonChargementPlan.setVisible(false);

						//Affichage du plan :
						 //creerPlan();	
					}
				}
			}
		});
	    
	}
	
	
	
	
	
	
	public void creerPlan(JFrame frame){
		 String path = "H:\\Mes documents\\PLDAgile\\PLD-AGILE\\UberMims\\fichiersXML2019\\moyenPlan.xml";
		 plan = XMLParser.chargerPlan(path);
		    
		 //Cr?ation du panel plan
		 JPanel panel = new JPanel();
		 panel.setSize(500, 500);
		 Border blackline = BorderFactory.createLineBorder(Color.black);
		 panel.setBorder(blackline);
		    	      
		 //this.setContentPane(pan);    
		 frame.getContentPane().add(panel, BorderLayout.CENTER);
	}
	
	public void dessinerIntersections(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		miseALEchelle();
		int i = 10;
	    for (Intersection intersection : plan.getIntersections().values()) {
	    	//g2d.drawLine(120+i, 50, 360, 50);
	    	Ellipse2D.Double shape = new Ellipse2D.Double(intersection.getLatitude(),  intersection.getLongitude(), 2, 2);
	    	g2d.draw(shape);
	    	g2d.fill(shape);
	    	i+=10;
		}

        //g2d.draw(new Line2D.Double(59.2d, 99.8d, 419.1d, 99.8d));
 
        //g2d.draw(new Line2D.Float(21.50f, 132.50f, 459.50f, 132.50f));
	}
	
	
	public void miseALEchelle(){
		
		double lol = plan.getLattitudeMax();
		double lol2 = plan.getLattitudeMin();
		if(plan != null){
			coefX = (double) (LARGEUR_PLAN) / (double)(plan.getLattitudeMax() - plan.getLattitudeMin());
			coefY = (double) (HAUTEUR_PLAN) / (double)(plan.getLongitudeMax() - plan.getLongitudeMin());
		}
	}
	
	
	
	@Override
	public void paint(Graphics g) {
        super.paint(g);
        dessinerIntersections(g);
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