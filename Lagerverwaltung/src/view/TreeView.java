package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class TreeView extends JPanel  {

	String lagerName;
	int hierachieEbene;
	int maxKapazitaet;
	int bestand;
	private JTree tree;
	
	public TreeView(){
		
		this.setPreferredSize(new Dimension(300,400));
		this.setLayout(new BorderLayout());
		
	    DefaultMutableTreeNode top =
	            new DefaultMutableTreeNode("Lagername A");
	        createNodes(top);
	        tree = new JTree(top);
	        JScrollPane treeView = new JScrollPane(tree);
	        
	        this.add(tree);
	    }
	private void createNodes(DefaultMutableTreeNode top) {
	    DefaultMutableTreeNode category = null;
	    DefaultMutableTreeNode book = null;
	    
	    category = new DefaultMutableTreeNode("Lagername A.1");
	    top.add(category);
	}
}

