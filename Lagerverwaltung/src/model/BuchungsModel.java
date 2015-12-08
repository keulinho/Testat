package model;

import java.util.Date;
import java.util.List;
import java.util.Vector;

abstract public class BuchungsModel {
	Date buchungsTag;
	int verteilteMenge;
	List<AnteilModel> anteile;
	
	//Konstruktor
	public BuchungsModel(Date buchungsTag) {
		this.buchungsTag = buchungsTag;
		this.anteile = new Vector<AnteilModel>();
	}

	//abstract Methoden
	/**
	 * gibt  zur�ck, ob eine Buchung bereit ist abgeschlossen zu werden
	 * @ return true wenn die Menge einer Buchung voll verteilt ist
	 */
	abstract public boolean isFertig();
	
	/**
	 * f�gt der Buchung einen Anteil hinzu
	 * @param lager		Lager an das der Anteil geht
	 * @param anteil	absoluter Anteil an der Buchung
	 */
	abstract public AnteilModel hinzufuegenAnteil(LagerModel lager, int anteil);
	
	//Methoden
	
	/**
	 * L�scht alle Anteile der Buchung
	 */
	public void loeschenAlleAnteile() {
		anteile.clear();
		verteilteMenge = 0;
	}
	
	/**
	 * L�scht alle Anteile von einem Lager
	 * @param lager dessen Anteil gel�scht werden soll
	 */
	public void loeschenAnteile(LagerModel lager) {
		for (AnteilModel anteil : anteile) {
			if(lager == anteil.getLager()){
				anteile.remove(anteile.indexOf(anteil));
				verteilteMenge -= anteil.getAnteil();
			}
		}
	}
	
	//TODO l�schen eines bestimmten Anteils(�ber Lager und Menge) z.B. anteilLoeschen(LagerModel lager, int menge)
	// und des Commands(auch �ber Lager und Menge nutzt funktion s. anteilLoeschen(lager, menge)); l�schen des RedoStacks
	/**
	 * l�scht den ersten Anteil, der ein bestimmtes Lager und eine bestimmte Menge beinhaltet
	 * @param lager das in dem Anteil entalten sein soll
	 * @param menge	die in dem Anteil sein soll
	 */
	public void loeschenAnteil(LagerModel lager, int menge) {
		for (AnteilModel anteil : anteile) {
			if(anteil.getLager() == lager && anteil.getAnteil() == menge){
				anteile.remove(anteile.indexOf(anteil));
				verteilteMenge -= anteil.getAnteil();
				break;
			}
		}
	}
	
	//getter
	/**
	 * gibt den Buchungstag zur�ck, an dem die Buchung durchgef�hrt wurde
	 * @return Buchungstag
	 */
	public Date getBuchungsTag() {
		return this.buchungsTag;
	}
	
	/**
	 * Gibt die Liste der Anteile auf die die Buchung aufgeteilt wurde wieder
	 * @return Liste der Anteile
	 */
	public List<AnteilModel> getAnteile() {
		return this.anteile;
	}
	
	/**
	 * Gibt die verteilte Menge einer Zubuchung
	 * bzw. die verteilte Menge einer Abbuchung
	 * @return
	 */
	public int getVerteilteMenge(){
		return this.verteilteMenge;
	}
}
