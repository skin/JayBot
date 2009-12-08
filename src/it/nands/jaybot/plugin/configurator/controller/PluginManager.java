package it.nands.jaybot.plugin.configurator.controller;


import it.nands.jaybot.bean.User;
import it.nands.jaybot.plugin.configurator.bean.Command;
import it.nands.jaybot.plugin.configurator.bean.CommandItem;
import it.nands.jaybot.plugin.configurator.bean.Plugin;
import it.nands.jaybot.plugin.configurator.bean.Plugins;
import it.nands.jaybot.plugin.configurator.exception.PluginInitializeException;
import it.nands.jaybot.plugin.configurator.util.PluginUtils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/***
 * Plugin Manager class
 * 
 * @author alessandrofranzi
 *
 */
public class PluginManager {
	private static Logger logger = Logger.getRootLogger();
	
	/***
	 * Get list of commands filtered by pluginName and by admin filter.
	 * 
	 * @param pluginName	: plugin name to filter (null or empty doesn't filter anything)
	 * @param onlyAdmin		: if true it takes all command, else only commands for normal user
	 * @return				: list of commands
	 */
	public static List<Command> getCommands(String pluginName,boolean onlyAdmin){
		List<Command> retList = new ArrayList<Command>();
		try {
			Plugins plugins = PluginLoader.getInstance().getPlugins();
			if (plugins!=null){
				 List<Plugin> pluginList = plugins.getPluginList();
				 if (pluginList!=null){
					 for (int i = 0; i<pluginList.size(); i++){
						Plugin currPlugin = pluginList.get(i);
						if (currPlugin.getCommandList()!=null){
							if (StringUtils.isEmpty(pluginName)){
								retList.addAll(getCommands(currPlugin.getCommandList(),onlyAdmin));
							}else{
								if (pluginName.equalsIgnoreCase(pluginName)){
									retList.addAll(getCommands(currPlugin.getCommandList(),onlyAdmin));
								}
							}
						}
					 }
				 }
			}
			logger.debug("Comands retrieved : "+retList.size());
		} catch (PluginInitializeException e) {
			logger.error("Exception : ",e);
		}
		return retList;
	}
	
	/***
	 * Get list of commands filtered by admin filter.
	 * 
	 * @param onlyAdmin		: if true it takes all command, else only commands for normal user
	 * @return				: list of commands
	 */
	public static List<Command> getCommands(boolean onlyAdmin){
		return getCommands("", onlyAdmin);
	}
	
	/***
	 * Get list of commands
	 * 
	 * @return				: list of commands
	 */
	public static List<Command> getCommands(){
		return getCommands("", false);
	}
	
	/***
	 * filters commands inside a list by admin filter
	 * 
	 * @param onlyAdmin		: if true it takes all command, else only commands for normal user
	 * @return				: list of commands
	 */
	public static List<Command> getCommands (List<Command> commandList,boolean onlyAdmin){
		if (onlyAdmin){
			List<Command> commandListToReturn = new ArrayList<Command>();
			
			for (int i = 0 ; i < commandList.size(); i++){
				Command currCommand = commandList.get(i);
				if (currCommand.getOnlyForAdmin().booleanValue()){
					commandListToReturn.add(currCommand);
				}
			}
			return commandListToReturn;
		}else{
			return commandList;
		}
	}
	
	
	/***
	 * Get plugins wich implements MessageHandlerPluginInterface
	 * 
	 * @return	: Plugins
	 */
	public static Plugins getOnMessageReceivedPlugins (){
		Plugins retPlugins = null;
		try {
			retPlugins = PluginLoader.getInstance().getOnMessageReceivedPlugins();
		} catch (PluginInitializeException e) {
			logger.error("Eccezione : ",e);
		}
		return retPlugins;
	}
	
	/***
	 * Metodo per recuperare il migliore CommandItem dall'elenco di tutti i comandi
	 * 
	 * @param message	: messaggio su cui basare la ricerca
	 * @return			: istanza del commandItem oppure null
	 */
	public static CommandItem getCommandItemFromMessage(String message){
		return PluginUtils.getCommandItemFromMessage(message);
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
		return PluginUtils.checkComandoMessaggio(messaggio, commandName,utente);
	}
}
