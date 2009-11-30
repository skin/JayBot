package it.nands.jaybot.plugin.configurator.exception;

/***
 * Eccezione in caso di problemi con il controllo dei plugins
 * @author Alessandro Franzi
 *
 */
public class PluginChekerException extends Exception {

	private static final long serialVersionUID = 7266549931358702672L;

	/***
	 * Costruttore di default
	 */
	public PluginChekerException() {
		super();
	}
	
	/**
	 * Genera un'eccezione con un messaggio di errore.
	 * 
	 * @param message il messaggio di errore.
	 */
	public PluginChekerException(String message){
		super(message);
	}
}
