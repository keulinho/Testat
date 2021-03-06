package model;

import java.util.Date;
/**@author Niklas Devenish
**/
public class AbBuchungsModel extends BuchungsModel {

	//Konstruktor
	public AbBuchungsModel(Date buchungsTag) {
		super(buchungsTag);
	}
	
	//Methoden
	/**
	 * f�gt der Buchung einen Anteil hinzu
	 * in Abbuchungen werden die Ateile mit negaiven Anteilen gespeichert
	 * pr�ft vorher, ob die Menge, die abgebucht werden soll, �berhaupt vorhanden ist
	 * @return true wenn Anteil erfolgreich erstellt wurde sonst false
	 */
	public AnteilModel hinzufuegenAnteil(LagerModel lager, int anteil) {
		if (lager.getBestand() >= anteil) {
			AnteilModel anteilModel = new AnteilModel(lager, -anteil);
			anteile.add(anteilModel);
			verteilteMenge += anteilModel.getAnteil();
			lager.aendernVerteilteMenge(-anteil);
			return anteilModel;
		}
		return null;
	}
	
	/**
	 * ruft die Methoden der SuperClasse auf mit dem ver�nderten Wert
	 */
	public void loeschenAnteil(LagerModel lager, int menge) {
		super.loeschenAnteil(lager, -menge);
	}

	public void loeschenAnteil(AnteilModel anteil) {
		verteilteMenge -= anteil.getAnteil();
		anteile.remove(anteil);
	}

	//getter
	/**
	 * eine Abuchung ist dann fertig wenn sie mindestens einen Anteil enth�lt
	 */
	public boolean isFertig() {
		return !this.anteile.isEmpty();
	}

	public int getVerteilbareMenge(LagerModel lager) {
		return lager.getBestand()+lager.getVerteilteMenge();
	}

}
