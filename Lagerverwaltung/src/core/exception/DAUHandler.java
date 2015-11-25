package core.exception;

import javax.swing.JOptionPane;

public class DAUHandler {
	//TODO Warnungsnummern als Konstante ier definieren

	public static void HandleWarning(int warnungsNummer)
	{
		JOptionPane.showMessageDialog(null, 
				warnungsNummer + ". Fehler in der Bedienung ", 
				"Fehler", 
				JOptionPane.ERROR_MESSAGE);
	}
	
}
