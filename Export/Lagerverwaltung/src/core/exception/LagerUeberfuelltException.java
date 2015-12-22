package core.exception;
/**@author Marius Mamsch
**/
public class LagerUeberfuelltException extends Exception {

	public LagerUeberfuelltException(){
		super();
	}
	public LagerUeberfuelltException(String message){
		super(message);
	}
	public LagerUeberfuelltException(String message, Throwable cause){
		super(message, cause);
	}
	public LagerUeberfuelltException(Throwable cause){
		super(cause);
	}
}
