package model;

import java.io.Serializable;

public class AnteilModel implements Serializable {
	int anteil;
	LagerModel lager;

	//Konstruktor
	/**
	 * Konstruktor f�r ein AnteilModel
	 * speichert einen Anteil an der gesamten Buchung und das zugeh�rige Lager
	 * @param lager in das der Anteil kommt
	 * @param anteil an der Buchung 
	 */
	public AnteilModel(LagerModel lager, int anteil) {
		this.anteil=anteil;
		this.lager=lager;
	}
	
	//Methoden
	/**
	 * erh�ht den Anteil an der Buchung
	 * @return zusatz an der Buchung
	 */
	public void erhoehenAnteil(int zusatz) {
		this.anteil += zusatz;
	}

	//getter
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
	
}
