package controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Observer;

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
		lVModel.erstellenZuBuchung(new Date(), gesamtMenge);
	}
	
	public void aendereName(String neuerName) {
		lVModel.umbenennenLager(aktuellesLager, neuerName);
	}
	
	public void loescheLager() {
		lVModel.loesschenLager(aktuellesLager);
	}
	
	public void erstelleUnterLager(String lagerName, int lagerKapazitaet) {
		lVModel.hinzufuegenLager(aktuellesLager, lagerKapazitaet, lagerName);
	}
	
	public void bucheAnteil(int menge) {
		lVModel.hinzugegenAnteil(aktuellesLager, menge);
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
