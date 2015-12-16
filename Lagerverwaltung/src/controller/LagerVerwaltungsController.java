package controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Observer;

import core.exception.ErrorHandler;
import core.exception.LagerNichtLoeschbarException;
import core.exception.MaxFreieKapazitaetUeberschritten;
import model.LagerModel;
import model.LagerVerwaltungsModel;
import view.LagerBaumKnoten;
import view.VerwaltungsView;

public class LagerVerwaltungsController {
	
	VerwaltungsView view;
	LagerVerwaltungsModel lVModel;
	LagerModel aktuellesLager;
	HashMap knotenZuLagerModel;
	
	public static void main(String... args){
		LagerVerwaltungsController control = new LagerVerwaltungsController();
	}
	
	public LagerVerwaltungsController() {
		knotenZuLagerModel=new HashMap<LagerBaumKnoten,LagerModel>();
		view=new VerwaltungsView(this);
		lVModel=new LagerVerwaltungsModel();
		lVModel.addObserver(view);
		
		aktuellesLager=lVModel.getLager().get(0);
		aktuellesLager.addObserver(view.getDetailPane());
	}
	
	public void undo() {
		
	}
	
	public void redo() {
		
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
		try {
			lVModel.erstellenZuBuchung(new Date(), gesamtMenge);
		} catch (MaxFreieKapazitaetUeberschritten e) {
			ErrorHandler.HandleException(ErrorHandler.MAX_FREIE_KAPAZITAET_UEBERSCHRITTEN, e);
		}
	}
	
	public void aendereName(String neuerName) {
		lVModel.umbenennenLager(aktuellesLager, neuerName);
	}
	
	
	public void loescheLager() {
		try {
			lVModel.loesschenLager(aktuellesLager);
		} catch (LagerNichtLoeschbarException e) {
			ErrorHandler.HandleException(ErrorHandler.LAGER_IST_NICHT_LEER, e);
		}
	}
	
	public void erstelleUnterLager(String lagerName, int lagerKapazitaet) {
		lVModel.hinzufuegenLager(aktuellesLager, lagerKapazitaet, lagerName);
	}
	
	public void bucheAnteil(int menge) {
		lVModel.hinzufuegenAnteil(aktuellesLager, menge);
	}
	
	public void speichern() {
		
	}
	
	public void laden() {
		
	}
	
	public void aktuellesLagerAendern(LagerBaumKnoten lBKnoten) {
		aktuellesLager.deleteObserver(view.getDetailPane());
		aktuellesLager=(LagerModel) knotenZuLagerModel.get(lBKnoten);
		aktuellesLager.addObserver(view.getDetailPane());
	}
	
	public void knotenLagerZuordnungAktualiseren(LagerBaumKnoten lBKnoten,LagerModel lModel) {
		knotenZuLagerModel.put(lBKnoten, lModel);
		
	}
}
