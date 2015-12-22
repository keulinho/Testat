package view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.tree.DefaultMutableTreeNode;

import core.utils.Rechner;
import model.LagerModel;
/**@author Marius Mamsch
**/
public class LagerBaumKnoten extends DefaultMutableTreeNode implements Observer{

	TreeView tree;
	/**
	 * erstellt einen neuen Knoten
	 * @param tree Baum dem der Knoten hinzugefügt werden soll
	 */
	public LagerBaumKnoten(TreeView tree) {
		super();
		this.tree = tree;
	}
	/**
	 * aktualisiert die Label, wenn sich das zugehörige Lager ändert
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		Rechner rechner = new Rechner();
		LagerModel lModel = (LagerModel) arg0;
		if (lModel.hatUnterlager()) {
			this.setUserObject(lModel.getName()+ " ("+rechner.rechneProzent(lModel.getBestand(), lModel.getMaxKapazitaet())+"%)");
		} else {
			this.setUserObject("<html>"+lModel.getName()+ " ("+rechner.rechneProzent(lModel.getBestand()+lModel.getVerteilteMenge(), lModel.getMaxKapazitaet())+"%)<br> maximale Kapazität: "+ lModel.getMaxKapazitaet()+"<br>aktueller Bestand: "+(lModel.getBestand()+lModel.getVerteilteMenge())+"</html>");
		}
		tree.revalidate();
		tree.repaint();
	}

}
