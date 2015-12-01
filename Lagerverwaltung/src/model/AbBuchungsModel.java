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
	 * in Abbuchungen werden die Ateile mit negaiven Anteilen gespeichert
	 * prüft vorher, ob die Menge, die abgebucht werden soll, überhaupt vorhanden ist
	 * @return true wenn Anteil erfolgreich erstellt wurde sonst false
	 */
	public AnteilModel hinzufuegenAnteil(LagerModel lager, int anteil) {
		if (lager.getBestand() >= anteil) {
			AnteilModel anteilModel = new AnteilModel(lager, -anteil);
			anteile.add(anteilModel);
			verteilteMenge += anteilModel.getAnteil();
			return anteilModel;
		}
		return null;
	}
	
	/**
	 * ruft die Methoden der SuperClasse auf mit dem veränderten Wert
	 */
	public void loeschenAnteil(LagerModel lager, int menge) {
		super.loeschenAnteil(lager, -menge);
	}

	//getter
	/**
	 * eine Abuchung ist dann fertig wenn sie mindestens einen Anteil enthält
	 */
	public boolean isFertig() {
		return !this.anteile.isEmpty();
	}

}
