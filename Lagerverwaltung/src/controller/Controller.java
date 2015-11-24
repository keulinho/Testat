package controller;

import view.LagerView;

public class Controller {
	
	public static void main(String... args){
		Controller control = new Controller(10);
	}
	
	public Controller(int lageranzahl) {
		LagerView view=new LagerView(lageranzahl,this);
	}
}
