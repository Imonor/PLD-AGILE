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
import java.util.ListIterator;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;

import controleur.Controleur;
import model.Chemin;
import model.ContraintesTournee;
import model.Intersection;
import model.Plan;
import model.PointEnlevement;
import model.PointLivraison;
import model.Tournee;

public class ModificationTournee extends JPanel implements MouseListener, ActionListener{

	
	private JPanel resultsPanel;
	private JLabel labelSelectionne;
	private JLabel precedentLabelSelectionne; //Permet de changer la couleur lorsqu'on sélectionne un autre bouton
	private List<Map<String, String>> ordrePassage;

	private int deplacementEtape;
	private List<JLabel> listeLabels;
	private Plan plan;
	private Controleur controleur;
	private Fenetre fenetre;
	
	public ModificationTournee(Fenetre fenetre, Controleur controleur) {
		this.controleur = controleur;
		this.fenetre = fenetre;
    	ordrePassage = new ArrayList<>();
    	listeLabels = new ArrayList<>();

    	deplacementEtape = 0;
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        this.fenetre = fenetre;
        
        JPanel panelAll = new JPanel();
        panelAll.setBackground(Color.red);
        panelAll.setLayout(layout);
        
        JPanel panelDetail = new JPanel();
        panelDetail.setBackground(Color.yellow);

        JButton boutonHaut = new JButton("^");
        JButton boutonBas = new JButton("v");
        JButton validerModif = new JButton("Valider les modifications");
        JButton supprLivr = new JButton("Supprimer la livraison associée");
        JButton modifAdresse = new JButton("Modifier l'emplacement de ce pick-up/delivery");
        boutonHaut.setBounds(15, 5, 20, 20);
        boutonBas.setBounds(15, 30, 20, 20);
        modifAdresse.setBounds(30, 45, 40, 20);
        validerModif.setBounds(30, 60, 40, 20);
        
        boutonHaut.addActionListener(this);
        boutonBas.addActionListener(this);
        validerModif.addActionListener(this);
        supprLivr.addActionListener(this);
        modifAdresse.addActionListener(this);

        panelDetail.add(boutonHaut);
        panelDetail.add(boutonBas);
        panelDetail.add(validerModif);
        panelDetail.add(supprLivr);
        panelDetail.add(modifAdresse);
        
        
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
    
    public void ajouterTournee(Plan plan) {
    	ordrePassage.clear();
    	Tournee tournee = controleur.getTournee();
    	this.plan = plan;
        //Retirer la dernière étape du parcours, qui est arrive sur le point de dépôt

        for (int i = 0; i<tournee.getPlusCourteTournee().size() - 1; ++i) {
        	JLabel l;

        	Chemin chemin = tournee.getPlusCourteTournee().get(i);
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
    
    public void afficherTournee() {

    	listeLabels.clear();
    	resultsPanel.removeAll();
        int compteurPointsEnlevement = 1;
        int compteurPointsLivraison = 1;
        int compteur = 0;
        
        Map<String, Integer> indexationPointsE =  new HashMap<>();
		Map<String, Integer> indexationPointsL =  new HashMap<>();
		int i = 1;
		for(PointEnlevement crntPointE: controleur.getContraintes().getPointsEnlevement() ) {
			indexationPointsE.put(crntPointE.getId(), i);
			for(PointLivraison crntPointL: controleur.getContraintes().getPointsLivraison() ) {
				if(crntPointL.getIdEnlevement().equals(crntPointE.getId())){
					indexationPointsL.put(crntPointL.getId(), i);
				}
	    	}
			i++;
    	}
        
    	resultsPanel.removeAll();
    	listeLabels.clear();
    	ContraintesTournee contraintes = controleur.getContraintes();

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
        		l = creerLabelEtape(etape, indexationPointsE.get(etape.getId()), adresse, "enlevement");
        		compteurPointsEnlevement ++;
        	} else {
        		l = creerLabelEtape(etape, indexationPointsL.get(etape.getId()), adresse, "livraison");
        		compteurPointsLivraison ++;
        	}
        	l.setForeground(Color.CYAN);
        	l.setName(Integer.toString(compteur));
        	l.addMouseListener(this);
        	l.setPreferredSize(new Dimension(100, 35));
            resultsPanel.add(l);
            listeLabels.add(l);
            compteur++;

        }
        repaint();

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
    	JLabel labelClique = (JLabel) m.getSource();
    	if(labelSelectionne != null) {
    		precedentLabelSelectionne = labelSelectionne;
    		precedentLabelSelectionne.setForeground(Color.CYAN);
    	}
    	labelSelectionne = labelClique;
    	labelSelectionne.setForeground(Color.BLUE);
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
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "^":
				if(labelSelectionne != null) {
					int index = Integer.parseInt(labelSelectionne.getName());
					if(index-1 >= 0) {
						Map<String, String> elemSelect = ordrePassage.get(index);
						Map<String, String> elemDessus = ordrePassage.get(index-1);
						ordrePassage.remove(index-1);
						ordrePassage.add(index-1, elemSelect);
						ordrePassage.remove(index);
						ordrePassage.add(index, elemDessus);
						
						Intersection modif = null, prec = null, suiv = null;
			        	for(String etapeId: elemSelect.keySet() ) {
			        		modif = plan.getIntersections().get(etapeId);
			        	}
			        	for(String etapeId: elemDessus.keySet() ) {
			        		suiv = plan.getIntersections().get(etapeId);
			        	}
			        	if(index -1 > 0) {
				        	for(String etapeId: ordrePassage.get(index-2).keySet() ) {
				        		prec = plan.getIntersections().get(etapeId);
				        	}
						}
						
						verifierPrecedencePickupDelivery(modif, suiv, e.getActionCommand());
						
						this.afficherTournee();
				        labelSelectionne = listeLabels.get(index-1);
				        labelSelectionne.setForeground(Color.BLUE);
				        repaint();
				        updateUI();

			        controleur.modifierOrdrePassage(modif, prec, suiv);
			        fenetre.setTournee(controleur.getTournee());
					}
				}
				break;

			case "v":
				if(labelSelectionne != null) {
					int index = Integer.parseInt(labelSelectionne.getName());
					if(index+1 < ordrePassage.size()) {
						Map<String, String> elemSelect = ordrePassage.get(index);
						Map<String, String> elemDessous = ordrePassage.get(index+1);
						ordrePassage.remove(index);
						ordrePassage.add(index, elemDessous);
						ordrePassage.remove(index+1);
						ordrePassage.add(index+1, elemSelect);
						
						Intersection modif = null, prec = null, suiv = null;
			        	for(String etapeId: elemSelect.keySet() ) {
			        		modif = plan.getIntersections().get(etapeId);
			        	}
			        	for(String etapeId: elemDessous.keySet() ) {
			        		prec = plan.getIntersections().get(etapeId);
			        	}
			        	if(index + 2 < ordrePassage.size()) {
				        	for(String etapeId: ordrePassage.get(index+2).keySet() ) {
				        		suiv = plan.getIntersections().get(etapeId);
				        	}
						}

						verifierPrecedencePickupDelivery(modif, prec, e.getActionCommand());
						
						this.afficherTournee();
				        labelSelectionne = listeLabels.get(index+1);
				        labelSelectionne.setForeground(Color.BLUE);
				        repaint();
				        updateUI();
			        controleur.modifierOrdrePassage(modif, prec, suiv);
			        fenetre.setTournee(controleur.getTournee());
					}
				}
				break;
			
			case "Valider les modifications":
				fenetre.apresModifOrdre();
				fenetre.afficherInfos();
				break;
			case "Supprimer la livraison associée":
				if(labelSelectionne != null) {
					int index = Integer.parseInt(labelSelectionne.getName());
					Map<String, String> elemSelect = ordrePassage.get(index);
					
					if (JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer les deux points associés à cette livraison ?") == JOptionPane.OK_OPTION) {
						ordrePassage.remove(index);
						Intersection elemSuppr = null;
						PointEnlevement enlevement = null;
						PointLivraison livraison = null;
						boolean isSuppr = false;
			        	for(String etapeId: elemSelect.keySet() ) {
			        		elemSuppr = plan.getIntersections().get(etapeId);
			        	}
			        	
						for(PointLivraison pl : controleur.getContraintes().getPointsLivraison()) {
							if(pl.equals(elemSuppr)) {
								isSuppr = true;
								for(ListIterator<Map<String, String>> it = ordrePassage.listIterator(); it.hasNext();) {
									Map<String, String> elem = it.next();
									if(elem.containsKey(pl.getIdEnlevement())) {
										it.remove();
										break;
									}
								}
								controleur.supprimerLivraison(pl);
								break;
							}
						}
						if(!isSuppr) {
							for(PointEnlevement pe : controleur.getContraintes().getPointsEnlevement()) {
								if(pe.equals(elemSuppr)) {
									for(ListIterator<Map<String, String>> it = ordrePassage.listIterator(); it.hasNext();) {
										Map<String, String> elem = it.next();
										if(elem.containsKey(pe.getIdLivraison())) {
											it.remove();
											break;
										}
									}
									controleur.supprimerLivraison(pe);
									break;
								}
							}
						}
						this.afficherTournee();
				        updateUI();
				        fenetre.setTournee(controleur.getTournee());
					}
				}
				break;
			//case "Modifier l'emplacement de ce pick-up/delivery":
				
		}
	}
	
	private void verifierPrecedencePickupDelivery(Intersection elemSelect, Intersection autreElem, String deplacement){
		if(deplacement.equals("^")) {
			for(PointLivraison pl : controleur.getContraintes().getPointsLivraison()) {
				if(pl.equals(elemSelect) && autreElem.getId().equals(pl.getIdEnlevement())) {
					JOptionPane.showMessageDialog(null, "Attention, le point de livraison est avant le point d'enlèvement !");
					break;
				}
			}
		} else {
			for(PointEnlevement pe : controleur.getContraintes().getPointsEnlevement()) {
				if(pe.equals(elemSelect) && autreElem.getId().equals(pe.getIdLivraison())) {
					JOptionPane.showMessageDialog(null, "Attention, le point d'enlèvement est après le point de livraison !");
					break;
				}
			}
		}
	}
}
