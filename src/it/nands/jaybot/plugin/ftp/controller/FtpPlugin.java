package it.nands.jaybot.plugin.ftp.controller;


import java.util.Map;

import net.sf.jml.MsnContact;
import net.sf.jml.MsnSwitchboard;
import net.sf.jml.message.MsnInstantMessage;

import org.apache.log4j.Logger;


import it.nands.jaybot.bean.User;
import it.nands.jaybot.constant.CommandConstant;
import it.nands.jaybot.constant.MessagesConstant;
import it.nands.jaybot.exception.ControlParamExeption;
import it.nands.jaybot.exception.ParamException;
import it.nands.jaybot.plugin.configurator.bean.CommandItem;
import it.nands.jaybot.plugin.configurator.controller.PluginManager;
import it.nands.jaybot.plugin.configurator.exception.PluginException;
import it.nands.jaybot.plugin.configurator.util.PluginUtils;
import it.nands.jaybot.plugin.impl.MessageHandlerPluginInterface;
import it.nands.jaybot.plugin.message.controller.MessagePlugin;
import it.nands.jaybot.plugin.server.constant.ServerPluginConstant;
import it.nands.jaybot.plugin.server.controller.ServerPlugin;
import it.nands.jaybot.plugin.tokenizer.constant.TokenizerPluginConstant;
import it.nands.jaybot.plugin.tokenizer.util.TokenizerUtils;
import it.nands.jaybot.util.MessageUtils;
import it.nands.jaybot.util.UserUtils;

/***
 * Plugin per il controllo dell'ftp
 * @author Alessandro Franzi
 *
 */
public class FtpPlugin implements MessageHandlerPluginInterface{
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
		logger.info("INIZIO - messaggio : "+messaggio);
		
		try {
			CommandItem itemComando = PluginUtils.getCommandItemFromMessage(messaggio);
			if (itemComando == null){
				logger.debug("itemComando nullo");
				throw new ControlParamExeption("itemComando nullo");
			}
			// recupero i parametri del comando
			Map<String,String> mapValues = TokenizerUtils.controlAndSplitMessage(messaggio,itemComando.getTemplate(),true);
			// recupero le informazioni sull'utente

			// recupero il nome del server
			String serverName = mapValues.get(TokenizerPluginConstant.TOKEN_SERVER_NAME);
			logger.debug("serverName : "+serverName);
			if (PluginManager.checkComandoMessaggio(messaggio,CommandConstant.FTP_PUT)){
				logger.info("Ftp put : "+messaggio);
				// notifico che la richiesta è stata accettata
				String messageToSend = MessagePlugin.getMessage(MessagesConstant.MESSAGE_PUT_REQUEST_ACCEPTED,
										ServerPlugin.getDefaultPathFromFtpServer(serverName),
										ServerPlugin.getNameAndHostFtpServer(serverName),
										ServerPluginConstant.FTP_SERVER);
				
				// invio messaggio
				MessageUtils.sendMessage(messageToSend, switchboard);
				
				
				valid = true;
			}
			if (PluginManager.checkComandoMessaggio(messaggio,CommandConstant.FTP_PUT_DIR)){
				logger.info("Ftp put dir: "+messaggio);
				// notifico che la richiesta è stata accettata
				String path = mapValues.get(TokenizerPluginConstant.TOKEN_SERVER_DIR);
				String messageToSend = MessagePlugin.getMessage(MessagesConstant.MESSAGE_PUT_REQUEST_ACCEPTED,
										path,
										ServerPlugin.getNameAndHostFtpServer(serverName),
										ServerPluginConstant.FTP_SERVER);
				
				// invio messaggio
				MessageUtils.sendMessage(messageToSend, switchboard);
				
				valid = true;
			}
			if (PluginManager.checkComandoMessaggio(messaggio,CommandConstant.FTP_PUT_LN)){
				logger.info("Ftp put ln: "+messaggio);
				// notifico che la richiesta è stata accettata
				String nameLogicalDir = mapValues.get(TokenizerPluginConstant.TOKEN_LN_DIR); 
				String messageToSend = MessagePlugin.getMessage(MessagesConstant.MESSAGE_PUT_REQUEST_ACCEPTED,
										ServerPlugin.getPathFromServerFromFtpServer(serverName,nameLogicalDir),
										ServerPlugin.getNameAndHostFtpServer(serverName),
										ServerPluginConstant.FTP_SERVER);
				
				// invio messaggio
				MessageUtils.sendMessage(messageToSend, switchboard);
				
				valid = true;
			}
		} catch (ParamException e){
			logger.error("Eccezione : "+e.getMessage());
			throw new PluginException(e.getMessage());
		} catch (ControlParamExeption e) {
		} catch (Exception e) {
			logger.error("Eccezione : "+e.getMessage());
			throw new PluginException(e.getMessage());
		} finally{
			logger.debug("FINE");
		}
		return valid;
		
	}
//	
//	
//	/***
//	 * Metodo che intercetta il messaggio ricevuto
//	 */
//	public Boolean chatMessageReceived(Chat chat, Message message,ReplyJabber jabberClient) throws PluginException {
//		logger.info("Inizio");
//		boolean valid = false;
//		
//		String messaggio = message.getBody().trim();
//		try {
//			CommandItem itemComando = ParamControllerPlugin.getCommandItemFromMessage(messaggio);
//			if (itemComando == null){
//				logger.debug("itemComando nullo");
//				throw new ControlParamExeption("itemComando nullo");
//			}
//			// recupero i parametri del comando
//			Map<String,String> mapValues = TokenizerUtils.controlAndSplitMessage(messaggio,itemComando.getTemplate(),true);
//			// recupero le informazioni sull'utente
//			User utente = UserUtils.getUserFromMessage(message);
//			// recupero il nome del server
//			String serverName = mapValues.get(TokenizerPluginConstant.TOKEN_SERVER_NAME);
//			logger.debug("serverName : "+serverName);
//			if (ParamControllerPlugin.checkComandoMessaggio(messaggio,CommandConstant.FTP_PUT)){
//				logger.info("Ftp put : "+messaggio);
//				// notifico che la richiesta è stata accettata
//				String messageToSend = MessagePlugin.getMessage(MessagesConstant.MESSAGE_PUT_REQUEST_ACCEPTED,
//										ServerPlugin.getDefaultPathFromFtpServer(serverName),
//										ServerPlugin.getNameAndHostFtpServer(serverName),
//										ServerPluginConstant.FTP_SERVER);
//				chat.sendMessage(messageToSend);
//				
//				
//				valid = true;
//			}
//			if (ParamControllerPlugin.checkComandoMessaggio(messaggio,CommandConstant.FTP_PUT_DIR)){
//				logger.info("Ftp put dir: "+messaggio);
//				// notifico che la richiesta è stata accettata
//				String path = mapValues.get(TokenizerPluginConstant.TOKEN_SERVER_DIR);
//				String messageToSend = MessagePlugin.getMessage(MessagesConstant.MESSAGE_PUT_REQUEST_ACCEPTED,
//										path,
//										ServerPlugin.getNameAndHostFtpServer(serverName),
//										ServerPluginConstant.FTP_SERVER);
//				chat.sendMessage(messageToSend);
//				
//				
//				valid = true;
//			}
//			if (ParamControllerPlugin.checkComandoMessaggio(messaggio,CommandConstant.FTP_PUT_LN)){
//				logger.info("Ftp put ln: "+messaggio);
//				// notifico che la richiesta è stata accettata
//				String nameLogicalDir = mapValues.get(TokenizerPluginConstant.TOKEN_LN_DIR); 
//				String messageToSend = MessagePlugin.getMessage(MessagesConstant.MESSAGE_PUT_REQUEST_ACCEPTED,
//										ServerPlugin.getPathFromServerFromFtpServer(serverName,nameLogicalDir),
//										ServerPlugin.getNameAndHostFtpServer(serverName),
//										ServerPluginConstant.FTP_SERVER);
//				chat.sendMessage(messageToSend);
//				
//				
//				valid = true;
//			}
//		} catch (ParamException e){
//			logger.error("Eccezione : "+e.getMessage());
//			throw new PluginException(e.getMessage());
//		} catch (ControlParamExeption e) {
//		} catch (XMPPException e) {
//			logger.error("Eccezione : "+e.getMessage());
//			throw new PluginException(e.getMessage());
//		} finally{
//			logger.debug("FINE");
//		}
//		return valid;
//	}
	
}
