package core.exception;
/**@author Marius Mamsch
**/
public class MaxFreieKapazitaetUeberschritten extends Exception {

	public MaxFreieKapazitaetUeberschritten(){
		super();
	}
	public MaxFreieKapazitaetUeberschritten(String message){
		super(message);
	}
	public MaxFreieKapazitaetUeberschritten(String message, Throwable cause){
		super(message, cause);
	}
	public MaxFreieKapazitaetUeberschritten(Throwable cause){
		super(cause);
	}
}
