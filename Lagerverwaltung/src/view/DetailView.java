package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import controller.LagerVerwaltungsController;
import core.utils.AbsoluterAnteilAbsteigend;
import core.utils.AbsoluterAnteilAufsteigend;
import core.utils.GesamtMengeAbsteigend;
import core.utils.GesamtMengeAufsteigend;
import core.utils.Rechner;
import core.utils.RelativerAnteilAbsteigend;
import core.utils.RelativerAnteilAufsteigend;
import core.utils.Sortierer;
import core.utils.TagAbsteigend;
import core.utils.TagAufsteigend;
import model.AbBuchungsModel;
import model.BuchungsModel;
import model.LagerModel;

public class DetailView extends JPanel implements Observer{
	
	String lagerName;
	int mengeKapazitaet, mengeBestand, gesamtMenge, maximum, verteilteMenge;
	EditNamePanel editName;
	OptionenPanel optionenPanel;
	JPanel lagerInfo;
	JLabel bestand, kapazitaet, meldung, tagHeader, mengeHeader, relativHeader, absolutHeader, artHeader;
	JScrollPane buchungen;
	JTable tabelle;
	String[] columnNames;
	Object[][] data;
	boolean zulieferung;
	boolean isUnterLager;
	boolean buchungsModus;
	LagerVerwaltungsController controller;
	Sortierer sortierer;
	LagerModel lModel;
	List<BuchungsModel> listeBuchungen;
	ImageIcon sort,sortAsc,sortDesc;
	Rechner rechner;
	

	/**
	 * erzeugt eine DetailView
	 * @param controller Controller an den alle Befehle runtergereicht werden
	 */
	public DetailView(LagerVerwaltungsController controller) {
		
		this.controller=controller;
		lagerName="";
		rechner = new Rechner();
		this.setPreferredSize(new Dimension(515,400));
		this.setLayout(new BorderLayout());
		
		columnNames = new String []{"Buchungstag",
				"Menge",
                "<html>relativer<br>Anteil</html>",
                "<html>absoluter<br>Anteil</html>",
                "Art"};
		meldung= new JLabel();

		try {
		    Image img = ImageIO.read(new File("src/icons/sort.png"));
		    sort = new ImageIcon(img);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		try {
		    Image img = ImageIO.read(new File("src/icons/sortAsc.png"));
		    sortAsc = new ImageIcon(img);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		try {
		    Image img = ImageIO.read(new File("src/icons/sortDesc.png"));
		    sortDesc = new ImageIcon(img);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		tagHeader=new JLabel(columnNames[0],sort,JLabel.CENTER );
		tagHeader.setBorder(new LineBorder(Color.lightGray));
		mengeHeader=new JLabel(columnNames[1],sort,JLabel.CENTER );
		mengeHeader.setBorder(new LineBorder(Color.lightGray));
		relativHeader=new JLabel(columnNames[2],sort,JLabel.CENTER );
	    relativHeader.setBorder(new LineBorder(Color.lightGray));
	    absolutHeader=new JLabel(columnNames[3],sort,JLabel.CENTER );
	    absolutHeader.setBorder(new LineBorder(Color.lightGray));
		artHeader=new JLabel(columnNames[4],JLabel.CENTER );
		artHeader.setBorder(new LineBorder(Color.lightGray));
			
		lagerInfoErstellen();
		this.add(lagerInfo,BorderLayout.NORTH);

		optionenPanel = new OptionenPanel(controller);
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
		verteilteMenge=lModel.getVerteilteMenge();
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
			listeBuchungen=lModel.getBuchungen();
			this.lModel = (LagerModel) o;
			bereiteBuchungenAuf();
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
		bestand = new JLabel("Aktueller Bestand: "+(mengeBestand+verteilteMenge));
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
				if (maximum>(mengeKapazitaet-(mengeBestand+verteilteMenge))) { 
					optionenPanel.zeigeSlider(gesamtMenge,mengeKapazitaet-(mengeBestand+verteilteMenge)); //wenn noch zu verteilende Menge größer als freie Kapazität des Lagers ist freie Kapazität Maximum des Sliders
				} else {
					optionenPanel.zeigeSlider(gesamtMenge,maximum); //wenn noch zu verteilende Menge kleiner als freie Kapazität ist zu verteilende Menge Maximum des Sliders
				}	
			} else {
				optionenPanel.zeigeSlider(mengeBestand+verteilteMenge,(mengeBestand+verteilteMenge)); //bei Abbuchungen ist der BEstand des Lagers Maximum des Sliders	 
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
	public void bereiteBuchungenAuf() {
		
		if (sortierer!=null) {
			listeBuchungen=sortierer.sortiere(listeBuchungen, lModel);
		}
		int i=0;
		data = new Object[listeBuchungen.size()][5];
		
		for (BuchungsModel bModel : listeBuchungen) { //für jede Buchung auf dieses Lager werden die Infos für die Tabelle als Array gespeichert
			data[i][0]=bModel.getBuchungsTag().toLocaleString();
			data[i][1]=bModel.getVerteilteMenge();
			int j;
			
			for (j = 0; !bModel.getAnteile().get(j).getLager().equals(lModel); j++) { //Anteil der zum aktuellem Lager gehört wird gesucht
					
				}
			
			data[i][3]=bModel.getAnteile().get(j).getAnteil();
			data[i][2]=""+rechner.rechneProzent((int)data[i][3],(int)data[i][1])+"%";
			if (bModel.getClass().equals(new AbBuchungsModel(null).getClass())) { //true bei Auslieferung
				data[i][4]="Auslieferung";
			} else {
				data[i][4]="Zulieferung";
			}
			i++;
		}
		
		tabelle=new JTable(data, columnNames);
		TableColumnModel columnModel=tabelle.getColumnModel();
		TableCellRenderer renderer = new JComponentTableCellRenderer();
		columnModel.getColumn(0).setHeaderRenderer(renderer);
		columnModel.getColumn(0).setHeaderValue(tagHeader);
		columnModel.getColumn(1).setHeaderRenderer(renderer);
		columnModel.getColumn(1).setHeaderValue(mengeHeader);
		columnModel.getColumn(2).setHeaderRenderer(renderer);
		columnModel.getColumn(2).setHeaderValue(relativHeader);
		columnModel.getColumn(3).setHeaderRenderer(renderer);
		columnModel.getColumn(3).setHeaderValue(absolutHeader);
		columnModel.getColumn(4).setHeaderRenderer(renderer);
		columnModel.getColumn(4).setHeaderValue(artHeader);
		tabelle.setEnabled(false);
		tabelle.getTableHeader().setReorderingAllowed(false);
		tabelle.getTableHeader().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {		
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				 int spalte = tabelle.columnAtPoint(arg0.getPoint());
			     switch (spalte) {
			     	case 0: if (sortierer!=null&&sortierer.getSort().getClass().equals(new TagAbsteigend().getClass())) {
			     		sortierer=new Sortierer(new TagAufsteigend());
			     		tagHeader.setIcon(sortAsc);
			     		
			     	} else {
			     		sortierer=new Sortierer(new TagAbsteigend());
			     		tagHeader.setIcon(sortDesc);
			     	}
			     	mengeHeader.setIcon(sort);
		     		relativHeader.setIcon(sort);
		     		absolutHeader.setIcon(sort);
			     	break;
			     	case 1: if (sortierer!=null&&sortierer.getSort().getClass().equals(new GesamtMengeAbsteigend().getClass())) {
			     		sortierer=new Sortierer(new GesamtMengeAufsteigend());
			     		mengeHeader.setIcon(sortAsc);
			     	} else {
			     		sortierer=new Sortierer(new GesamtMengeAbsteigend());
			     		mengeHeader.setIcon(sortDesc);
			     	}
			     	tagHeader.setIcon(sort);
			     	relativHeader.setIcon(sort);
		     		absolutHeader.setIcon(sort);
			     	break;
			     	case 2: if (sortierer!=null&&sortierer.getSort().getClass().equals(new RelativerAnteilAbsteigend().getClass())) {
			     		sortierer=new Sortierer(new RelativerAnteilAufsteigend());	     		
			     		relativHeader.setIcon(sortAsc);	
			     	} else {
			     		sortierer=new Sortierer(new RelativerAnteilAbsteigend());
			     		relativHeader.setIcon(sortDesc);	
			     	}
			     	tagHeader.setIcon(sort);
		     		mengeHeader.setIcon(sort);
		     		absolutHeader.setIcon(sort);
			     	break;
			     	case 3: if (sortierer!=null&&sortierer.getSort().getClass().equals(new AbsoluterAnteilAbsteigend().getClass())) {
			     		sortierer=new Sortierer(new AbsoluterAnteilAufsteigend());
			     		absolutHeader.setIcon(sortAsc);
			     	} else {
			     		sortierer=new Sortierer(new AbsoluterAnteilAbsteigend());
			     		absolutHeader.setIcon(sortDesc);
			     	}
			     	tagHeader.setIcon(sort);
		     		mengeHeader.setIcon(sort);
		     		relativHeader.setIcon(sort);
			     	break;
			     }
			     bereiteBuchungenAuf();	
			}
		});
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
		this.revalidate();
		this.repaint();
	}
	
	public void editName() {
		editName.edit();
	}
}

