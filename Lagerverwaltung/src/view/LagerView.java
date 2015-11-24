package view;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import controller.Controller;

public class LagerView extends JFrame{

	public LagerView(int anzahlLager, Controller control){
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
		
		this.add(toolbar);
		this.setSize(200, 200);
		this.setVisible(true);	
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
