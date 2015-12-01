package controller;

import view.VerwaltungsView;

public class LagerVerwaltungsController {
	
	public static void main(String... args){
		LagerVerwaltungsController control = new LagerVerwaltungsController(10);
	}
	
	public LagerVerwaltungsController(int lageranzahl) {
		VerwaltungsView view=new VerwaltungsView(this);
	}
}
