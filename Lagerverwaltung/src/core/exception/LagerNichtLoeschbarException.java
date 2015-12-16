package core.exception;
/**@author Marius Mamsch
**/
public class LagerNichtLoeschbarException extends Exception {

	public LagerNichtLoeschbarException(){
		super();
	}
	public LagerNichtLoeschbarException(String message){
		super(message);
	}
	public LagerNichtLoeschbarException(String message, Throwable cause){
		super(message, cause);
	}
	public LagerNichtLoeschbarException(Throwable cause){
		super(cause);
	}
}
