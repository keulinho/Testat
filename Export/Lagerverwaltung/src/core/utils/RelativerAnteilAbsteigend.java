package core.utils;

import model.BuchungsModel;
/**@author Marius Mamsch
**/
public class RelativerAnteilAbsteigend implements SortierStrategie{

	@Override
	public boolean sortiere(BuchungsModel a, BuchungsModel b) {
		if (((1.00*a.getAnteile().get(0).getAnteil())/a.getVerteilteMenge())>((1.00*b.getAnteile().get(0).getAnteil())/b.getVerteilteMenge())) {
			return true;
		} else {
			return false;
		}
	}
}
