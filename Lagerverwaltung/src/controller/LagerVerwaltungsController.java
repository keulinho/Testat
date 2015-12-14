package controller;

import java.awt.FileDialog;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import core.command.AnteilCommand;
import core.command.Command;
import model.LagerModel;
import model.LagerVerwaltungsModel;
import view.LagerBaumKnoten;
import view.VerwaltungsView;

public class LagerVerwaltungsController extends Observable{
	
	VerwaltungsView view;
	LagerVerwaltungsModel lVModel;
	LagerModel aktuellesLager;
	HashMap knotenZuLagerModel;
	Stack<Command> redoStack, undoStack;
	
	public static void main(String... args){
		LagerVerwaltungsController control = new LagerVerwaltungsController();
	}
	
	public LagerVerwaltungsController() {
		knotenZuLagerModel=new HashMap<LagerBaumKnoten,LagerModel>();
		redoStack=new Stack<Command>();
		undoStack=new Stack<Command>();
		view=new VerwaltungsView(this);
		
		lVModel=new LagerVerwaltungsModel();
		lVModel.addObserver(view);
		
		aktuellesLager=lVModel.getLager().get(0);
		aktuellesLager.addObserver(view.getDetailPane());
	}
	
	public void undo() {
		Command command = undoStack.pop();
		command.undo();
		redoStack.push(command);
		setChanged();
		notifyObservers();
	}
	
	public Stack<Command> getRedoStack() {
		return redoStack;
	}

	public Stack<Command> getUndoStack() {
		return undoStack;
	}

	public void redo() {
		Command command = redoStack.pop();
		command.execute();
		undoStack.push(command);
		setChanged();
		notifyObservers();
	}
	
	public void buchungVerwerfen() {
		lVModel.verwerfenBuchung();
	}
	
	public void buchungAbschliessen() {
		lVModel.abschliessenBuchung();
	}
	
	public void auslieferungErstellen() {
		lVModel.erstellenAbBuchung(new Date());
	}
	
	public void zulieferungErstellen(int gesamtMenge) {
		lVModel.erstellenZuBuchung(new Date(), gesamtMenge);
	}
	
	public void aendereName(String neuerName) {
		lVModel.umbenennenLager(aktuellesLager, neuerName);
	}
	
	public void loescheLager() {
		lVModel.loesschenLager(aktuellesLager);
	}
	
	public void erstelleUnterLager(String lagerName, int lagerKapazitaet, boolean oberLager) {
		if (!oberLager) {
			lVModel.hinzufuegenLager(aktuellesLager, lagerKapazitaet, lagerName);
		} else {
			lVModel.hinzufuegenLager(null, lagerKapazitaet, lagerName);
		}
		
	}
	
	public void bucheAnteil(int menge) {
		Command command = new AnteilCommand(redoStack,lVModel,aktuellesLager,menge);
		command.execute();
		undoStack.push(command);
		setChanged();
		notifyObservers();
	}
	
	public void speichern() {
		FileDialog fd = new FileDialog(view, "Choose a file", FileDialog.SAVE);
		fd.setDirectory("C:\\Users\\"+System.getProperty("user.name").toUpperCase());
		fd.setFile("LagerVerwaltung");
		fd.setVisible(true);
		String filename = fd.getDirectory()+"\\"+fd.getFile();
		if (filename.contains(".")) {
			filename=filename.split("\\.")[0];
		}
		
		if ((fd.getDirectory()!=null)&&(fd.getFile()!=null)) {
			
			FileOutputStream fout;
			try {
				fout = new FileOutputStream(filename+".lvm");
				ObjectOutputStream oos = new ObjectOutputStream(fout);
				oos.writeObject(lVModel);
				oos.close();
			} catch (Exception e) {
				//err.handleExcepion(1, e);
				e.printStackTrace();
			}
		}
	}
	
	public void laden() {
		FileDialog fd = new FileDialog(view, "Choose a file", FileDialog.LOAD);
		fd.setDirectory("C:\\Users\\"+System.getProperty("user.name").toUpperCase());
		fd.setFile(".lvm");
		fd.setVisible(true);
		String filename = fd.getDirectory()+"\\"+fd.getFile();
		if ((fd.getDirectory()!=null)&&(fd.getFile()!=null)) {
			
			try {
				FileInputStream fins = new FileInputStream(filename);
				ObjectInputStream ois = new ObjectInputStream(fins);
				LagerVerwaltungsModel temp=(LagerVerwaltungsModel)ois.readObject();
				lVModel=temp;
				lVModel.addObserver(view);
				aktuellesLager=lVModel.getLager().get(0);
				aktuellesLager.addObserver(view.getDetailPane());
				view.aktualisiereBaum();
			} catch (Exception e) {
				//err.handleExcepion(2, e);
				e.printStackTrace();
			}
			
		}
	}
	
	public void aktuellesLagerAendern(LagerBaumKnoten lBKnoten) {
		aktuellesLager.deleteObserver(view.getDetailPane());
		aktuellesLager=(LagerModel) knotenZuLagerModel.get(lBKnoten);
		aktuellesLager.addObserver(view.getDetailPane());
	}
	
	public void knotenLagerZuordnungAktualiseren(LagerBaumKnoten lBKnoten,LagerModel lModel) {
		knotenZuLagerModel.put(lBKnoten, lModel);	
	}
	public void addObserver(Observer o) {
		super.addObserver(o);
		setChanged();
		notifyObservers();
	}
}
