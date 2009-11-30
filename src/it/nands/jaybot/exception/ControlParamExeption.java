package it.nands.jaybot.exception;

/***
 * Eccezione personalizzata in caso di problema con il controllo dei
 * parametri di un comando
 * 
 * @author a.franzi
 *
 */
public class ControlParamExeption extends Exception{
	
	private static final long serialVersionUID = -3661572676719524469L;

	/***
	 * Costruttore di default
	 */
	public ControlParamExeption(){
		super();
	}
	/**
	   * Genera un'eccezione con un messaggio di errore.
	   * 
	   * @param message il messaggio di errore.
	   */
	public ControlParamExeption(String message){
		super(message);
	}
}
