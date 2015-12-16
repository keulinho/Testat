package core.utils;

import model.BuchungsModel;

public class TagAufsteigend implements SortierStrategie{

	@Override
	public boolean sortiere(BuchungsModel a, BuchungsModel b) {
	
		if (a.getBuchungsTag().getTime()<=b.getBuchungsTag().getTime()) {
			return true;
		} else {
			return false;
		}
	}
}
