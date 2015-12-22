package model;

import org.junit.Test;
import junit.framework.TestCase;
/**@author Niklas Devenish
**/
public class AnteilModelTest extends TestCase {
	LagerModel lager;
	AnteilModel anteil;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		lager = new LagerModel(100, "lager1", null);
		anteil = new AnteilModel(lager, 100);
	}

	public void testAnteilErhoehen() {
		anteil.erhoehenAnteil(100);
		assertEquals(200, anteil.getAnteil());
	}

}
