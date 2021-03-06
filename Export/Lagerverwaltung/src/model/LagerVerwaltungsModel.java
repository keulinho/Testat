package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import core.exception.ErrorHandler;
import core.exception.LagerHatZuKleineKapazitaet;
import core.exception.LagerNichtLoeschbarException;
import core.exception.LagerUeberfuelltException;
import core.exception.MaxFreieKapazitaetUeberschritten;
import view.LagerBaumKnoten;
/**@author Niklas Devenish
**/
public class LagerVerwaltungsModel extends Observable implements Serializable {
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
		try {
			initialBefuellung();
		} catch(MaxFreieKapazitaetUeberschritten e){
			ErrorHandler.HandleException(ErrorHandler.MAX_FREIE_KAPAZITAET_UEBERSCHRITTEN, e);
		} catch(LagerHatZuKleineKapazitaet e){
			ErrorHandler.HandleException(ErrorHandler.LAGER_MUSS_MIT_MEHR_KAPAZITAET_ERSTELLT_WERDEN, e);
		}
		this.laufendeBuchung = null;
	}
	
	//Methoden
	/**
	 * Erstellt Lager und f�llt diese mit der in der Aufgabe gew�nschten Menge
	 * @throws MaxFreieKapazitaetUeberschritten
	 * @throws LagerHatZuKleineKapazitaet 
	 */
	public void initialBefuellung() throws MaxFreieKapazitaetUeberschritten, LagerHatZuKleineKapazitaet{
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
		LagerModel lager212 = hinzufuegenLager(lager21, 15000, "Orl�ans");
		LagerModel lager213 = hinzufuegenLager(lager21, 15000, "Marseille");
		LagerModel lager214 = hinzufuegenLager(lager21, 15000, "N�mes");
		LagerModel lager22 = hinzufuegenLager(lager2, 0, "Italien");
		LagerModel lager221 = hinzufuegenLager(lager22, 15000, "Mailand");
		LagerModel lager222 = hinzufuegenLager(lager22, 15000, "L'Aquila");
		LagerModel lager23 = hinzufuegenLager(lager2, 15000, "Spanien");
		LagerModel lager3 = hinzufuegenLager(null, 15000, "Gro�britannien");
		
		//Buchungen
		erstellenZuBuchung(new Date(1), 1000);
		hinzufuegenAnteil(lager13, 500);
		hinzufuegenAnteil(lager17, 200);
		hinzufuegenAnteil(lager221, 100);
		hinzufuegenAnteil(lager23, 100);
		hinzufuegenAnteil(lager3, 100);
		abschliessenBuchung();
		
		erstellenZuBuchung(new Date(2000000000), 2000);
		hinzufuegenAnteil(lager112, 1000);
		hinzufuegenAnteil(lager12, 400);
		hinzufuegenAnteil(lager14, 400);
		hinzufuegenAnteil(lager15, 200);
		abschliessenBuchung();
		
		erstellenZuBuchung(new Date(3000000), 10000);
		hinzufuegenAnteil(lager16, 2000);
		hinzufuegenAnteil(lager212, 1000);
		hinzufuegenAnteil(lager222, 2500);
		hinzufuegenAnteil(lager23, 2500);
		hinzufuegenAnteil(lager3, 2000);
		abschliessenBuchung();
		
		erstellenZuBuchung(new Date(400000000), 5000);
		hinzufuegenAnteil(lager214, 2500);
		hinzufuegenAnteil(lager17, 2000);
		hinzufuegenAnteil(lager112, 500);
		abschliessenBuchung();
		
		erstellenZuBuchung(new Date(500000000), 12500);
		hinzufuegenAnteil(lager211, 3750);
		hinzufuegenAnteil(lager16, 2500);
		hinzufuegenAnteil(lager111, 1875);
		hinzufuegenAnteil(lager13, 1875);
		hinzufuegenAnteil(lager221, 2500);
		abschliessenBuchung();
	}
	
	/**
	 * f�gt Observer zu dem LagerVerwaltungsModel hinzu und updatet sie initial
	 */
	public void addObserver(Observer o) {
		super.addObserver(o);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * f�gt einem Lager einem Knoten aus der View als Observer hinzu
	 * @param lager
	 * @param knoten
	 */
	public void addObserverTo(LagerModel lager, LagerBaumKnoten knoten){
		lager.addObserver(knoten);
	}

	/**
	 * erzeugt ein neues Lager und f�gt es der Liste der Unterlager des Oberlagers hinzu
	 * ist es das erste Unterlager wird der Bestand des Oberlagers �bernommen
	 * @param oberLager
	 * @param kapazitaet
	 * @param name
	 * @return true wenn das Lager erfolgreich erstellt wurde sonst false
	 */
	public LagerModel hinzufuegenLager(LagerModel oberLager, int kapazitaet, String name) throws LagerHatZuKleineKapazitaet{
		if(oberLager != null){
			if(oberLager.getUnterLager().isEmpty() && oberLager.getBestand() <= kapazitaet){
				LagerModel lager = oberLager.addUnterlager(kapazitaet, name);
				oberLager.verschiebeAnteileRunter(lager);
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
			throw new LagerHatZuKleineKapazitaet("Die Kapazit�t f�r das neue Lager muss min.");
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
	 * erstellt eine neue Zubuchung und setzt diese als laufende Buchung, wenn kein Buchung l�uft
	 * @param 	buchungsTag 	Zeitpunkt der Buchung
	 * @param 	menge			Menge der Buchung
	 * @return 	boolean			true wenn eine Buchung erstellt wurde sonst false
	 */
	public void erstellenZuBuchung(Date buchungsTag, int menge) throws MaxFreieKapazitaetUeberschritten{
		if(laufendeBuchung == null && maxFreieKapazitaet >= menge) {
			laufendeBuchung = new ZuBuchungsModel(buchungsTag, menge);
			this.setChanged();
			this.notifyObservers();
		} else {
			throw new MaxFreieKapazitaetUeberschritten("Die maximale freie Kapazit�t aller Lager betr�gt nur " + maxFreieKapazitaet);
		}
	}
	
	/**
	 * erstellt eine neue Abbuchung und setzt diese als laufende Buchung, wenn kein Buchung l�uft
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
	 * verwerfen aller Anteile und l�schen der Buchung
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
	 * schlie�t die laufende Buchung ab, wenn die Menge Voll verteilt ist
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
					ErrorHandler.HandleException(ErrorHandler.LAGER_VOLL, e);
					//bisher ausgef�hrten Anteile und die Buchung werden zur�ckgenommen
					for(int i= anteile.indexOf(anteile.get(a))-1; i >= 0; i--){
						try {
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
	 * f�gt einen Anteil zu der laufenden Buchung
	 * @param lager 	das den Anteil erh�lt
	 * @param anteil	der Buchung
	 * @return true wenn der Anteil erfolgreich hinzugef�gt wurde sonst false
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
	 * l�scht den Anteil der dem Lager und der Menge entspricht
	 * @param lager	das der Anteil hat
	 * @param menge	die der Anteil hat
	 */
	public void loeschenAnteil(LagerModel lager, int menge){
		laufendeBuchung.loeschenAnteil(lager, menge);
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * l�scht alle Anteile, die zu einem Lager geh�ren, aus einer Buchung
	 * @param lager
	 */
	public void loeschenAnteile(LagerModel lager){
		laufendeBuchung.loeschenAnteile(lager);
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * l�scht alle Anteile einer Buchung
	 */
	public void loeschenAlleAnteile() {
		laufendeBuchung.loeschenAlleAnteile();
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * l�scht ein Lager aus der Lagerstruktur wie unten dargestellt
	 * 
	 * A1
	 *  - A1.1
	 *   - A1.1.1
	 *  - A1.2
	 *   - A1.2.1
	 *   - A1.2.2
	 * A2
	 * 
	 *  A1      l�schen	--> alle gehen einen nach oben
	 *  A1.1    l�schen	--> A1.1.1 geht einen nach oben
	 *  A1.1.1  l�schen --> alles aus A1.1.1 geht nach oben Buchungen und Anteile auch
	 *  A1.2    l�schen	--> alle gehen eine Ebene nach oben
	 *  A1.2.1  l�schen	--> nur wenn es leer ist
	 *  A2		l�schen --> nur wenn es leer ist
	 */
	public void loesschenLager(LagerModel lager) throws LagerNichtLoeschbarException{
		if(lager.getOberLager() != null){
			if(lager.getUnterLager().isEmpty()){
				if(lager.getOberLager().getUnterLager().size() == 1){
					//A1.1.1 in alle Anteile das Lager �ndern
					lager.verschiebeAnteileHoch();
					lager.loeschenLager(this,false);
				} else {
					//A1.2.1
					if(lager.getBestand() == 0){
						lager.loeschenLager(this,true);
					} else {
						throw new LagerNichtLoeschbarException("Das Lager muss leer sein um gel�scht zu werden.\n"
								+ lager.getName() + " enth�lt noch: " + lager.getBestand());
					}
				}
			} else {
				//A1.1 und A1.2 �ber alle Unterlager gehen und dann hochziehen
				lager.verschiebeAnteileHoch();
				lager.loeschenLager(this,false);
			}
		} else {
			if(!lager.getUnterLager().isEmpty()){
				//A1
				lager.verschiebeAnteileHoch();
				lager.loeschenLager(this,false);
			} else {
				//A2
				if(lager.getBestand() == 0){
					lager.loeschenAnteile(this);
					lager.loeschenLager(this,false);
				} else {
					throw new LagerNichtLoeschbarException("Das Lager muss leer sein um gel�scht zu werden.\n"
							+ lager.getName() + " enth�lt noch: " + lager.getBestand());
				}
			}
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * l�scht eine Buchung aus der Liste der Buchungen
	 * @param buchung Buchung die gel�scht werden soll
	 */
	public void loescheBuchung(BuchungsModel buchung){
		buchungen.remove(buchung);
	}
	
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
	 * @return lager Liste der Lager oberster Ebene
	 */
	public List<LagerModel> getLager(){
		return this.lager;
	}
	
	/**
	 * gibt zu einem Lager die in der aktuellen Buchung verteilbare Menge zur�ck
	 * @param lager
	 * @return int
	 */
	public int getVerteilbareMenge(LagerModel lager) {
		return laufendeBuchung.getVerteilbareMenge(lager);
	}
	
}
