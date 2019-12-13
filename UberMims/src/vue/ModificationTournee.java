package vue;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
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
import vue.AffichagePlan.Etat;

public class ModificationTournee extends JPanel implements MouseListener, ActionListener, KeyListener{

	private Color backgroundBleuCiel = new Color(191, 252, 251);
	private Color backgroundTurquoiseClair = new Color(135, 216, 217);
	private Color backgroundTurquoise = new Color(25, 174, 186);
	private Color backgroundJaune = new Color(226, 179, 72);
	private Color backgroundOrange = new Color(229, 138, 86);
	private Color backgroundRougeClair = new Color(184, 64, 57);

	private Font police = new Font("Avenir", 0, 15);
	private List<Color> listColors = new ArrayList();
	Map<String, Color> colorPointsE =  new HashMap<>();
	Map<String, Color> colorPointsL =  new HashMap<>();
	
	private JPanel panelAll;
	private JPanel resultsPanel;
	
	private JPanel panelDetail;
	
	private JPanel panelModifAdresse;
	private JPanel panelModifTemps;
	private JPanel panelInfoModifAdresse;
	private JPanel panelInfoModifTemps;
	private JPanel panelValiderModifAdresse;
	private JPanel panelValiderModifTemps;
	private JFormattedTextField champTemps = new JFormattedTextField(NumberFormat.getIntegerInstance());



	private JLabel labelSelectionne;
	private JLabel precedentLabelSelectionne; //Permet de changer la couleur lorsqu'on selectionne un autre bouton
	private List<Map<String, String>> ordrePassage;

	
	private int nouveauTemps=0;
	private int deplacementEtape;
	private List<JLabel> listeLabels;
	private Plan plan;
	private Controleur controleur;
	private Fenetre fenetre;
	
	public ModificationTournee(Fenetre fenetre, Controleur controleur) {
		listColors.add(Color.decode("#2F4F4F"));
		listColors.add(Color.decode("#808080"));
		listColors.add(Color.decode("#800000"));
		listColors.add(Color.decode("#8B4513"));
		listColors.add(Color.decode("#D2691E"));
		listColors.add(Color.decode("#191970"));
		listColors.add(Color.decode("#4169E1"));
		listColors.add(Color.decode("#556B2F"));
		listColors.add(Color.decode("#006400"));
		listColors.add(Color.decode("#3CB371"));
		listColors.add(Color.decode("#32CD32"));
		listColors.add(Color.decode("#BDB76B"));
		listColors.add(Color.decode("#FF4500"));
		listColors.add(Color.decode("#DC143C"));
		listColors.add(Color.decode("#FA8072"));
		listColors.add(Color.decode("#4B0082"));
		listColors.add(Color.decode("#8B008B"));
		listColors.add(Color.decode("#C71585"));
		listColors.add(Color.decode("#9400D3"));
		listColors.add(Color.decode("#6365ff"));
		
		
		this.controleur = controleur;
		this.fenetre = fenetre;
    	ordrePassage = new ArrayList<>();
    	listeLabels = new ArrayList<>();
    	
		
    	//--------------------LAYOUT-----------------------------//
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        
      //--------------------PANEL ALL-----------------------------//
        panelAll = new JPanel();
        panelAll.setBackground(Color.red);
        panelAll.setLayout(layout);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty=2.5;
        gbc.fill = GridBagConstraints.BOTH;
        
        this.add(panelAll, gbc);
        
      //--------------------PANEL DETAIL-----------------------------//
        panelDetail = new JPanel();
        panelDetail.setBackground(Color.yellow);

        JButton boutonHaut = new JButton("^");
        JButton boutonBas = new JButton("v");
        JButton validerModif = new JButton("Valider les modifications");
        JButton supprLivr = new JButton("Supprimer la livraison associee");
        JButton modifAdresse = new JButton("Modifier l'emplacement de ce point");
        JButton modifTemps = new JButton("Modifier le temps de passage a ce point");
        boutonHaut.setBounds(15, 5, 20, 20);
        boutonBas.setBounds(15, 30, 20, 20);
        modifAdresse.setBounds(30, 45, 40, 20);
        modifTemps.setBounds(30, 70, 40, 20);
        validerModif.setBounds(30, 60, 40, 20);
        
        boutonHaut.addActionListener(this);
        boutonBas.addActionListener(this);
        validerModif.addActionListener(this);
        supprLivr.addActionListener(this);
        modifAdresse.addActionListener(this);
        modifTemps.addActionListener(this);

        panelDetail.add(boutonHaut);
        panelDetail.add(boutonBas);
        panelDetail.add(supprLivr);
        panelDetail.add(modifAdresse);
        panelDetail.add(modifTemps);
        panelDetail.add(validerModif);

        panelDetail.setBackground(backgroundTurquoiseClair);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty=1.0;
        gbc.fill = GridBagConstraints.BOTH;
        
        this.add(panelDetail, gbc);
        
      //--------------------PANEL MODIF ADRESSE-----------------------------//
        panelModifAdresse = new JPanel();
        panelModifAdresse.setBackground(Color.red);
        panelModifAdresse.setVisible(false);
        
        JButton annulerModifAdresse = new JButton("Annuler la modification de l'adresse");
        annulerModifAdresse.addActionListener(this);
        
        panelInfoModifAdresse = new JPanel();
        JLabel infoModifAdresse = new JLabel("Veuillez cliquer sur l'intersection ou vous souhaitez realiser le pick-up/delivery");
        panelInfoModifAdresse.add(infoModifAdresse);
        
        
        panelValiderModifAdresse = new JPanel();
        panelValiderModifAdresse.setVisible(false);
        JButton validerModifAdresse = new JButton("Valider la modification de l'adresse");
        validerModifAdresse.addActionListener(this);
        panelValiderModifAdresse.add(validerModifAdresse);
        
        
        panelModifAdresse.add(panelInfoModifAdresse);
        panelModifAdresse.add(panelValiderModifAdresse);
        panelModifAdresse.add(annulerModifAdresse);
             
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty=1.0;
        gbc.fill = GridBagConstraints.BOTH;
        
        this.add(panelModifAdresse, gbc);
        
        //--------------------PANEL MODIF TEMPS-----------------------------//
        panelModifTemps = new JPanel();
        panelModifTemps.setBackground(backgroundTurquoiseClair);
        panelModifTemps.setVisible(false);
        
        JButton annulerModifTemps = new JButton("Annuler la modification de la duree");
        annulerModifTemps.addActionListener(this);

    	
		
		panelInfoModifTemps = new JPanel();
        panelInfoModifTemps.setBackground(backgroundTurquoiseClair);
        JLabel infoModifTemps = new JLabel("Veuillez entrer la duree a changer (en minutes) :");
        infoModifTemps.setBackground(backgroundTurquoiseClair);
        //infoModifTemps.setBounds());
        panelInfoModifTemps.add(infoModifTemps);
		
        panelValiderModifTemps = new JPanel();
        panelValiderModifTemps.setVisible(true);
        panelValiderModifTemps.setBackground(backgroundTurquoiseClair);
        JButton validerModifTemps = new JButton("Valider la modification de la duree");
        validerModifTemps.addActionListener(this);
        panelModifTemps.add(validerModifTemps);
        // Champ Temps
     		champTemps.setVisible(true);
     		champTemps.addKeyListener(this);
             
     	//champTemps.setFont(police);
     	champTemps.setSize(100, 30);;
     	champTemps.setBackground(Color.white);
        
        panelModifTemps.add(panelInfoModifTemps);
 		panelModifTemps.add(champTemps);
 		panelModifTemps.add(panelValiderModifTemps);
        panelModifTemps.add(annulerModifTemps);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty=1.0;
        gbc.fill = GridBagConstraints.BOTH;
        
        this.add(panelModifTemps, gbc);
        
      //--------------------AFFICHAGE DES ETAPES-----------------------------//
        JPanel textArea = new JPanel();
        textArea.setLayout(new BorderLayout());
        textArea.setBackground(Color.cyan);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty= 1.5;
        gbc.weightx= 1.5;
        panelAll.add(textArea, gbc);
        
        
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollpane = new JScrollPane(resultsPanel);
        scrollpane.getVerticalScrollBar().setUnitIncrement(16);
        scrollpane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        resultsPanel.setBackground(backgroundBleuCiel);
        scrollpane.setBackground(backgroundBleuCiel);
        scrollpane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        textArea.add(scrollpane, BorderLayout.CENTER);
        
    }
    
    public void ajouterTournee(Plan plan) {
    	ordrePassage.clear();
    	Tournee tournee = controleur.getTournee();
    	this.plan = plan;
        //Retirer la derni�re �tape du parcours, qui est arrive sur le point de d�p�t

        for (int i = 0; i<tournee.getPlusCourteTournee().size() - 1; ++i) {
        	JLabel l;

        	Chemin chemin = tournee.getPlusCourteTournee().get(i);
        	//R�cup�rer une intersection �tape du parcours et l'ajouter � la liste
        	Intersection etape = chemin.getDerniere();
        	//R�cup�rer l'adresse (en prenant le nom de rue du dernier troncon menant � l'intersection)
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
			colorPointsE.put(crntPointE.getId(), listColors.get(i));
			for(PointLivraison crntPointL: controleur.getContraintes().getPointsLivraison() ) {
				if(crntPointL.getIdEnlevement().equals(crntPointE.getId())){
					indexationPointsL.put(crntPointL.getId(), i);
					colorPointsL.put(crntPointL.getId(), listColors.get(i));
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
        	//Tester si l'�tape est un pick up ou un delivery
        	boolean isPtEnlevement = false;

        	for(PointEnlevement ptEnlevement: contraintes.getPointsEnlevement()) {
        		if(ptEnlevement.getId().equals(etape.getId())) {
        			isPtEnlevement = true;
        			break;
        		}
        	}
        	//Cr�er le label correspondant
        	if(isPtEnlevement) {
        		l = creerLabelEtape(etape, indexationPointsE.get(etape.getId()), adresse, "enlevement");
        		compteurPointsEnlevement ++;
        	} else {
        		l = creerLabelEtape(etape, indexationPointsL.get(etape.getId()), adresse, "livraison");
        		compteurPointsLivraison ++;
        	}
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
    		label.setText(label.getText() + "Pick Up numero " + position + " :   <br>");
        	label.setForeground(colorPointsE.get(etape.getId()));
    	} else {
    		label.setText(label.getText() + "Delivery numero " + position + " :   <br>");
        	label.setForeground(colorPointsL.get(etape.getId()));
    	}
			
		label.setText(label.getText() + "&rarr; Adresse : " + adresse +"<br><br>");	
		return label;
    }
    
    public void mouseClicked(MouseEvent m) {
    	JLabel labelClique = (JLabel) m.getSource();
    	if(labelSelectionne != null) {
    		Color c = labelSelectionne.getForeground();
    		precedentLabelSelectionne = labelSelectionne;
    		precedentLabelSelectionne.setForeground(c);
    	}
    	labelSelectionne = labelClique;
    	labelSelectionne.setForeground(Color.RED);
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
				        labelSelectionne.setForeground(Color.RED);
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
				        labelSelectionne.setForeground(Color.RED);
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
			case "Supprimer la livraison associee":
				if(labelSelectionne != null) {
					int index = Integer.parseInt(labelSelectionne.getName());
					Map<String, String> elemSelect = ordrePassage.get(index);
					
					if (JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer les deux points associes a cette livraison ?") == JOptionPane.OK_OPTION) {
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
						labelSelectionne=null;

					}
					labelSelectionne = null;
				}
				
				break;
			case "Modifier l'emplacement de ce point":
				this.panelValiderModifAdresse.setVisible(false);
				if(labelSelectionne != null) {
					fenetre.getAffichagePlan().setPlanClickable(true);
					panelDetail.setVisible(false);
					panelModifAdresse.setVisible(true);
					fenetre.getAffichagePlan().setEtat(Etat.MODIF_ADRESSE);
				} else {
					JOptionPane.showMessageDialog(null, "Veuillez saisir un point de pick-up ou delivery !");
				}
				break;
				
			case "Annuler la modification de l'adresse":
				fenetre.getAffichagePlan().setPlanClickable(false);
				fenetre.getAffichagePlan().setNouvelleAdresse(null);
				fenetre.getAffichagePlan().repaint();
				labelSelectionne = null;
				panelDetail.setVisible(true);
				panelModifAdresse.setVisible(false);
				break;
				
			case "Valider la modification de l'adresse":
				fenetre.getAffichagePlan().setPlanClickable(false);
				modifierAdresse();
				fenetre.getAffichagePlan().setNouvelleAdresse(null);
				fenetre.getAffichagePlan().repaint();
				labelSelectionne = null;
				panelDetail.setVisible(true);
				panelModifAdresse.setVisible(false);
				ajouterTournee(fenetre.getPlan());
				afficherTournee();
				break;
				
			case "Modifier le temps de passage a ce point":
				this.panelValiderModifTemps.setVisible(true);
				if(labelSelectionne != null) {
					int index = Integer.parseInt(labelSelectionne.getName());
					Map<String, String> elemSelect = ordrePassage.get(index);
					String intersectionId = elemSelect.keySet().iterator().next();
					Intersection intersection = plan.getIntersections().get(intersectionId);
					int duree = 0;
					for(PointEnlevement ptE: controleur.getContraintes().getPointsEnlevement()) {
						if(ptE.equals(intersection)) {
							duree = ptE.getTempsEnlevement();
							break;
						}
					}
					for(PointLivraison ptL: controleur.getContraintes().getPointsLivraison()) {
						if(ptL.equals(intersection)) {
							duree = ptL.getTempsLivraison();
							break;
						}
					}
					champTemps.setText(String.valueOf((int)duree/60));
					panelDetail.setVisible(false);
					panelModifTemps.setVisible(true);
					fenetre.getAffichagePlan().setEtat(Etat.MODIF_TEMPS);
				} else {
					JOptionPane.showMessageDialog(null, "Veuillez saisir un point de pick-up ou delivery !");
				}
				break;	
				
			
			case "Annuler la modification de la duree":
				fenetre.getAffichagePlan().repaint();
				labelSelectionne = null;
				panelDetail.setVisible(true);
				panelModifTemps.setVisible(false);
				break;
				
			case "Valider la modification de la duree":
				if(labelSelectionne != null) {
					int index = Integer.parseInt(labelSelectionne.getName());
					Map<String, String> elemSelect = ordrePassage.get(index);
					String intersectionId = elemSelect.keySet().iterator().next();
					Intersection intersection = plan.getIntersections().get(intersectionId);
					int duree = ((Number) champTemps.getValue()).intValue();
					duree=duree*60;
					for(PointEnlevement ptE: controleur.getContraintes().getPointsEnlevement()) {
						if(ptE.equals(intersection)) {
							controleur.modifierTemps(ptE, duree);
							break;
						}
					}
					for(PointLivraison ptL: controleur.getContraintes().getPointsLivraison()) {
						if(ptL.equals(intersection)) {
							System.out.println(ptL.getId() +"    "+duree*60);
							controleur.modifierTemps(ptL, duree);
							break;
						}
					}
					champTemps.setText(String.valueOf(duree));
					panelDetail.setVisible(false);
					panelModifTemps.setVisible(true);
					fenetre.getAffichagePlan().setEtat(Etat.MODIF_TEMPS);
				} else {
					JOptionPane.showMessageDialog(null, "Veuillez saisir un point de pick-up ou delivery !");
				}
				
				fenetre.getAffichagePlan().repaint();
				labelSelectionne = null;
				panelDetail.setVisible(true);
				panelModifTemps.setVisible(false);
				ajouterTournee(fenetre.getPlan());
				afficherTournee();
				break;
		}
	}
	
	
	private void verifierPrecedencePickupDelivery(Intersection elemSelect, Intersection autreElem, String deplacement){
		if(deplacement.equals("^")) {
			for(PointLivraison pl : controleur.getContraintes().getPointsLivraison()) {
				if(pl.equals(elemSelect) && autreElem.getId().equals(pl.getIdEnlevement())) {
					JOptionPane.showMessageDialog(null, "Attention, le point de livraison est avant le point d'enlevement !");
					break;
				}
			}
		} else {
			for(PointEnlevement pe : controleur.getContraintes().getPointsEnlevement()) {
				if(pe.equals(elemSelect) && autreElem.getId().equals(pe.getIdLivraison())) {
					JOptionPane.showMessageDialog(null, "Attention, le point d'enlevement est apres le point de livraison !");
					break;
				}
			}
		}
	}
	
	
	public void afficherValidationModifAdresse() {
		panelValiderModifAdresse.setVisible(true);
	}
	
	private void modifierAdresse() {
		Intersection nouvelleAdresse = fenetre.getAffichagePlan().getNouvelleAdresse();
		int index = Integer.parseInt(labelSelectionne.getName());
		Map<String, String> elemSelect = ordrePassage.get(index);
		
		String intersectionAmodifierId = elemSelect.keySet().iterator().next();
		Intersection intersectionAModifier = plan.getIntersections().get(intersectionAmodifierId);
		
		for(PointEnlevement ptE: controleur.getContraintes().getPointsEnlevement()) {
			if(ptE.equals(intersectionAModifier)) {
				controleur.modifierAdresse(ptE, nouvelleAdresse);
				return;
			}
		}
		for(PointLivraison ptL: controleur.getContraintes().getPointsLivraison()) {
			if(ptL.equals(intersectionAModifier)) {
				controleur.modifierAdresse(ptL, nouvelleAdresse);
				return;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("keypressed");
		repaint();
		updateUI();
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	public JPanel getPanelModifAdresse() {
		return panelModifAdresse;
	}

	public JPanel getPanelModifTemps() {
		return panelModifTemps;
	}

	public JPanel getPanelDetail() {
		return panelDetail;
	}
	
	
}
