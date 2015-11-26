package model;

import java.util.Date;

public class AbBuchungsModel extends BuchungsModel {

	//Konstruktor
	public AbBuchungsModel(Date buchungsTag) {
		super(buchungsTag);
	}

	//Methoden
	/**
	 * f�gt der Buchung einen Anteil hinzu
	 * pr�ft vorher, ob die Menge, die abgebucht werden soll, �berhaupt vorhanden ist
	 * @return true wenn Anteil erfolgreich erstellt wurde sonst false
	 */
	public boolean hinzufuegenAnteil(LagerModel lager, int anteil) {
		if (lager.getBestand() >= anteil) {
			AnteilModel anteilModel = new AnteilModel(lager, anteil);
			anteile.add(anteilModel);
			verteilteMenge += anteilModel.getAnteil();
			return true;
		}
		return false;
	}

	//getter
	/**
	 * eine Zubuchung enth�lt keine spezifische Menge und ist zu jeder Zeit abschlie�bar
	 */
	public boolean isFertig() {
		return true;
	}

}
