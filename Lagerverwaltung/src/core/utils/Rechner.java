package core.utils;
/**@author Marius Mamsch
**/
public class Rechner {
	public static double rechneProzent(int anteil, int gesamt) {
		double prozent = (Double.parseDouble("" + anteil)/(Double.parseDouble(""+ gesamt))*100.00);
		prozent = (prozent*1000)+5;
		int temp = (int) (prozent/10);
		prozent = (double)temp/100.00;
		return prozent;
	}
}
