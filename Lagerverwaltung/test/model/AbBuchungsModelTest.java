package model;

import java.util.Date;

import org.junit.Test;

import junit.framework.TestCase;

public class AbBuchungsModelTest extends TestCase {
	LagerModel lager1, lager2;
	AnteilModel anteil;
	AbBuchungsModel abBuchung;
	
	@Override
	public void setUp() throws Exception{
		super.setUp();
		lager1 = new LagerModel(1000, "lager1", null);
		lager1.veraendernBestand(100);
		lager2 = new LagerModel(1000, "lager2", null);
		lager2.veraendernBestand(100);
		abBuchung = new AbBuchungsModel(new Date(2015, 11, 26, 22, 46, 35));
		
	}
	
	//TODO test klappen nicht weil Anteil nicht geaddet wird weil kein Bestand vorhanden ist
	@Test
	public void testHinzufuegenAnteil(){
		boolean result = (abBuchung.hinzufuegenAnteil(lager1, 50) &&
				(abBuchung.getAnteile().size() == 1 && 
				abBuchung.getAnteile().get(0).getAnteil() == -50 && 
				abBuchung.getAnteile().get(0).getLager() == lager1));
		assertEquals(true, result);
	}
	
	@Test
	public void testHinzufuegenAnteilWenigerAlsImLager(){
		abBuchung.hinzufuegenAnteil(lager1, 50);
		assertEquals(false, abBuchung.hinzufuegenAnteil(lager1, 500));
	}
	
	@Test
	public void testloeschenAnteile(){
		abBuchung.hinzufuegenAnteil(lager1, 50);
		abBuchung.hinzufuegenAnteil(lager1, 50);
		abBuchung.hinzufuegenAnteil(lager1, 50);
		abBuchung.hinzufuegenAnteil(lager1, 50);
		abBuchung.hinzufuegenAnteil(lager1, 50);
		abBuchung.hinzufuegenAnteil(lager1, 50);
		abBuchung.hinzufuegenAnteil(lager1, 50);
		abBuchung.hinzufuegenAnteil(lager1, 50);
		abBuchung.hinzufuegenAnteil(lager1, 50);
		abBuchung.loeschenAnteile();
		assertEquals(true, abBuchung.getAnteile().isEmpty());
	}

	@Test
	public void testloeschenAnteilLager(){
		if(!abBuchung.getAnteile().isEmpty()){
			abBuchung.loeschenAnteile();
		}
		abBuchung.hinzufuegenAnteil(lager1, 50);
		abBuchung.hinzufuegenAnteil(lager2, 50);
		abBuchung.loeschenAnteil(lager1);
		boolean result = (abBuchung.getAnteile().size() == 1 &&
				abBuchung.getAnteile().get(0).getAnteil() == -50 &&
				abBuchung.getAnteile().get(0).getLager() == lager2);
		assertEquals(true, result);
	}
	
	@Test
	public void testloeschenAnteilSpezifisch(){
		abBuchung.hinzufuegenAnteil(lager2, 50);
		abBuchung.hinzufuegenAnteil(lager2, 100);
		abBuchung.loeschenAnteil(lager2, 100);
		boolean result = (abBuchung.getAnteile().size() == 1 && 
				abBuchung.getAnteile().get(0).getAnteil() == -50 && 
				abBuchung.getAnteile().get(0).getLager() == lager2);
		assertEquals(true, result);
		
	}

}
