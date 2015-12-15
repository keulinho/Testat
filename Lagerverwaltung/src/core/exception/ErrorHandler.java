package core.exception;

import javax.swing.JOptionPane;

public class ErrorHandler {
	//TODO Fehlernummern hier als Konstanten definieren
	public final static int LAGER_VOLL = 1;
	public final static int BILD_NICHT_GEFUNDEN = 2;
	
	
	public static void HandleException(int Fehlernummer, Exception e)
	{
		JOptionPane.showMessageDialog(null, 
				Fehlernummer + ". Es ist ein Fehler aufgetreten: " + e.getMessage(), 
				"Fehler", 
				JOptionPane.ERROR_MESSAGE);
		
		e.printStackTrace();
	}
	
}
