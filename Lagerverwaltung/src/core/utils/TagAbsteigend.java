package core.utils;

import model.BuchungsModel;
/**@author Marius Mamsch
**/
public class TagAbsteigend implements SortierStrategie{

	@Override
	public boolean sortiere(BuchungsModel a, BuchungsModel b) {
		if (a.getBuchungsTag().getTime()>=b.getBuchungsTag().getTime()) {
			return true;
		} else {
			return false;
		}
	}
}
