package core.sortierer;

import model.BuchungsModel;

public class TagAufsteigend implements SortierStrategie{

	@Override
	public boolean sortiere(BuchungsModel a, BuchungsModel b) {
		if (a.getBuchungsTag().before(b.getBuchungsTag())) {
			return true;
		} else {
			return false;
		}
	}
}
