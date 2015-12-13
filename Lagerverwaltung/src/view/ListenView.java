package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import model.BuchungsModel;

public class ListenView extends JPanel{

	JList liste;
	DefaultListModel model;
	public ListenView(List<BuchungsModel> buchungsListe) {
		this.setPreferredSize(new Dimension(815,800));
		model=new DefaultListModel();
		for (BuchungsModel bModel : buchungsListe) {
			model.addElement("<html>Buchungstag: " +bModel.getBuchungsTag().toLocaleString()+"<br>Gesamte Menge: " + bModel.getVerteilteMenge()+"</html>");
		}
		liste = new JList(model);
		liste.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		liste.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollListe = new JScrollPane(liste);
		scrollListe.setPreferredSize(new Dimension(300,400));
		this.add(scrollListe);
		this.revalidate();
		this.repaint();
	}
}
