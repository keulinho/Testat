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

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

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
	/**
	 * erzeugt einen neuen Controller
	 */
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
	/**
	 * macht letzte Buchung auf ein Lager rückgänig
	 */
	public void undo() {
		Command command = undoStack.pop();
		command.undo();
		redoStack.push(command);
		setChanged();
		notifyObservers();
	}
	/**
	 * gibt den RedoStack zurück
	 * @return RedoStack
	 */
	public Stack<Command> getRedoStack() {
		return redoStack;
	}
	/**
	 * gibt den UndoStack zurück
	 * @return UndoStack
	 */
	public Stack<Command> getUndoStack() {
		return undoStack;
	}
	/**
	 * wiederholt die zuletzt rückgängig gemachte Buchung
	 */
	public void redo() {
		Command command = redoStack.pop();
		command.execute();
		undoStack.push(command);
		setChanged();
		notifyObservers();
	}
	/**
	 * verwirft die laufende Buchung
	 */
	public void buchungVerwerfen() {
		lVModel.verwerfenBuchung();
	}
	/**
	 * schließt die aktuelle Buchung ab
	 */
	public void buchungAbschliessen() {
		lVModel.abschliessenBuchung();
	}
	/**
	 * erstellt eine neue Auslieferung
	 */
	public boolean auslieferungErstellen() {
		return lVModel.erstellenAbBuchung(new Date());
	}
	/**
	 * erstellt eine neue Zulieferung
	 * @param gesamtMenge menge der Zulieferung
	 */
	public boolean zulieferungErstellen(int gesamtMenge) {
		return lVModel.erstellenZuBuchung(new Date(), gesamtMenge);
	}
	/**
	 * ändert den Namen des naktuellen Lagers
	 * @param neuerName neuer Name
	 */
	public void aendereName(String neuerName) {
		lVModel.umbenennenLager(aktuellesLager, neuerName);
	}
	/**
	 * löscht das aktuelle Lager
	 */
	public void loescheLager() {
		lVModel.loesschenLager(aktuellesLager);
	}
	/**
	 * erstellt ein neues Unterlager
	 * @param lagerName name des neuen Lagers
	 * @param lagerKapazitaet Kapazität des neuen Lagers
	 * @param oberLager gibt an ob ein Lager ObersterEbene erzeugt werden soll
	 */
	public void erstelleUnterLager(String lagerName, int lagerKapazitaet, boolean oberLager) {
		if (!oberLager) {
			lVModel.hinzufuegenLager(aktuellesLager, lagerKapazitaet, lagerName);
		} else {
			lVModel.hinzufuegenLager(null, lagerKapazitaet, lagerName);
		}
		
	}
	/**
	 * bucht die mitgegebene Menge auf das aktuelle Lager
	 * @param menge menge des Anteils
	 */
	public void bucheAnteil(int menge) {
		Command command = new AnteilCommand(redoStack,lVModel,aktuellesLager,menge);
		command.execute();
		undoStack.push(command);
		setChanged();
		notifyObservers();
	}
	/**
	 * speichert das momentane Modell
	 */
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
	/**
	 * lädt ein LagerModell
	 */
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
				LagerVerwaltungsModel lVModel=(LagerVerwaltungsModel)ois.readObject();
				lVModel.addObserver(view);
				view.neuesModel();
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
