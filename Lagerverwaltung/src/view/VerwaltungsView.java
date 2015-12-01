package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import controller.LagerVerwaltungsController;
import model.LagerVerwaltungsModel;

public class VerwaltungsView extends JFrame implements Observer{

	int restMenge;
	JButton laden, neueZulieferung, speichern, neueAuslieferung;
	JToolBar toolbar;
	BuchungBar buchungBar;
	DetailView detailPane;
	TreeView treePane;
	LagerVerwaltungsModel lvModel;
	LagerVerwaltungsController controller;
	
	/**
	 * erzeugt eine VerwaltungsView
	 * @param controller der Controller an den alle Befehle runtergereicht werden
	 */
	public VerwaltungsView(LagerVerwaltungsController controller){
		
		this.controller=controller;
		this.setTitle("Lagerverwaltung");
		
		restMenge=1000;
		guiElementeErstellen();
		
		toolbar = new JToolBar();
		
		toolbar.add(speichern);
		toolbar.add(laden);
		toolbar.add(neueZulieferung);
		toolbar.add(neueAuslieferung);
		toolbar.setFloatable(false);
		
		this.add(toolbar,BorderLayout.PAGE_START);

		detailPane = new DetailView(controller);
		this.add(detailPane, BorderLayout.EAST);
			
		treePane = new TreeView();
		treePane.setBackground(Color.LIGHT_GRAY);
		this.add(treePane, BorderLayout.WEST);
		
		buchungBar = new BuchungBar(controller);
		buchungBar.setVisible(false);
		this.add(buchungBar, BorderLayout.PAGE_END);

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
	 * erzeugt alle Button und Label dieses Panels
	 */
	public void guiElementeErstellen(){
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
				// TODO Auto-generated method stub
				controller.speichern();
			}
		});
		laden= new JButton("Öffnen");
		try {
		    Image img = ImageIO.read(new File("src/icons/open.png"));
		    laden.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		laden.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
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
			}
		});
		
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
/**	@Override
	public void update(Observable o, Object arg) {
		lvModel  = (LagerVerwaltungsModel) o;
		
	}

**/
		
	
}
