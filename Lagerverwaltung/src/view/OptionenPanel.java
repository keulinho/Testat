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
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.LagerVerwaltungsController;
import core.exception.ErrorHandler;
import core.exception.ImageNotFoundException;
import core.utils.Rechner;

public class OptionenPanel extends JPanel{
	JButton loeschen, neuesLager, umbenennen, anteilBuchen;
	JLabel infoText,menge, anteil;
	JSlider slider;
	LagerVerwaltungsController controller;
	int gesamtMenge, restMenge;
	Rechner rechner;
	
	/**
	 * erzeugt ein OptionenPanel im zeige Button Modus
	 * @param controller controller an den Befehle weitergeleitet werden
	 */
	public OptionenPanel(LagerVerwaltungsController controller){
		rechner=new Rechner();
		this.controller=controller;
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
		    Image img = ImageIO.read(new File("src/icons/trash.png"));
		    loeschen.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ErrorHandler.HandleException(ErrorHandler.BILD_NICHT_GEFUNDEN, new ImageNotFoundException("Bilddatei mit dem Pfad \"src/icons/trash.png\" nicht gefunden",(Throwable) ex));
		  }
		loeschen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.loescheLager();
			}
		});
		neuesLager = new JButton("Unterlager erstellen");
		try {
		    Image img = ImageIO.read(new File("src/icons/new.png"));
		    neuesLager.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ErrorHandler.HandleException(ErrorHandler.BILD_NICHT_GEFUNDEN, new ImageNotFoundException("Bilddatei mit dem Pfad \"src/icons/new.png\" nicht gefunden",(Throwable) ex));
		  }
		neuesLager.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VerwaltungsView vView = (VerwaltungsView) SwingUtilities.getWindowAncestor(OptionenPanel.this);
				vView.zeigeNeuesLager(false);
			}
		});
		umbenennen = new JButton("Lager umbennen");
		try {
		    Image img = ImageIO.read(new File("src/icons/edit2.png"));
		    umbenennen.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ErrorHandler.HandleException(ErrorHandler.BILD_NICHT_GEFUNDEN, new ImageNotFoundException("Bilddatei mit dem Pfad \"src/icons/edit2.png\" nicht gefunden",(Throwable) ex));
		  }
		umbenennen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VerwaltungsView vView = (VerwaltungsView) SwingUtilities.getWindowAncestor(OptionenPanel.this);
				vView.editName();
			}
		});
		//Info Modus bei offener Buchung und gewähltem Oberlager
		infoText = new JLabel("<html>Um die Buchung zu verteilen gehen Sie zu einem Lager der untersten Ebene <br>Um die Optionen zu öffnen schließen Sie die Buchung ab</html>");
		infoText.setPreferredSize(new Dimension(515,50));
		try {
		    Image img = ImageIO.read(new File("src/icons/info.png"));
		    infoText.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ErrorHandler.HandleException(ErrorHandler.BILD_NICHT_GEFUNDEN, new ImageNotFoundException("Bilddatei mit dem Pfad \"src/icons/info.png\" nicht gefunden",(Throwable) ex));
		  }
		//Slider Modus bei offener Buchung und gewähltem Unterlager
		menge= new JLabel("<html>absolute Menge: <br>relative Menge:</html>");
		anteil = new JLabel();
		anteil.setMinimumSize(new Dimension(50, 30));
		anteil.setPreferredSize(new Dimension(50, 30));
		anteil.setMaximumSize(new Dimension(50, 30));
		
		slider = new JSlider(JSlider.HORIZONTAL);
		
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
			  ErrorHandler.HandleException(ErrorHandler.BILD_NICHT_GEFUNDEN, new ImageNotFoundException("Bilddatei mit dem Pfad \"src/icons/enterBuchung.png\" nicht gefunden",(Throwable) ex));
		  }
		anteilBuchen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.bucheAnteil(slider.getValue());
			}
		});
		
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
		this.repaint();
	}
	
	/**
	 * löscht alle Elemente dieses Panels und fügt alle GUI-Elemente für die Info Anzeige hinzu
	 */
	public void zeigeInfo() {
		this.setLayout(new FlowLayout());
		this.removeAll();
		this.add(infoText);
		this.revalidate();
		this.repaint();
	}
	
	
	/**
	 * löscht alle Elemente dieses Panels und fügt alle GUI-Elemente für die Slider Anzeige hinzu
	 * @param gesamtMenge gesamt Menge zu der der Prozentsatz ausgerrechnent wird
	 * @param maximum maximum des Sliders
	 */
	public void zeigeSlider(int gesamtMenge, int maximum) {
		this.gesamtMenge=gesamtMenge;
		this.restMenge=maximum;
		this.removeAll();
		this.add(menge);
		slider.setMinimum(0);
		slider.setMaximum(maximum);
		slider.setValue(slider.getMaximum()/2);
		anteil.setText("<html>"+slider.getValue()+"<br>"+rechneProzent()+"%</html>");
		this.add(anteil);
		this.add(slider);
		this.add(anteilBuchen);
		this.revalidate();
		this.repaint();
	}
	/**
	 * Rechnet den aktuellen Wert des Sliders in ein auf zwei Stellen gerundete Prozentzahl um
	 * @return Prozentzahl auf zwei Stellen gerundet
	 */
	public double rechneProzent() {
		if (this.gesamtMenge==0) {
			return 100;
		} else {
			int gesamtMenge;
			if (this.gesamtMenge<0) {
				gesamtMenge = this.gesamtMenge * (-1);
			} else {
				gesamtMenge = this.gesamtMenge;
			}
			return rechner.rechneProzent(slider.getValue(), gesamtMenge);
		}	
	}
}
