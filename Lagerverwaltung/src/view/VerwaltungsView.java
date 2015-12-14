package view;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JPanel;
import javax.swing.JToolBar;

import controller.LagerVerwaltungsController;
import model.AbBuchungsModel;
import model.BuchungsModel;
import model.LagerModel;
import model.LagerVerwaltungsModel;
import model.ZuBuchungsModel;

public class VerwaltungsView extends JFrame implements Observer{

	int restMenge;
	JButton laden, neueZulieferung, speichern, neueAuslieferung,alleBuchungen, listeZeigen;
	JToolBar toolbar;
	BuchungBar buchungBar;
	DetailView detailPane;
	TreeView1 treePane;
	LagerVerwaltungsModel lvModel;
	LagerVerwaltungsController controller;
	List<BuchungsModel> listeBuchungen;
	BuchungsView buchungsView;
	ListenView liste;
	
	/**
	 * erzeugt eine VerwaltungsView
	 * @param controller der Controller an den alle Befehle runtergereicht werden
	 */
	public VerwaltungsView(LagerVerwaltungsController controller){
		
		this.controller=controller;
		this.setTitle("Lagerverwaltung");
		
		restMenge=1000;
		guiElementeErstellen();
		
		//Toolbar hinzufügen
		toolbar = new JToolBar();
		toolbar.add(speichern);
		toolbar.add(laden);
		toolbar.add(neueZulieferung);
		toolbar.add(neueAuslieferung);
		toolbar.add(alleBuchungen);
		toolbar.add(listeZeigen);
		toolbar.setFloatable(false);
		this.add(toolbar,BorderLayout.PAGE_START);

		//DetailPane hinzufügen
		detailPane = new DetailView(controller);
		this.add(detailPane, BorderLayout.EAST);
		
		//TreePane hinzufügen
		treePane = new TreeView1(null,controller);
		//treePane.setBackground(Color.LIGHT_GRAY);
		this.add(treePane, BorderLayout.WEST);
		
		//BuchungsBar hinzufügen
		buchungBar = new BuchungBar(controller);
		buchungBar.setVisible(false);
		this.add(buchungBar, BorderLayout.PAGE_END);
		controller.addObserver(buchungBar);
		
		//Frame-Einstellungen
		try {
		    Image img = ImageIO.read(new File("src/icons/icon.png"));
		    this.setIconImage(img);
		  } catch (IOException ex) {
			  ex.printStackTrace();
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
			  ex.printStackTrace();
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
			  ex.printStackTrace();
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
			  ex.printStackTrace();
		  }
		neueZulieferung.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buchungBar.zeigeNeueBuchung(true);
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
			  ex.printStackTrace();
		  }
		neueAuslieferung.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buchungBar.zeigeNeueBuchung(false);
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
			  ex.printStackTrace();
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
			  ex.printStackTrace();
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
	 * aktualisiert die ganze View basierend auf den Veränderungen im Observer
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
	}
	
	/**
	 * zeigt die BuchungsBar im neues Lager Modus
	 */
	public void zeigeNeuesLager(boolean oberLager) {
		buchungBar.zeigeNeuesLager(oberLager);
		buchungBar.setVisible(true);
	}
	
	
	/**
	 * löscht die Ansicht aller Buchungen und zeigt die Details zu einem Lager
	 */
	public void zeigeDetailPane() {
		VerwaltungsView.this.remove(buchungsView);
		VerwaltungsView.this.add(detailPane,BorderLayout.EAST);
		VerwaltungsView.this.revalidate();
		VerwaltungsView.this.repaint();
	}
	
	/**
	 * löscht die Ansicht der Details zu einem Lager und zeigt alle Buchungen
	 */
	/**public void zeigeAlleBuchungen() {
		VerwaltungsView.this.remove(detailPane);
		buchungsView=new BuchungsView(listeBuchungen);
		VerwaltungsView.this.add(buchungsView,BorderLayout.EAST);
		VerwaltungsView.this.revalidate();
		VerwaltungsView.this.repaint();
	}
	*/
	public DetailView getDetailPane() {
		return detailPane;
	}
	
	public void editName() {
		detailPane.editName();
	}
	
	public void standardAnsicht() {
		if (liste!=null&&liste.isVisible()) {
			this.remove(liste);
		} else {
			this.remove(buchungsView);
		}
		
		this.add(treePane, BorderLayout.WEST);
		this.add(detailPane, BorderLayout.EAST);
		neueZulieferung.setEnabled(true);
		neueAuslieferung.setEnabled(true);
		alleBuchungen.setEnabled(true);
		listeZeigen.setEnabled(true);
		this.revalidate();
		this.repaint();
	}
	
	public void aktualisiereBaum() {
		treePane.aktualisiereBaum(lvModel.getLager(),true);
	}
}
