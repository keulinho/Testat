package model;

import java.util.Date;

import org.junit.Test;

import core.exception.LagerUeberfuelltException;
import junit.framework.TestCase;

public class LagerModelTest extends TestCase{
	LagerModel lager1;
	LagerModel lager2;
	LagerModel lager3;
	
	@Override
	public void setUp(){
		lager1 = new LagerModel(1000, "Lager1", null);
		lager2 = new LagerModel(1000, "Lager2", null);
		lager3 = new LagerModel(1000, "Lager3", lager2);
	}
	
	@Test
	public void testVeraendernBestand() throws LagerUeberfuelltException{
		lager1.veraendernBestand(100);
		assertEquals(100, lager1.getBestand());
	}
	
	@Test
	public void testVeraendernBestandMehrAlsPlatzInLager(){
		try {
			lager1.veraendernBestand(1100);
			fail("Es wurde keine Exception geworfen!");
		} catch (LagerUeberfuelltException e) {
			
		}
	}
	
	@Test
	public void testAnpassenOberlagerBestand() throws LagerUeberfuelltException{
		lager3.anpassenOberlagerBestand(100);
		assertEquals(100, lager3.getOberLager().getBestand());
	}
	
	@Test
	public void testAddUnterlager(){
		LagerModel lagerNeu = lager1.addUnterlager(100, "LagerNeu");
		boolean result = (lagerNeu.getOberLager() == lager1 &&
				lagerNeu.getName().equalsIgnoreCase("LagerNeu") &&
				lagerNeu.getMaxKapazitaet() == 100);
		assertEquals(true, result);
	}
	
	@Test
	public void testAendernKapazitaet(){
		lager1.aendernkapazitaet(500);
		assertEquals(1500, lager1.getMaxKapazitaet());
	}
	
	@Test
	public void testAddBuchung(){
		AbBuchungsModel ab = new AbBuchungsModel(new Date(1));
		ZuBuchungsModel zu = new ZuBuchungsModel(new Date(1), 1);
		lager1.addBuchung(ab);
		lager1.addBuchung(zu);
		assertEquals(2, lager1.getBuchungen().size());
	}
	
	@Test
	public void testEntfernenBuchung(){
		AbBuchungsModel ab = new AbBuchungsModel(new Date(1));
		ZuBuchungsModel zu = new ZuBuchungsModel(new Date(1), 1);
		lager1.addBuchung(ab);
		lager1.addBuchung(zu);
		lager1.entfernenBuchung(ab);
		lager1.entfernenBuchung(zu);
		assertEquals(0, lager1.getBuchungen().size());
	}
}
