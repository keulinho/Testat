package model;

import java.util.Date;

import junit.framework.TestCase;
/**@author Niklas Devenish
**/
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
	public void testHinzufuegenAnteil(){
		boolean result = (abBuchung.hinzufuegenAnteil(lager1, 50) != null &&
				(abBuchung.getAnteile().size() == 1 && 
				abBuchung.getAnteile().get(0).getAnteil() == -50 && 
				abBuchung.getAnteile().get(0).getLager() == lager1));
		assertEquals(true, result);
	}
	
	public void testHinzufuegenAnteilWenigerAlsImLager(){
		abBuchung.hinzufuegenAnteil(lager1, 50);
		assertEquals(true, null==abBuchung.hinzufuegenAnteil(lager1, 500));
	}
	
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
		abBuchung.loeschenAlleAnteile();
		assertEquals(true, abBuchung.getAnteile().isEmpty());
	}

	public void testloeschenAnteilLager(){
		if(!abBuchung.getAnteile().isEmpty()){
			abBuchung.loeschenAlleAnteile();
		}
		abBuchung.hinzufuegenAnteil(lager1, 50);
		abBuchung.hinzufuegenAnteil(lager2, 50);
		abBuchung.loeschenAnteile(lager1);
		boolean result = (abBuchung.getAnteile().size() == 1 &&
				abBuchung.getAnteile().get(0).getAnteil() == -50 &&
				abBuchung.getAnteile().get(0).getLager() == lager2);
		assertEquals(true, result);
	}
	
	public void testloeschenAnteilSpezifisch(){
		abBuchung.hinzufuegenAnteil(lager2, 50);
		abBuchung.hinzufuegenAnteil(lager2, 100);
		abBuchung.loeschenAnteil(lager2, 100);
		boolean result = (abBuchung.getAnteile().size() == 1 && 
				abBuchung.getAnteile().get(0).getAnteil() == -50 && 
				abBuchung.getAnteile().get(0).getLager() == lager2);
		assertEquals(true, result);
	}
	
	public void testIsFetig(){
		abBuchung.hinzufuegenAnteil(lager1, 10);
		assertEquals(true, abBuchung.isFertig());
	}
	
	public void testIsFertigBuchungLeer(){
		assertEquals(false, abBuchung.isFertig());
	}

}
