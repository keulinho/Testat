package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class DetailView extends JPanel{

	public DetailView() {
		this.setPreferredSize(new Dimension(400,400));
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

		JTable buchungen = new JTable(data, columnNames);
		
		JPanel tabelle = new JPanel(new BorderLayout());
		tabelle.add(buchungen.getTableHeader(),BorderLayout.PAGE_START);
		tabelle.add(buchungen,BorderLayout.CENTER);
		tabelle.setPreferredSize(new Dimension(400,100));
		
		JPanel lagerInfo = new JPanel();
		lagerInfo.setLayout(new BoxLayout(lagerInfo, BoxLayout.PAGE_AXIS));
		JLabel standard = new JLabel("Lagerinfo:");
		lagerInfo.add(standard);
		JLabel name = new JLabel("Lagername");
		name.setFont(new Font(name.getFont().getName(),Font.BOLD,20));
		lagerInfo.add(name);
		JLabel kapazitaet = new JLabel("Maximale Kapazität: 1000");
		lagerInfo.add(kapazitaet);
		JLabel bestand = new JLabel("Aktueller Bestand: 500");
		lagerInfo.add(bestand);
		JLabel platzhalter = new JLabel("\t");
		lagerInfo.add(platzhalter);
		JLabel buchungInfo = new JLabel("Buchungen im Detail:");
		buchungInfo.setFont(new Font(name.getFont().getName(),Font.BOLD,20));
		lagerInfo.add(buchungInfo);
		JLabel platzhalter2 = new JLabel("\t");
		lagerInfo.add(platzhalter2);
		
		
		this.add(lagerInfo,BorderLayout.NORTH);
		
		this.add(tabelle,BorderLayout.CENTER);
	}
}
