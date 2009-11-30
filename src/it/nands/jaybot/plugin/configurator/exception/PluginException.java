package it.nands.jaybot.plugin.configurator.exception;

/**
 * @author a.pellegatta
 *
 */
public class PluginException extends Exception {

	private static final long serialVersionUID = 4294772434847709557L;

	/**
	 * 
	 */
	public PluginException() {
		super();
	}
	
	/**
	 * @param message
	 */
	public PluginException(String message){
		super(message);
	}
}
