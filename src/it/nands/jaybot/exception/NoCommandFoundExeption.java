package it.nands.jaybot.exception;

/***
 * Eccezione personalizzata in caso di comando non trovato
 * 
 * @author a.franzi
 *
 */
public class NoCommandFoundExeption extends Exception{
	
	private static final long serialVersionUID = -3661572676719524469L;

	/***
	 * Costruttore di default
	 */
	public NoCommandFoundExeption(){
		super();
	}
	/**
	   * Genera un'eccezione con un messaggio di errore.
	   * 
	   * @param message il messaggio di errore.
	   */
	public NoCommandFoundExeption(String message){
		super(message);
	}
}
