package model;

import java.util.Date;
import junit.framework.TestCase;

public class LagerVerwaltungsModelTest extends TestCase{
	LagerVerwaltungsModel lagerVM;
	
	public void setUp() {
		lagerVM = new LagerVerwaltungsModel();
	}
	
	public void testHinzufuegenLagerOberlager(){
		LagerModel lager1 = lagerVM.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVM.hinzufuegenLager(null, 100, "Oberlager2");
		boolean result = (lagerVM.getLager().size() == 2 &&
				lagerVM.getMaxFreieKapazitaet() == 200 &&
				lager1 != null && lager2 != null);
		assertEquals(true, result);
	}
	
	public void testHinzufuegenLagerUntereEbenen(){
		LagerModel lager1 = lagerVM.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVM.hinzufuegenLager(lager1, 200, "Unterlager1");
		boolean result = (lagerVM.getLager().size() == 1 &&
				lagerVM.getMaxFreieKapazitaet() == 300 &&
				lager1 != null && lager2 != null);
		assertEquals(true, result);
	}
	
	public void testUmbennenLager(){
		LagerModel lager1 = lagerVM.hinzufuegenLager(null, 100, "Oberlager1");
		lagerVM.umbenennenLager(lager1, "Lager 2");
		int index = lagerVM.getLager().indexOf(lager1);
		assertEquals("Lager 2", lagerVM.getLager().get(index).getName());
	}
	
	public void testErstellenZuBuchung(){
		assertEquals(true, lagerVM.erstellenZuBuchung(new Date(1), 100) && lagerVM.getLaufendeBuchung() != null);
	}
	
	public void testErstellenZuBuchungMitLaufenderBuchung(){
		lagerVM.erstellenZuBuchung(new Date(1), 100);
		assertEquals(false, lagerVM.erstellenZuBuchung(new Date(1), 100));
	}
	
	public void testErstellenAbBuchung(){
		assertEquals(true, lagerVM.erstellenAbBuchung(new Date(1)) && lagerVM.getLaufendeBuchung() != null);
	}
	
	public void testErstellenAbBuchungMitLaufenderBuchung(){
		lagerVM.erstellenAbBuchung(new Date(1));
		assertEquals(false, lagerVM.erstellenAbBuchung(new Date(1)));
	}
	
	public void testHinzufuegenAnteil(){
		lagerVM.erstellenZuBuchung(new Date(1), 100);
		LagerModel lager1 = lagerVM.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVM.hinzufuegenLager(null, 100, "Oberlager2");
		assertEquals(true,
				lagerVM.hinzugegenAnteil(lager1, 50) &&
				lagerVM.hinzugegenAnteil(lager2, 50) &&
				lagerVM.getLaufendeBuchung().anteile.size() == 2 &&
				lagerVM.getLaufendeBuchung().getVerteilteMenge() == 100);
	}
	
	public void testHinzufuegenAnteilMerhAlsBuchung(){
		lagerVM.erstellenZuBuchung(new Date(1), 100);
		LagerModel lager1 = lagerVM.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVM.hinzufuegenLager(null, 100, "Oberlager2");
		assertEquals(true, lagerVM.hinzugegenAnteil(lager1, 50));
		assertEquals(false, lagerVM.hinzugegenAnteil(lager2, 60));
		assertEquals(true,
				lagerVM.getLaufendeBuchung().anteile.size() == 1 &&
				lagerVM.getLaufendeBuchung().getVerteilteMenge() == 50);
	}
	
	public void testLoeschenAnteil(){
		lagerVM.erstellenZuBuchung(new Date(1), 100);
		LagerModel lager1 = lagerVM.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVM.hinzufuegenLager(null, 100, "Oberlager2");
		lagerVM.hinzugegenAnteil(lager1, 50);
		lagerVM.hinzugegenAnteil(lager2, 50);
		lagerVM.loeschenAnteil(lager1, 50);
		assertEquals(true, lagerVM.getLaufendeBuchung().getAnteile().size() == 1 &&
				lagerVM.getLaufendeBuchung().getAnteile().get(0).getLager() == lager2 &&
				lagerVM.getLaufendeBuchung().getAnteile().get(0).getAnteil() == 50);
	}
	
	public void testAbschliessenZuBuchung(){
		lagerVM.erstellenZuBuchung(new Date(1), 100);
		LagerModel lager1 = lagerVM.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVM.hinzufuegenLager(null, 100, "Oberlager2");
		lagerVM.hinzugegenAnteil(lager1, 50);
		lagerVM.hinzugegenAnteil(lager2, 50);
		assertEquals(true, lagerVM.abschliessenBuchung() &&
				lagerVM.getBuchungen().size() == 1 &&
				lager1.getBestand() == 50 &&
				lager2.getBestand() == 50&&
				lagerVM.getLaufendeBuchung() == null);
		assertEquals(true, lagerVM.maxFreieKapazitaet == 100);
	}
	
	public void testAbschliessenZuBuchungNichtVollVerteilt(){
		lagerVM.erstellenZuBuchung(new Date(1), 100);
		LagerModel lager1 = lagerVM.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVM.hinzufuegenLager(null, 100, "Oberlager2");
		lagerVM.hinzugegenAnteil(lager1, 50);
		assertEquals(true, lagerVM.abschliessenBuchung() == false &&
				lagerVM.getBuchungen().size() == 0 &&
				lager1.getBestand() == 0 &&
				lager2.getBestand() == 0);
		assertEquals(true, lagerVM.maxFreieKapazitaet == 200);
	}
	
	public void testAbschliessenAbBuchung(){
		lagerVM.erstellenZuBuchung(new Date(1), 100);
		LagerModel lager1 = lagerVM.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVM.hinzufuegenLager(null, 100, "Oberlager2");
		lagerVM.hinzugegenAnteil(lager1, 50);
		lagerVM.hinzugegenAnteil(lager2, 50);
		lagerVM.abschliessenBuchung();
		lagerVM.erstellenAbBuchung(new Date(1));
		lagerVM.hinzugegenAnteil(lager1, 50);
		lagerVM.hinzugegenAnteil(lager2, 50);
		assertEquals(true, lagerVM.abschliessenBuchung() &&
				lager1.getBestand() == 0 &&
				lager2.getBestand() == 0 &&
				lagerVM.getLaufendeBuchung() == null);
	}
	
	public void testAbschliessenAbBuchungOhneAnteil(){
		lagerVM.erstellenZuBuchung(new Date(1), 100);
		LagerModel lager1 = lagerVM.hinzufuegenLager(null, 100, "Oberlager1");
		LagerModel lager2 = lagerVM.hinzufuegenLager(null, 100, "Oberlager2");
		lagerVM.hinzugegenAnteil(lager1, 50);
		lagerVM.hinzugegenAnteil(lager2, 50);
		lagerVM.abschliessenBuchung();
		lagerVM.erstellenAbBuchung(new Date(1));
		assertEquals(false, lagerVM.abschliessenBuchung());
	}
	
	public void testInitialBefuellung(){
		lagerVM.initialBefuellung();
		lagerVM.hinzufuegenLager(null, 100, "hier debuggen!");
	}

}
