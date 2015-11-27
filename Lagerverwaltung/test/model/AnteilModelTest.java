package model;

import org.junit.Test;
import junit.framework.TestCase;

public class AnteilModelTest extends TestCase {
	LagerModel lager;
	AnteilModel anteil;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		lager = new LagerModel(100, "lager1", null);
		anteil = new AnteilModel(lager, 100);
	}

	@Test
	public void testAnteilErhoehen() {
		anteil.erhoehenAnteil(100);
		assertEquals(200, anteil.getAnteil());
	}

}
