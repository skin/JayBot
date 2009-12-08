package it.nands.jaybot.plugin.quit;

import it.nands.jaybot.bean.User;
import it.nands.jaybot.bot.JavaBotMessenger;
import it.nands.jaybot.constant.CommandConstant;
import it.nands.jaybot.constant.MessagesConstant;
import it.nands.jaybot.exception.JavaBotInitializingExeption;
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
 * Plugin per eseguire il quit
 * @author a.franzi
 *
 */
public class QuitPlugin implements MessageHandlerPluginInterface {
	private static Logger logger = Logger.getRootLogger();
	
	public Boolean chatMessageReceived(MsnSwitchboard switchboard,
			MsnInstantMessage message, MsnContact contact)
			throws PluginException {
		
		logger.info("INIZIO");
		boolean valid = false;
		
		String messaggio = message.getContent().trim();
		User utente = UserUtils.getUser(contact);
		
		try {
			if (PluginUtils.checkComandoMessaggio(messaggio,CommandConstant.QUIT,utente)){
				valid = true;
				if (JavaBotMessenger.getInstance().isIdle()){
					logger.info("Bot IDLE : Shutting down");
					System.out.println("Bot IDLE : Shutting down");
					
					String parsedMessage = MessagePlugin.getMessage
					(
						MessagesConstant.MESSAGE_QUIT, 
						JavaBotMessenger.getInstance().getNick()
					);
					// invio messaggio
					MessageUtils.sendMessage(parsedMessage, switchboard);
					
					JavaBotMessenger.getInstance().quit();
					
				}
			}
		} catch (JavaBotInitializingExeption e) {
			logger.error("Eccezione : ",e);
			throw new PluginException(e.getMessage());
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
