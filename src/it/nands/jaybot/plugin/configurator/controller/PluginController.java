package it.nands.jaybot.plugin.configurator.controller;

import it.nands.jaybot.bean.User;
import it.nands.jaybot.exception.ControlParamExeption;
import it.nands.jaybot.exception.NoCommandFoundExeption;
import it.nands.jaybot.plugin.configurator.exception.PluginException;
import it.nands.jaybot.plugin.configurator.util.PluginUtils;
import it.nands.jaybot.plugin.impl.MessageHandlerPluginInterface;
import it.nands.jaybot.util.UserUtils;

import net.sf.jml.MsnContact;
import net.sf.jml.MsnSwitchboard;
import net.sf.jml.message.MsnInstantMessage;

import org.apache.log4j.Logger;

/***
 * Plugin per il controllo dei comandi registrati per i plugin
 * 
 * N.B = Serve solo ad identificare l'esistenza di un comando che matcha il template,
 * il comando non viene gestito, ma validato, fino a livello di parametri
 * 
 * @author Alessandro Franzi
 *
 */
public class PluginController implements MessageHandlerPluginInterface {
	private static Logger logger = Logger.getRootLogger();
	
	/***
	 * chatMessageReceived
	 */
	public Boolean chatMessageReceived(MsnSwitchboard switchboard,
			MsnInstantMessage message, MsnContact contact)
			throws PluginException {
		
		boolean valid = false;
		
		String messaggio = message.getContent().trim();
		User utente = UserUtils.getUser(contact);
		logger.info("INIZIO");
		
		try {
			PluginUtils.checkCommand(messaggio,utente);
			valid = true;
		} catch (ControlParamExeption e){
			throw new PluginException(e.getMessage());
		}catch (NoCommandFoundExeption e) {
			logger.info("Nessun comando trovato");
			valid = false;
		}
		finally{
			
		}
		
		logger.info("FINE - valid : "+valid);
		return valid;
		
	}
}
