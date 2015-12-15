package core.exception;

import javax.swing.JOptionPane;

public class ErrorHandler {
	//TODO Fehlernummern hier als Konstanten definieren
	public final static int LAGER_VOLL = 1;
	public final static int MAX_FREIE_KAPAZITAET_UEBERSCHRITTEN = 3;
	
	
	public static void HandleException(int Fehlernummer, Exception e)
	{
		JOptionPane.showMessageDialog(null, 
				Fehlernummer + ". Es ist ein Fehler aufgetreten: " + e.getMessage(), 
				"Fehler", 
				JOptionPane.ERROR_MESSAGE);
		
		e.printStackTrace();
	}
	
}
