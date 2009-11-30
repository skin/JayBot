package it.nands.jaybot.exception;

/***
 * Eccezione personalizzata in caso di problema con l'inizializzazione del bot
 * 
 * @author a.franzi
 *
 */
public class JavaBotInitializingExeption extends Exception{
	
	private static final long serialVersionUID = -3661572676719524469L;

	/***
	 * Costruttore di default
	 */
	public JavaBotInitializingExeption(){
		super();
	}
	/**
	   * Genera un'eccezione con un messaggio di errore.
	   * 
	   * @param message il messaggio di errore.
	   */
	public JavaBotInitializingExeption(String message){
		super(message);
	}
}
