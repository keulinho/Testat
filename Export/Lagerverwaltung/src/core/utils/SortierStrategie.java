package core.utils;

import model.BuchungsModel;
/**@author Marius Mamsch
**/
public interface SortierStrategie {

	/**
	 * vergleicht die beiden Buchungen
	 * @param a zu vergleichendes BuchungsModel
	 * @param b zu vergleichendes BuchungsModel
	 * @return true wenn a zuerst angezeigt werden muss
	 */
	public boolean sortiere(BuchungsModel a, BuchungsModel b);
}
