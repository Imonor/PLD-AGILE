package vue;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;

import model.Chemin;
import model.Intersection;
import model.Tournee;

public class ModificationTournee extends JPanel implements ActionListener{
	
	JPanel resultsPanel;
	Button boutonSelectionne;
	Button precedentBoutonSelectionne; //Permet de changer la couleur lorsqu'on sélectionne un autre bouton
	
    public ModificationTournee() {
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
    
    public void ajouterTournee(Tournee tournee) {
        List<Chemin> parcours = tournee.getPlusCourteTournee();
        String depotId = parcours.get(0).getPremiere().getId();
        Button boutonDepot = new Button(depotId);
        boutonDepot.setBackground(Color.BLUE);
        resultsPanel.add(boutonDepot);
        boutonSelectionne = boutonDepot;
        for (Chemin chemin: parcours) {
        	String intersectionId = chemin.getDerniere().getId();
        	Button j = new Button(intersectionId);
        	j.setBackground(Color.CYAN);
        	j.addActionListener(this);
        	j.setPreferredSize(new Dimension(100, 25));
            resultsPanel.add(j);
        }
    }
    
    public void actionPerformed(ActionEvent e) {
    	Button boutonClique = (Button) e.getSource();
    	precedentBoutonSelectionne = boutonSelectionne;
    	boutonSelectionne = boutonClique;
    	
    	boutonSelectionne.setBackground(Color.BLUE);
    	precedentBoutonSelectionne.setBackground(Color.CYAN);
    }
}
