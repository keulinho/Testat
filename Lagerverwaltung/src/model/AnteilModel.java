package model;

public class AnteilModel {
	int anteil;
	LagerModel lager;
	
	/**
	 * gibt den Prozentsatz, der auf das angegebene Lager gebucht wurde, wieder
	 * @return Anteil der Buchung
	 */
	public int getAnteil() {
		return this.anteil;
	}

	/**
	 * gibt das Lager wieder auf die der angegebene Anteil gebucht wurde
	 * @return Lager des Anteils
	 */
	public LagerModel getLager() {
		return this.lager;
	}

	/**
	 * Konstruktor für ein AnteilModel
	 * speichert einen Prozentsatz der gesamten Buchung und das zugehörige Lager
	 * @param prozent Prozentsatz 
	 * @param lagerID LagerID 
	 */
	public AnteilModel(LagerModel lager, int anteil) {
		this.anteil=anteil;
		this.lager=lager;
	}
	
}
