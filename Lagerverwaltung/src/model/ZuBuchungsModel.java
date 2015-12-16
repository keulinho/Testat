package model;

import java.util.Date;

public class ZuBuchungsModel extends BuchungsModel {
	int menge;
	
	//Konstruktor
	/**
	 * Konstruktor für eine Zubuchung, die den Buchungstag und die Menge enthält
	 * @param buchungsTag 	Tag der Buchung
	 * @param menge 		Menge der Buchung
	 */
	public ZuBuchungsModel(Date buchungsTag, int menge){
		super(buchungsTag);
		this.menge=menge;
	}

	//Methoden
	/**
	 * fügt der Buchung einen Anteil hinzu
	 * prüft vorher ob im Lager noch genug Platz vorhanden ist
	 * @return true wenn Anteil erfolgreich erstellt wurde sonst false
	 */
	public AnteilModel hinzufuegenAnteil(LagerModel lager, int anteil) {
		if(anteil <= this.getFreienPlatz()){
			AnteilModel anteilModel = new AnteilModel(lager, anteil);
			anteile.add(anteilModel);
			verteilteMenge += anteilModel.getAnteil();
			lager.aendernVerteilteMenge(anteil);
			return anteilModel;
		}
		return null;
	}

	//TODO JAVADOC
	public void loeschenAnteil(AnteilModel anteil) {
		//Bestand ändern
		setMenge(getMenge()-anteil.getAnteil());
		//Anteil löschen
		anteile.remove(anteil);
	}
	
	//setter
	public void setMenge(int menge){
		this.menge = menge;
	}

	//getter
	/**
	 * gibt die noch zu verteilende Menge einer Buchung zurück
	 * @return int Platz in der Buchung
	 */
	private int getFreienPlatz() {
		return menge - verteilteMenge;
	}
	
	/**
	 * gibt die Menge der Buchung zurück
	 * nur in Zubuchung
	 * @return Menge
	 */
	public int getMenge() {
		return this.menge;
	}

	/**
	 * ist bereit abgeschlossen zu werden, wenn die Menge voll verteilt ist
	 */
	public boolean isFertig() {
		if(menge == verteilteMenge) {
			return true;
		}
		return false;
	}
	
	public int getVerteilbareMenge(LagerModel lager){
		return lager.getFreienPlatz()-lager.getVerteilteMenge();
	}

}
