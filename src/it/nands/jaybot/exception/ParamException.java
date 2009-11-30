package it.nands.jaybot.exception;

/***
 * Eccezione personalizzata in caso di problema con i parametri di un comando
 * 
 * @author a.franzi
 *
 */
public class ParamException extends Exception{
	
	private static final long serialVersionUID = -3661572676719524469L;

	/***
	 * Costruttore di default
	 */
	public ParamException(){
		super();
	}
	/**
	   * Genera un'eccezione con un messaggio di errore.
	   * 
	   * @param message il messaggio di errore.
	   */
	public ParamException(String message){
		super(message);
	}
}
