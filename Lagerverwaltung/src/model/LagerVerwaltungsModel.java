package model;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import core.exception.LagerUeberfuelltException;

public class LagerVerwaltungsModel {
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
	public boolean lagerHinzufuegen(LagerModel oberLager, int kapazitaet, String name){
		if(oberLager != null){
			if(oberLager.getUnterLager().isEmpty() && oberLager.getBestand() < kapazitaet){
				LagerModel lager = new LagerModel(kapazitaet, name, oberLager);
				oberLager.addUnterlager(lager);
				try {
					lager.bestandVer�ndern(oberLager.getBestand());
				} catch (LagerUeberfuelltException e) {
					// TODO ErrorHandler und evtl. was r�ckh�ngig machen und evtl. false zur�ckgeben
					e.printStackTrace();
				}
				return true;
			} else if(!oberLager.getUnterLager().isEmpty()) {
				oberLager.addUnterlager(new LagerModel(kapazitaet, name, oberLager));
				oberLager.kapazitaetSteigern(kapazitaet);
				return true;
			}
			return false;
		} else {
			new LagerModel(kapazitaet, name, oberLager);
			return true;
		}
	}
	
	/**
	 * benennt das Lager um
	 * @param lager	Lager, das umbenannt wird
	 * @param name	neuer Name des Lagers
	 */
	public void lagerUmbenennen(LagerModel lager, String name){
		lager.setName(name);
	}
	
	/**
	 * erstellt eine neue Buchung und setzt diese als laufende Buchung, wenn kein Buchung l�uft
	 * @param 	buchungsTag 	Zeitpunkt der Buchung
	 * @param 	menge			Menge der Buchung
	 * @return 	boolean			true wenn eine Buchung erstellt wurde sonst false
	 */
	public boolean buchungErstellen(Date buchungsTag, int menge){
		if(laufendeBuchung == null) {
			laufendeBuchung = new BuchungsModel(buchungsTag, menge);
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
	public boolean buchungAbschliessen(){
		if(laufendeBuchung.isVollVerteilt()){
			List<AnteilModel> anteile = laufendeBuchung.getAnteile();
			for(AnteilModel anteil: anteile){
				for(int i = anteile.indexOf(anteil) + 1; i < anteile.size(); i++){
					if(anteil.getLager() == anteile.get(i).getLager()){
						anteil.anteilErhoehen(anteile.get(i).getAnteil());
						anteile.remove(i);
					}
				}
				try {
					anteil.getLager().bestandVer�ndern((anteil.getAnteil() * laufendeBuchung.getMenge() / 100));
					anteil.getLager().addBuchung(laufendeBuchung);
				} catch (LagerUeberfuelltException e) {
					//TODO ErrorHandler
					//bisher ausgef�hrten Anteile und die Buchung werden zur�ck genommen
					for(int i= anteile.indexOf(anteil)-1; i >= 0; i--){
						try {
							//TODO ErrorHandler
							anteile.get(i).getLager().entfernenBuchung(laufendeBuchung);
							anteile.get(i).getLager().bestandVer�ndern(-anteile.get(i).getAnteil());
						} catch (LagerUeberfuelltException e1) {
							e1.printStackTrace();
						}
					}
					System.out.println("Fehler beim bef�llen von den Lagern!!!");
					e.printStackTrace();
					break;
				}
			}
			laufendeBuchung = null;
			return true;
		}
		return false;
	}
	
	/**
	 * f�gt einen Anteil zu der laufenden Buchung
	 * @param anteil	der Anteil
	 * @return true wenn der Anteil erfolgreich hinzugef�gt wurde sonst false
	 */
	public boolean anteilHinzugegen(LagerModel lager, int anteil){
		if((laufendeBuchung.getMenge()*anteil/100) <= laufendeBuchung.getFreienPlatz()){
			laufendeBuchung.anteilHinzufuegen(lager, anteil);
			return true;
		}
		return false;
	}
	
	public void lagerLoeschen(LagerModel lager){
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
	 * gibt die Liste aller Lager zur�ck, die auf der obersten Ebene sind
	 * @return lager Lister der Lager oberster Ebene
	 */
	public List<LagerModel> getLager(){
		return this.lager;
	}
	
}
