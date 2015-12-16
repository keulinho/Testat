package core.exception;

import javax.swing.JOptionPane;

public class ErrorHandler {
	//TODO Fehlernummern hier als Konstanten definieren
	public final static int LAGER_VOLL = 1;
	public final static int BILD_NICHT_GEFUNDEN = 2;
	public final static int MAX_FREIE_KAPAZITAET_UEBERSCHRITTEN = 3;
	public final static int LAGER_IST_NICHT_LEER = 4;
	public final static int FEHLER_DATEIVERARBEITUNG = 5;
	public final static int LAGER_MUSS_MIT_MEHR_KAPAZITAET_ERSTELLT_WERDEN = 6;
	
	
	public static void HandleException(int Fehlernummer, Exception e)
	{
		JOptionPane.showMessageDialog(null, 
				Fehlernummer + ". Es ist ein Fehler aufgetreten: " + e.getMessage(), 
				"Fehler", 
				JOptionPane.ERROR_MESSAGE);
		
		e.printStackTrace();
	}
	
}
