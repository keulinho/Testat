package model;

import java.util.Date;
import java.util.List;

public class BuchungsModel {
	Date buchungsTag;
	int menge, verteilteMenge;
	List<AnteilModel> anteile;
	
	//Kunstruktoren
	/**
	 * Konstruktor für eine Buchung, die den Buchungstag und die Menge anlegt
	 * @param buchungsTag 	Tag der Buchung
	 * @param menge 		Menge der Buchung
	 */
	public BuchungsModel(Date buchungsTag, int menge){
		this.buchungsTag=buchungsTag;
		this.menge=menge;
	}
	
	//Methoden
	/**
	 * fügt der Buchung einen Anteil hinzu
	 * @param lager		Lager an das der Anteil geht
	 * @param anteil	absoluter Anteil an der Buchung
	 */
	public void anteilHinzufuegen(LagerModel lager, int anteil){
		AnteilModel anteilModel = new AnteilModel(lager, anteil);
		anteile.add(anteilModel);
		verteilteMenge += anteilModel.getAnteil();
	}
	
	/**
	 * Die Aufteilung der Buchung wird gelöscht
	 */
	public void anteileLoeschen() {
		anteile.clear();
		verteilteMenge = 0;
	}
	
	/**
	 * Löscht alle Anteile von einem Lager
	 * @param lager	Lager, dessen Anteil gelöscht werden soll
	 */
	public void anteilLoeschen(LagerModel lager) {
		for (AnteilModel anteil : anteile) {
			if(lager == anteil.getLager()){
				anteile.remove(anteile.indexOf(anteil));
				verteilteMenge -= anteil.getAnteil();
			}
		}
	}
	
	//TODO löschen eines bestimmten Anteils(über Lager und Menge) z.B. anteilLoeschen(LagerModel lager, int menge)
	// und des Commands(auch über Lager und Menge nutzt funktion s. anteilLoeschen(lager, menge)); löschen des RedoStacks
	/**
	 * löscht den ersten Anteil, der ein bestimmtes Lager und eine bestimmte Menge beinhaltet
	 * @param lager das in dem Anteil entalten sein soll
	 * @param menge	die in dem Anteil sein soll
	 */
	public void anteilLoeschen(LagerModel lager, int menge) {
		for (AnteilModel anteil : anteile) {
			if(anteil.getLager() == lager && anteil.getAnteil() == menge){
				anteile.remove(anteile.indexOf(anteil));
				verteilteMenge -= anteil.getAnteil();
				break;
			}
		}
	}
	
	
	//getter
	/**
	 * gibt den Buchungstag zurück, an dem die Buchung durchgeführt wurde
	 * @return Buchungstag
	 */
	public Date getBuchungsTag() {
		return this.buchungsTag;
	}
	
	/**
	 * gibt die Menge der Buchung zurück
	 * @return Menge
	 */
	public int getMenge() {
		return this.menge;
	}
	
	/**
	 * gibt die restliche zu verteilende Menge zurück
	 * @return Menge, die noch zu verteilen ist
	 */
	public int getFreienPlatz(){
		return this.menge - this.verteilteMenge;
	}
	
	/**
	 * gibt zurück ob eine Buchung voll verteilt ist und somit bereit zum Abschluss
	 * @ return true wenn die Menge einer Buchung voll verteilt ist
	 */
	public boolean isVollVerteilt(){
		if(menge == verteilteMenge) {
			return true;
		}
		return false;
	}
	
	/**
	 * Gibt die Liste der Anteile auf die die Buchung aufgeteilt wurde wieder
	 * @return Liste der Anteile
	 */
	public List<AnteilModel> getAnteile() {
		return this.anteile;
	}
}
