package core.exception;
/**@author Marius Mamsch
**/
public class LagerHatZuKleineKapazitaet extends Exception {
	public LagerHatZuKleineKapazitaet(){
		super();
	}
	public LagerHatZuKleineKapazitaet(String message){
		super(message);
	}
	public LagerHatZuKleineKapazitaet(String message, Throwable cause){
		super(message, cause);
	}
	public LagerHatZuKleineKapazitaet(Throwable cause){
		super(cause);
	}
}
