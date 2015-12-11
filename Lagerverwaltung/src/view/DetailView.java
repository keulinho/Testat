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
	int mengeKapazitaet, mengeBestand, gesamtMenge, maximum;
	EditNamePanel editName;
	OptionenPanel optionenPanel;
	JPanel lagerInfo;
	JLabel bestand, kapazitaet, meldung;
	JScrollPane buchungen;
	JTable tabelle;
	String[] columnNames;
	Object[][] data;
	boolean zulieferung;
	boolean isUnterLager;
	boolean buchungsModus;
	LagerVerwaltungsController controller;
	

	/**
	 * erzeugt eine DetailView
	 * @param controller Controller an den alle Befehle runtergereicht werden
	 */
	public DetailView(LagerVerwaltungsController controller) {
		
		this.controller=controller;
		lagerName="";
		
		this.setPreferredSize(new Dimension(515,400));
		this.setLayout(new BorderLayout());
		
		columnNames = new String []{"Buchungstag",
				"Gesamte Menge",
                "relativer Anteil",
                "absoluter Anteil",
                "Art"};
		meldung= new JLabel();
			
		lagerInfoErstellen();
		this.add(lagerInfo,BorderLayout.NORTH);

		optionenPanel = new OptionenPanel(editName,controller);
		optionenPanel.zeigeButton();
		this.add(optionenPanel,BorderLayout.PAGE_END);
	}

	/**
	 * Die View überwacht ein Lagermodel
	 * wird dieses verändert aktualisiert die View sich auch
	 */
	@Override
	public void update(Observable o, Object arg1) {
		LagerModel lModel = (LagerModel)o;
		mengeBestand=lModel.getBestand();
		mengeKapazitaet=lModel.getMaxKapazitaet();
		lagerName=lModel.getName();
		this.remove(lagerInfo);
		lagerInfoErstellen();
		this.add(lagerInfo,BorderLayout.NORTH);
		if (!lModel.isUntersteEbene()) {
			isUnterLager=false;
			meldung.setText("Bei einem Oberlager gibt es keine Buchungen");
		} else {
			isUnterLager=true;
			meldung.setText("Es gibt noch keine Buchungen auf dieses Lager");
		}
		if (buchungsModus) {
			zeigeBuchungsOptionen(gesamtMenge, maximum, zulieferung);
		}
		if ((lModel.getBuchungen()!=null) && (lModel.getBuchungen().size()>0)) { //True wenn es Buchungen zu diesem Lager gibt
			bereiteBuchungenAuf(lModel.getBuchungen(),(LagerModel)o);
		} else {
			if ((buchungen!=null)&&(buchungen.isVisible())) {  //Wenn Buchungen angezeigt werden es aber keine gibt werden diese aus der Ansicht gelöscht und stattdessen eine Meldung angezeigt
				this.remove(buchungen);
				this.add(meldung,BorderLayout.CENTER);
			} else {
				this.add(meldung,BorderLayout.CENTER);
			}
		}
		this.revalidate();
		this.repaint();
		
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
		
		//erstellt alle Elemente der LagerInfo mit aktuellen werten neu
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
	 * erstellt alle variablen Texte der View mit den in den Klassenvariablen gespeicherten Werten neu
	 */
	public void lagerInfoAktualisieren(){
		editName = new EditNamePanel(lagerName,controller);
		kapazitaet = new JLabel("Maximale Kapazität: "+mengeKapazitaet);
		kapazitaet.revalidate();
		kapazitaet.repaint();
		bestand = new JLabel("Aktueller Bestand: "+mengeBestand);
		bestand.revalidate();
		bestand.repaint();
		lagerInfo.revalidate();
		lagerInfo.repaint();
	}
	
	/**
	 * setzt das Optionen-Panel in den zeige Button Modus
	 */
	public void zeigeButton(){
		optionenPanel.zeigeButton();
		buchungsModus=false;
	}
	
	/**
	 * setzt das Optionen-Panel in den Slider Modus oder den Info Modus
	 * @param gesamtMenge gesamt Menge der Buchung zu der der Prozentsatz errechnet wird
	 * @param maximum maximal noch zu verteilende Menge
	 * @param zulieferung true wenn zulieferung, sonst false
	 */
	public void zeigeBuchungsOptionen(int gesamtMenge, int maximum, boolean zulieferung){
		buchungsModus=true;
		this.gesamtMenge=gesamtMenge;
		this.maximum=maximum;
		this.zulieferung=zulieferung;
		if (isUnterLager) {
			if (zulieferung) { //wenn Unterlager auf das Zugebucht werden soll
				if (maximum>(mengeKapazitaet-mengeBestand)) { 
					optionenPanel.zeigeSlider(gesamtMenge,mengeKapazitaet-mengeBestand); //wenn noch zu verteilende Menge größer als freie Kapazität des Lagers ist freie Kapazität Maximum des Sliders
				} else {
					optionenPanel.zeigeSlider(gesamtMenge,maximum); //wenn noch zu verteilende Menge kleiner als freie Kapazität ist zu verteilende Menge Maximum des Sliders
				}	
			} else {
				optionenPanel.zeigeSlider(gesamtMenge,mengeBestand); //bei Abbuchungen ist der BEstand des Lagers Maximum des Sliders	 
			}
				
		} else {
			optionenPanel.zeigeInfo(); //Bei Oberlagern wird der Infotext angezeigt
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
		
		for (BuchungsModel bModel : listeBuchungen) { //für jede Buchung auf dieses Lager werden die Infos für die Tabelle als Array gespeichert
			data[i][0]=bModel.getBuchungsTag().toLocaleString();
			data[i][1]=bModel.getVerteilteMenge();
			int j;
			if (bModel.getAnteile().size()==1) {
				j=0;
			} else {
				for (j = 0; bModel.getAnteile().get(j).getLager().equals(lModel); j++) { //Anteil der zum aktuellem Lager gehört wird gesucht
					
				}
			}
			
			data[i][3]=bModel.getAnteile().get(j).getAnteil();
			double prozent = (Double.parseDouble("" + data[i][3])/(Double.parseDouble(""+ data[i][1]))*100.00);
			prozent = (prozent*1000)+5;
			int temp = (int) (prozent/10);
			prozent = (double)temp/100.00;
			data[i][2]=""+prozent+"%";
			if (bModel.getClass().equals(new AbBuchungsModel(null).getClass())) { //true bei Auslieferung
				data[i][4]="Auslieferung";
			} else {
				data[i][4]="Zulieferung";
			}
			i++;
		}
		
		tabelle=new JTable(data, columnNames);
		tabelle.setEnabled(false);
		tabelle.getTableHeader().setReorderingAllowed(false);
		if ((buchungen!=null)&&(buchungen.isVisible())) {
			this.remove(buchungen);
		}
		
		buchungen = new JScrollPane(tabelle);
		buchungen.setPreferredSize(new Dimension(515,400));
		this.add(buchungen);
		//Wenn Meldung angezeigt wird das Keine Buchungen vorhanden sind, wird die Meldung entfernt und die Buchungen angezeigt
		if (meldung.isVisible()) {
			this.remove(meldung);
			this.add(buchungen,BorderLayout.CENTER);
		}
		
	}
}

