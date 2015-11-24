package model;

import java.util.Date;
import java.util.List;

public class BuchungsModel {
	Date buchungsTag;
	int menge;
	List<AnteilModel> anteile;
	
	/**
	 * gibt den Buchungstag zurück, an dem die Buchung durchgeführt wurde
	 * @return Buchungstag
	 */
	public Date getBuchungsTag() {
		return buchungsTag;
	}
	
	/**
	 * gibt die Menge der Buchung zurück
	 * @return Menge
	 */
	public int getMenge() {
		return menge;
	}
	
	/**
	 * Gibt die Liste der Anteile auf die die Buchung aufgeteilt wurde wieder
	 * @return Liste der Anteile
	 */
	public List<AnteilModel> getAnteile() {
		return anteile;
	}
	
	/**
	 * Konstruktor für eine Buchung, die den Buchungstag, die Menge und die Aufteilung der Buchung anlegt
	 * @param buchungsTag Tag der Buchung
	 * @param menge Menge der Buchung
	 * @param anteile Liste der Anteile
	 */
	public BuchungsModel(Date buchungsTag, int menge, List<AnteilModel> anteile){
		this.buchungsTag=buchungsTag;
		this.menge=menge;
		this.anteile=anteile;
	}
	/**
	 * Konstruktor für eine Buchung, die den Buchungstag und die Menge anlegt
	 * @param buchungsTag Tag der Buchung
	 * @param menge Menge der Buchung
	 */
	public BuchungsModel(Date buchungsTag, int menge){
		this.buchungsTag=buchungsTag;
		this.menge=menge;
	}
	
	/**
	 * fügt der Liste der Anteile einen neuen eintrag mit den übergebenen Anteil und LagerID hinzu
	 * @param prozent Prozentsatz der gesamten Buchung
	 * @param lagerID LagerID des Lagers auf das der Anteil gebucht wurde
	 */
	public void anteilHinzufügen(int anteil, LagerModel lager){
		anteile.add(new AnteilModel(anteil, lager));
	}
	
	/**
	 * Die Aufteilung der Buchung wird gelöscht
	 */
	public void anteileLoeschen() {
		anteile.clear();
	}
}
