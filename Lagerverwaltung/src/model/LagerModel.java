package model;

import java.util.List;
import java.util.Vector;

/**
 * 
 * Diese Klasse stellt die Daten f�r ein Lager bereit und verarbeitet diese
 */
public class LagerModel {
	int maxKapazitaet, bestand;
	String name;
	List<LagerModel> unterLager;
	LagerModel oberLager;
	
	/**
	 * Konstruktor f�r ein Lager
	 * @param maxKapazit�t 	maximale Kapazit�t
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
	 * ver�ndert den aktuellen Bestand um den angegeben Wert
	 * @param menge absolute Bestandsver�nderung
	 */
	public void bestandVer�ndern(int menge) {
		this.bestand += menge;
	}
	
	/**
	 * Gibt den Namen des Lagers zur�ck
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
		} else {
			return true;
		}
	}
}
