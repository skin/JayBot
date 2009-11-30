package it.nands.jaybot.plugin.configurator.exception;

/***
 * Eccezione in caso di problemi con l'inizializzazione dei plugins
 * @author Alessandro Franzi
 *
 */
public class PluginInitializeException extends Exception{

	private static final long serialVersionUID = 1120877951839907974L;
	/***
	 * Costruttore di default
	 */
	public PluginInitializeException(){
		super();
	}
	/**
	   * Genera un'eccezione con un messaggio di errore.
	   * 
	   * @param message il messaggio di errore.
	   */
	public PluginInitializeException(String message){
		super(message);
	}
}
