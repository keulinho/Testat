package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import controller.LagerVerwaltungsController;
import core.exception.ErrorHandler;
import core.exception.ImageNotFoundException;
import model.LagerModel;

public class TreeView extends JPanel{

	JTree tree;
	LagerBaumKnoten root;
	LagerVerwaltungsController controller;
	JScrollPane treeScrollPanel;
	DefaultTreeModel model;
	List<LagerBaumKnoten> knoten;
	/**
	 * erzeugt eine neue TreeView
	 * @param controller controller, an den Befehle runtergereicht werden
	 */ 
	public TreeView(final LagerVerwaltungsController controller){
		this.controller=controller;
		this.setPreferredSize(new Dimension(300,400));
		this.setLayout(new BorderLayout());
		knoten = new ArrayList<LagerBaumKnoten>();
		//tree erstellen und konfigurieren
	    root = new LagerBaumKnoten(this);
	    model = new DefaultTreeModel(root);
	    tree = new JTree(model);
	    tree.setExpandsSelectedPaths(true);
	    tree.setRootVisible(false);
	    tree.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				if (!e.getPath().getLastPathComponent().equals(root)) {
					System.out.println(((LagerBaumKnoten)e.getPath().getLastPathComponent()).getUserObject());
					controller.aktuellesLagerAendern((LagerBaumKnoten) e.getPath().getLastPathComponent());
				}	
			}
		});
	   
	    DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
	    //Branch-Icon setzen
	    try{
	    	Image branchIconImg = ImageIO.read(new File("src/icons/home.png"));
	    	ImageIcon branchIcon = new ImageIcon(branchIconImg);
    		renderer.setClosedIcon(branchIcon);
    		renderer.setOpenIcon(branchIcon);
    		tree.setCellRenderer(renderer);
    	} catch(IOException ex){
    		ErrorHandler.HandleException(ErrorHandler.BILD_NICHT_GEFUNDEN, new ImageNotFoundException("Bilddatei mit dem Pfad \"src/icons/home.png\" nicht gefunden",(Throwable) ex));
    	}
    
	    //Leaf-Icon setzen
	    try{
	    	Image leafIconImg = ImageIO.read(new File("src/icons/truck.png"));
	    	ImageIcon leafIcon = new ImageIcon(leafIconImg);
    		renderer.setLeafIcon(leafIcon);
    		tree.setCellRenderer(renderer);
    	} catch(IOException ex){
    		ErrorHandler.HandleException(ErrorHandler.BILD_NICHT_GEFUNDEN, new ImageNotFoundException("Bilddatei mit dem Pfad \"src/icons/truck.png\" nicht gefunden",(Throwable) ex));
    	}

	    // JPanel füllen
	    treeScrollPanel= new JScrollPane(tree);    
	    treeScrollPanel.setPreferredSize(new Dimension(300, 350));
	    this.add(treeScrollPanel,BorderLayout.NORTH);
	    JButton neuesOberLager= new JButton("Lager erster Ebene erstellen");
		try {
		    Image img = ImageIO.read(new File("src/icons/new.png"));
		    neuesOberLager.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ErrorHandler.HandleException(ErrorHandler.BILD_NICHT_GEFUNDEN, new ImageNotFoundException("Bilddatei mit dem Pfad \"src/icons/new.png\" nicht gefunden",(Throwable) ex));
		  }
		
		neuesOberLager.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				VerwaltungsView vView = (VerwaltungsView) SwingUtilities.getWindowAncestor(TreeView.this);
				vView.zeigeNeuesLager(true);
				
			}
		});
		this.add(neuesOberLager,BorderLayout.CENTER);
		this.setBorder(new EmptyBorder(5,5,5,5));
	    }
	/**
	 * rekursive Methode um BaumKnoten zu erzeugen zu verknoten und die Zuordnung zu Models speichert
	 * @param lagerListe Liste in der die Lager einer Ebene sind
	 * @param elternKnoten Knoten an den an die die Lager der LagerListe angehängt werden
	 */
	public void baumEbeneErzeugen(List<LagerModel> lagerListe, LagerBaumKnoten elternKnoten) {
		if ((lagerListe!=null)&&(lagerListe.size()>0)) {
			for (LagerModel lModel : lagerListe) {
				lModel.deleteObservers();
				LagerBaumKnoten lBKnoten= new LagerBaumKnoten(this);
				lModel.addObserver(lBKnoten);
				controller.knotenLagerZuordnungAktualiseren(lBKnoten, lModel);
				DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
				model.insertNodeInto(lBKnoten, elternKnoten, elternKnoten.getChildCount());
				model.nodeStructureChanged(elternKnoten);
				knoten.add(lBKnoten);
				baumEbeneErzeugen(lModel.getUnterLager(),lBKnoten);	
			}
		}
	}
	/**
	 * aktualisiert den Baum
	 * @param lagerListe neue Liste aller Lager die als Baum dargestellt werden sollen
	 * @param mussAktualisieren gibt an ob der Baum zwangsweise aktualisiert werden muss
	 */
	public void aktualisiereBaum(List<LagerModel> lagerListe, boolean mussAktualisieren) {
		if ((getGroesseLagerList(lagerListe)-1!=knoten.size())||(mussAktualisieren)) {
			for (LagerBaumKnoten node : knoten) {
				System.out.println(node.getUserObject());
				model.removeNodeFromParent(node);
				model.nodeStructureChanged(node.getParent());
			}
			knoten= new ArrayList<LagerBaumKnoten>();
			baumEbeneErzeugen(lagerListe,root);
			if (mussAktualisieren){
				controller.aktuellesLagerAendern((LagerBaumKnoten) tree.getModel().getChild(tree.getModel().getRoot(), 0));
			}
		}
	}
	/**
	 * ermittelt die Anzahl an Lagern in einer Lagerliste +1 
	 * @param lagerListe Liste mit den Lagern dessen Anzahl ermittelt werden soll
	 * @return Anzahl der Lager in der Liste +1
	 */
	public int getGroesseLagerList(List<LagerModel> lagerListe){
		int start=0;
		for (LagerModel lModel : lagerListe) {
			start+=getGroesseLagerList(lModel.getUnterLager());
		}
		start++; //hier wird auch beim ersten Aufruf +1 gerechnet, gedachte "Root" wird mitgezählt die aber nicht in der Liste ist, daher Ergebniss Anzahl Lager in Liste + 1
		return start;
	}
	
	
}
