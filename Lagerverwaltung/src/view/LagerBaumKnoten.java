package view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.tree.DefaultMutableTreeNode;

import controller.LagerVerwaltungsController;
import model.LagerModel;

public class LagerBaumKnoten extends DefaultMutableTreeNode implements Observer{

	TreeView1 tree;
	
	public LagerBaumKnoten(String lagerName, TreeView1 tree) {
		super(lagerName);
		this.tree = tree;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		LagerModel lModel = (LagerModel) arg0;
		if (lModel.hatUnterlager()) {
			this.setUserObject(lModel.getName());
		} else {
			this.setUserObject("<html>"+lModel.getName()+"<br>aktueller Bestand: "+(lModel.getBestand()+lModel.getVerteilteMenge()) + "<br>maximale Kapazitšt: "+ lModel.getMaxKapazitaet()+"</html>");
		}
		tree.revalidate();
		tree.repaint();
	}

}
