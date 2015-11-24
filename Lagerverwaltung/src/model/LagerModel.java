package model;

import java.util.List;
import java.util.Vector;

/**
 * 
 * Diese Klasse stellt die Daten für ein Lager bereit und verarbeitet diese
 */
public class LagerModel {
	int maxKapazitaet, bestand;
	String name;
	List<LagerModel> unterLager;
	LagerModel oberLager;
	
	/**
	 * Konstruktor für ein Lager
	 * @param maxKapazität 	maximale Kapazität
	 * @param name 			Name des Lagers
	 */
	public LagerModel(int maxKapazitaet, String name, LagerModel oberLager) {
		this.oberLager=oberLager;
		this.maxKapazitaet=maxKapazitaet;
		this.bestand=0;
		this.name=name;
		this.unterLager = new Vector<LagerModel>();
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
	
	/**
	 * verändert den aktuellen Bestand um den angegeben Wert
	 * @param menge absolute Bestandsveränderung
	 */
	public void bestandVerändern(int menge) {
		this.bestand += menge;
	}
	
	/**
	 * Gibt den Namen des Lagers zurück
	 * @return Name des Lagers
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Setzt den Namen des Lagers
	 * @return Name des Lagers
	 */
	public void setName(String name) {
		this.name = name;
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
		} else {
			return true;
		}
	}
}
