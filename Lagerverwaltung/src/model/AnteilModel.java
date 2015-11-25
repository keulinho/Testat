package model;

public class AnteilModel {
	int anteil;
	LagerModel lager;

	//Konstruktor
	/**
	 * Konstruktor für ein AnteilModel
	 * speichert einen Anteil an der gesamten Buchung und das zugehörige Lager
	 * @param lager in das der Anteil kommt
	 * @param anteil an der Buchung 
	 */
	public AnteilModel(LagerModel lager, int anteil) {
		this.anteil=anteil;
		this.lager=lager;
	}
	
	//Methoden
	/**
	 * erhöht den Anteil an der Buchung
	 * @return zusatz an der Buchung
	 */
	public void anteilErhoehen(int zusatz) {
		this.anteil += zusatz;
	}

	//getter
	/**
	 * gibt dAnteil, der auf das angegebene Lager gebucht wurde, wieder
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
	
}
