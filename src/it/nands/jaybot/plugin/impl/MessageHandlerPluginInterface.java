package it.nands.jaybot.plugin.impl;

import it.nands.jaybot.plugin.configurator.exception.PluginException;
import net.sf.jml.MsnContact;
import net.sf.jml.MsnSwitchboard;
import net.sf.jml.message.MsnInstantMessage;

/**
 * Interfaccia dei plugin addetti alla gestione dei messaggi in input
 * @author a.franzi
 *
 */
public interface MessageHandlerPluginInterface {

	/***
	 * Metodo invocato sull'evento : messaggio di chat ricevuto
	 * 
	 * @param switchboard			: switchboard
	 * @param message				: messaggio
	 * @param contact				: contact
	 * @return						: boolean
	 * @throws PluginException		: eccezione del plugin
	 */
	public Boolean chatMessageReceived(MsnSwitchboard switchboard,MsnInstantMessage message, MsnContact contact) throws PluginException;
}
