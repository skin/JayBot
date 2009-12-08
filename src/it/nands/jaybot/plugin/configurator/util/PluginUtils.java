package it.nands.jaybot.plugin.configurator.util;

import it.nands.jaybot.bean.User;
import it.nands.jaybot.exception.ControlParamExeption;
import it.nands.jaybot.exception.NoCommandFoundExeption;
import it.nands.jaybot.exception.ParamException;
import it.nands.jaybot.plugin.configurator.bean.Command;
import it.nands.jaybot.plugin.configurator.bean.CommandItem;
import it.nands.jaybot.plugin.configurator.bean.Plugin;
import it.nands.jaybot.plugin.configurator.bean.Plugins;
import it.nands.jaybot.plugin.configurator.controller.PluginManager;
import it.nands.jaybot.plugin.configurator.exception.PluginException;
import it.nands.jaybot.plugin.configurator.exception.PluginInitializeException;
import it.nands.jaybot.plugin.configurator.exception.ReplicatePluginException;
import it.nands.jaybot.plugin.impl.MessageHandlerPluginInterface;
import it.nands.jaybot.plugin.impl.SingletonPluginLoader;
import it.nands.jaybot.plugin.properties.constant.PropertiesConstant;
import it.nands.jaybot.plugin.properties.controller.PropertiesPlugin;
import it.nands.jaybot.plugin.tokenizer.controller.TokenizerPlugin;
import it.nands.jaybot.plugin.tokenizer.util.TokenizerUtils;
import it.nands.jaybot.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/***
 * Classe di utilita' per la gestione dei plugins
 * @author Alessandro Franzi
 *
 */
public class PluginUtils {
	private static Logger logger = Logger.getRootLogger();
	/***
	 * Metodo per recuperare l'elenco dei plugin. Nel caso in cui si desideri controllare
	 * che non ci siano plugin ripetuti, e' possibile farsi inviare un eccezione specificando
	 * il determinato parametro nella chiamata
	 * @param plugins						: bean di tipo Plugins
	 * @param generateExceptionIfReplicate	: boolean (true se si desidera ricevere l'eccezione in caso di replicazione)
	 * @return								: lista di stringhe contenenti i nomi dei plugins
	 * @throws ReplicatePluginException		: Eccezione di replicazione di un plugin
	 */
	public static List<String> getNamesOfPlugins (Plugins plugins,boolean generateExceptionIfReplicate) throws ReplicatePluginException{
		List<String> elencoPlugin = new ArrayList<String>();
		if (PluginChecker.checkIfThereIsSomePlugin(plugins)){
			// per tutti i plugin controllo che le classi associate esistano
			
			// recupero l'elenco dei plugin
			List<Plugin> listaPlugin = plugins.getPluginList();
			
			Plugin currPlugin = null;
			
			// scorro i plugin generandomi una lista che li distingua
			// nel caso esistano 2 istanze dello stesso plugin genero errore
			
			for (int i = 0 ; i < listaPlugin.size(); i++){
				currPlugin = listaPlugin.get(i);
				
				if (elencoPlugin.contains(currPlugin.getName())){
					// se giˆ lo contiene controllo il boolean per generare un eccezione
					// ed interrompere il controllo
					if (generateExceptionIfReplicate){
						throw new ReplicatePluginException("Il plugin "+currPlugin.getName()+" sembra essere replicato nel file di configurazione");
					}
				}else{
					// se il nome non e' ancora contenuto in lista
					elencoPlugin.add(currPlugin.getName());
				}
				
			}
		}
		return elencoPlugin;
	}
	
	/***
	 * This method get all registered plugins and for each of them it cheks if implements
	 * SingletonPluginLoader class.
	 * In this case it will call method getInstance.
	 * If the current plugin returns an error and is a blocking plugin, then an PluginInitializeException
	 * will be thrown. Else the error is logged but the execution continue.
	 * 
	 * @throws PluginInitializeException	: exception
	 */
	public static void forceIstanceOfAllPlugins(Plugins plugins) throws PluginInitializeException{
		Plugin currPlugin = null;
		String classCurr ="";
		try{
			String methodName = PropertiesPlugin.getValue(PropertiesConstant.MODULE_CONFIGURATION, PropertiesConstant.PROP_LOADERMETHOD);
			logger.info("Method to call : "+methodName);
			if (PluginChecker.checkIfThereIsSomePlugin(plugins)){
				List<Plugin> listaPlugin = plugins.getPluginList();
				for (int i = 0 ; i < listaPlugin.size(); i++){
					currPlugin = listaPlugin.get(i);
					for (int j = 0 ; j < currPlugin.getClassList().size(); j++){
						classCurr = currPlugin.getClassList().get(j);
						// controllo se la classe e' definita, altrimenti pu˜ anche non essere un problema
						if (!StringUtils.isEmpty(classCurr)){
							try{
								// controllo dell'esistenza della classe mediante Reflection
								Class classToIstanciate = Class.forName(classCurr);
								Class superclass = classToIstanciate.getSuperclass();
								if (ReflectionUtils.confrontateClasses(SingletonPluginLoader.class, superclass)){								
									// se e' istanza dei singletonLoader chiamo il metodo getIstance()
									Class partypes[] = new Class[0];
									Method meth = classToIstanciate.getMethod(methodName,partypes);
									Object arglist[] = new Object[0];
							        meth.invoke(classToIstanciate, arglist);
								}
							}catch(ClassNotFoundException ex){
								String eccezione = "La classe di riferimento per il plugin "+currPlugin.getName()+" non esiste. Blocking : "+currPlugin.getBlocking().booleanValue();
								logger.error(eccezione,ex);
								if (currPlugin.getBlocking().booleanValue()){
									throw new PluginInitializeException (eccezione);
								}
							}catch(NoSuchMethodException ex){
								String eccezione = "La classe "+currPlugin.getName()+" presenta un metodo "+methodName+". Blocking : "+currPlugin.getBlocking().booleanValue();
								logger.error(eccezione,ex);
								if (currPlugin.getBlocking().booleanValue()){
									throw new PluginInitializeException (eccezione);
								}
							}catch(IllegalAccessException ex){
								String eccezione = "La classe "+currPlugin.getName()+" presenta presenta problemi di accesso. Blocking : "+currPlugin.getBlocking().booleanValue();
								logger.error(eccezione,ex);
								if (currPlugin.getBlocking().booleanValue()){
									throw new PluginInitializeException (eccezione);
								}
							}catch(InvocationTargetException ex){
								String eccezione = "La classe "+currPlugin.getName()+" presenta un probema nell'initialize del plugin "+currPlugin.getName();
								logger.error(eccezione,ex);
								if (currPlugin.getBlocking().booleanValue()){
									throw new PluginInitializeException (eccezione);
								}
							}
						}
					}
					
				}
			}
		}catch(PluginException ex){
			logger.error("Eccezione : ",ex);
			throw new PluginInitializeException (ex.getMessage());
		}catch(Exception ex){
			logger.error("Eccezione : ",ex);
			throw new PluginInitializeException (ex.getMessage());
		}
	}
	
	/***
	 * Metodo per recuperare i plugin associati all'evento "onMessageReceived"
	 * @param plugins		: lista dei plugins
	 * @return				: lista dei plugins associati all'evento
	 */
	public static Plugins getOnMessageReceivedPlugins(Plugins plugins){
		Plugin currPlugin = null;
		Plugins retPlugins = plugins;
		List<Plugin> retPluginList = new ArrayList<Plugin>();
		if (PluginChecker.checkIfThereIsSomePlugin(plugins)){
			List<Plugin> listaPlugin = plugins.getPluginList();
			logger.debug("Plugin handler messaggi : ");
			for (int i = 0 ; i < listaPlugin.size(); i++){
				currPlugin = listaPlugin.get(i);
				//
				if (checkIfPluginHasAClassThatImplementsInterface(currPlugin, MessageHandlerPluginInterface.class)){
					logger.debug(currPlugin.getName());
					retPluginList.add(currPlugin);
				}
			}
			// setto la nuova lista per il plugins di output
			retPlugins.setPluginList(retPluginList);
		}
		return retPlugins;
		
	}
	
	/***
	 * Metodo per verificare se un plugin contiene classi che implementano una determinata
	 * interfaccia
	 * @param currPlugin      	: plugin
	 * @param interfaceClass  	: interfaccia	
	 * @return					: boolean
	 */
	public static boolean checkIfPluginHasAClassThatImplementsInterface(Plugin currPlugin,Class interfaceClass){
		String classCurr ="";
		boolean retBool = false;
		for (int j = 0 ; j < currPlugin.getClassList().size() && !retBool; j++){
			classCurr = currPlugin.getClassList().get(j);
			// controllo se la classe e' definita, altrimenti pu˜ anche non essere un problema
			if (!StringUtils.isEmpty(classCurr)){
				// controllo dell'esistenza della classe mediante Reflection
				Class classToIstanciate;
				try {
					classToIstanciate = Class.forName(classCurr);
					retBool =  ReflectionUtils.checkIfClassIsImplementationOfAnInterface(classToIstanciate,MessageHandlerPluginInterface.class);
				} catch (ClassNotFoundException e) {
					logger.error("Eccezione : ",e);
				}
				
			}
		}
		return retBool;
	}
	
	/***
	 * Metodo per recupare l'elenco dei comandi in una versione stampabile dal bot
	 * @return			: stringa contenente le informazioni sui comandi a disposizione
	 */
	public static String getPrintableCommandList() throws PluginException{
		return getCommandListPrintable(PluginManager.getCommands());
	}
	
	/***
	 * MEtodo per recupare l'elenco dei comandi in una versione stampabile dal bot
	 * @param comandi	: elenco comandi
	 * @return			: stringa contenente le informazioni sui comandi a disposizione
	 */
	protected static String getCommandListPrintable(List<Command> comandi){
		StringBuffer retBuffer = new StringBuffer("");
		
		if (comandi!=null){
			for (Command currComando : comandi){
				if (currComando!=null && currComando.getVisible().booleanValue()){
					retBuffer.append("\n"+currComando.getName()+" : "+currComando.getCommandDescription());
				}
			}
		}
		
		return retBuffer.toString();
	}
	
	/***
	 * Metodo per controllare che un comando sia associato ad un messaggio
	 * @param messaggio		: messaggio
	 * @param commandName	: nome del comando
	 * @param utente		: utente
	 * @return				: boolean
	 */
	public static boolean checkComandoMessaggio(String messaggio,String commandName,User utente){
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
	 * MEtodo per recuperare da un messaggio un comando
	 * 
	 * @param messaggio						: messaggio
	 * @param utente						: utente
	 * @return								: comando associato
	 * @throws PluginInitializeException	: problema di inizializzazione del plugin
	 */
	protected static Command getCommandFromMessage(String messaggio,User utente) throws PluginInitializeException{
		
		
		
			// se i comandi esistono
			List<Command> listaComandi = PluginManager.getCommands(utente.isAdmin());
			for (int i = 0 ; i < listaComandi.size(); i++){
				// controllo che esista almeno un comando associato
				Command comando = listaComandi.get(i);
				if (comando!=null){
					List<CommandItem> listaItemComandi = comando.getCommandItemList();
					if (listaItemComandi!=null){
						for (CommandItem currItem : listaItemComandi){
							// per ogni item relativo ad un comando
							if (checkCommand(messaggio,currItem.getTemplate())){
								return comando;
							}
						}
					}
				}
				
				
				
				
				
			}
		
		return null;
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
	public static void checkCommand(String message,User utente) throws ControlParamExeption,NoCommandFoundExeption{
		boolean foundSomething = false;
		try{
			// se i comandi esistono
			List<Command> listaComandi = PluginManager.getCommands(utente.isAdmin());
			for (int i = 0 ; i < listaComandi.size(); i++){
				// controllo che esista almeno un comando associato
				Command comando = listaComandi.get(i);
				CommandItem bestItemForThisCommand = getBestItemForCommand(comando,message);
				if (bestItemForThisCommand!=null){
					TokenizerUtils.controlAndSplitMessage(message,bestItemForThisCommand.getTemplate(),true);
					foundSomething = true;
				}
			}
		}catch (ParamException e){
			throw new ControlParamExeption(e.getMessage());
		}
		
		if (!foundSomething){
			throw new NoCommandFoundExeption("No command found");
		}
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
					logger.debug("Confronto : "+checkCommand(messaggio,currItem.getTemplate()));
					if (checkCommand(messaggio,currItem.getTemplate())){
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
	 * Metodo per recuperare la prima parte del comando dal template, esclusi i parametri
	 * 
	 * @param template	: template relativo al comando
	 * @return			: prima parte del comando senza parametri
	 */
	public static String getCommandWithoutParamsFromTemplate (String template){
		String[] splitTemplate = template.split(TokenizerPlugin.getDelimitator());
		if (!StringUtils.isEmpty( splitTemplate[0])){
			return splitTemplate[0].toUpperCase().trim();
		}
		return "";
	}
	
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
	 * Metodo per recuperare il migliore CommandItem dall'elenco di tutti i comandi
	 * 
	 * @param message	: messaggio su cui basare la ricerca
	 * @return			: istanza del commandItem oppure null
	 */
	public static CommandItem getCommandItemFromMessage(String message){
		
		// se i comandi esistono
		List<Command> listaComandi = PluginManager.getCommands();
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
		return null;
	}
	
	/***
	 * Metodo per recuperare dal template di un comando solamente la 
	 * parte relativa ai parametri
	 * @param template	: template del comando
	 * @return			: parametri del comando
	 */
	public static String getParametersFromTemplate(String template){
		if (template!=null){
			if (template.indexOf(TokenizerPlugin.getDelimitator())!=-1){
				// se c'e' un occorrenza di parametri
				return template.substring(template.indexOf(TokenizerPlugin.getDelimitator()));				
			}
		}
		return "";
	}
	
	
}
