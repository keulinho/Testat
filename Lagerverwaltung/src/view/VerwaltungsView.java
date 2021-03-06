package view;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import controller.LagerVerwaltungsController;
import core.exception.ErrorHandler;
import core.exception.ImageNotFoundException;
import model.AbBuchungsModel;
import model.BuchungsModel;
import model.LagerVerwaltungsModel;
import model.ZuBuchungsModel;
/**@author Jonas Elfering
**/
public class VerwaltungsView extends JFrame implements Observer{

	int restMenge, maxFreieKapazitaet;
	JButton laden, neueZulieferung, speichern, neueAuslieferung,alleBuchungen, listeZeigen;
	JToolBar toolbar;
	BuchungBar buchungBar;
	DetailView detailPane;
	TreeView treePane;
	LagerVerwaltungsModel lvModel;
	LagerVerwaltungsController controller;
	List<BuchungsModel> listeBuchungen;
	BuchungsView buchungsView;
	ListenView liste;
	boolean loescheDetailView=false;
	
	/**
	 * erzeugt eine VerwaltungsView
	 * @param controller der Controller an den alle Befehle runtergereicht werden
	 */
	public VerwaltungsView(LagerVerwaltungsController controller){
		
		this.controller=controller;
		this.setTitle("Lagerverwaltung");
		restMenge=1000;
		guiElementeErstellen();
		//Toolbar hinzuf�gen
		toolbar = new JToolBar();
		toolbar.add(speichern);
		toolbar.add(laden);
		toolbar.add(neueZulieferung);
		toolbar.add(neueAuslieferung);
		toolbar.add(alleBuchungen);
		toolbar.add(listeZeigen);
		toolbar.setFloatable(false);
		this.add(toolbar,BorderLayout.PAGE_START);

		//DetailPane hinzuf�gen
		detailPane = new DetailView(controller);
		this.add(detailPane, BorderLayout.EAST);
		
		//TreePane hinzuf�gen
		treePane = new TreeView(controller);
		this.add(treePane, BorderLayout.WEST);
		
		//BuchungsBar hinzuf�gen
		buchungBar = new BuchungBar(controller);
		buchungBar.setVisible(false);
		this.add(buchungBar, BorderLayout.PAGE_END);
		controller.addObserver(buchungBar);
		
		//Frame-Einstellungen
		try {
		    Image img = ImageIO.read(new File("src/icons/marke.png"));
		    this.setIconImage(img);
		  } catch (IOException ex) {
			  ErrorHandler.HandleException(ErrorHandler.BILD_NICHT_GEFUNDEN, new ImageNotFoundException("Bilddatei mit dem Pfad \"src/icons/marke.png\" nicht gefunden",(Throwable) ex));
		  }
		this.setLocation(150, 100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
		this.setVisible(true);		
	}
	
	/**
	 * erzeugt alle Button der Toolbar
	 */
	public void guiElementeErstellen(){
		//Toolbar
		speichern= new JButton("Speichern");
		try {
		    Image img = ImageIO.read(new File("src/icons/save.png"));
		    speichern.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ErrorHandler.HandleException(ErrorHandler.BILD_NICHT_GEFUNDEN, new ImageNotFoundException("Bilddatei mit dem Pfad \"src/icons/save.png\" nicht gefunden",(Throwable) ex));
		  }
		speichern.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.speichern();
			}
		});
		laden= new JButton("Laden");
		try {
		    Image img = ImageIO.read(new File("src/icons/open.png"));
		    laden.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ErrorHandler.HandleException(ErrorHandler.BILD_NICHT_GEFUNDEN, new ImageNotFoundException("Bilddatei mit dem Pfad \"src/icons/open.png\" nicht gefunden",(Throwable) ex));
		  }
		laden.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.laden();
			}
		});
		neueZulieferung= new JButton("Neue Zulieferung");
		try {
		    Image img = ImageIO.read(new File("src/icons/zulieferung.png"));
		    neueZulieferung.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ErrorHandler.HandleException(ErrorHandler.BILD_NICHT_GEFUNDEN, new ImageNotFoundException("Bilddatei mit dem Pfad \"src/icons/zulieferung.png\" nicht gefunden",(Throwable) ex));
		  }
		neueZulieferung.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buchungBar.zeigeNeueBuchung(true,maxFreieKapazitaet);
				buchungBar.setVisible(true);
				neueAuslieferung.setEnabled(false);
				neueZulieferung.setEnabled(false);
				alleBuchungen.setEnabled(false);
				listeZeigen.setEnabled(false);
			}
		});
		neueAuslieferung= new JButton("Neue Auslieferung");
		try {
		    Image img = ImageIO.read(new File("src/icons/auslieferung.png"));
		    neueAuslieferung.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ErrorHandler.HandleException(ErrorHandler.BILD_NICHT_GEFUNDEN, new ImageNotFoundException("Bilddatei mit dem Pfad \"src/icons/auslieferung.png\" nicht gefunden",(Throwable) ex));
		  }
		neueAuslieferung.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buchungBar.zeigeNeueBuchung(false,0);
				buchungBar.setVisible(true);
				neueAuslieferung.setEnabled(false);
				neueZulieferung.setEnabled(false);
				alleBuchungen.setEnabled(false);
				listeZeigen.setEnabled(false);
			}
		});
		alleBuchungen= new JButton("Alle Buchungen");
		try {
		    Image img = ImageIO.read(new File("src/icons/alleBuchungen.png"));
		    alleBuchungen.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ErrorHandler.HandleException(ErrorHandler.BILD_NICHT_GEFUNDEN, new ImageNotFoundException("Bilddatei mit dem Pfad \"src/icons/alleBuchungen.png\" nicht gefunden",(Throwable) ex));
		  }
		alleBuchungen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buchungsView=new BuchungsView(listeBuchungen);
				VerwaltungsView.this.remove(treePane);
				VerwaltungsView.this.remove(detailPane);
				VerwaltungsView.this.add(buchungsView,BorderLayout.WEST);
				VerwaltungsView.this.revalidate();
				VerwaltungsView.this.repaint();
				neueZulieferung.setEnabled(false);
				neueAuslieferung.setEnabled(false);
				alleBuchungen.setEnabled(false);
				listeZeigen.setEnabled(false);
			}
		});
		listeZeigen= new JButton("Buchungsliste");
		try {
		    Image img = ImageIO.read(new File("src/icons/Liste.png"));
		    listeZeigen.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ErrorHandler.HandleException(ErrorHandler.BILD_NICHT_GEFUNDEN, new ImageNotFoundException("Bilddatei mit dem Pfad \"src/icons/Liste.png\" nicht gefunden",(Throwable) ex));
		  }
		listeZeigen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				liste = new ListenView(listeBuchungen);
				VerwaltungsView.this.remove(treePane);
				VerwaltungsView.this.remove(detailPane);
				VerwaltungsView.this.add(liste,BorderLayout.WEST);
				VerwaltungsView.this.revalidate();
				VerwaltungsView.this.repaint();
				neueZulieferung.setEnabled(false);
				neueAuslieferung.setEnabled(false);
				alleBuchungen.setEnabled(false);
				listeZeigen.setEnabled(false);
			}
		});
	}

	/**
	 * aktualisiert die ganze View basierend auf den Ver�nderungen im Observer
	 */
	@Override
	public void update(Observable o, Object arg) {
		lvModel  = (LagerVerwaltungsModel) o;
		if (lvModel.getLaufendeBuchung()!=null) {  //Es gibt eine nicht abgeschlossene Buchung
			neueZulieferung.setEnabled(false);
			neueAuslieferung.setEnabled(false);
			alleBuchungen.setEnabled(false);
			listeZeigen.setEnabled(false);
			if (lvModel.getLaufendeBuchung().getClass().equals(new AbBuchungsModel(new Date()).getClass())) { //nicht abgeschlossene Buchung ist eine Abbuchung
				buchungBar.zeigeLaufendeAuslieferung(lvModel.getLaufendeBuchung().getVerteilteMenge());
				detailPane.zeigeBuchungsOptionen(lvModel.getLaufendeBuchung().getVerteilteMenge(), 0, false);
			} else { //laufende Buchung ist eine Zubuchung
				buchungBar.zeigeLaufendeZulieferung(((ZuBuchungsModel) (lvModel.getLaufendeBuchung())).getMenge()-lvModel.getLaufendeBuchung().getVerteilteMenge());
				detailPane.zeigeBuchungsOptionen(((ZuBuchungsModel) (lvModel.getLaufendeBuchung())).getMenge(), ((ZuBuchungsModel) (lvModel.getLaufendeBuchung())).getMenge()-lvModel.getLaufendeBuchung().getVerteilteMenge(), true);
			}
			buchungBar.setVisible(true);
		} else { //es gibt keine laufende Buchung
			neueZulieferung.setEnabled(true);
			neueAuslieferung.setEnabled(true);
			alleBuchungen.setEnabled(true);
			listeZeigen.setEnabled(true);
			detailPane.zeigeButton();
			buchungBar.setVisible(false);
		}
		listeBuchungen=lvModel.getBuchungen(); //Liste wird bei jedem Update aktualisiert
		treePane.aktualisiereBaum(lvModel.getLager(),false);
		if (!lvModel.getLager().isEmpty()) {
			loescheDetailView=false;
		}
		maxFreieKapazitaet=lvModel.getMaxFreieKapazitaet();
	}
	
	/**
	 * zeigt die BuchungsBar im neues Lager Modus
	 */
	public void zeigeNeuesLager(boolean oberLager) {
		buchungBar.zeigeNeuesLager(oberLager);
		buchungBar.setVisible(true);
	}
	/**
	 * setzt die DetailPane in den EditName Modus
	 */
	public void editName() {
		detailPane.editName();
	}
	/**
	 * l�scht Listenansichten und wechselt zur Standardansicht mit buchungsdetails und Baum
	 */
	public void standardAnsicht() {
		if (liste!=null) {
			this.remove(liste);
		} 
		if (buchungsView!=null) {
			this.remove(buchungsView);
		}
		this.add(treePane, BorderLayout.WEST);
		if (!loescheDetailView) {
			this.add(detailPane, BorderLayout.EAST);
		}
		neueZulieferung.setEnabled(true);
		neueAuslieferung.setEnabled(true);
		alleBuchungen.setEnabled(true);
		listeZeigen.setEnabled(true);
		this.revalidate();
		this.repaint();
	}
	/**
	 * verdrahtet ein neues Model mit der alten View
	 */
	public void neuesModel() {
		treePane.aktualisiereBaum(lvModel.getLager(),true);
		standardAnsicht();
	}
	/**
	 * loescht die DetailView wenn es kein Lager gibt
	 */
	public void loescheDetailView() {
		loescheDetailView=true;
		this.remove(detailPane);
		this.revalidate();
		this.repaint();
	}
	/**
	 * f�gt die DetailView wieder hinzu wenn es wieder ein Lager gibt
	 */
	public void zeigeDetailView() {
		this.add(detailPane, BorderLayout.EAST);
		this.revalidate();
		this.repaint();
	}
	/**
	 * getter DetailPane
	 * @return detailPane
	 */
	public DetailView getDetailPane() {
		return detailPane;
	}
}
