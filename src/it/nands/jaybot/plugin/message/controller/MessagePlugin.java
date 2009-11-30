package it.nands.jaybot.plugin.message.controller;

import org.apache.log4j.Logger;

import it.nands.jaybot.constant.MessagesConstant;
import it.nands.jaybot.plugin.configurator.exception.PluginException;
import it.nands.jaybot.plugin.configurator.exception.PluginInitializeException;

/***
 * Classe per la gestione del plugin dei messaggi
 * @author a.franzi
 *
 */
public class MessagePlugin {
	private static Logger logger = Logger.getRootLogger();
	/***
	 * Metodo per recuperare un messaggio di cui si specifica il nome ed i valori
	 * dei tokens : 
	 * - parametro 1 sostituisce token %1
	 * - parametro 2 sostituisce token %2
	 * - ......
	 * @param nomeMessaggio		: nome del messaggio
	 * @param parametri			: parametri con cui sostituire i token
	 * @return					: stringa dopo il parsing
	 * @throws PluginException	: eccezione nel recupero del messaggio
	 */
	public static String getMessage(String nomeMessaggio,String ... parametri) throws PluginException{
		try {
			return MessageLoader.getInstance().getMessage(nomeMessaggio, parametri);
		} catch (PluginInitializeException e) {
			throw new PluginException(e.getMessage());
		}
	}
	/***
	 * Metodo per recuperare un messaggio di errore di cui si specifica il nome e l'errore
	 * dei tokens : 
	 * - errore 1 sostituisce token %1
	 * - ......
	 * @param nomeMessaggio		: nome del messaggio
	 * @param errore			: eccezione
	 * @return					: messaggio di errore
	 */
	public static String getError(String nomeMessaggio,Exception errore){
		try {
			String errorMessage = "";
			if (errore!=null){
				errorMessage = errore.getMessage();
			}
			return MessageLoader.getInstance().getMessage(nomeMessaggio, errorMessage);
		} catch (PluginInitializeException e) {
			logger.error("Eccezione : ",e);
		}
		return "";
	}
	
	/***
	 * Metodo per recuperare il messaggio di errore associato all'eccezione
	 * dovuta ad un plugin
	 * @param errore	: Eccezione
	 * @return			: messaggio di errore
	 */
	public static String getPluginError(Exception errore){
		return getError(MessagesConstant.ERROR_PLUGIN_INIT, errore);
	}
}
