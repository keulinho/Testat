package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OptionenPanel extends JPanel{
	EditNamePanel editName;
	JButton loeschen, neuesLager, umbenennen;
	JLabel infoText;

	public OptionenPanel(EditNamePanel editName){
		this.editName=editName;
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(515,50));
		
		guiElementeErstellen();
		
		zeigeButton();
		
		
	}
	
	/**
	 * Erstellt alle GUI-Elemente die in diesem Panel angezeigt werden können
	 */
	public void guiElementeErstellen(){
		//Button-Modus
		loeschen = new JButton("Lager löschen");
		try {
		    Image img = ImageIO.read(new File("src/icons/delete.png"));
		    loeschen.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		neuesLager = new JButton("Unterlager erstellen");
		try {
		    Image img = ImageIO.read(new File("src/icons/new.png"));
		    neuesLager.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		umbenennen = new JButton("Lager umbennen");
		try {
		    Image img = ImageIO.read(new File("src/icons/edit2.png"));
		    umbenennen.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		umbenennen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				OptionenPanel.this.editName.edit();
			}
		});
		
		infoText = new JLabel("<html>Um die Buchung zu verteilen gehen Sie zu einem Lager der untersten Ebene <br>Um die Optionen zu öffnen schließen Sie die Buchung ab</html>");
		infoText.setPreferredSize(new Dimension(515,50));
		try {
		    Image img = ImageIO.read(new File("src/icons/info.png"));
		    infoText.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		
	}
	
	/**
	 * löscht alle Elemente dieses Panels und fügt alle GUI-Elemente für die Button Anzeige hinzu
	 */
	public void zeigeButton() {
		this.removeAll();
		this.add(loeschen);
		this.add(Box.createRigidArea(new Dimension(2,0)));
		this.add(neuesLager);
		this.add(Box.createRigidArea(new Dimension(2,0)));
		this.add(umbenennen);
		this.revalidate();
	}
	
	/**
	 * löscht alle Elemente dieses Panels und fügt alle GUI-Elemente für die Info Anzeige hinzu
	 */
	public void zeigeInfo() {
		this.removeAll();
		this.add(infoText);
		this.revalidate();
	}
}
