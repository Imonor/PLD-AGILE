package vue;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;

import model.Chemin;
import model.ContraintesTournee;
import model.Intersection;
import model.Plan;
import model.PointEnlevement;
import model.Tournee;

public class ModificationTournee extends JPanel implements MouseListener{
	
	private JPanel resultsPanel;
	private JLabel labelSelectionne;
	private JLabel precedentLabelSelectionne; //Permet de changer la couleur lorsqu'on sélectionne un autre bouton
	private List<Map<String, String>> ordrePassage;
	private int deplacementEtape;
	private Plan plan;
	
	public ModificationTournee() {
    	ordrePassage = new ArrayList<>();
    	deplacementEtape = 0;
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
 
        JPanel panelAll = new JPanel();
        panelAll.setBackground(Color.red);
        panelAll.setLayout(layout);
        
        JPanel panelDetail = new JPanel();
        panelDetail.setBackground(Color.yellow);
        
        JPanel separation = new JPanel();
        separation.setBackground(Color.black);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty=2.5;
        gbc.fill = GridBagConstraints.BOTH;

        
        this.add(panelAll, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty=1.0;
        gbc.fill = GridBagConstraints.BOTH;
        
        this.add(panelDetail, gbc);
        
        //----------------------
        
        JPanel topMargin = new JPanel();
        panelAll.setBackground(Color.pink);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty=0.0;
        gbc.weightx=0.0;
        panelAll.add(topMargin, gbc);
        
        JPanel textArea = new JPanel();
        textArea.setLayout(new BorderLayout());
        textArea.setBackground(Color.cyan);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty= 1.5;
        gbc.weightx= 1.5;
        panelAll.add(textArea, gbc);
        
        
        JPanel bottomMargin = new JPanel();
        panelAll.setBackground(Color.magenta);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty=0.0;
        gbc.weightx= 0.0;
        panelAll.add(bottomMargin, gbc);
        
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollpane = new JScrollPane(resultsPanel);


        
        textArea.add(scrollpane, BorderLayout.CENTER);
    }
    
    public void ajouterTournee(Tournee tournee, Plan plan) {
    	this.plan = plan;
        List<Chemin> parcours = tournee.getPlusCourteTournee();
        //Retirer la dernière étape du parcours, qui est arrive sur le point de dépôt
        parcours.remove(parcours.size() - 1);

        for (Chemin chemin: parcours) {
        	JLabel l;
        	//Récupérer une intersection étape du parcours et l'ajouter à la liste
        	Intersection etape = chemin.getDerniere();
        	//Récupérer l'adresse (en prenant le nom de rue du dernier troncon menant à l'intersection)
        	List<Intersection> cheminement = chemin.getIntersections();
        	Intersection interPrecedente = cheminement.get(cheminement.size() - 2);
        	String adresse = interPrecedente.getTronconsSortants().get(etape.getId()).getNomRue();
        	Map<String, String> map = new HashMap();
        	map.put(etape.getId(), adresse);
            ordrePassage.add(map);
        }
    }
    
    public void afficherTournee(ContraintesTournee contraintes) {

        int compteurPointsEnlevement = 1;
        int compteurPointsLivraison = 1;
        int compteur = 0;
        for (Map<String, String> paire: ordrePassage) {
        	JLabel l;
        	Intersection etape = new Intersection();
        	String adresse = "";
        	//Il n'y a qu'une seule valeur dans la map
        	for(String etapeId: paire.keySet() ) {
        		etape = plan.getIntersections().get(etapeId);
        		adresse = paire.get(etapeId);
        	}
        	//Tester si l'étape est un pick up ou un delivery
        	boolean isPtEnlevement = false;
        	for(PointEnlevement ptEnlevement: contraintes.getPointsEnlevement()) {
        		if(ptEnlevement.getId().equals(etape.getId())) {
        			isPtEnlevement = true;
        			break;
        		}
        	}
        	//Créer le label correspondant
        	if(isPtEnlevement) {
        		l = creerLabelEtape(etape, compteurPointsEnlevement, adresse, "enlevement");
        		compteurPointsEnlevement ++;
        	} else {
        		l = creerLabelEtape(etape, compteurPointsLivraison, adresse, "livraison");
        		compteurPointsLivraison ++;
        	}
        	l.setForeground(Color.CYAN);
        	l.setName(Integer.toString(compteur));
        	l.addMouseListener(this);
        	l.setPreferredSize(new Dimension(100, 35));
            resultsPanel.add(l);
            compteur++;
        }
    }
    
    private JLabel creerLabelEtape(Intersection etape, int position, String adresse, String type) {
    	JLabel label = new JLabel("<html> ");
    	if(type.equals("enlevement")) {
    		label.setText(label.getText() + "Pick Up n° " + position + " :   <br>");
    	} else {
    		label.setText(label.getText() + "Delivery n° " + position + " :   <br>");
    	}
			
		label.setText(label.getText() + "&rarr; Adresse : " + adresse +"<br>");	
		return label;
    }
    
    public void mouseClicked(MouseEvent m) {
    	if(deplacementEtape == 0) {
	    	JLabel labelClique = (JLabel) m.getSource();
	    	if(labelSelectionne != null) {
	    		precedentLabelSelectionne = labelSelectionne;
	    		precedentLabelSelectionne.setForeground(Color.CYAN);
	    	}
	    	
	    	labelSelectionne = labelClique;
	    	labelSelectionne.setForeground(Color.BLUE);
	    	
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
