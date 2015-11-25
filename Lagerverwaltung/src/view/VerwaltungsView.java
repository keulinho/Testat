package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import controller.LagerVerwaltungsController;

public class VerwaltungsView extends JFrame{

	int restMenge;
	public VerwaltungsView(int anzahlLager, LagerVerwaltungsController control){
		this.setTitle("Lagerverwaltung");
		JToolBar toolbar = new JToolBar();
		
		JButton saveButton= new JButton("Speichern");
		try {
		    Image img = ImageIO.read(new File("src/icons/save.png"));
		    saveButton.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
	
		JButton openButton= new JButton("Öffnen");
		try {
		    Image img = ImageIO.read(new File("src/icons/open.png"));
		    openButton.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		JButton zulieferungButton= new JButton("Neue Zulieferung");
		try {
		    Image img = ImageIO.read(new File("src/icons/zulieferung.png"));
		    zulieferungButton.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		JButton auslieferungButton= new JButton("Neue Auslieferung");
		try {
		    Image img = ImageIO.read(new File("src/icons/auslieferung.png"));
		    auslieferungButton.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		restMenge=1000;
		JLabel laufendeBuchung = new JLabel("<html>Es gibt eine laufende Buchung <br>"+restMenge+" Einheiten müssen noch verteilt werden</html>");
		try {
		    Image img = ImageIO.read(new File("src/icons/exclamation.png"));
		    laufendeBuchung.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		toolbar.add(saveButton);
		toolbar.add(openButton);
		toolbar.add(zulieferungButton);
		toolbar.add(auslieferungButton);
		toolbar.add(Box.createRigidArea(new Dimension(10,0)));
		toolbar.add(laufendeBuchung);
		toolbar.setFloatable(false);
		
		this.add(toolbar,BorderLayout.PAGE_START);
		
		
		
		
		DetailView detailPane = new DetailView();
		detailPane.setBackground(Color.LIGHT_GRAY);
		this.add(detailPane, BorderLayout.EAST);
			
		TreeView treePane = new TreeView();
		treePane.setBackground(Color.LIGHT_GRAY);
		this.add(treePane, BorderLayout.WEST);
		
		this.setLocation(150, 100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
		this.setVisible(true);	
	
		
		
		
		
	}
}
