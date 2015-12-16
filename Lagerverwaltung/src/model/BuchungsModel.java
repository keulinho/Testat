package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;
/**@author Niklas Devenish
**/
abstract public class BuchungsModel implements Serializable {
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
	 * gibt zurück, ob eine Buchung bereit ist abgeschlossen zu werden
	 * @ return true wenn die Buchung abgeschlossen werden kann
	 */
	abstract public boolean isFertig();
	
	/**
	 * fügt der Buchung einen Anteil hinzu
	 * @param lager		Lager an das der Anteil geht
	 * @param anteil	absoluter Anteil an der Buchung
	 */
	abstract public AnteilModel hinzufuegenAnteil(LagerModel lager, int anteil);
	
	/**
	 * gibt die die verteilbare Menge eines Lagers für die Buchung wieder
	 * @param lager für das die verteilbare Menge ermittelt werden soll
	 * @return int verteilbare Menge
	 */
	abstract public int getVerteilbareMenge(LagerModel lager);
	
	/**
	 * löscht den Anteil aus der Buchung und passt die Buchung an
	 * @param anteil
	 */
	abstract public void loeschenAnteil(AnteilModel anteil);
	
	//Methoden
	/**
	 * Löscht alle Anteile der Buchung
	 */
	public void loeschenAlleAnteile() {
		anteile.clear();
		verteilteMenge = 0;
	}
	
	/**
	 * Löscht alle Anteile von einem Lager
	 * @param lager dessen Anteil gelöscht werden soll
	 */
	public void loeschenAnteile(LagerModel lager) {
		for (AnteilModel anteil : anteile) {
			if(lager == anteil.getLager()){
				anteile.remove(anteile.indexOf(anteil));
				verteilteMenge -= anteil.getAnteil();
				lager.aendernVerteilteMenge(-anteil.getAnteil());
			}
		}
	}
	
	/**
	 * löscht den ersten Anteil, der ein bestimmtes Lager und eine bestimmte Menge beinhaltet
	 * @param lager das in dem Anteil entalten sein soll
	 * @param menge	die in dem Anteil sein soll
	 */
	public void loeschenAnteil(LagerModel lager, int menge) {
		for (AnteilModel anteil : anteile) {
			if(anteil.getLager() == lager && anteil.getAnteil() == menge){
				anteile.remove(anteile.indexOf(anteil));
				verteilteMenge -= anteil.getAnteil();
				lager.aendernVerteilteMenge(-anteil.getAnteil());
				break;
			}
		}
	}
	
	//getter
	/**
	 * gibt den Buchungstag zurück, an dem die Buchung durchgeführt wurde
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
	
	/**
	 * sucht aus einer Buchung der Anteil zu einem Lager hinaus und gibt diesen zurück
	 * @param lager dessen Anteil gesucht wird
	 * @return den Anteil der zu dem Lager gehört
	 */
	public AnteilModel getAnteil(LagerModel lager){
		for(AnteilModel anteil: anteile){
			if(anteil.getLager() == lager){
				return anteil;
			}
		}
		return null;
	}
}
