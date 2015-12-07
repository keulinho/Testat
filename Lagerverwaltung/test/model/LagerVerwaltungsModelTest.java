package model;

import java.util.Date;
import junit.framework.TestCase;
/**
 * Die Tests funktionieren nur, wenn die Initialbefüllung im Kontruktor auskommentiert ist 
 */
public class LagerVerwaltungsModelTest extends TestCase{
	LagerVerwaltungsModel lagerVerwaltungsModel;
	
	public void setUp() {
		lagerVerwaltungsModel = new LagerVerwaltungsModel();
	}
	
	public void testHinzufuegenLagerOberlager(){
		LagerModel lager1 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager2");
		boolean result = (lagerVerwaltungsModel.getLager().size() == 2 &&
				lagerVerwaltungsModel.getMaxFreieKapazitaet() == 200 &&
				lager1 != null && lager2 != null);
		assertEquals(true, result);
	}
	
	public void testHinzufuegenLagerUntereEbenen(){
		LagerModel lager1 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVerwaltungsModel.hinzufuegenLager(lager1, 200, "Unterlager1");
		boolean result = (lagerVerwaltungsModel.getLager().size() == 1 &&
				lagerVerwaltungsModel.getMaxFreieKapazitaet() == 300 &&
				lager1 != null && lager2 != null);
		assertEquals(true, result);
	}
	
	public void testUmbennenLager(){
		LagerModel lager1 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager1");
		lagerVerwaltungsModel.umbenennenLager(lager1, "Lager 2");
		int index = lagerVerwaltungsModel.getLager().indexOf(lager1);
		assertEquals("Lager 2", lagerVerwaltungsModel.getLager().get(index).getName());
	}
	
	public void testErstellenZuBuchung(){
		assertEquals(true, lagerVerwaltungsModel.erstellenZuBuchung(new Date(1), 100) && lagerVerwaltungsModel.getLaufendeBuchung() != null);
	}
	
	public void testErstellenZuBuchungMitLaufenderBuchung(){
		lagerVerwaltungsModel.erstellenZuBuchung(new Date(1), 100);
		assertEquals(false, lagerVerwaltungsModel.erstellenZuBuchung(new Date(1), 100));
	}
	
	public void testErstellenAbBuchung(){
		assertEquals(true, lagerVerwaltungsModel.erstellenAbBuchung(new Date(1)) && lagerVerwaltungsModel.getLaufendeBuchung() != null);
	}
	
	public void testErstellenAbBuchungMitLaufenderBuchung(){
		lagerVerwaltungsModel.erstellenAbBuchung(new Date(1));
		assertEquals(false, lagerVerwaltungsModel.erstellenAbBuchung(new Date(1)));
	}
	
	public void testHinzufuegenAnteil(){
		lagerVerwaltungsModel.erstellenZuBuchung(new Date(1), 100);
		LagerModel lager1 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager2");
		assertEquals(true,
				lagerVerwaltungsModel.hinzugegenAnteil(lager1, 50) &&
				lagerVerwaltungsModel.hinzugegenAnteil(lager2, 50) &&
				lagerVerwaltungsModel.getLaufendeBuchung().anteile.size() == 2 &&
				lagerVerwaltungsModel.getLaufendeBuchung().getVerteilteMenge() == 100);
	}
	
	public void testHinzufuegenAnteilMerhAlsBuchung(){
		lagerVerwaltungsModel.erstellenZuBuchung(new Date(1), 100);
		LagerModel lager1 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager2");
		assertEquals(true, lagerVerwaltungsModel.hinzugegenAnteil(lager1, 50));
		assertEquals(false, lagerVerwaltungsModel.hinzugegenAnteil(lager2, 60));
		assertEquals(true,
				lagerVerwaltungsModel.getLaufendeBuchung().anteile.size() == 1 &&
				lagerVerwaltungsModel.getLaufendeBuchung().getVerteilteMenge() == 50);
	}
	
	public void testLoeschenAnteil(){
		lagerVerwaltungsModel.erstellenZuBuchung(new Date(1), 100);
		LagerModel lager1 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager2");
		lagerVerwaltungsModel.hinzugegenAnteil(lager1, 50);
		lagerVerwaltungsModel.hinzugegenAnteil(lager2, 50);
		lagerVerwaltungsModel.loeschenAnteil(lager1, 50);
		assertEquals(true, lagerVerwaltungsModel.getLaufendeBuchung().getAnteile().size() == 1 &&
				lagerVerwaltungsModel.getLaufendeBuchung().getAnteile().get(0).getLager() == lager2 &&
				lagerVerwaltungsModel.getLaufendeBuchung().getAnteile().get(0).getAnteil() == 50);
	}
	
	public void testAbschliessenZuBuchung(){
		lagerVerwaltungsModel.erstellenZuBuchung(new Date(1), 100);
		LagerModel lager1 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager2");
		lagerVerwaltungsModel.hinzugegenAnteil(lager1, 50);
		lagerVerwaltungsModel.hinzugegenAnteil(lager2, 50);
		assertEquals(true, lagerVerwaltungsModel.abschliessenBuchung() &&
				lagerVerwaltungsModel.getBuchungen().size() == 1 &&
				lager1.getBestand() == 50 &&
				lager2.getBestand() == 50&&
				lagerVerwaltungsModel.getLaufendeBuchung() == null &&
				lagerVerwaltungsModel.buchungen.get(lagerVerwaltungsModel.getBuchungen().size()-1).getVerteilteMenge() == 100);
		assertEquals(true, lagerVerwaltungsModel.maxFreieKapazitaet == 100);
	}
	
	public void testAbschliessenZuBuchungNichtVollVerteilt(){
		lagerVerwaltungsModel.erstellenZuBuchung(new Date(1), 100);
		LagerModel lager1 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager2");
		lagerVerwaltungsModel.hinzugegenAnteil(lager1, 50);
		assertEquals(true, lagerVerwaltungsModel.abschliessenBuchung() == false &&
				lagerVerwaltungsModel.getBuchungen().size() == 0 &&
				lager1.getBestand() == 0 &&
				lager2.getBestand() == 0);
		assertEquals(true, lagerVerwaltungsModel.maxFreieKapazitaet == 200);
	}
	
	public void testAbschliessenAbBuchung(){
		lagerVerwaltungsModel.erstellenZuBuchung(new Date(1), 100);
		LagerModel lager1 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager2");
		lagerVerwaltungsModel.hinzugegenAnteil(lager1, 50);
		lagerVerwaltungsModel.hinzugegenAnteil(lager2, 50);
		lagerVerwaltungsModel.abschliessenBuchung();
		lagerVerwaltungsModel.erstellenAbBuchung(new Date(1));
		lagerVerwaltungsModel.hinzugegenAnteil(lager1, 50);
		lagerVerwaltungsModel.hinzugegenAnteil(lager2, 50);
		assertEquals(true, lagerVerwaltungsModel.abschliessenBuchung() &&
				lager1.getBestand() == 0 &&
				lager2.getBestand() == 0 &&
				lagerVerwaltungsModel.getLaufendeBuchung() == null);
	}
	
	public void testAbschliessenAbBuchungOhneAnteil(){
		lagerVerwaltungsModel.erstellenZuBuchung(new Date(1), 100);
		LagerModel lager1 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVerwaltungsModel.hinzufuegenLager(null, 100, "Oberlager2");
		lagerVerwaltungsModel.hinzugegenAnteil(lager1, 50);
		lagerVerwaltungsModel.hinzugegenAnteil(lager2, 50);
		lagerVerwaltungsModel.abschliessenBuchung();
		lagerVerwaltungsModel.erstellenAbBuchung(new Date(1));
		assertEquals(false, lagerVerwaltungsModel.abschliessenBuchung());
	}
	
	public void testInitialBefuellung(){
		lagerVerwaltungsModel.initialBefuellung();
		lagerVerwaltungsModel.hinzufuegenLager(null, 100, "hier debuggen!");
	}

}
