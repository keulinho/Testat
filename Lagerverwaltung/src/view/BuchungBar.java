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

public class BuchungBar extends JToolBar{
	
	JButton undo, redo, buchungVerwerfen,buchungAbschliessen,auslieferungErstellen,zulieferungErstellen;
	JLabel laufendeBuchung, neueBuchung;
	JFormattedTextField menge;
	int restMenge;
	
	public BuchungBar(){
		restMenge=100;
		this.setFloatable(false);
		guiElementeErstellen();

	}

	/**
	 * Erstellt alle GUI-Elemente die in diesem Panel angezeigt werden können
	 */
	public void guiElementeErstellen(){
		undo= new JButton("Undo");
		try {
		    Image img = ImageIO.read(new File("src/icons/undo.png"));
		    undo.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		
		redo= new JButton("Redo");
		try {
		    Image img = ImageIO.read(new File("src/icons/redo.png"));
		    redo.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		laufendeBuchung = new JLabel();
		try {
		    Image img = ImageIO.read(new File("src/icons/exclamation.png"));
		    laufendeBuchung.setIcon(new ImageIcon(img));
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
		buchungAbschliessen= new JButton("Buchung abschließen");
		try {
		    Image img = ImageIO.read(new File("src/icons/check.png"));
		    buchungAbschliessen.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
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
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if ((e.getKeyCode() == KeyEvent.VK_ENTER)&&(menge.getValue()!=null)) {
					restMenge=(int) menge.getValue();
					zeigeLaufendeZulieferung(false);
				} else if(menge.getValue()!=null){
					zulieferungErstellen.setEnabled(true);
				} else {
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
				zeigeLaufendeAuslieferung(false);
				
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
				zeigeLaufendeZulieferung(false);
				
			}
		});
		
	}
	
	public void zeigeLaufendeZulieferung(boolean vollständigVerteilt) {

		this.removeAll();
		this.add(undo);
		this.addSeparator(new Dimension(10,0));
		laufendeBuchung.setText("<html>Es gibt eine laufende Zulieferung <br>"+restMenge+" Einheiten müssen noch verteilt werden</html>");
		this.add(laufendeBuchung);
		this.add(buchungVerwerfen);
		if (vollständigVerteilt){
			this.add(buchungAbschliessen);
		}
		this.add(redo);
		this.revalidate();
		this.repaint();
	}
	
	public void zeigeLaufendeAuslieferung(boolean istEinAnteilVerteilt){
		this.removeAll();
		this.add(undo);
		this.addSeparator(new Dimension(10,0));
		laufendeBuchung.setText("<html>Es gibt eine laufende Auslieferung <br>"+restMenge+" Einheiten wurden schon verteilt</html>");
		this.add(laufendeBuchung);
		this.add(buchungVerwerfen);
		if (istEinAnteilVerteilt){
			this.add(buchungAbschliessen);
		}
		this.add(redo);
		this.revalidate();
		this.repaint();
	}
	
	public void zeigeNeueBuchung(boolean zulieferung){
		this.removeAll();
		if (zulieferung) {
			neueBuchung.setText("Neue Zulieferung erstellen: Bitte geben Sie die gewünschte Menge ein:");
			this.add(neueBuchung);
			this.addSeparator(new Dimension(10,0));
			this.add(menge);
		} else {
			neueBuchung.setText("Neue Auslieferung erstellen: Wenn Sie die Lieferung erstellen können Sie von jedem Bestandslager abbuchen");
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
}
