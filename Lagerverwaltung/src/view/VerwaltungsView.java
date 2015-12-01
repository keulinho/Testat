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
	JButton openButton, zulieferungButton, saveButton, auslieferungButton;
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
		
		toolbar.add(saveButton);
		toolbar.add(openButton);
		toolbar.add(zulieferungButton);
		toolbar.add(auslieferungButton);
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
		saveButton= new JButton("Speichern");
		try {
		    Image img = ImageIO.read(new File("src/icons/save.png"));
		    saveButton.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
	
		openButton= new JButton("Öffnen");
		try {
		    Image img = ImageIO.read(new File("src/icons/open.png"));
		    openButton.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		zulieferungButton= new JButton("Neue Zulieferung");
		try {
		    Image img = ImageIO.read(new File("src/icons/zulieferung.png"));
		    zulieferungButton.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		zulieferungButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buchungBar.zeigeNeueBuchung(true);
				buchungBar.setVisible(true);
			}
		});
		auslieferungButton= new JButton("Neue Auslieferung");
		try {
		    Image img = ImageIO.read(new File("src/icons/auslieferung.png"));
		    auslieferungButton.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		auslieferungButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buchungBar.zeigeNeueBuchung(false);
				buchungBar.setVisible(true);
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
