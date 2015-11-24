package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import controller.LagerVerwaltungsController;

public class VerwaltungsView extends JFrame{

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
		JButton buchungButton= new JButton("Neue Buchung erstellen");
		try {
		    Image img = ImageIO.read(new File("src/icons/newBuchung.png"));
		    buchungButton.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		
		toolbar.add(saveButton);
		toolbar.add(openButton);
		toolbar.add(buchungButton);
		toolbar.setFloatable(false);
		
		this.add(toolbar,BorderLayout.PAGE_START);
		
		JPanel treePane = new JPanel(new BorderLayout());
		JLabel demo = new JLabel("xdf");
		treePane.setBackground(Color.cyan);
		treePane.setPreferredSize(new Dimension(200, 100));
		treePane.add(demo,BorderLayout.CENTER);
		
		DetailView detailPane = new DetailView();
		detailPane.setBackground(Color.LIGHT_GRAY);
		
		this.add(detailPane, BorderLayout.EAST);
		this.add(treePane, BorderLayout.WEST);
		
		this.setLocation(150, 100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
		this.setVisible(true);	
	}
}
