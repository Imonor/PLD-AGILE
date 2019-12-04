package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;

import model.Chemin;
import model.Tournee;

public class ModificationTournee extends JPanel{
	
	private JPanel mainList;

    public ModificationTournee() {
        setLayout(new BorderLayout());
        
        //List<Chemin> listeChemins = tournee.getPlusCourteTournee();
        
        mainList = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        
        mainList.add(new JPanel(), gbc);

        add(new JScrollPane(mainList));
        
        for(int i = 0; i < 5; i++) {
        	JPanel panel = new JPanel();
            panel.add(new JLabel("Hello"+i));
            panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
            GridBagConstraints gbc2 = new GridBagConstraints();
            gbc2.gridwidth = GridBagConstraints.REMAINDER;
            gbc2.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            mainList.add(panel, gbc, 0);
        }
    }
}
