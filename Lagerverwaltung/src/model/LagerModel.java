package model;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import core.exception.LagerUeberfuelltException;

/**
 * Diese Klasse stellt die Daten f�r ein Lager bereit und verarbeitet diese
 */
public class LagerModel extends Observable {
	int maxKapazitaet, bestand;
	String name;
	List<LagerModel> unterLager;
	LagerModel oberLager;
	List<BuchungsModel> buchungen;
	

	//Konstruktor
	/**
	 * Konstruktor f�r ein Lager
	 * @param maxKapazit�t 	maximale Kapazit�t
	 * @param name 			Name des Lagers
	 */
	public LagerModel(int maxKapazitaet, String name, LagerModel oberLager) {
		this.oberLager=oberLager;
		this.maxKapazitaet=maxKapazitaet;
		this.name=name;
		this.unterLager = new Vector<LagerModel>();
		if(oberLager != null){
			this.bestand = oberLager.getBestand();
		} else {
			this.bestand=0;
		}
	}
	
	//Methoden
	/**
	 * f�gt eine Element der GUI als Obsever hinzu und f�llt das Element initial mit den richtigen Werten
	 * @param o GUI-Element
	 */
	public void addObserver(Observer o){
		super.addObserver(o);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * ver�ndert den aktuellen Bestand um den angegeben Wert
	 * der sich ergebende Wert muss: 0 <= neuer Bestand <= maxKapazit�t
	 * und gibt die Menge an die Oberlager weiter
	 * @param menge absolute Bestandsver�nderung
	 * @throws LagerUeberfuelltException 
	 */
	public void veraendernBestand(int menge) throws LagerUeberfuelltException{
		if(0 <= (this.bestand += menge) || (this.bestand += menge) > this.maxKapazitaet){
			throw new LagerUeberfuelltException(this.name + " ist �berf�llt.\n"
					+ "Kapazit�t: \t" + this.bestand + "\nBestand: \t" + (this.bestand += menge));
		}
		this.bestand += menge;
		this.anpassenOberlagerBestand(menge);
	}
	
	/**
	 * passt den Bestand in allen �ber einem Lager an
	 * @param menge die auf den Bestand addiert wird
	 * @throws LagerUeberfuelltException 
	 */
	public void anpassenOberlagerBestand(int menge) throws LagerUeberfuelltException{
		this.oberLager.veraendernBestand(menge);
		if (this.oberLager != null) {
			this.oberLager.anpassenOberlagerBestand(menge);
		}
	}
	
	/**
	 * f�gt dem Lager ein Unterlager hinzu
	 * @param lager LagerModel, das hinzugef�gt werden soll
	 */
	public void addUnterlager(LagerModel lager){
		unterLager.add(lager);
	}
	
	/**
	 * �ndert die maxKapazit�t um den mitgegebenen Wert
	 * @param steigerung
	 */
	public void aendernkapazitaet(int steigerung){
		this.maxKapazitaet += steigerung;
	}
	
	/**
	 * f�gt eine Buchung zu der Liste der Buchungen hinzu
	 * @param buchung
	 */
	public void addBuchung(BuchungsModel buchung){
		buchungen.add(buchung);
	}
	
	/**
	 * entfernt die �bergebene Buchung aus der Liste der Buchungen des Lagers
	 * @param buchung die gel�scht werden soll
	 */
	public void entfernenBuchung(BuchungsModel buchung){
		buchungen.remove(buchung);
	}
	
	//setter
	/**
	 * Setzt den Namen des Lagers
	 * @return Name des Lagers
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	//getter
	/**
	 * Gibt den Namen des Lagers zur�ck
	 * @return Name des Lagers
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * gibt die maximale Kapazit�t des Lagers zur�ck
	 * @return maximale Kapazit�t
	 */
	public int getMaxKapazitaet() {
		return this.maxKapazitaet;
	}
	
	/**
	 * gibt den aktuellen Bestand des Lagers zur�ck
	 * @return aktueller Bestand
	 */
	public int getBestand() {
		return this.bestand;
	}
	
	/**
	 * gibt eine Liste aller Unterlager zur�ck
	 * @return Liste der Unterlager oder null
	 */
	public List<LagerModel> getUnterLager() {
		return this.unterLager;
	}
	
	/**
	 * gibt das Oberlager zur�ck
	 * @return Oberlager oder null
	 */
	public LagerModel getOberLager() {
		return this.oberLager;
	}
	
	/**
	 * gibt eine Liste aller Buchungen zur�ck an denen das Lager beteiligt war
	 * @return
	 */
	public List<BuchungsModel> getBuchungen() {
		return buchungen;
	}

	/**
	 * Gibt zur�ck ob das Lager UnterLager hat
	 * @return true wenn Unterlager existieren sonst false
	 */
	public boolean hatUnterlager(){
		if (unterLager.isEmpty()) {
			return false;
		}
		return true;
	}
	
	/**
	 * gibt zur�ck ob das Lager Oberlager hat
	 * @return true falls das Lager Oberlager hat sonst false
	 */
	public boolean hatOberLager() {
		if (oberLager == null) {
			return false;
		}
		return true;
	}
}
