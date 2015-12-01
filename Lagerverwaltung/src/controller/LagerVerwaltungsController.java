package controller;

import view.VerwaltungsView;

public class LagerVerwaltungsController {
	
	public static void main(String... args){
		LagerVerwaltungsController control = new LagerVerwaltungsController(10);
	}
	
	public LagerVerwaltungsController(int lageranzahl) {
		VerwaltungsView view=new VerwaltungsView(this);
	}
	
	public void undo() {
		
	}
	
	public void redo() {
		
	}
	
	public void buchungVerwerfen() {
		
	}
	
	public void buchungAbschliessen() {
		
	}
	
	public void auslieferungErstellen() {
		
	}
	
	public void zulieferungErstellen(int gesamtMenge) {
		
	}
	
	public void aendereName(String neuerName) {
		
	}
	
	public void loescheLager() {
		
	}
	
	public void erstelleUnterLager() {
		
	}
	
	public void bucheAnteil() {
		
	}
	
	public void speichern() {
		
	}
	
	public void laden() {
		
	}
}
