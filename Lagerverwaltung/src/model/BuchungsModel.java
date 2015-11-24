package model;

import java.util.Date;
import java.util.List;

public class BuchungsModel {
	Date buchungsTag;
	int menge;
	List<AnteilModel> anteile;
	
	/**
	 * gibt den Buchungstag zur�ck, an dem die Buchung durchgef�hrt wurde
	 * @return Buchungstag
	 */
	public Date getBuchungsTag() {
		return buchungsTag;
	}
	
	/**
	 * gibt die Menge der Buchung zur�ck
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
	 * Konstruktor f�r eine Buchung, die den Buchungstag, die Menge und die Aufteilung der Buchung anlegt
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
	 * Konstruktor f�r eine Buchung, die den Buchungstag und die Menge anlegt
	 * @param buchungsTag Tag der Buchung
	 * @param menge Menge der Buchung
	 */
	public BuchungsModel(Date buchungsTag, int menge){
		this.buchungsTag=buchungsTag;
		this.menge=menge;
	}
	
	/**
	 * f�gt der Liste der Anteile einen neuen eintrag mit den �bergebenen Anteil und LagerID hinzu
	 * @param prozent Prozentsatz der gesamten Buchung
	 * @param lagerID LagerID des Lagers auf das der Anteil gebucht wurde
	 */
	public void anteilHinzuf�gen(int anteil, LagerModel lager){
		anteile.add(new AnteilModel(anteil, lager));
	}
	
	/**
	 * Die Aufteilung der Buchung wird gel�scht
	 */
	public void anteileLoeschen() {
		anteile.clear();
	}
}
