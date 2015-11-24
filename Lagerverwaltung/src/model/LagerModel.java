package model;

import java.util.List;

/**
 * 
 * @author jonas
 * Diese Klasse stellt die Daten für ein Lager bereit und verarbeitet diese
 */
public class LagerModel {
	int maxKapazität, bestand;
	String name;
	List<LagerModel> unterLager;
	LagerModel oberLager;
	
	/**
	 * Konstruktor für ein Lager
	 * @param maxKapazität maximale Kapazität
	 * @param name Name des Lagers
	 */
	public LagerModel(int maxKapazität, String name, LagerModel oberLager) {
		this.oberLager=oberLager;
		this.maxKapazität=maxKapazität;
		this.bestand=0;
		this.name=name;
	}
	/**
	 * gibt die maximale Kapazität des Lagers zurück
	 * @return maximale Kapazität
	 */
	public int getMaxKapazität() {
		return maxKapazität;
	}
	
	/**
	 * gibt den aktuellen Bestand des Lagers zurück
	 * @return aktueller Bestand
	 */
	public int getBestand() {
		return bestand;
	}
	
	/**
	 * verändert den aktuellen Bestand um den angegeben Wert
	 * @param bestand absolute Bestandsveränderung
	 */
	public void bestandVerändern(int bestand) {
		this.bestand += bestand;
	}
	
	/**
	 * Gibt den Namen des Lagers zurück
	 * @return Name des Lagers
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * gibt alle Unterlager als Liste zurück
	 * @return Liste der Unterlager oder null
	 */
	public List<LagerModel> getUnterLager() {
		return unterLager;
	}

	/**
	 * Gibt zurück ob das Lager UnterLager hat
	 * @return true wenn Unterlager existieren sonst false
	 */
	public boolean hatUnterlager(){
		if ((unterLager==null)||unterLager.isEmpty()) {
			return true;
		}
		else return false;
	}
	
	/**
	 * gibt das Oberlager zurück
	 * @return Oberlager oder null
	 */
	public LagerModel getOberLager() {
		return oberLager;
	}
	
	/**
	 * gibt zurück ob das Lager Oberlager hat
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
