package vue;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color backgroundColor = new Color(191,252,251);

	
	private JButton boutonChargement = new JButton("Charger le plan de la ville" );

	public Fenetre(){
		this.setTitle("Accueil UberMims");
	    this.setSize(1200, 800);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    
	    //Instanciation d'un objet JPanel
	    JPanel pan = new JPanel();
	    //Définition de sa couleur de fond
	    pan.setLayout(null);
	    pan.setBackground(backgroundColor);
	    pan.setSize(1200, 800);
	    boutonChargement.setBounds(400,350,400,100);
	    
	    pan.add(boutonChargement);
	    this.setContentPane(pan);    
	    this.setVisible(true);
	    this.setResizable(false);
	}
	
	public static void main(String[] args){       
	    Fenetre fen = new Fenetre();
	}       
}