package it.nands.jaybot.plugin.answer;

import it.nands.jaybot.bean.User;
import it.nands.jaybot.constant.CommandConstant;
import it.nands.jaybot.constant.MessagesConstant;
import it.nands.jaybot.plugin.configurator.exception.PluginException;
import it.nands.jaybot.plugin.configurator.util.PluginUtils;
import it.nands.jaybot.plugin.impl.MessageHandlerPluginInterface;
import it.nands.jaybot.plugin.message.controller.MessagePlugin;
import it.nands.jaybot.util.MessageUtils;
import it.nands.jaybot.util.UserUtils;
import net.sf.jml.MsnContact;
import net.sf.jml.MsnSwitchboard;
import net.sf.jml.message.MsnInstantMessage;

import org.apache.log4j.Logger;

/***
 * Plugin per la gestione delle risposte generiche da parte del bot
 * Es. Ciao -> Ciao a te [nickname]
 * 
 * @author a.franzi
 *
 */
public class AnswerPlugin implements MessageHandlerPluginInterface {
	private static Logger logger = Logger.getRootLogger();
	
	/***
	 * chatMessageReceived
	 */
	public Boolean chatMessageReceived(MsnSwitchboard switchboard,
			MsnInstantMessage message, MsnContact contact)
			throws PluginException {
		
		logger.info("INIZIO");
		boolean valid = false;
		
		String messaggio = message.getContent().trim();
		User utente = UserUtils.getUser(contact);
		
		try {
			// CIAO
			if (PluginUtils.checkComandoMessaggio(messaggio,CommandConstant.CIAO,utente)){
				valid = true;
				
				String parsedMessage = MessagePlugin.getMessage
				(
					MessagesConstant.MESSAGE_CIAO, 
					utente.getUserName()
				);
				
				// invio messaggio
				MessageUtils.sendMessage(parsedMessage, switchboard);
			}
			
			
		}catch (Exception e) {
			logger.error("Eccezione : ",e);
			throw new PluginException(e.getMessage());
		} finally{
			logger.info("fine : valid "+valid);
		}
		logger.info("FINE - valid : "+valid);
		return valid;
		
	}

}
