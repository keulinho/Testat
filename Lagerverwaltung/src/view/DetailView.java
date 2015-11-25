package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class DetailView extends JPanel implements Observer{
	
	String lagerName, mengeKapazitaet, mengeBestand;

	public DetailView() {
		
		lagerName="Beispiel";
		mengeKapazitaet="1000";
		mengeBestand="300";
		
		this.setPreferredSize(new Dimension(515,400));
		this.setBackground(Color.CYAN);
		this.setLayout(new BorderLayout());
		
		String[] columnNames = {"Buchungstag",
				"Gesamte Menge",
                "relativer Anteil",
                "absoluter Anteil"};
		
		Object[][] data = {
			    {new Date(2015,11,24), 1000,
			     ""+30+"%", 300},
			    {new Date(2015,11,25), 200,
			    	 ""+10+"%", 20}
			};

		JPanel lagerInfo = new JPanel();
		lagerInfo.setLayout(new BoxLayout(lagerInfo, BoxLayout.PAGE_AXIS));
		JLabel standard = new JLabel("Lagerinfo:");
		lagerInfo.add(standard);
		
		// Namensfeld + edit-Button erstellen
		final EditNameView editName = new EditNameView("Beispiel");
		lagerInfo.add(editName);
		JLabel kapazitaet = new JLabel("Maximale Kapazität: "+mengeKapazitaet);
		lagerInfo.add(kapazitaet);
		JLabel bestand = new JLabel("Aktueller Bestand: "+mengeBestand);
		lagerInfo.add(bestand);
		JLabel platzhalter = new JLabel("\t");
		lagerInfo.add(platzhalter);
		JLabel buchungInfo = new JLabel("Buchungen im Detail:");
		buchungInfo.setFont(new Font(this.getFont().getName(),Font.BOLD,20));
		lagerInfo.add(buchungInfo);
		JLabel platzhalter2 = new JLabel("\t");
		lagerInfo.add(platzhalter2);
		
		
		this.add(lagerInfo,BorderLayout.NORTH);
		
		JTable buchungen = new JTable(data, columnNames);
		JPanel tabelle = new JPanel(new BorderLayout());
		tabelle.add(buchungen.getTableHeader(),BorderLayout.PAGE_START);
		tabelle.add(buchungen,BorderLayout.CENTER);
		//tabelle.setPreferredSize(new Dimension(400,100));
		
		this.add(tabelle,BorderLayout.CENTER);
		
		JPanel buttonGroup = new JPanel();
		buttonGroup.setLayout(new FlowLayout());
		JButton loeschen = new JButton("Lager löschen");
		try {
		    Image img = ImageIO.read(new File("src/icons/delete.png"));
		    loeschen.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		buttonGroup.add(loeschen);
		buttonGroup.add(Box.createRigidArea(new Dimension(2,0)));
		JButton neuesLager = new JButton("Unterlager erstellen");
		try {
		    Image img = ImageIO.read(new File("src/icons/new.png"));
		    neuesLager.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		buttonGroup.add(neuesLager);
		buttonGroup.add(Box.createRigidArea(new Dimension(2,0)));
		JButton umbennen = new JButton("Lager umbennen");
		try {
		    Image img = ImageIO.read(new File("src/icons/edit2.png"));
		    umbennen.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		umbennen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				editName.edit();
			}
		});
		buttonGroup.add(umbennen);
		buttonGroup.setPreferredSize(new Dimension(515,50));
		
		this.add(buttonGroup,BorderLayout.PAGE_END);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Die View überwacht ein Lagermodel
	 * wird dieses verändert aktualisiert die View sich auch
	 */
/*	@Override
	public void update(Observable o, Object arg1) {
		// TODO Auto-generated method stub
		LagerModel lModel = (LagerModel)o;
		mengeBestand=""+lModel.getBestand();
		mengeKapazitaet=""+lModel.getMaxKapazitaet();
		lagerName=lModel.getName();
	}
	*/
	
	
}
