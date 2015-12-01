package model;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import org.junit.Test;

import junit.framework.TestCase;

public class ZuBuchungsModelTest extends TestCase{
	LagerModel lager1, lager2;
	AnteilModel anteil;
	ZuBuchungsModel zuBuchung;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		lager1 = new LagerModel(100, "lager1", null);
		lager2 = new LagerModel(100, "lager2", null);
		zuBuchung = new ZuBuchungsModel(new Date(2015, 11, 26, 22, 46, 35), 500);
	}
	
	public void testHinzufuegenAnteil(){
		boolean result = (zuBuchung.hinzufuegenAnteil(lager1, 50) != null ==
				(zuBuchung.getAnteile().size() == 1 && 
				zuBuchung.getAnteile().get(0).getAnteil() == 50 && 
				zuBuchung.getAnteile().get(0).getLager() == lager1));
		assertEquals(true, result);
	}
	
	public void testHinzufuegenAnteilMehrAlsPlatzImLager(){
		zuBuchung.hinzufuegenAnteil(lager1, 50);
		assertEquals(false, zuBuchung.hinzufuegenAnteil(lager1, 500));
	}
	
	public void testloeschenAnteile(){
		zuBuchung.hinzufuegenAnteil(lager1, 50);
		zuBuchung.hinzufuegenAnteil(lager1, 50);
		zuBuchung.hinzufuegenAnteil(lager1, 50);
		zuBuchung.hinzufuegenAnteil(lager1, 50);
		zuBuchung.hinzufuegenAnteil(lager1, 50);
		zuBuchung.hinzufuegenAnteil(lager1, 50);
		zuBuchung.hinzufuegenAnteil(lager1, 50);
		zuBuchung.hinzufuegenAnteil(lager1, 50);
		zuBuchung.hinzufuegenAnteil(lager1, 50);
		zuBuchung.loeschenAnteile();
		assertEquals(true, zuBuchung.getAnteile().isEmpty());
	}
	
	public void testloeschenAnteilLager(){
		zuBuchung.hinzufuegenAnteil(lager1, 50);
		zuBuchung.hinzufuegenAnteil(lager2, 50);
		zuBuchung.loeschenAnteil(lager1);
		boolean result = (zuBuchung.getAnteile().size() == 1 && 
				zuBuchung.getAnteile().get(0).getAnteil() == 50 && 
				zuBuchung.getAnteile().get(0).getLager() == lager2);
		assertEquals(true, result);
		
	}
	
	public void testloeschenAnteilSpezifisch(){
		zuBuchung.loeschenAnteile();
		zuBuchung.hinzufuegenAnteil(lager2, 50);
		zuBuchung.hinzufuegenAnteil(lager2, 100);
		zuBuchung.loeschenAnteil(lager2, 100);
		boolean result = (zuBuchung.getAnteile().size() == 1 && 
				zuBuchung.getAnteile().get(0).getAnteil() == 50 && 
				zuBuchung.getAnteile().get(0).getLager() == lager2);
		assertEquals(true, result);
		
	}
}
