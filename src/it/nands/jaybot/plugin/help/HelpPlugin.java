package it.nands.jaybot.plugin.help;

import net.sf.jml.MsnContact;
import net.sf.jml.MsnSwitchboard;
import net.sf.jml.message.MsnInstantMessage;

import org.apache.log4j.Logger;


import it.nands.jaybot.bean.User;
import it.nands.jaybot.constant.CommandConstant;
import it.nands.jaybot.constant.MessagesConstant;
import it.nands.jaybot.plugin.configurator.exception.PluginException;
import it.nands.jaybot.plugin.impl.MessageHandlerPluginInterface;
import it.nands.jaybot.plugin.message.controller.MessagePlugin;
import it.nands.jaybot.plugin.paramcontroller.controller.ParamControllerPlugin;
import it.nands.jaybot.plugin.properties.constant.PropertiesConstant;
import it.nands.jaybot.plugin.properties.controller.PropertiesPlugin;
import it.nands.jaybot.util.MessageUtils;
import it.nands.jaybot.util.UserUtils;


/***
 * Plugin per i messaggi di Help
 * 
 * @author a.franzi
 *
 */
public class HelpPlugin implements MessageHandlerPluginInterface {
	private static Logger logger = Logger.getRootLogger();
	
	public Boolean chatMessageReceived(MsnSwitchboard switchboard,
			MsnInstantMessage message, MsnContact contact)
			throws PluginException {
		
		logger.info("INIZIO");
		boolean valid = false;
		
		String messaggio = message.getContent().trim();
		User utente = UserUtils.getUser(contact);
		
		try {
			if (ParamControllerPlugin.checkComandoMessaggio(messaggio,CommandConstant.HELP,utente)){
				String parsedMessage = MessagePlugin.getMessage
				(
							MessagesConstant.MESSAGE_HELP, 
							PropertiesPlugin.getValue
							(
									PropertiesConstant.MODULE_DEFAULT,
									PropertiesConstant.PROP_BOTNAME
							),
							ParamControllerPlugin.getPrintableCommandList()
				);
				
				// invio messaggio
				MessageUtils.sendMessage(parsedMessage, switchboard);
				
				valid = true;
			}
		} catch (Exception e) {
			logger.error("Eccezione : ",e);
			throw new PluginException(e.getMessage());
		} finally{
			logger.info("fine : valid "+valid);
		}
		logger.info("FINE - valid : "+valid);
		return valid;
		
	}

}
