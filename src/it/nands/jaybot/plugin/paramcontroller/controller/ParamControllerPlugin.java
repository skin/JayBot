package it.nands.jaybot.plugin.paramcontroller.controller;

import net.sf.jml.MsnContact;
import net.sf.jml.MsnSwitchboard;
import net.sf.jml.message.MsnInstantMessage;

import org.apache.log4j.Logger;

import it.nands.jaybot.bean.User;
import it.nands.jaybot.exception.ControlParamExeption;
import it.nands.jaybot.exception.NoCommandFoundExeption;
import it.nands.jaybot.plugin.configurator.exception.PluginException;
import it.nands.jaybot.plugin.configurator.exception.PluginInitializeException;
import it.nands.jaybot.plugin.impl.MessageHandlerPluginInterface;
import it.nands.jaybot.plugin.paramcontroller.bean.CommandItem;
import it.nands.jaybot.util.UserUtils;

/***
 * Plugin per il controllo del ParamController
 * 
 * N.B = Serve solo ad identificare l'esistenza di un comando che matcha il template,
 * il comando non viene gestito
 * 
 * @author Alessandro Franzi
 *
 */
public class ParamControllerPlugin implements MessageHandlerPluginInterface {
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
			ParamControllerUtils.checkCommand(messaggio,utente);
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
	
	/***
	 * Metodo per recupare l'elenco dei comandi in una versione stampabile dal bot
	 * @return			: stringa contenente le informazioni sui comandi a disposizione
	 */
	public static String getPrintableCommandList() throws PluginException{
		try {
			return ParamControllerUtils.getCommandListPrintable(ParamControllerLoader.getInstance().getCommands());
		} catch (PluginInitializeException e) {
			throw new PluginException(e.getMessage());
		}
	}
	
	/***
	 * Metodo per controllare che un comando sia associato ad un messaggio
	 * @param messaggio		: messaggio
	 * @param commandName	: nome del comando
	 * @param utente		: utente
	 * @return				: boolean
	 */
	public static boolean checkComandoMessaggio(String messaggio,String commandName,User utente){
		return ParamControllerUtils.checkComandoMessaggio(messaggio, commandName,utente);
	}
	
	/***
	 * Metodo per controllare che un comando sia associato ad un messaggio
	 * @param messaggio		: messaggio
	 * @param commandName	: nome del comando
	 * @return				: boolean
	 */
	public static boolean checkComandoMessaggio(String messaggio,String commandName){
		User utente = new User();
		utente.setAdmin(false);
		return ParamControllerUtils.checkComandoMessaggio(messaggio, commandName,utente);
	}
	
	/***
	 * Metodo per recuperare la prima parte del comando dal template, esclusi i parametri
	 * 
	 * @param template	: template relativo al comando
	 * @return			: prima parte del comando senza parametri
	 */
	public static String getCommandWithoutParamsFromTemplate (String template){
		return ParamControllerUtils.getCommandWithoutParamsFromTemplate(template);
	}
	
	/***
	 * Metodo per recuperare dal template di un comando solamente la 
	 * parte relativa ai parametri
	 * @param template	: template del comando
	 * @return			: parametri del comando
	 */
	public static String getParametersFromTemplate(String template){
		return ParamControllerUtils.getParametersFromTemplate(template);
	}
	
	/***
	 * Metodo per recuperare il migliore CommandItem dall'elenco di tutti i comandi
	 * 
	 * @param message	: messaggio su cui basare la ricerca
	 * @return			: istanza del commandItem oppure null
	 */
	public static CommandItem getCommandItemFromMessage(String message){
		return ParamControllerUtils.getCommandItemFromMessage(message);
	}

	
}
