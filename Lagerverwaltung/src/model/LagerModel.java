package model;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import core.exception.LagerUeberfuelltException;

/**
 * Diese Klasse stellt die Daten für ein Lager bereit und verarbeitet diese
 */
public class LagerModel extends Observable implements Serializable {
	int maxKapazitaet, bestand, verteilteMenge; //TODO verteilbare Menge implementieren
	String name;
	List<LagerModel> unterLager;
	LagerModel oberLager;
	List<BuchungsModel> buchungen;
	

	//Konstruktor
	/**
	 * Konstruktor für ein Lager
	 * @param maxKapazität 	maximale Kapazität
	 * @param name 			Name des Lagers
	 */
	public LagerModel(int maxKapazitaet, String name, LagerModel oberLager) {
		this.oberLager=oberLager;
		this.maxKapazitaet=maxKapazitaet;
		this.name=name;
		this.unterLager = new Vector<LagerModel>();
		this.buchungen = new Vector<BuchungsModel>();
		if(oberLager != null){
			this.bestand = oberLager.getBestand();
		} else {
			this.bestand=0;
		}
		verteilteMenge = 0;
	}
	
	//Methoden
	/**
	 * fügt eine Element der GUI als Obsever hinzu und füllt das Element initial mit den richtigen Werten
	 * @param o GUI-Element
	 */
	public void addObserver(Observer o){
		super.addObserver(o);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * verändert den aktuellen Bestand um den angegeben Wert
	 * der sich ergebende Wert muss: 0 <= neuer Bestand <= maxKapazität
	 * und gibt die Menge an die Oberlager weiter
	 * @param menge absolute Bestandsveränderung
	 * @throws LagerUeberfuelltException 
	 */
	public void veraendernBestand(int menge) throws LagerUeberfuelltException{
		if( (this.bestand + menge) < 0 || this.maxKapazitaet < (this.bestand + menge)){
			throw new LagerUeberfuelltException(this.name + " ist überfüllt.\n"
					+ "Kapazität: \t" + this.bestand + "\nBestand: \t" + (this.bestand += menge));
		}
		this.bestand += menge;
		this.setChanged();
		this.notifyObservers();
		if(this.oberLager != null){
			this.anpassenOberlagerBestand(menge);
		}
	}
	
	/**
	 * passt den Bestand in allen über einem Lager an
	 * @param menge die auf den Bestand addiert wird
	 * @throws LagerUeberfuelltException 
	 */
	public void anpassenOberlagerBestand(int menge) throws LagerUeberfuelltException{
		if (this.oberLager != null) {
			this.oberLager.veraendernBestand(menge);
		}
	}
	
	/**
	 * ändert die maxKapazität um den mitgegebenen Wert
	 * @param aenderung
	 */
	public void aendernKapazitaet(int aenderung){
		this.maxKapazitaet += aenderung;
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * ändert die maxKapazität um den mitgegebenen Wert
	 * @param aenderung
	 */
	public void aendernOberlagerKapazitaet(int aenderung){
		if (this.oberLager != null) {
			this.oberLager.aendernOberlagerKapazitaet(aenderung);
			this.oberLager.aendernKapazitaet(aenderung);
		}
	}
	
	/**
	 * erzeugt ein neues Lager und fügt es dem Oberlafer hinzu
	 * und gibt das erzeugte Lafer zurück
	 * @param kapazitaet	des neuen Lagers
	 * @param name			des neuen Lagers
	 * @param oberLager		des neuen Lagers oder null
	 * @return				LagerModel das erzeugt wurde
	 */
	public LagerModel addUnterlager(int kapazitaet, String name){
		LagerModel lager = new LagerModel(kapazitaet, name, this);
		unterLager.add(lager);
		return lager;
	}
	
	/**
	 * fügt eine Buchung zu der Liste der Buchungen hinzu
	 * @param buchung
	 */
	public void addBuchung(BuchungsModel buchung){
		//TODO evtl. Logik hierhin verschieben, wenn Buchung eingeht, wird hier alles gebucht
		buchungen.add(buchung);
	}
	
	/**
	 * entfernt die übergebene Buchung aus der Liste der Buchungen des Lagers
	 * @param buchung die gelöscht werden soll
	 */
	public void entfernenBuchung(BuchungsModel buchung){
		buchungen.remove(buchung);
	}
	
	/**
	 * ändert die verteilte um den mitgegebenen Wert
	 * @param menge int
	 */
	public void aendernVerteilteMenge(int menge){
		verteilteMenge += menge;
	}
	
	/**
	 * setzt die verteilte Menge auf 0 zurück
	 */
	public void resetVerteilteMenge(){
		verteilteMenge = 0;
	}
	
	//setter
	/**
	 * Setzt den Namen des Lagers
	 * @return Name des Lagers
	 */
	public void setName(String name) {
		this.name = name;
		this.setChanged();
		this.notifyObservers();
	}
	
	//getter
	/**
	 * Gibt den Namen des Lagers zurück
	 * @return Name des Lagers
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * gibt die maximale Kapazität des Lagers zurück
	 * @return maximale Kapazität
	 */
	public int getMaxKapazitaet() {
		return this.maxKapazitaet;
	}
	
	/**
	 * gibt den aktuellen Bestand des Lagers zurück
	 * @return aktueller Bestand
	 */
	public int getBestand() {
		return this.bestand;
	}
	
	public int getVerteilteMenge(){
		return this.verteilteMenge;
	}
	
	/**
	 * gibt eine Liste aller Unterlager zurück
	 * @return Liste der Unterlager oder null
	 */
	public List<LagerModel> getUnterLager() {
		return this.unterLager;
	}
	
	/**
	 * gibt das Oberlager zurück
	 * @return Oberlager oder null
	 */
	public LagerModel getOberLager() {
		return this.oberLager;
	}
	
	/**
	 * gibt eine Liste aller Buchungen zurück an denen das Lager beteiligt war
	 * @return
	 */
	public List<BuchungsModel> getBuchungen() {
		return buchungen;
	}

	/**
	 * Gibt zurück ob das Lager UnterLager hat
	 * @return true wenn Unterlager existieren sonst false
	 */
	public boolean hatUnterlager(){
		if (unterLager.isEmpty()) {
			return false;
		}
		return true;
	}
	
	/**
	 * gibt zurück ob das Lager Oberlager hat
	 * @return true falls das Lager Oberlager hat sonst false
	 */
	public boolean hatOberLager() {
		if (oberLager == null) {
			return false;
		}
		return true;
	}
	
	public boolean isUntersteEbene(){
		return this.unterLager.isEmpty();
	}
	
	public int getFreienPlatz(){
		return maxKapazitaet-bestand;
	}
}
