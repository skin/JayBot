package it.nands.jaybot.plugin.configurator.exception;

/***
 * Eccezione in caso di problemi con la duplicazione dei plugins
 * @author Alessandro Franzi
 *
 */
public class ReplicatePluginException extends Exception {

	private static final long serialVersionUID = 7266549931358702672L;

	/***
	 * Costruttore di default
	 */
	public ReplicatePluginException() {
		super();
	}
	
	/**
	 * Genera un'eccezione con un messaggio di errore.
	 * 
	 * @param message il messaggio di errore.
	 */
	public ReplicatePluginException(String message){
		super(message);
	}
}
