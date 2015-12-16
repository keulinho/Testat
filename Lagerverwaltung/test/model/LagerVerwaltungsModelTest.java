package model;

import java.util.Date;

import core.exception.LagerHatZuKleineKapazitaet;
import core.exception.MaxFreieKapazitaetUeberschritten;
import junit.framework.TestCase;
/**
 * Die Tests funktionieren nur, wenn die Initialbefüllung im Kontruktor auskommentiert ist 
 * @author Niklas Devenish
 */
public class LagerVerwaltungsModelTest extends TestCase{
	LagerVerwaltungsModel lagerVerwaltungsModel;
	
	public void setUp() {
		lagerVerwaltungsModel = new LagerVerwaltungsModel();
	}
	
	public void testHinzufuegenLagerOberlager() throws LagerHatZuKleineKapazitaet{
		LagerModel lager1 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager2");
		boolean result = (lagerVerwaltungsModel.getLager().size() == 2 &&
				lagerVerwaltungsModel.getMaxFreieKapazitaet() == 200 &&
				lager1 != null && lager2 != null);
		assertEquals(true, result);
	}
	
	public void testHinzufuegenLagerUntereEbenen() throws LagerHatZuKleineKapazitaet{
		LagerModel lager1 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVerwaltungsModel.hinzufuegenLager(lager1, 200, "Unterlager1");
		boolean result = (lagerVerwaltungsModel.getLager().size() == 1 &&
				lagerVerwaltungsModel.getMaxFreieKapazitaet() == 300 &&
				lager1 != null && lager2 != null);
		assertEquals(true, result);
	}
	
	public void testUmbennenLager() throws LagerHatZuKleineKapazitaet{
		LagerModel lager1 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager1");
		lagerVerwaltungsModel.umbenennenLager(lager1, "Lager 2");
		int index = lagerVerwaltungsModel.getLager().indexOf(lager1);
		assertEquals("Lager 2", lagerVerwaltungsModel.getLager().get(index).getName());
	}
	
	public void testErstellenZuBuchung(){
		assertEquals(true, lagerVerwaltungsModel.getLaufendeBuchung() != null);
	}
	
	public void testErstellenZuBuchungMitLaufenderBuchung() throws MaxFreieKapazitaetUeberschritten{
		assertEquals(false, lagerVerwaltungsModel.getLaufendeBuchung() == null);
		lagerVerwaltungsModel.erstellenZuBuchung(new Date(1), 100);
		assertEquals(false, lagerVerwaltungsModel.getLaufendeBuchung() != null);
	}
	
	public void testErstellenAbBuchung(){
		assertEquals(true, lagerVerwaltungsModel.erstellenAbBuchung(new Date(1)) && lagerVerwaltungsModel.getLaufendeBuchung() != null);
	}
	
	public void testErstellenAbBuchungMitLaufenderBuchung(){
		lagerVerwaltungsModel.erstellenAbBuchung(new Date(1));
		assertEquals(false, lagerVerwaltungsModel.erstellenAbBuchung(new Date(1)));
	}
	
	public void testHinzufuegenAnteil() throws MaxFreieKapazitaetUeberschritten, LagerHatZuKleineKapazitaet{
		lagerVerwaltungsModel.erstellenZuBuchung(new Date(1), 100);
		LagerModel lager1 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager2");
		assertEquals(true,
				lagerVerwaltungsModel.hinzufuegenAnteil(lager1, 50) &&
				lagerVerwaltungsModel.hinzufuegenAnteil(lager2, 50) &&
				lagerVerwaltungsModel.getLaufendeBuchung().anteile.size() == 2 &&
				lagerVerwaltungsModel.getLaufendeBuchung().getVerteilteMenge() == 100);
	}
	
	public void testHinzufuegenAnteilMerhAlsBuchung() throws MaxFreieKapazitaetUeberschritten, LagerHatZuKleineKapazitaet{
		lagerVerwaltungsModel.erstellenZuBuchung(new Date(1), 100);
		LagerModel lager1 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager2");
		assertEquals(true, lagerVerwaltungsModel.hinzufuegenAnteil(lager1, 50));
		assertEquals(false, lagerVerwaltungsModel.hinzufuegenAnteil(lager2, 60));
		assertEquals(true,
				lagerVerwaltungsModel.getLaufendeBuchung().anteile.size() == 1 &&
				lagerVerwaltungsModel.getLaufendeBuchung().getVerteilteMenge() == 50);
	}
	
	public void testLoeschenAnteil() throws MaxFreieKapazitaetUeberschritten, LagerHatZuKleineKapazitaet{
		lagerVerwaltungsModel.erstellenZuBuchung(new Date(1), 100);
		LagerModel lager1 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager2");
		lagerVerwaltungsModel.hinzufuegenAnteil(lager1, 50);
		lagerVerwaltungsModel.hinzufuegenAnteil(lager2, 50);
		lagerVerwaltungsModel.loeschenAnteil(lager1, 50);
		assertEquals(true, lagerVerwaltungsModel.getLaufendeBuchung().getAnteile().size() == 1 &&
				lagerVerwaltungsModel.getLaufendeBuchung().getAnteile().get(0).getLager() == lager2 &&
				lagerVerwaltungsModel.getLaufendeBuchung().getAnteile().get(0).getAnteil() == 50);
	}
	
	public void testAbschliessenZuBuchung() throws MaxFreieKapazitaetUeberschritten, LagerHatZuKleineKapazitaet{
		lagerVerwaltungsModel.erstellenZuBuchung(new Date(1), 100);
		LagerModel lager1 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager2");
		lagerVerwaltungsModel.hinzufuegenAnteil(lager1, 50);
		lagerVerwaltungsModel.hinzufuegenAnteil(lager2, 50);
		assertEquals(true, lagerVerwaltungsModel.abschliessenBuchung() &&
				lagerVerwaltungsModel.getBuchungen().size() == 1 &&
				lager1.getBestand() == 50 &&
				lager2.getBestand() == 50&&
				lagerVerwaltungsModel.getLaufendeBuchung() == null &&
				lagerVerwaltungsModel.buchungen.get(lagerVerwaltungsModel.getBuchungen().size()-1).getVerteilteMenge() == 100);
		assertEquals(true, lagerVerwaltungsModel.maxFreieKapazitaet == 100);
	}
	
	public void testAbschliessenZuBuchungNichtVollVerteilt() throws MaxFreieKapazitaetUeberschritten, LagerHatZuKleineKapazitaet{
		lagerVerwaltungsModel.erstellenZuBuchung(new Date(1), 100);
		LagerModel lager1 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager2");
		lagerVerwaltungsModel.hinzufuegenAnteil(lager1, 50);
		assertEquals(true, lagerVerwaltungsModel.abschliessenBuchung() == false &&
				lagerVerwaltungsModel.getBuchungen().size() == 0 &&
				lager1.getBestand() == 0 &&
				lager2.getBestand() == 0);
		assertEquals(true, lagerVerwaltungsModel.maxFreieKapazitaet == 200);
	}
	
	public void testAbschliessenAbBuchung() throws MaxFreieKapazitaetUeberschritten, LagerHatZuKleineKapazitaet{
		lagerVerwaltungsModel.erstellenZuBuchung(new Date(1), 100);
		LagerModel lager1 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager2");
		lagerVerwaltungsModel.hinzufuegenAnteil(lager1, 50);
		lagerVerwaltungsModel.hinzufuegenAnteil(lager2, 50);
		lagerVerwaltungsModel.abschliessenBuchung();
		lagerVerwaltungsModel.erstellenAbBuchung(new Date(1));
		lagerVerwaltungsModel.hinzufuegenAnteil(lager1, 50);
		lagerVerwaltungsModel.hinzufuegenAnteil(lager2, 50);
		assertEquals(true, lagerVerwaltungsModel.abschliessenBuchung() &&
				lager1.getBestand() == 0 &&
				lager2.getBestand() == 0 &&
				lagerVerwaltungsModel.getLaufendeBuchung() == null);
	}
	
	public void testAbschliessenAbBuchungOhneAnteil() throws MaxFreieKapazitaetUeberschritten, LagerHatZuKleineKapazitaet{
		lagerVerwaltungsModel.erstellenZuBuchung(new Date(1), 100);
		LagerModel lager1 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager2");
		lagerVerwaltungsModel.hinzufuegenAnteil(lager1, 50);
		lagerVerwaltungsModel.hinzufuegenAnteil(lager2, 50);
		lagerVerwaltungsModel.abschliessenBuchung();
		lagerVerwaltungsModel.erstellenAbBuchung(new Date(1));
		assertEquals(false, lagerVerwaltungsModel.abschliessenBuchung());
	}
	
	public void testInitialBefuellung() throws MaxFreieKapazitaetUeberschritten, LagerHatZuKleineKapazitaet{
		lagerVerwaltungsModel.initialBefuellung();
		lagerVerwaltungsModel.hinzufuegenLager(null, 100, "hier debuggen!");
	}

}
