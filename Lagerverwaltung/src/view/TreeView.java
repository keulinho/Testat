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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;

import controller.LagerVerwaltungsController;
import model.LagerModel;

public class TreeView extends JPanel{

	JTree tree;
	LagerBaumKnoten root;
	LagerVerwaltungsController controller;
	JScrollPane treeScrollPanel;
	DefaultTreeModel model;
	List<LagerBaumKnoten> knoten;
	
	public TreeView(List<LagerModel> lagerListe, final LagerVerwaltungsController controller){
		
		this.controller=controller;
		this.setPreferredSize(new Dimension(300,400));
		this.setLayout(new BorderLayout());
		
		knoten = new ArrayList<LagerBaumKnoten>();
	    root = new LagerBaumKnoten("Gesamtlager",this);
	    model = new DefaultTreeModel(root);
	    baumEbeneErzeugen(lagerListe,root);
	    tree = new JTree(model);
	    tree.setExpandsSelectedPaths(true);
	    tree.setRootVisible(false);
	    tree.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				if (!e.getPath().getLastPathComponent().equals(root)) {
					controller.aktuellesLagerAendern((LagerBaumKnoten) e.getPath().getLastPathComponent());
				}	
			}
		});
	    
	    treeScrollPanel= new JScrollPane(tree);    
	    treeScrollPanel.setPreferredSize(new Dimension(300, 350));
	    this.add(treeScrollPanel,BorderLayout.NORTH);
	    JButton neuesOberLager= new JButton("Lager erster Ebene erstellen");
		try {
		    Image img = ImageIO.read(new File("src/icons/new.png"));
		    neuesOberLager.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
			  ex.printStackTrace();
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
		
	

	
	public void baumEbeneErzeugen(List<LagerModel> lagerListe, LagerBaumKnoten elternKnoten) {
		if ((lagerListe!=null)&&(lagerListe.size()>0)) {
			for (LagerModel lModel : lagerListe) {
				lModel.deleteObservers();
				LagerBaumKnoten lBKnoten= new LagerBaumKnoten(lModel.getName(),this);
				lModel.addObserver(lBKnoten);
				controller.knotenLagerZuordnungAktualiseren(lBKnoten, lModel);
				DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
				model.insertNodeInto(lBKnoten, elternKnoten, elternKnoten.getChildCount());
				model.nodesWereInserted(elternKnoten, new int [elternKnoten.getChildCount()]);
				model.reload(elternKnoten);
				model.reload(lBKnoten);
				model.nodeStructureChanged(elternKnoten);
				knoten.add(lBKnoten);
				baumEbeneErzeugen(lModel.getUnterLager(),lBKnoten);
				
			}
		}
	}
	
	public void aktualisiereBaum(List<LagerModel> lagerListe, boolean mussAktualisieren) {
		
		if ((getGroesseLagerList(lagerListe)-1!=knoten.size())||(mussAktualisieren)) {
			for (LagerBaumKnoten node : knoten) {
				model.removeNodeFromParent(node);
				model.nodeStructureChanged(node.getParent());
			}
			knoten= new ArrayList<LagerBaumKnoten>();
			baumEbeneErzeugen(lagerListe,root);
			tree=new JTree(model);
			tree.setExpandsSelectedPaths(true);
			
			tree.addTreeSelectionListener(new TreeSelectionListener() {
					
					@Override
					public void valueChanged(TreeSelectionEvent e) {
						if (!e.getPath().getLastPathComponent().equals(root)) {
							controller.aktuellesLagerAendern((LagerBaumKnoten) e.getPath().getLastPathComponent());
						}	
					}
			});	
			if (mussAktualisieren){
				controller.aktuellesLagerAendern((LagerBaumKnoten) tree.getModel().getChild(tree.getModel().getRoot(), 0));
			}
		}
	}
	public int getGroesseLagerList(List<LagerModel> lagerListe){
		int start=0;
		for (LagerModel lModel : lagerListe) {
			start+=getGroesseLagerList(lModel.getUnterLager());
		}
		start++;
		return start;
	}
	
	
}
