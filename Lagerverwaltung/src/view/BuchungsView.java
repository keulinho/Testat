package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import model.AbBuchungsModel;
import model.BuchungsModel;

public class BuchungsView extends JPanel{

	String[] columnNames;
	Object[][] data;
	
	/**
	 * erstellt eine Ansicht in der alle Buchungen aufgelistet werden
	 * @param listeBuchungen Liste mit allen Buchungen
	 */
	public BuchungsView(List<BuchungsModel> listeBuchungen) {
		
		this.setPreferredSize(new Dimension(515,400));
		this.setLayout(new BorderLayout());
		
		//Überschrift erstellen und hinzufügen
		JLabel ueberschrift= new JLabel("Alle Buchungen:");
		ueberschrift.setFont(new Font(this.getFont().getName(),Font.BOLD,20));
		this.add(ueberschrift, BorderLayout.PAGE_START);
		
		//Tabelle/Meldung erstellen und hinzufügen
		if ((listeBuchungen!= null) && (listeBuchungen.size()>0)) { //true wenn die Liste nicht leer ist
			//Tabelle wird erzeugt und hinzugefügt
			datenAufbereiten(listeBuchungen);
			
			JTable tabelle = new JTable(data,columnNames);
			tabelle.setEnabled(false);
			tabelle.getTableHeader().setReorderingAllowed(false);
			JScrollPane alleBuchungen = new JScrollPane(tabelle);
			this.add(alleBuchungen, BorderLayout.CENTER);
		} else {
			//Meldung wird erzeugt und hinzugefügt
			JLabel meldung= new JLabel("Noch keine Buchungen vorhanden!");
			meldung.setFont(new Font(this.getFont().getName(),Font.BOLD,20));
			this.add(meldung, BorderLayout.CENTER);
		}
		
		//Schliessen-Button wird erzeugt und hinzugefügt
		JButton schliessen = new JButton("Schließen");
		try {
		    Image img = ImageIO.read(new File("src/icons/delete.png"));
		    schliessen.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		schliessen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VerwaltungsView vView = (VerwaltungsView) SwingUtilities.getWindowAncestor(BuchungsView.this);
				vView.zeigeDetailPane();
			}
		});
		this.add(schliessen, BorderLayout.PAGE_END);
	}
	
	/**
	 * speichert die Infos für die Tabelle aus der Liste in die nötigen Arrays ab
	 * @param listeBuchungen Liste aller Buchungen
	 */
	public void datenAufbereiten(List<BuchungsModel> listeBuchungen) {
		int maxAnteile = 0;
		for (BuchungsModel bModel : listeBuchungen) {
			if (bModel.getAnteile().size()>maxAnteile) { 
				maxAnteile=bModel.getAnteile().size(); //speichert Größte Menge an Anteilen einer Buchung
			}
		}
		columnNames=new String[3+(maxAnteile*2)];
		columnNames[0]="Buchungstag";
		columnNames[1]="Art";
		columnNames[2]="gesamte Menge";
		int stelle=3;
		for (int i = 1; i<maxAnteile+1; i++) { //Überschriften für Anteile und Lager werden anhand der maximalen Anzahl Anteile erstellt
			columnNames[stelle]="Lager "+i;
			stelle++;
			columnNames[stelle]="Anteil "+i;
			stelle++;
		}
		
		data=new Object[listeBuchungen.size()][3+(maxAnteile*2)];
		for (int i = 0; i<listeBuchungen.size(); i++) { //pro Buchung wird ein Array mit den nötigen Infos gefüllt
			data[i][0]=listeBuchungen.get(i).getBuchungsTag().toLocaleString();
			if (listeBuchungen.get(i).getClass().equals(new AbBuchungsModel(null).getClass())) { //true wenn Abbuchung
				data[i][1]="Auslieferung";
			} else {
				data[i][1]="Zulieferung";
			}
			data[i][2]=listeBuchungen.get(i).getVerteilteMenge();
			stelle=3;
			for (int j = 0; j<listeBuchungen.get(i).getAnteile().size(); j++) { //Für jeden Anteil an der Buchung wird Lagername und relative Menge gespeichert
				data[i][stelle]=listeBuchungen.get(i).getAnteile().get(j).getLager().getName();
				stelle++;
				double prozent = (Double.parseDouble(""+listeBuchungen.get(i).getAnteile().get(j).getAnteil())/(Double.parseDouble(""+ data[i][2]))*100.00);
				System.out.println(prozent);
				prozent = (prozent*1000)+5;
				int temp = (int) (prozent/10);
				prozent = (double)temp/100.00;
				data[i][stelle]=""+prozent+"%";
				stelle++;
			}
			for (int k = 0; k<(maxAnteile-listeBuchungen.get(i).getAnteile().size()); k++) { //Blanks werden eingesetzt wenn die Buchung weniger Anteile hat als die maximalen Anteile
				data[i][stelle]="";
				stelle++;
				data[i][stelle]="";
				stelle++;
			}
		}
	}
}
