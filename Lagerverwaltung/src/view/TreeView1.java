package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import controller.LagerVerwaltungsController;
import model.LagerModel;

public class TreeView1 extends JPanel{

	JTree tree;
	LagerBaumKnoten root;
	LagerVerwaltungsController controller;
	JScrollPane treeScrollPanel;
	DefaultTreeModel model;
	List<LagerBaumKnoten> knoten;
	
	public TreeView1(List<LagerModel> lagerListe, final LagerVerwaltungsController controller){
		
		this.controller=controller;
		this.setPreferredSize(new Dimension(300,400));
		this.setLayout(new BorderLayout());
		
		knoten = new ArrayList<LagerBaumKnoten>();
	    root = new LagerBaumKnoten("Gesamtlager");
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
	    this.add(treeScrollPanel);
	    }
	
	
	public void baumEbeneErzeugen(List<LagerModel> lagerListe, LagerBaumKnoten elternKnoten) {
		if ((lagerListe!=null)&&(lagerListe.size()>0)) {
			for (LagerModel lModel : lagerListe) {
				LagerBaumKnoten lBKnoten= new LagerBaumKnoten(lModel.getName());
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
	
	public void aktualisiereBaum(List<LagerModel> lagerListe) {
		

		for (LagerBaumKnoten node : knoten) {
			model.removeNodeFromParent(node);
			model.nodeStructureChanged(node.getParent());
		}
		knoten= new ArrayList<LagerBaumKnoten>();
		baumEbeneErzeugen(lagerListe,root);
		tree=new JTree(model);
		tree.setExpandsSelectedPaths(true);
		
		List<Object> knoten = new ArrayList<Object>();
		for (int i = 0; i<tree.getModel().getChildCount(tree.getModel().getRoot()); i++) {
		    knoten.add(tree.getModel().getChild(tree.getModel().getRoot(), i));
		}
		
		tree.expandPath(new TreePath(knoten.toArray()));
		
		tree.addTreeSelectionListener(new TreeSelectionListener() {
				
				@Override
				public void valueChanged(TreeSelectionEvent e) {
					if (!e.getPath().getLastPathComponent().equals(root)) {
						controller.aktuellesLagerAendern((LagerBaumKnoten) e.getPath().getLastPathComponent());
					}	
				}
		});
		
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		model.reload();
		model.nodeStructureChanged(root);
		
		treeScrollPanel = new JScrollPane(tree);
		this.revalidate();
		this.repaint();
	}
	
}
