package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.LagerVerwaltungsController;

public class EditNamePanel extends JPanel{
	
	JLabel editieren, name, speichern;
	JTextField neuerName;
	LagerVerwaltungsController controller;
	
	/**
	 * Erstellt ein editierbares NamensPanel
	 * @param lagerName Name des Lagers das angezeigt werden soll
	 * @param controller Controller an den Befehle weitergereicht werden
	 */
	public EditNamePanel(String lagerName, LagerVerwaltungsController controller){
		
		this.controller=controller;
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setPreferredSize(new Dimension(515,25));
		
		guiElementeErstellen();
		
		zeigeName(lagerName);
	}
	/**
	 * löscht alle Elemente dieses Panels und fügt alle GUI-Elemente für den Edit-Modus neu hinzu
	 * @param lagerName Name des Lagers der angezeigt wird
	 */
	public void zeigeName(String lagerName) {
		this.removeAll();
		name.setText(lagerName);
		this.add(name);
		this.add(Box.createRigidArea(new Dimension(10,0)));
		this.add(editieren);
		this.setAlignmentX(LEFT_ALIGNMENT);
		this.revalidate();
	}
	
	/**
	 * löscht alle Elemente dieses Panels und fügt alle GUI-Elemente für den Edit-Modus neu hinzu
	 */
	public void edit() {
		this.removeAll();
		neuerName.setText(name.getText());
		this.add(neuerName);
		this.add(Box.createRigidArea(new Dimension(10,0)));
		this.add(speichern);
		neuerName.requestFocus();
		neuerName.selectAll();
		this.revalidate();
	}
	
	/**
	 * Erstellt alle GUI-Elemente die in diesem Panel angezeigt werden können
	 */
	public void guiElementeErstellen(){
		//Anzeige-Modus
		name = new JLabel("");
		name.setFont(new Font(name.getFont().getName(),Font.BOLD,20));
		editieren = new JLabel("");
		try {
		    Image img = ImageIO.read(new File("src/icons/edit.png"));
		    editieren.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		editieren.setPreferredSize(new Dimension(20,30));
		editieren.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				edit();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		//Edit-Modus
		neuerName = new JTextField();
		neuerName.setPreferredSize(name.getSize());
		speichern = new JLabel("");
		try {
		    Image img = ImageIO.read(new File("src/icons/edit.png"));
		    speichern.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		speichern.setPreferredSize(new Dimension(20,30));
		speichern.addMouseListener(new MouseListener() {
		
			@Override
			public void mousePressed(MouseEvent e) {
				zeigeName(neuerName.getText());
				controller.aendereName(neuerName.getText());
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		neuerName.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if ((neuerName.getText()!=null)&&(e.getKeyCode() == KeyEvent.VK_ENTER)) {
					zeigeName(neuerName.getText());
					controller.aendereName(neuerName.getText());
				}
			}
		});
	}
}
