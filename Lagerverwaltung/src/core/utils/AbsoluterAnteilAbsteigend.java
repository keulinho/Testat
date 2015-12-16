package core.utils;

import model.BuchungsModel;
/**@author Marius Mamsch
**/
public class AbsoluterAnteilAbsteigend implements SortierStrategie{

	@Override
	public boolean sortiere(BuchungsModel a, BuchungsModel b) {
		if (a.getAnteile().get(0).getAnteil()>b.getAnteile().get(0).getAnteil()) {
			return true;
		} else {
			return false;
		}
	}
}
