package model;

import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

import core.exception.LagerUeberfuelltException;

public class LagerVerwaltungsModel extends Observable {
	List<LagerModel> lager;
	List<BuchungsModel> buchungen;
	int maxFreieKapazitaet;
	BuchungsModel laufendeBuchung;
	
	//Konstruktor
	/**
	 * Konstruktor f�r ein LagerVerwaltungsModel
	 */
	public LagerVerwaltungsModel(){
		lager = new Vector<LagerModel>();
		buchungen = new Vector<BuchungsModel>();
		this.maxFreieKapazitaet = 0;
		this.laufendeBuchung = null;
	}
	
	//Methoden
	public void initialBef�llung(){
		//TODO
	}
	
	public void addObserverTo(){
		//TODO
	}

	/**
	 * erzeugt ein neues Lager und f�gt es der Liste der Unterlager des Oberlagers hinzu
	 * ist es das erste Unterlager wird der Bestand des Oberlagers �bernommen
	 * @param oberLager
	 * @param kapazitaet
	 * @param name
	 * @return true wenn das Lager erfolgreich erstellt wurde sonst false
	 */
	public LagerModel hinzufuegenLager(LagerModel oberLager, int kapazitaet, String name){
		if(oberLager != null){
			if(oberLager.getUnterLager().isEmpty() && oberLager.getBestand() <= kapazitaet){
				LagerModel lager = oberLager.addUnterlager(kapazitaet, name);
				try {
					lager.veraendernBestand(oberLager.getBestand());
					oberLager.aendernKapazitaet(kapazitaet);
					maxFreieKapazitaet += kapazitaet;
				} catch (LagerUeberfuelltException e) {
					// TODO ErrorHandler und evtl. was r�ckh�ngig machen und evtl. false zur�ckgeben
					e.printStackTrace();
				}
				return lager;
			} else if(!oberLager.getUnterLager().isEmpty()) {
				LagerModel lager = oberLager.addUnterlager(kapazitaet, name);
				oberLager.aendernKapazitaet(kapazitaet);
				maxFreieKapazitaet += kapazitaet;
				return lager;
			}
			return null;
		} else {
			LagerModel newLager = new LagerModel(kapazitaet, name, oberLager);
			lager.add(newLager);
			maxFreieKapazitaet += kapazitaet;
			return newLager;
		}
	}
	
	/**
	 * benennt das Lager um
	 * @param lager	Lager, das umbenannt wird
	 * @param name	neuer Name des Lagers
	 */
	public void umbenennenLager(LagerModel lager, String name){
		lager.setName(name);
	}
	
	/**
	 * erstellt eine neue Zubuchung und setzt diese als laufende Buchung, wenn kein Buchung l�uft
	 * @param 	buchungsTag 	Zeitpunkt der Buchung
	 * @param 	menge			Menge der Buchung
	 * @return 	boolean			true wenn eine Buchung erstellt wurde sonst false
	 */
	public boolean erstellenZuBuchung(Date buchungsTag, int menge){
		if(laufendeBuchung == null) {
			laufendeBuchung = new ZuBuchungsModel(buchungsTag, menge);
			return true;
		}
		return false;
	}
	/**
	 * erstellt eine neue ABbuchung und setzt diese als laufende Buchung, wenn kein Buchung l�uft
	 * @param buchungsTag	Zeitpunkt der Buchung
	 * @return				true wenn die erstelt wurde sonst false
	 */
	public boolean erstellenAbBuchung(Date buchungsTag){
		if(laufendeBuchung == null) {
			laufendeBuchung = new AbBuchungsModel(buchungsTag);
			return true;
		}
		return false;
	}
	
	/**
	 * schlie�t die laufende Buchung ab, wenn die Menge Voll verteilt ist
	 * Anteile mit dem selben Lager werden zu einem Anteil zusammengefasst
	 * Die Mengen aus den Anteilen werden in die Lager gebucht
	 * @return true wenn die Buchung abgeschlossen wurde sonst false
	 */
	public boolean abschliessenBuchung(){
		if(laufendeBuchung.isFertig()){
			List<AnteilModel> anteile = laufendeBuchung.getAnteile();
			for(AnteilModel anteil: anteile){
				for(int i = anteile.indexOf(anteil) + 1; i < anteile.size(); i++){
					if(anteil.getLager() == anteile.get(i).getLager()){
						anteil.erhoehenAnteil(anteile.get(i).getAnteil());
						anteile.remove(i);
					}
				}
				try {
					anteil.getLager().veraendernBestand(anteil.getAnteil());
					anteil.getLager().addBuchung(laufendeBuchung);
				} catch (LagerUeberfuelltException e) {
					//TODO ErrorHandler
					//bisher ausgef�hrten Anteile und die Buchung werden zur�ck genommen
					for(int i= anteile.indexOf(anteil)-1; i >= 0; i--){
						try {
							//TODO ErrorHandler
							anteile.get(i).getLager().entfernenBuchung(laufendeBuchung);
							anteile.get(i).getLager().veraendernBestand(-anteile.get(i).getAnteil());
						} catch (LagerUeberfuelltException e1) {
							e1.printStackTrace();
						}
					}
					System.out.println("Fehler beim bef�llen von den Lagern!!!");
					e.printStackTrace();
					break;
				}
			}
			maxFreieKapazitaet -= laufendeBuchung.getVerteilteMenge();
			buchungen.add(laufendeBuchung);
			laufendeBuchung = null;
			return true;
		}
		return false;
	}
	
	/**
	 * f�gt einen Anteil zu der laufenden Buchung
	 * @param lager 	das den Anteil erh�lt
	 * @param anteil	der Buchung
	 * @return true wenn der Anteil erfolgreich hinzugef�gt wurde sonst false
	 */
	public boolean hinzugegenAnteil(LagerModel lager, int anteil){
		//TODO pr�fen ob Lager unterster Eben
		if(laufendeBuchung.hinzufuegenAnteil(lager, anteil) != null){
			return true;
		}
		return false;
	}
	
	/**
	 * l�scht den Anteil der dem Lager und der Menge entspricht
	 * @param lager	das der Anteil hat
	 * @param menge	die der Anteil hat
	 */
	public void loeschenAnteil(LagerModel lager, int menge){
		laufendeBuchung.loeschenAnteil(lager, menge);
	}
	//TODO andere Varianten des l�schen implementieren
	
	public void loesschenLager(LagerModel lager){
		// TODO
		/**
		 * Wenn kein Lager auf gleicher Ebene dann geht alles eine Ebene nach oben -- was passiert mit Anteilen in Buchungen
		 * befinden sich Lager auf gleicher Ebene findet eine Umbuchung statt
		 * 		eine Buchung auf andere Lager so wie eine Abbuchung von dem zu l�schenden Lager
		 */
	}
	
	//TODO generell Umbuchung
	
	//getter
	/**
	 * gibt die max. freie Kapazit�t aller Lager zur�ck
	 * @return maxFreeKapazitaet int
	 */
	public int getMaxFreieKapazitaet(){
		return this.maxFreieKapazitaet;
	}
	
	/**
	 * gibt die laufenden Buchung zur�ck
	 * @return BuchungsModel
	 */
	public BuchungsModel getLaufendeBuchung(){
		return this.laufendeBuchung;
	}
	
	/**
	 * gibt alle abgeschlossenen Buchungen zur�ck
	 * @return Liste von BuchungsModdeln
	 */
	public List<BuchungsModel> getBuchungen(){
		return this.buchungen;
	}
	
	/**
	 * gibt die Liste aller Lager zur�ck, die auf der obersten Ebene sind
	 * @return lager Lister der Lager oberster Ebene
	 */
	public List<LagerModel> getLager(){
		return this.lager;
	}
	
}
