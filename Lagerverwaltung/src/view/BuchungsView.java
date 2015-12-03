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
	
	public BuchungsView(List<BuchungsModel> listeBuchungen) {
		this.setPreferredSize(new Dimension(515,400));
		this.setLayout(new BorderLayout());
		
		JLabel ueberschrift= new JLabel("Alle Buchungen:");
		ueberschrift.setFont(new Font(this.getFont().getName(),Font.BOLD,20));
		this.add(ueberschrift, BorderLayout.PAGE_START);
		
		if (listeBuchungen!= null) {
			datenAufbereiten(listeBuchungen);
			JTable tabelle = new JTable(data,columnNames);
			tabelle.setEnabled(false);
			tabelle.getTableHeader().setReorderingAllowed(false);
			JScrollPane alleBuchungen = new JScrollPane(tabelle);
			this.add(alleBuchungen, BorderLayout.CENTER);
		} else {
			JLabel meldung= new JLabel("Noch keine Buchungen vorhanden!");
			meldung.setFont(new Font(this.getFont().getName(),Font.BOLD,20));
			this.add(meldung, BorderLayout.CENTER);
		}
		
		JButton schliessen = new JButton("Schlieﬂen");
		try {
		    Image img = ImageIO.read(new File("src/icons/delete.png"));
		    schliessen.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
		  }
		schliessen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				VerwaltungsView vView = (VerwaltungsView) SwingUtilities.getWindowAncestor(BuchungsView.this);
				vView.zeigeDetailPane();
			}
		});
		this.add(schliessen, BorderLayout.PAGE_END);
	}
	
	public void datenAufbereiten(List<BuchungsModel> listeBuchungen) {
		int maxAnteile = 0;
		for (BuchungsModel bModel : listeBuchungen) {
			if (bModel.getAnteile().size()>maxAnteile) {
				maxAnteile=bModel.getAnteile().size();
			}
		}
		columnNames=new String[3+(maxAnteile*2)];
		columnNames[0]="Buchungstag";
		columnNames[1]="Art";
		columnNames[2]="gesamte Menge";
		int stelle=3;
		for (int i = 1; i>=maxAnteile; i++) {
			columnNames[stelle]="Lager "+i;
			stelle++;
			columnNames[stelle]="Anteil "+i;
			stelle++;
		}
		
		data=new Object[listeBuchungen.size()][3+(maxAnteile*2)];
		for (int i = 0; i>=listeBuchungen.size(); i++) {
			data[i][0]=listeBuchungen.get(i).getBuchungsTag().toLocaleString();
			if (listeBuchungen.get(i).getClass().equals(new AbBuchungsModel(null).getClass())) {
				data[i][1]="Auslieferung";
			} else {
				data[i][1]="Zulieferung";
			}
			data[i][2]=listeBuchungen.get(i).getVerteilteMenge();
			stelle=3;
			for (int j = 0; j>=listeBuchungen.get(i).getAnteile().size(); j++) {
				data[i][stelle]=listeBuchungen.get(i).getAnteile().get(j).getLager();
				stelle++;
				double prozent = (((double)data[i][2]/(double)listeBuchungen.get(i).getAnteile().get(j).getAnteil())*100.00);
				prozent = (prozent*1000)+5;
				int temp = (int) (prozent/10);
				prozent = (double)temp/100.00;
				data[i][stelle]=""+prozent+"%";
				stelle++;
			}
			for (int k = 0; k>=(maxAnteile-listeBuchungen.get(i).getAnteile().size()); k++) {
				data[i][stelle]="";
				stelle++;
				data[i][stelle]="";
				stelle++;
			}
		}
	}
}
