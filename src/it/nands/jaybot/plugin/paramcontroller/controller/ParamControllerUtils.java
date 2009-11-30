package it.nands.jaybot.plugin.paramcontroller.controller;

import it.nands.jaybot.bean.User;
import it.nands.jaybot.exception.ControlParamExeption;
import it.nands.jaybot.exception.NoCommandFoundExeption;
import it.nands.jaybot.exception.ParamException;
import it.nands.jaybot.plugin.configurator.exception.PluginInitializeException;
import it.nands.jaybot.plugin.paramcontroller.bean.Command;
import it.nands.jaybot.plugin.paramcontroller.bean.CommandItem;
import it.nands.jaybot.plugin.paramcontroller.bean.Commands;
import it.nands.jaybot.plugin.tokenizer.controller.TokenizerPlugin;
import it.nands.jaybot.plugin.tokenizer.util.TokenizerUtils;
import it.nands.jaybot.util.StringUtils;

import java.util.List;

import org.apache.log4j.Logger;

/***
 * Utilita' per il controllo dei parametri
 * @author a.franzi
 *
 */
public class ParamControllerUtils {
	private static Logger logger = Logger.getRootLogger();
	
	/***
	 * Metodo per controllare che si stia invocando un determinato comando
	 * @param message	: messaggio
	 * @param template	: template
	 * @return			: boolean indicante se il comando fa match
	 */
	protected static boolean checkCommand (String message,String template){
		return message.toUpperCase().startsWith(getCommandWithoutParamsFromTemplate(template));
	}
	
	/***
	 * Metodo per recuperare la prima parte del comando dal template, esclusi i parametri
	 * 
	 * @param template	: template relativo al comando
	 * @return			: prima parte del comando senza parametri
	 */
	protected static String getCommandWithoutParamsFromTemplate (String template){
		String[] splitTemplate = template.split(TokenizerPlugin.getDelimitator());
		if (!StringUtils.isEmpty( splitTemplate[0])){
			return splitTemplate[0].toUpperCase().trim();
		}
		return "";
	}
	
	/***
	 * Metodo per recuperare dal template di un comando solamente la 
	 * parte relativa ai parametri
	 * @param template	: template del comando
	 * @return			: parametri del comando
	 */
	protected static String getParametersFromTemplate(String template){
		if (template!=null){
			if (template.indexOf(TokenizerPlugin.getDelimitator())!=-1){
				// se c'e' un occorrenza di parametri
				return template.substring(template.indexOf(TokenizerPlugin.getDelimitator()));				
			}
		}
		return "";
	}
	
	/***
	 * Metodo per controllare un comando.
	 * Nel caso un comando matcha con il template, vengono controllati anche i parametri,
	 * altrimenti restituisce NoCommandFoundExeption.
	 * 
	 * @param message					: messaggio
	 * @param utente					: utente
	 * @throws ControlParamExeption		: eccezione nel controllare il parametro
	 * @throws NoCommandFoundExeption 	: eccezione nel caso in cui nn ci sia nessun comando che matchi
	 */
	protected static void checkCommand(String message,User utente) throws ControlParamExeption,NoCommandFoundExeption{
		boolean foundSomething = false;
		try{
			Commands commands = ParamControllerLoader.getInstance().getCommands(utente.isAdmin());
			if (commands!=null){
				// se i comandi esistono
				List<Command> listaComandi = commands.getCommandList();
				for (int i = 0 ; i < listaComandi.size(); i++){
					// controllo che esista almeno un comando associato
					Command comando = listaComandi.get(i);
					CommandItem bestItemForThisCommand = getBestItemForCommand(comando,message);
					if (bestItemForThisCommand!=null){
						TokenizerUtils.controlAndSplitMessage(message,bestItemForThisCommand.getTemplate(),true);
						foundSomething = true;
					}
				}
			}
		} catch (PluginInitializeException e) {
			throw new ControlParamExeption(e.getMessage());
		}catch (ParamException e){
			throw new ControlParamExeption(e.getMessage());
		}
		
		if (!foundSomething){
			throw new NoCommandFoundExeption("No command found");
		}
	}
	
	/***
	 * Metodo per recuperare il migliore CommandItem dall'elenco di tutti i comandi
	 * 
	 * @param message	: messaggio su cui basare la ricerca
	 * @return			: istanza del commandItem oppure null
	 */
	protected static CommandItem getCommandItemFromMessage(String message){
		Commands commands;
		try {
			commands = ParamControllerLoader.getInstance().getCommands();
			if (commands!=null){
				// se i comandi esistono
				List<Command> listaComandi = commands.getCommandList();
				for (int i = 0 ; i < listaComandi.size(); i++){
					// controllo che esista almeno un comando associato
					Command comando = listaComandi.get(i);
					CommandItem bestItemForThisCommand = getBestItemForCommand(comando,message);
					if (bestItemForThisCommand!=null){
						// la prima occorrenza la restituisco
						logger.debug("Messaggio : "+message+" best item : "+bestItemForThisCommand.getTemplate());
						return bestItemForThisCommand;
					}
				}
			}
		} catch (PluginInitializeException e) {
			logger.debug("Eccezione : ",e);
		}
		return null;
	}
	
	/***
	 * MEtodo per recuperare il miglior template
	 * @param comando		: comando da cui estrapolare la migliore item
	 * @param messaggio		: messaggio da processare
	 * @return				: un commandItem oppure null
	 */
	protected static CommandItem getBestItemForCommand(Command comando,String messaggio){
		CommandItem bestCommand = null;
		String comandoBest = "";
		if (comando!=null){
			List<CommandItem> listaItemComandi = comando.getCommandItemList();
			if (listaItemComandi!=null){
				for (CommandItem currItem : listaItemComandi){
					// per ogni item relativo ad un comando
					logger.debug("Confronto messaggio : "+messaggio+" con : "+currItem.getTemplate());
					logger.debug("Comando senza comandi : "+getCommandWithoutParamsFromTemplate(currItem.getTemplate()));
					logger.debug("Confronto : "+ParamControllerUtils.checkCommand(messaggio,currItem.getTemplate()));
					if (ParamControllerUtils.checkCommand(messaggio,currItem.getTemplate())){
						logger.debug("Matchato comando :"+comando.getName());
						if (getCommandWithoutParamsFromTemplate(currItem.getTemplate()).length()>comandoBest.length()){
							comandoBest = getCommandWithoutParamsFromTemplate(currItem.getTemplate());
							bestCommand = currItem;
						}
						
					}
				}
			}
		}
		return bestCommand;
	}
	
	/***
	 * MEtodo per recuperare da un messaggio un comando
	 * 
	 * @param messaggio						: messaggio
	 * @param utente						: utente
	 * @return								: comando associato
	 * @throws PluginInitializeException	: problema di inizializzazione del plugin
	 */
	protected static Command getCommandFromMessage(String messaggio,User utente) throws PluginInitializeException{
		Commands commands = ParamControllerLoader.getInstance().getCommands(utente.isAdmin());
		
		if (commands!=null){
			// se i comandi esistono
			List<Command> listaComandi = commands.getCommandList();
			for (int i = 0 ; i < listaComandi.size(); i++){
				// controllo che esista almeno un comando associato
				Command comando = listaComandi.get(i);
				if (comando!=null){
					List<CommandItem> listaItemComandi = comando.getCommandItemList();
					if (listaItemComandi!=null){
						for (CommandItem currItem : listaItemComandi){
							// per ogni item relativo ad un comando
							if (ParamControllerUtils.checkCommand(messaggio,currItem.getTemplate())){
								return comando;
							}
						}
					}
				}
				
				
				
				
				
			}
		}
		return null;
	}
	
	/***
	 * Metodo per controllare che un comando sia associato ad un messaggio
	 * @param messaggio		: messaggio
	 * @param commandName	: nome del comando
	 * @param utente		: utente
	 * @return				: boolean
	 */
	protected static boolean checkComandoMessaggio(String messaggio,String commandName,User utente){
		boolean check = false;
		try {
			Command comando = getCommandFromMessage(messaggio,utente);
			if (comando!=null){
				if (comando.getName().equalsIgnoreCase(commandName)){
					check = true;
				}
			}else{
				check = false;
			}
		} catch (PluginInitializeException e) {
			check = false;
		}
		
		return check;
	}
	
	/***
	 * MEtodo per recupare l'elenco dei comandi in una versione stampabile dal bot
	 * @param comandi	: elenco comandi
	 * @return			: stringa contenente le informazioni sui comandi a disposizione
	 */
	protected static String getCommandListPrintable(Commands comandi){
		StringBuffer retBuffer = new StringBuffer("");
		if (comandi!=null){
			List<Command> commandList =  comandi.getCommandList();
			if (commandList!=null){
				for (Command currComando : commandList){
					if (currComando!=null && currComando.getVisible().booleanValue()){
						retBuffer.append("\n"+currComando.getName()+" : "+currComando.getCommandDescription());
					}
				}
			}
		}
		
		return retBuffer.toString();
	}
}
