package controller;

import java.util.Date;

import model.LagerModel;
import model.LagerVerwaltungsModel;
import view.VerwaltungsView;

public class LagerVerwaltungsController {
	
	VerwaltungsView view;
	LagerVerwaltungsModel lVModel;
	LagerModel aktuellesLager;
	
	public static void main(String... args){
		LagerVerwaltungsController control = new LagerVerwaltungsController();
	}
	
	public LagerVerwaltungsController() {
		view=new VerwaltungsView(this);
		lVModel=new LagerVerwaltungsModel();
		lVModel.addObserver(view);
	}
	
	public void undo() {
		
	}
	
	public void redo() {
		
	}
	
	public void buchungVerwerfen() {

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
}
