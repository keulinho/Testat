package core.sortierer;

import model.BuchungsModel;

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
