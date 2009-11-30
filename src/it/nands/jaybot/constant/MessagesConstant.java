package it.nands.jaybot.constant;

/***
 * Classe per lo storage delle costanti relative ai messaggi
 * @author a.franzi
 *
 */
public class MessagesConstant {
	
	public static final String MESSAGE_HELP = "help";
	public static final String MESSAGE_QUIT = "quit";
	public static final String MESSAGE_QUIT_BUSY = "quit_busy";
	public static final String MESSAGE_CIAO= "ciao";
	
	// messaggio comando sconosciuto
	public static final String MESSAGE_UNKNOWN_COMMAND="unknownCommand";
	
	// messaggio per deploy gia richiesto
	public static final String MESSAGE_ALREADY_ASKED_DEPLOY = "messageAlreadyAskedDeploy";
	
	// messaggio per esecuzione deploy
	public static final String MESSAGE_DEPLOY_IN_EXECUTION = "deployExecuting";
	
	// messaggio per notificare l'accettazione di una richiesta di put di un file
	public static final String MESSAGE_PUT_REQUEST_ACCEPTED = "putRequestAccepted";
	
	// ERRORI -----------------------
	
	// messaggio per un errore del plugin
	public static final String ERROR_PLUGIN_INIT = "pluginInitializeException";
	
	
}
