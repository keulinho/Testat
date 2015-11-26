package model;

import java.util.Date;

public class AbBuchungsModel extends BuchungsModel {

	//Konstruktor
	public AbBuchungsModel(Date buchungsTag) {
		super(buchungsTag);
	}

	//Methoden
	/**
	 * fügt der Buchung einen Anteil hinzu
	 * prüft vorher, ob die Menge, die abgebucht werden soll, überhaupt vorhanden ist
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
	 * eine Zubuchung enthält keine spezifische Menge und ist zu jeder Zeit abschließbar
	 */
	public boolean isFertig() {
		return true;
	}

}
