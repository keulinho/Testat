package model;

import java.util.List;

/**
 * 
 * @author jonas
 * Diese Klasse stellt die Daten f�r ein Lager bereit und verarbeitet diese
 */
public class LagerModel {
	int maxKapazit�t, bestand;
	String name;
	List<LagerModel> unterLager;
	LagerModel oberLager;
	
	/**
	 * Konstruktor f�r ein Lager
	 * @param maxKapazit�t maximale Kapazit�t
	 * @param name Name des Lagers
	 */
	public LagerModel(int maxKapazit�t, String name, LagerModel oberLager) {
		this.oberLager=oberLager;
		this.maxKapazit�t=maxKapazit�t;
		this.bestand=0;
		this.name=name;
	}
	/**
	 * gibt die maximale Kapazit�t des Lagers zur�ck
	 * @return maximale Kapazit�t
	 */
	public int getMaxKapazit�t() {
		return maxKapazit�t;
	}
	
	/**
	 * gibt den aktuellen Bestand des Lagers zur�ck
	 * @return aktueller Bestand
	 */
	public int getBestand() {
		return bestand;
	}
	
	/**
	 * ver�ndert den aktuellen Bestand um den angegeben Wert
	 * @param bestand absolute Bestandsver�nderung
	 */
	public void bestandVer�ndern(int bestand) {
		this.bestand += bestand;
	}
	
	/**
	 * Gibt den Namen des Lagers zur�ck
	 * @return Name des Lagers
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * gibt alle Unterlager als Liste zur�ck
	 * @return Liste der Unterlager oder null
	 */
	public List<LagerModel> getUnterLager() {
		return unterLager;
	}

	/**
	 * Gibt zur�ck ob das Lager UnterLager hat
	 * @return true wenn Unterlager existieren sonst false
	 */
	public boolean hatUnterlager(){
		if ((unterLager==null)||unterLager.isEmpty()) {
			return true;
		}
		else return false;
	}
	
	/**
	 * gibt das Oberlager zur�ck
	 * @return Oberlager oder null
	 */
	public LagerModel getOberLager() {
		return oberLager;
	}
	
	/**
	 * gibt zur�ck ob das Lager Oberlager hat
	 * @return true falls das Lager Oberlager hat sonst false
	 */
	public boolean hatOberLager() {
		if (oberLager==null) {
			return false;
		} else {
			return true;
		}
	}
}
