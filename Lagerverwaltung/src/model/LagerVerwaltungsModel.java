package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import core.exception.LagerUeberfuelltException;
import view.LagerBaumKnoten;

public class LagerVerwaltungsModel extends Observable implements Serializable {
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
		initialBefuellung();
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
		LagerModel lager2 = hinzufuegenLager(null, 0, "Europa");
		LagerModel lager21 = hinzufuegenLager(lager2, 0, "Frankreich");
		LagerModel lager211 = hinzufuegenLager(lager21, 15000, "Paris-Nord");
		LagerModel lager212 = hinzufuegenLager(lager21, 15000, "Orléans");
		LagerModel lager213 = hinzufuegenLager(lager21, 15000, "Marseille");
		LagerModel lager214 = hinzufuegenLager(lager21, 15000, "Nîmes");
		LagerModel lager22 = hinzufuegenLager(lager2, 0, "Italien");
		LagerModel lager221 = hinzufuegenLager(lager22, 15000, "Mailand");
		LagerModel lager222 = hinzufuegenLager(lager22, 15000, "L'Aquila");
		LagerModel lager23 = hinzufuegenLager(lager2, 15000, "Spanien");
		LagerModel lager3 = hinzufuegenLager(null, 15000, "Großbritannien");
		
		//Buchungen
		erstellenZuBuchung(new Date(1), 1000);
		hinzufuegenAnteil(lager13, 500);
		hinzufuegenAnteil(lager17, 200);
		hinzufuegenAnteil(lager221, 100);
		hinzufuegenAnteil(lager23, 100);
		hinzufuegenAnteil(lager3, 100);
		abschliessenBuchung();
		
		erstellenZuBuchung(new Date(2), 2000);
		hinzufuegenAnteil(lager112, 1000);
		hinzufuegenAnteil(lager12, 400);
		hinzufuegenAnteil(lager14, 400);
		hinzufuegenAnteil(lager15, 200);
		abschliessenBuchung();
		
		erstellenZuBuchung(new Date(3), 10000);
		hinzufuegenAnteil(lager16, 2000);
		hinzufuegenAnteil(lager212, 1000);
		hinzufuegenAnteil(lager222, 2500);
		hinzufuegenAnteil(lager23, 2500);
		hinzufuegenAnteil(lager3, 2000);
		abschliessenBuchung();
		
		erstellenZuBuchung(new Date(4), 5000);
		hinzufuegenAnteil(lager214, 2500);
		hinzufuegenAnteil(lager17, 2000);
		hinzufuegenAnteil(lager112, 500);
		abschliessenBuchung();
		
		erstellenZuBuchung(new Date(5), 12500);
		hinzufuegenAnteil(lager211, 3750);
		hinzufuegenAnteil(lager16, 2500);
		hinzufuegenAnteil(lager111, 1875);
		hinzufuegenAnteil(lager13, 1875);
		hinzufuegenAnteil(lager221, 2500);
		abschliessenBuchung();
	}
	
	public void addObserver(Observer o) {
		super.addObserver(o);
		setChanged();
		notifyObservers();
	}
	public void addObserverTo(LagerModel lager, LagerBaumKnoten knoten){
		lager.addObserver(knoten);
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
				oberLager.aendernKapazitaet(kapazitaet-oberLager.getMaxKapazitaet());
				oberLager.aendernOberlagerKapazitaet(kapazitaet);
				maxFreieKapazitaet += kapazitaet;
				setChanged();
				notifyObservers();
				return lager;
			} else if(!oberLager.getUnterLager().isEmpty()) {
				LagerModel lager = oberLager.addUnterlager(kapazitaet, name);
				oberLager.aendernKapazitaet(kapazitaet);
				oberLager.aendernOberlagerKapazitaet(kapazitaet);
				maxFreieKapazitaet += kapazitaet;
				setChanged();
				notifyObservers();
				return lager;
			}
			return null;
		} else {
			LagerModel newLager = new LagerModel(kapazitaet, name, oberLager);
			lager.add(newLager);
			maxFreieKapazitaet += kapazitaet;
			setChanged();
			notifyObservers();
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
		setChanged();
		notifyObservers();
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
			this.setChanged();
			this.notifyObservers();
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
			this.setChanged();
			this.notifyObservers();
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
				laufendeBuchung.getAnteile().get(0).getLager().resetVerteilteMenge();
				laufendeBuchung.getAnteile().remove(0);
			}
		}
		laufendeBuchung = null;
		this.setChanged();
		this.notifyObservers();
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
			for(int a = 0; a < anteile.size(); a++){
				for(int i = anteile.indexOf(anteile.get(a)) + 1; i < anteile.size(); i=i){
					if(anteile.get(a).getLager() == anteile.get(i).getLager()){
						anteile.get(a).erhoehenAnteil(anteile.get(i).getAnteil());
						anteile.remove(i);
					} else {
						i++;
					}
				}
				try {
					anteile.get(a).getLager().veraendernBestand(anteile.get(a).getAnteil());
					anteile.get(a).getLager().addBuchung(laufendeBuchung);
				} catch (LagerUeberfuelltException e) {
					//TODO ErrorHandler
					//bisher ausgeführten Anteile und die Buchung werden zurückgenommen
					for(int i= anteile.indexOf(anteile.get(a))-1; i >= 0; i--){
						try {
							anteile.get(i).getLager().entfernenBuchung(laufendeBuchung);
							anteile.get(i).getLager().veraendernBestand(-anteile.get(i).getAnteil());
						} catch (LagerUeberfuelltException e1) {
							//TODO ErrorHandler
							e1.printStackTrace();
						}
					}
					//TODO ErrorHandler
					System.out.println("Fehler beim befüllen von den Lagern!!!");
					e.printStackTrace();
					break;
				}
			}
			maxFreieKapazitaet -= laufendeBuchung.getVerteilteMenge();
			buchungen.add(laufendeBuchung);
			laufendeBuchung = null;
			this.setChanged();
			this.notifyObservers();
			for(AnteilModel anteil: anteile){
				anteil.getLager().resetVerteilteMenge();
			}
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
	public boolean hinzufuegenAnteil(LagerModel lager, int anteil){
		if(lager.isUntersteEbene() && laufendeBuchung.hinzufuegenAnteil(lager, anteil) != null){
			this.setChanged();
			this.notifyObservers();
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
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * löscht alle Anteile, die zu einem Lager gehören, aus einer Buchung
	 * @param lager
	 */
	public void loeschenAnteile(LagerModel lager){
		laufendeBuchung.loeschenAnteile(lager);
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * löscht alle Anteile einer Buchung
	 */
	public void loeschenAlleAnteile() {
		laufendeBuchung.loeschenAlleAnteile();
		this.setChanged();
		this.notifyObservers();
	}
	
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
	
	/**
	 * gibt zu einem Lager die in der aktuellen Buchung verteilbare Menge zurück
	 * @param lager
	 * @return int
	 */
	public int getVerteilbareMenge(LagerModel lager) {
		return laufendeBuchung.getVerteilbareMenge(lager);
	}
	
}
