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
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import core.exception.ErrorHandler;
import core.exception.ImageNotFoundException;
import core.utils.Rechner;
import model.AnteilModel;
import model.BuchungsModel;

public class ListenView extends JPanel{

	JList liste;
	DefaultListModel model;
	JPanel detailPanel;
	Rechner rechner;
	
	/**
	 * erstellt eine ListenView mit den mitgegebenen Einträgen
	 * @param buchungsListe Liste die angezeigt werden soll
	 */
	public ListenView(final List<BuchungsModel> buchungsListe) {
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(815,300));
		rechner = new Rechner();
		//liste erzeugen und konfigurieren
		model=new DefaultListModel<String>();
		for (BuchungsModel bModel : buchungsListe) {
			model.addElement("<html>Buchungstag: " +bModel.getBuchungsTag().toLocaleString()+"<br>Gesamte Menge: " + bModel.getVerteilteMenge()+"</html>");
		}
		liste = new JList<String>(model);
		liste.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		liste.setLayoutOrientation(JList.VERTICAL);

		liste.setFixedCellHeight(40);
		liste.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				 if (!arg0.getValueIsAdjusting()) {
						ListenView.this.remove(detailPanel);
						detailPanel=anteilDetailsErstellen(buchungsListe.get(liste.getSelectedIndex()));
						ListenView.this.add(detailPanel);
						ListenView.this.revalidate();
						ListenView.this.repaint();
	                }
	
			}
		});
		JScrollPane scrollListe = new JScrollPane(liste);
		scrollListe.setPreferredSize(new Dimension(300,350));
		
		JButton schliessen= new JButton("Schließen");
		try {
		    Image img = ImageIO.read(new File("src/icons/delete.png"));
		    schliessen.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ErrorHandler.HandleException(ErrorHandler.BILD_NICHT_GEFUNDEN, new ImageNotFoundException("Bilddatei mit dem Pfad \"src/icons/delete.png\" nicht gefunden",(Throwable) ex));
		  }
		schliessen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VerwaltungsView vView = (VerwaltungsView) SwingUtilities.getWindowAncestor(ListenView.this);
				vView.standardAnsicht();
				
			}
		});
		if (!buchungsListe.isEmpty()) {
			detailPanel=anteilDetailsErstellen(buchungsListe.get(0));
			this.add(detailPanel,BorderLayout.EAST);
		} else {
			JLabel meldung= new JLabel("Noch keine Buchungen vorhanden!");
			meldung.setFont(new Font(this.getFont().getName(),Font.BOLD,20));
			this.add(meldung, BorderLayout.CENTER);
		}
		this.add(scrollListe, BorderLayout.WEST);
		this.add(schliessen, BorderLayout.PAGE_END);
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * erstellt Details zu der mitgegebenen Buchung und gibt das Panel zurück
	 * @param buchung Buchung deren Details angezeigt werden sollen
	 * @return JPanel auf das dem Details angezeigt werden
	 */
	public JPanel anteilDetailsErstellen(BuchungsModel buchung) {
		JPanel detailPanel = new JPanel();
		detailPanel.setPreferredSize(new Dimension(515,350));
		detailPanel.setLayout(new BoxLayout(detailPanel,BoxLayout.Y_AXIS));
		//gesamt buchungdetails erstellen
		JLabel bDetails = new JLabel("Buchungsdetails:");
		bDetails.setFont(new Font(bDetails.getFont().getName(),Font.BOLD,20));
		detailPanel.add(bDetails);
		JLabel bTag = new JLabel("Buchungstag: "+buchung.getBuchungsTag().toLocaleString());
		detailPanel.add(bTag);
		JLabel bMenge = new JLabel("Gesamte Menge: "+buchung.getVerteilteMenge());
		detailPanel.add(bMenge);
		// anteile darstellen
		JPanel anteilPanel = new JPanel();
		anteilPanel.setLayout(new BoxLayout(anteilPanel,BoxLayout.Y_AXIS));
		int i = 1;
		// für jeden Anteil passende Label erstellen
		for (AnteilModel aModel : buchung.getAnteile()) {
			
			JLabel lager = new JLabel("Lager " + i+": "+aModel.getLager().getName());
			lager.setFont(new Font(bDetails.getFont().getName(),Font.BOLD,20));
			if (i>1) {
				lager.setBorder(new EmptyBorder(10, 0, 0, 0));
			}
			anteilPanel.add(lager);
			JLabel absoluteMenge = new JLabel("absolute Menge: "+aModel.getAnteil());
			anteilPanel.add(absoluteMenge);
			JLabel relativeMenge = new JLabel("relative Menge: "+rechner.rechneProzent(aModel.getAnteil(), buchung.getVerteilteMenge())+"%");
			anteilPanel.add(relativeMenge);
			i++;
		}
		JScrollPane scrollAnteil = new JScrollPane(anteilPanel);
		detailPanel.add(scrollAnteil);
		return detailPanel;
	}
}
