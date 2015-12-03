package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.LagerVerwaltungsController;
import model.AbBuchungsModel;
import model.BuchungsModel;
import model.LagerModel;

public class DetailView extends JPanel implements Observer{
	
	String lagerName;
	int mengeKapazitaet, mengeBestand;
	EditNamePanel editName;
	OptionenPanel optionenPanel;
	JPanel lagerInfo;
	JLabel bestand, kapazitaet, meldung;
	JScrollPane buchungen;
	JTable tabelle;
	String[] columnNames;
	Object[][] data;
	boolean isUnterLager;
	LagerVerwaltungsController controller;

	/**
	 * erzeugt eine DetailView
	 * @param controller Controller an den alle Befehle runtergereicht werden
	 */
	public DetailView(LagerVerwaltungsController controller) {
		
		this.controller=controller;
		lagerName="Beispiel";
		mengeKapazitaet=1000;
		mengeBestand=300;
		
		this.setPreferredSize(new Dimension(515,400));
		this.setLayout(new BorderLayout());
		
		columnNames = new String []{"Buchungstag",
				"Gesamte Menge",
                "relativer Anteil",
                "absoluter Anteil",
                "Art"};
		
		data = new Object [][]{
			    {new Date(115,10,24).toLocaleString(), 1000,
			     ""+30+"%", 300,"Auslieferung"},
			    {new Date(115,11,25).toLocaleString(), 200,
			    	 ""+10+"%", 20,"Auslieferung"},
			    {new Date(115,10,24).toLocaleString(), 1000,
					 ""+30+"%", 300,"Auslieferung"},
			    {new Date(115,11,25).toLocaleString(), 200,
					""+10+"%", 20,"Auslieferung"},
				{new Date(115,10,24).toLocaleString(), 1000,
					""+30+"%", 300,"Auslieferung"},
				{new Date(115,11,25).toLocaleString(), 200,
					""+10+"%", 20,"Auslieferung"},
				{new Date(115,10,24).toLocaleString(), 1000,
					""+30+"%", 300,"Auslieferung"},
				{new Date(115,11,25).toLocaleString(), 200,
					""+10+"%", 20,"Auslieferung"},
				{new Date(115,10,24).toLocaleString(), 1000,
					""+30+"%", 300,"Auslieferung"},
				{new Date(115,11,25).toLocaleString(), 200,
					""+10+"%", 20,"Auslieferung"},
				{new Date(115,10,24).toLocaleString(), 1000,
					""+30+"%", 300,"Auslieferung"},
				{new Date(115,11,25).toLocaleString(), 200,
					""+10+"%", 20,"Auslieferung"},
				{new Date(115,10,24).toLocaleString(), 1000,
					""+30+"%", 300,"Auslieferung"},
				{new Date(115,11,25).toLocaleString(), 200,
					""+10+"%", 20,"Auslieferung"},
				{new Date(115,10,24).toLocaleString(), 1000,
					""+30+"%", 300,"Auslieferung"},
				{new Date(115,11,25).toLocaleString(), 200,
					""+10+"%", 20,"Auslieferung"}
			};
		lagerInfoErstellen();
		this.add(lagerInfo,BorderLayout.NORTH);

		buchungsTabelleErstellen();
		this.add(buchungen,BorderLayout.CENTER);
		
		optionenPanel = new OptionenPanel(editName,controller);
		//optionenPanel.zeigeSlider(1327,1327);
		optionenPanel.zeigeButton();
		this.add(optionenPanel,BorderLayout.PAGE_END);
	}

	/**
	 * Die View überwacht ein Lagermodel
	 * wird dieses verändert aktualisiert die View sich auch
	 */
	@Override
	public void update(Observable o, Object arg1) {
		// TODO Auto-generated method stub
		LagerModel lModel = (LagerModel)o;
		mengeBestand=lModel.getBestand();
		mengeKapazitaet=lModel.getMaxKapazitaet();
		lagerName=lModel.getName();
		lagerInfoAktualisieren();
		if (lModel.hatUnterlager()) {
			isUnterLager=false;
		} else {
			isUnterLager=true;
		}
		if ((lModel.getBuchungen()!=null) && (lModel.getBuchungen().size()>0)) {
			bereiteBuchungenAuf(lModel.getBuchungen(),(LagerModel)o);
		} else {
			if (buchungen.isVisible()) {
				this.remove(buchungen);
				this.add(meldung,BorderLayout.CENTER);
			}
		}
		
	}
	
	
	/**
	 * erstellt ein JPanel, welches alle Infos über ein Lager enthält
	 * es zeigt die in den Klassenvariablen gespeicherten Werte an
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
		lagerInfo.repaint();
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
		meldung= new JLabel("Es gibt noch keine Buchungen auf dieses Lager");
	}
	
	/**
	 * setzt das Optionen-Panel in den zeige Button Modus
	 */
	public void zeigeButton(){
		optionenPanel.zeigeButton();
	}
	
	/**
	 * setzt das Optionen-Panel in den zeige Slider Modus
	 * @param gesamtMenge gesamt Menge der Buchung zu der der Prozentsatz errechnet wird
	 * @param maximum maximal noch zu verteilende Menge
	 * @param zulieferung true wenn zulieferung, sonst false
	 */
	public void zeigeBuchungsOptionen(int gesamtMenge, int maximum, boolean zulieferung){
		if (isUnterLager) {
			if (zulieferung) {
				if (maximum>mengeKapazitaet) {
					optionenPanel.zeigeSlider(gesamtMenge,mengeKapazitaet);
				} else {
					optionenPanel.zeigeSlider(gesamtMenge,maximum);
				}	
			} else {
				optionenPanel.zeigeSlider(gesamtMenge,mengeKapazitaet);	
			}
				
		} else {
			optionenPanel.zeigeInfo();
		}
	}
	
	/**
	 * bereitet die Liste mit den Buchungen für die Tabellenansicht des mitgegebenen Lagers vor
	 * @param listeBuchungen Liste der Buchungen für das mitgegebene Lager
	 * @param lModel Lager für das die Tabelle erzeugt wird
	 */
	public void bereiteBuchungenAuf(List<BuchungsModel> listeBuchungen, LagerModel lModel) {
		int i=0;
		data = new Object[listeBuchungen.size()][5];
		for (BuchungsModel bModel : listeBuchungen) {
			data[i][0]=bModel.getBuchungsTag().toLocaleString();
			data[i][1]=bModel.getVerteilteMenge();
			int j;
			for (j = 0; bModel.getAnteile().get(j).getLager().equals(lModel); j++) {
				
				}
			data[i][3]=bModel.getAnteile().get(j).getAnteil();
			double prozent = (((double)data[i][3]/(double)data[i][1])*100.00);
			prozent = (prozent*1000)+5;
			int temp = (int) (prozent/10);
			prozent = (double)temp/100.00;
			data[i][2]=""+prozent+"%";
			if (bModel.getClass().equals(new AbBuchungsModel(null).getClass())) {
				data[i][4]="Auslieferung";
			} else {
				data[i][4]="Zulieferung";
			}
		}
		tabelle=new JTable(data, columnNames);
		tabelle.setEnabled(false);
		tabelle.getTableHeader().setReorderingAllowed(false);
		buchungen.revalidate();
		buchungen.repaint();
		if (meldung.isVisible()) {
			this.remove(meldung);
			this.add(buchungen,BorderLayout.CENTER);
		}
		
	}
}

