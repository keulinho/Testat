package model;

public class AnteilModel {
	int anteil;
	LagerModel lager;
	
	/**
	 * gibt den Prozentsatz der auf das angegebene Lager gebucht wurde wieder
	 * @return Prozentsatz
	 */
	public int getAnteil() {
		return anteil;
	}

	/**
	 * gibt die LagerID wieder auf die der angegebene Anteil gebucht wurde
	 * @return LagerID
	 */
	public LagerModel getLager() {
		return lager;
	}

	/**
	 * Konstruktor für ein AnteilModel
	 * speichert einen Prozentsatz der gesamten Buchung und das zugehörige Lager
	 * @param prozent Prozentsatz 
	 * @param lagerID LagerID 
	 */
	public AnteilModel(int anteil, LagerModel lager) {
		this.anteil=anteil;
		this.lager=lager;
	}
	
}
