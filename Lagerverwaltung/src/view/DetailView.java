package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.LagerVerwaltungsController;

public class DetailView extends JPanel implements Observer{
	
	String lagerName, mengeKapazitaet, mengeBestand;
	EditNamePanel editName;
	OptionenPanel optionenPanel;
	JPanel lagerInfo;
	JLabel bestand, kapazitaet;
	JScrollPane buchungen;
	JTable tabelle;
	String[] columnNames;
	Object[][] data;
	boolean unterLager;
	LagerVerwaltungsController controller;

	public DetailView(LagerVerwaltungsController controller) {
		
		this.controller=controller;
		lagerName="Beispiel";
		mengeKapazitaet="1000";
		mengeBestand="300";
		
		this.setPreferredSize(new Dimension(515,400));
		this.setLayout(new BorderLayout());
		
		columnNames = new String []{"Buchungstag",
				"Gesamte Menge",
                "relativer Anteil",
                "absoluter Anteil"};
		
		data = new Object [][]{
			    {new Date(115,10,24).toLocaleString(), 1000,
			     ""+30+"%", 300},
			    {new Date(115,11,25).toLocaleString(), 200,
			    	 ""+10+"%", 20},
			    {new Date(115,10,24).toLocaleString(), 1000,
					 ""+30+"%", 300},
			    {new Date(115,11,25).toLocaleString(), 200,
					""+10+"%", 20},
				{new Date(115,10,24).toLocaleString(), 1000,
					""+30+"%", 300},
				{new Date(115,11,25).toLocaleString(), 200,
					""+10+"%", 20},
				{new Date(115,10,24).toLocaleString(), 1000,
					""+30+"%", 300},
				{new Date(115,11,25).toLocaleString(), 200,
					""+10+"%", 20},
				{new Date(115,10,24).toLocaleString(), 1000,
					""+30+"%", 300},
				{new Date(115,11,25).toLocaleString(), 200,
					""+10+"%", 20},
				{new Date(115,10,24).toLocaleString(), 1000,
					""+30+"%", 300},
				{new Date(115,11,25).toLocaleString(), 200,
					""+10+"%", 20},
				{new Date(115,10,24).toLocaleString(), 1000,
					""+30+"%", 300},
				{new Date(115,11,25).toLocaleString(), 200,
					""+10+"%", 20},
				{new Date(115,10,24).toLocaleString(), 1000,
					""+30+"%", 300},
				{new Date(115,11,25).toLocaleString(), 200,
					""+10+"%", 20}
			};
		lagerInfoErstellen();
		this.add(lagerInfo,BorderLayout.NORTH);

		buchungsTabelleErstellen();
		this.add(buchungen,BorderLayout.CENTER);
		
		optionenPanel = new OptionenPanel(editName,controller);
		optionenPanel.zeigeSlider(1327,1327);
		this.add(optionenPanel,BorderLayout.PAGE_END);
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
		lagerInfoAktualisieren()
		if (lModel.hatUnterlager) {
			unterLager=false
		} else {
			unterLager=true
		}
		
	}
	*/
	
	/**
	 * erstellt ein JPanel, welches alle Infos über ein Lager enthält
	 * es zeigt die in den Klassenvariablen gespeicherten Werte an
	 * @return das erstellte JPanel mit den Lagerinfos
	 */
	public void lagerInfoErstellen(){
		
		lagerInfo = new JPanel();
		lagerInfo.setLayout(new BoxLayout(lagerInfo, BoxLayout.PAGE_AXIS));
		JLabel standard = new JLabel("Lagerinfo:");
		lagerInfo.add(standard);
		
		// Namensfeld + edit-Button erstellen
		lagerInfoAktualisieren();
		
		lagerInfo.add(editName);
		lagerInfo.add(kapazitaet);
		lagerInfo.add(bestand);
		
		JLabel platzhalter = new JLabel("\t");
		lagerInfo.add(platzhalter);
		JLabel buchungInfo = new JLabel("Buchungen im Detail:");
		buchungInfo.setFont(new Font(this.getFont().getName(),Font.BOLD,20));
		lagerInfo.add(buchungInfo);
		JLabel platzhalter2 = new JLabel("\t");
		lagerInfo.add(platzhalter2);
	}
	
	/**
	 * Aktualisiert alle variablen Texte der View auf die in den Klassenvariablen gespeicherten Werte
	 */
	public void lagerInfoAktualisieren(){
		editName = new EditNamePanel(lagerName,controller);
		kapazitaet = new JLabel("Maximale Kapazität: "+mengeKapazitaet);
		bestand = new JLabel("Aktueller Bestand: "+mengeBestand);
		lagerInfo.revalidate();
	}
	
	/**
	 * Erstellt ein JPanel mit einer Tabelle die die Buchungen anzeigt
	 */
	public void buchungsTabelleErstellen() {
		tabelle = new JTable(data, columnNames);
		tabelle.setEnabled(false);
		tabelle.getTableHeader().setReorderingAllowed(false);
		buchungen = new JScrollPane(tabelle);
		buchungen.setPreferredSize(new Dimension(515,400));
	}
	
	/**
	 * setzt das Optionen-Panel in den zeige Button Modus
	 */
	public void zeigeButton(){
		optionenPanel.zeigeButton();
	}
	
	public void zeigeBuchungsOptionen(){
		if (unterLager) {
			optionenPanel.zeigeSlider(1327,1327);		
		} else {
			optionenPanel.zeigeInfo();
		}
	}
}

