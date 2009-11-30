package it.nands.jaybot.plugin.message.controller;

import org.apache.log4j.Logger;

import it.nands.jaybot.plugin.configurator.exception.PluginException;

/***
 * Utilità per l'invio dei messaggi
 * @author a.franzi
 *
 */
public class MessageUtils {
	
	/***
	 * Metodo per recuperare un messaggio
	 * @param nomeMessaggio	: nome messaggio
	 * @param parametri		: parametri da sostituire ai token
	 * @return				: messaggio parsato
	 * @throws PluginException : eccezione del plugin
	 */
	protected static String getMessage(String nomeMessaggio,String ... parametri) throws PluginException{
		return MessagePlugin.getMessage(nomeMessaggio,parametri);
	}
}
