package model;

import java.util.Date;
import java.util.List;
import java.util.Vector;

public class LagerVerwaltungsModel {
	List<LagerModel> lager;
	List<BuchungsModel> buchungen;
	int maxFreieKapazitaet;
	BuchungsModel laufendeBuchung;
	
	/**
	 * Konstruktor für ein LagerVerwaltungsModel
	 */
	public LagerVerwaltungsModel(){
		lager = new Vector<LagerModel>();
		buchungen = new Vector<BuchungsModel>();
		this.maxFreieKapazitaet = 0;
		this.laufendeBuchung = null;
	}
	
	/**
	 * benennt das Lager um
	 * @param lager	Lager, das umbenannt wird
	 * @param name	neuer Name des Lagers
	 */
	public void lagerUmbenennen(LagerModel lager, String name){
		lager.setName(name);
	}
	
	/**
	 * erstellt eine neue Buchung und setzt diese als laufende Buchung, wenn kein Buchung läuft
	 * @param buchungsTag 	Zeitpunkt der Buchung
	 * @param menge			Menge der Buchung
	 * @return boolean		true wenn eine Buchung erstellt wurde sonst false
	 */
	public boolean buchungErstellen(Date buchungsTag, int menge){
		if(laufendeBuchung == null) {
			laufendeBuchung = new BuchungsModel(buchungsTag, menge);
			return true;
		}
		return false;
	}
	
	/**
	 * schließt die laufende Buchung ab, wenn diese Voll verteilt ist
	 * @return true wenn die Buchung abgeschlossen wurde sonst false
	 */
	public boolean buchungAbschliessen(){
		if(laufendeBuchung.isVollVerteilt()){
			laufendeBuchung = null;
			return true;
		}
		return false;
	}
	
}
