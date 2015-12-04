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
	 * Konstruktor für ein LagerVerwaltungsModel
	 */
	public LagerVerwaltungsModel(){
		lager = new Vector<LagerModel>();
		buchungen = new Vector<BuchungsModel>();
		this.maxFreieKapazitaet = 0;
		this.laufendeBuchung = null;
	}
	
	//Methoden
	public void initialBefuellung(){
		//Lager
		LagerModel lager1 = hinzufuegenLager(null, 0, "Deutschland");
		LagerModel lager11 = hinzufuegenLager(lager1, 0, "Niedersachsen");
		LagerModel lager111 = hinzufuegenLager(lager11, 15000, "Hannover-Misburg");
		LagerModel lager112 = hinzufuegenLager(lager11, 15000, "Nienburg");
		LagerModel lager12 = hinzufuegenLager(lager1, 15000, "NRW");
		LagerModel lager13 = hinzufuegenLager(lager1, 15000, "Bremen");
		LagerModel lager14 = hinzufuegenLager(lager1, 15000, "Hessen");
		LagerModel lager15 = hinzufuegenLager(lager1, 15000, "Sachsen");
		LagerModel lager16 = hinzufuegenLager(lager1, 15000, "Brandeburg");
		LagerModel lager17 = hinzufuegenLager(lager1, 15000, "MV");
		LagerModel lager2 = hinzufuegenLager(null, 0, "Deutschland");
		LagerModel lager21 = hinzufuegenLager(null, 0, "Frankreich");
		LagerModel lager211 = hinzufuegenLager(lager21, 15000, "Paris-Nord");
		LagerModel lager212 = hinzufuegenLager(lager21, 15000, "Orléans");
		LagerModel lager213 = hinzufuegenLager(lager21, 15000, "Marseille");
		LagerModel lager214 = hinzufuegenLager(lager21, 15000, "Nîmes");
		LagerModel lager22 = hinzufuegenLager(null, 0, "Italien");
		LagerModel lager221 = hinzufuegenLager(lager22, 15000, "Mailand");
		LagerModel lager222 = hinzufuegenLager(lager22, 15000, "L'Aquila");
		LagerModel lager23 = hinzufuegenLager(lager2, 15000, "Spanien");
		LagerModel lager3 = hinzufuegenLager(null, 15000, "Großbritannien");
		
		//Buchungen
		erstellenZuBuchung(new Date(1), 1000);
		hinzugegenAnteil(lager13, 500);
		hinzugegenAnteil(lager17, 200);
		hinzugegenAnteil(lager221, 100);
		hinzugegenAnteil(lager23, 100);
		hinzugegenAnteil(lager3, 100);
		abschliessenBuchung();
		
		erstellenZuBuchung(new Date(1), 2000);
		hinzugegenAnteil(lager112, 1000);
		hinzugegenAnteil(lager12, 400);
		hinzugegenAnteil(lager14, 400);
		hinzugegenAnteil(lager15, 200);
		abschliessenBuchung();
		
		erstellenZuBuchung(new Date(1), 10000);
		hinzugegenAnteil(lager16, 2000);
		hinzugegenAnteil(lager212, 1000);
		hinzugegenAnteil(lager222, 2500);
		hinzugegenAnteil(lager23, 2500);
		hinzugegenAnteil(lager3, 2000);
		abschliessenBuchung();
		
		erstellenZuBuchung(new Date(1), 5000);
		hinzugegenAnteil(lager214, 2500);
		hinzugegenAnteil(lager17, 2000);
		hinzugegenAnteil(lager112, 500);
		abschliessenBuchung();
		
		erstellenZuBuchung(new Date(1), 12500);
		hinzugegenAnteil(lager211, 3750);
		hinzugegenAnteil(lager16, 2500);
		hinzugegenAnteil(lager111, 1875);
		hinzugegenAnteil(lager13, 1875);
		hinzugegenAnteil(lager221, 2500);
		abschliessenBuchung();
	}
	
	public void addObserverTo(){
		//TODO
	}

	/**
	 * erzeugt ein neues Lager und fügt es der Liste der Unterlager des Oberlagers hinzu
	 * ist es das erste Unterlager wird der Bestand des Oberlagers übernommen
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
					// TODO neues Unterlager hat weniger Kapazität als das alte -> Kapazität wird nicht blind nach oben gegeben
					lager.veraendernBestand(oberLager.getBestand());
					oberLager.aendernKapazitaet(kapazitaet);
					maxFreieKapazitaet += kapazitaet;
				} catch (LagerUeberfuelltException e) {
					// TODO ErrorHandler und evtl. was rückhängig machen und evtl. false zurückgeben
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
	 * erstellt eine neue Zubuchung und setzt diese als laufende Buchung, wenn kein Buchung läuft
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
	 * erstellt eine neue ABbuchung und setzt diese als laufende Buchung, wenn kein Buchung läuft
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
	 * verwerfen aller Anteile und löschen der Buchung
	 */
	public void verwerfenBuchung(){
		if(laufendeBuchung != null){
			while(!laufendeBuchung.getAnteile().isEmpty()){
				laufendeBuchung.getAnteile().remove(0);
			}
		}
		laufendeBuchung = null;
	}
	
	/**
	 * schließt die laufende Buchung ab, wenn die Menge Voll verteilt ist
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
					//bisher ausgeführten Anteile und die Buchung werden zurück genommen
					for(int i= anteile.indexOf(anteil)-1; i >= 0; i--){
						try {
							//TODO ErrorHandler
							anteile.get(i).getLager().entfernenBuchung(laufendeBuchung);
							anteile.get(i).getLager().veraendernBestand(-anteile.get(i).getAnteil());
						} catch (LagerUeberfuelltException e1) {
							e1.printStackTrace();
						}
					}
					System.out.println("Fehler beim befüllen von den Lagern!!!");
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
	 * fügt einen Anteil zu der laufenden Buchung
	 * @param lager 	das den Anteil erhält
	 * @param anteil	der Buchung
	 * @return true wenn der Anteil erfolgreich hinzugefügt wurde sonst false
	 */
	public boolean hinzugegenAnteil(LagerModel lager, int anteil){
		//TODO prüfen ob Lager unterster Eben
		if(laufendeBuchung.hinzufuegenAnteil(lager, anteil) != null){
			return true;
		}
		return false;
	}
	
	/**
	 * löscht den Anteil der dem Lager und der Menge entspricht
	 * @param lager	das der Anteil hat
	 * @param menge	die der Anteil hat
	 */
	public void loeschenAnteil(LagerModel lager, int menge){
		laufendeBuchung.loeschenAnteil(lager, menge);
	}
	//TODO andere Varianten des löschen implementieren
	
	public void loesschenLager(LagerModel lager){
		// TODO
		/**
		 * Wenn kein Lager auf gleicher Ebene dann geht alles eine Ebene nach oben -- was passiert mit Anteilen in Buchungen
		 * befinden sich Lager auf gleicher Ebene findet eine Umbuchung statt
		 * 		eine Buchung auf andere Lager so wie eine Abbuchung von dem zu löschenden Lager
		 */
	}
	
	//TODO generell Umbuchung
	
	//getter
	/**
	 * gibt die max. freie Kapazität aller Lager zurück
	 * @return maxFreeKapazitaet int
	 */
	public int getMaxFreieKapazitaet(){
		return this.maxFreieKapazitaet;
	}
	
	/**
	 * gibt die laufenden Buchung zurück
	 * @return BuchungsModel
	 */
	public BuchungsModel getLaufendeBuchung(){
		return this.laufendeBuchung;
	}
	
	/**
	 * gibt alle abgeschlossenen Buchungen zurück
	 * @return Liste von BuchungsModdeln
	 */
	public List<BuchungsModel> getBuchungen(){
		return this.buchungen;
	}
	
	/**
	 * gibt die Liste aller Lager zurück, die auf der obersten Ebene sind
	 * @return lager Lister der Lager oberster Ebene
	 */
	public List<LagerModel> getLager(){
		return this.lager;
	}
	
}
