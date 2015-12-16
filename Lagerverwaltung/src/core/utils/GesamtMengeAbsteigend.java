package core.utils;

import model.BuchungsModel;

public class GesamtMengeAbsteigend implements SortierStrategie{

	@Override
	public boolean sortiere(BuchungsModel a, BuchungsModel b) {
		if (a.getVerteilteMenge()>b.getVerteilteMenge()) {
			return true;
		} else {
			return false;
		}
	}
}
