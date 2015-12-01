package view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.text.NumberFormatter;

import controller.LagerVerwaltungsController;

public class BuchungBar extends JToolBar{
	
	JButton undo, redo, buchungVerwerfen,buchungAbschliessen,auslieferungErstellen,zulieferungErstellen;
	JLabel laufendeZulieferung, neueBuchung,laufendeAuslieferung;
	JFormattedTextField menge;
	LagerVerwaltungsController controller;

	
	/**
	 * erzeugt eine BuchungsBar
	 * @param controller Controller an den alle Befehle runtergereicht werden
	 */
	public BuchungBar(LagerVerwaltungsController controller){
		
		this.controller=controller;
		this.setFloatable(false);
		guiElementeErstellen();

	}

	/**
	 * Erstellt alle GUI-Elemente die in diesem Panel angezeigt werden k�nnen
	 */
	public void guiElementeErstellen(){
		undo= new JButton("Undo");
		try {
		    Image img = ImageIO.read(new File("src/icons/undo.png"));
		    undo.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		undo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.undo();
			}
		});
		
		redo= new JButton("Redo");
		try {
		    Image img = ImageIO.read(new File("src/icons/redo.png"));
		    redo.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		redo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.redo();
			}
		});
		laufendeZulieferung = new JLabel();
		try {
		    Image img = ImageIO.read(new File("src/icons/exclamation.png"));
		    laufendeZulieferung.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		laufendeAuslieferung = new JLabel();
		try {
		    Image img = ImageIO.read(new File("src/icons/exclamation.png"));
		    laufendeAuslieferung.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		buchungVerwerfen= new JButton("Buchung verwerfen");
		try {
		    Image img = ImageIO.read(new File("src/icons/delete.png"));
		    buchungVerwerfen.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		buchungVerwerfen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.buchungVerwerfen();
			}
		});
		buchungAbschliessen= new JButton("Buchung abschlie�en");
		try {
		    Image img = ImageIO.read(new File("src/icons/check.png"));
		    buchungAbschliessen.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		buchungAbschliessen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.buchungAbschliessen();
			}
		});
		neueBuchung = new JLabel();
		NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setAllowsInvalid(false);
	    formatter.setCommitsOnValidEdit(true);
	    menge = new JFormattedTextField(formatter);
	    menge.setPreferredSize(new Dimension(200,30));
	    menge.setMaximumSize(new Dimension(200,30));
	    menge.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(menge.getValue()==null){
					zulieferungErstellen.setEnabled(false);
				} else {
					zulieferungErstellen.setEnabled(true);
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						zeigeLaufendeZulieferung((int) menge.getValue());
					}
					
				}
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if ((menge.getValue()!=null)&&(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)&&((int)menge.getValue()<10)) {
					menge.setValue(null);
					zulieferungErstellen.setEnabled(false);
				}
			}
		});
	    auslieferungErstellen= new JButton("Buchung erstellen");
		try {
		    Image img = ImageIO.read(new File("src/icons/check.png"));
		    auslieferungErstellen.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		auslieferungErstellen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				zeigeLaufendeAuslieferung(0);
				controller.auslieferungErstellen();
				
			}
		});
		zulieferungErstellen= new JButton("Buchung erstellen");
		try {
		    Image img = ImageIO.read(new File("src/icons/check.png"));
		    zulieferungErstellen.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		zulieferungErstellen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				zeigeLaufendeZulieferung((int)menge.getValue());
				controller.zulieferungErstellen((int)menge.getValue());
			}
		});
		
	}
	
	
	/**
	 * zeigt die BuchungsBar im LaufendeZulieferungs Modus
	 * @param restMenge gibt an wie viele Einheiten noch verteilt werden m�ssen
	 */
	public void zeigeLaufendeZulieferung(int restMenge) {

		this.removeAll();
		this.add(undo);
		this.addSeparator(new Dimension(10,0));
		laufendeZulieferung.setText("<html>Es gibt eine laufende Zulieferung <br>"+restMenge+" Einheiten m�ssen noch verteilt werden</html>");
		this.add(laufendeZulieferung);
		this.add(buchungVerwerfen);
		if (restMenge==0){
			this.add(buchungAbschliessen);
		}
		this.add(redo);
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * zeigt die BuchungsBar im LaufendeZAusieferungs Modus
	 * @param restMenge gibt an wie viele Einheiten schon verteilt wurden
	 */
	public void zeigeLaufendeAuslieferung(int restMenge){
		this.removeAll();
		this.add(undo);
		this.addSeparator(new Dimension(10,0));
		laufendeAuslieferung.setText("<html>Es gibt eine laufende Auslieferung <br>"+restMenge+" Einheiten wurden schon verteilt</html>");
		this.add(laufendeAuslieferung);
		this.add(buchungVerwerfen);
		if (restMenge>0){
			this.add(buchungAbschliessen);
		}
		this.add(redo);
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * zeigt die BuchungBar im neue Buchung Modus
	 * @param zulieferung true wenn neue Zulieferung erstellt werden soll
	 */
	public void zeigeNeueBuchung(boolean zulieferung){
		this.removeAll();
		if (zulieferung) {
			neueBuchung.setText("Neue Zulieferung erstellen: Bitte geben Sie die gew�nschte Menge ein:");
			this.add(neueBuchung);
			this.addSeparator(new Dimension(10,0));
			this.add(menge);
		} else {
			neueBuchung.setText("Neue Auslieferung erstellen: Wenn Sie die Lieferung erstellen k�nnen Sie von jedem Bestandslager abbuchen");
			this.add(neueBuchung);
		}
		this.addSeparator(new Dimension(10,0));
		if (zulieferung) {
			zulieferungErstellen.setEnabled(false);
			this.add(zulieferungErstellen);
		} else {
			this.add(auslieferungErstellen);
		}
		this.revalidate();
		this.repaint();
	}
	
	public void aktualisiereRestMenge(int restMenge) {
		laufendeAuslieferung.setText("<html>Es gibt eine laufende Auslieferung <br>"+restMenge+" Einheiten wurden schon verteilt</html>");
		laufendeZulieferung.setText("<html>Es gibt eine laufende Zulieferung <br>"+restMenge+" Einheiten m�ssen noch verteilt werden</html>");
		this.revalidate();
		this.repaint();

	}
}