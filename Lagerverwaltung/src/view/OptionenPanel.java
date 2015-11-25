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
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class OptionenPanel extends JPanel{
	EditNamePanel editName;
	JButton loeschen, neuesLager, umbenennen, anteilBuchen;
	JLabel infoText,menge, anteil;
	JSlider slider;

	public OptionenPanel(EditNamePanel editName){
		this.editName=editName;
		
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
		menge= new JLabel("<html>absolute Menge: <br>relative Menge:</html>");
		anteil = new JLabel();
		anteil.setMinimumSize(new Dimension(50, 30));
		anteil.setPreferredSize(new Dimension(50, 30));
		anteil.setMaximumSize(new Dimension(50, 30));
		
		slider = new JSlider(JSlider.HORIZONTAL,1,1327,5);
		
		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				anteil.setText("<html>"+slider.getValue()+"<br>"+rechneProzent()+"%</html>");
			}
		});
		anteilBuchen = new JButton("Anteil buchen");
		try {
		    Image img = ImageIO.read(new File("src/icons/enterBuchung.png"));
		    anteilBuchen.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		
	}
	
	/**
	 * löscht alle Elemente dieses Panels und fügt alle GUI-Elemente für die Button Anzeige hinzu
	 */
	public void zeigeButton() {
		this.setLayout(new FlowLayout());
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
		this.setLayout(new FlowLayout());
		this.removeAll();
		this.add(infoText);
		this.revalidate();
	}
	
	/**
	 * löscht alle Elemente dieses Panels und fügt alle GUI-Elemente für die Slider Anzeige hinzu
	 */
	public void zeigeSlider() {
		
		this.removeAll();
		this.add(menge);
		anteil.setText("<html>"+slider.getValue()+"<br>"+rechneProzent()+"%</html>");
		this.add(anteil);
		this.add(slider);
		this.add(anteilBuchen);
		this.revalidate();
	}
	/**
	 * Rechnet den aktuellen Wert des Sliders in ein auf zwei Stellen gerundete Prozentzahl um
	 * @return Prozentzahl auf zwei Stellen gerundet
	 */
	public double rechneProzent() {
		double prozent = (((double)slider.getValue()/slider.getMaximum())*100.00);
		prozent = (prozent*1000)+5;
		int temp = (int) (prozent/10);
		prozent = (double)temp/100.00;
		return prozent;
	}
}
