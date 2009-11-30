package it.nands.jaybot.plugin.paramcontroller.controller;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.rmi.MarshalException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.InputSource;

import it.nands.jaybot.plugin.configurator.controller.PluginLoader;
import it.nands.jaybot.plugin.configurator.exception.PluginInitializeException;
import it.nands.jaybot.plugin.impl.SingletonPluginLoader;
import it.nands.jaybot.plugin.paramcontroller.bean.Command;
import it.nands.jaybot.plugin.paramcontroller.bean.Commands;

/***
 * Singleton per il caricamento dei comandi
 * @author a.franzi
 *
 */
public class ParamControllerLoader extends SingletonPluginLoader{
	private static ParamControllerLoader instance;
	private static Logger logger = Logger.getRootLogger();
	private Commands commands;
	
	/***
	 * Metodo per ottenere l'istanza del singleton
	 * @return				: istanza
	 * @throws PluginInitializeException	: eccezione modellata
	 */
	public static ParamControllerLoader getInstance() throws PluginInitializeException{
		if (instance == null) instance = new ParamControllerLoader();
		return instance;
	}
	
	/***
	 * MEtodo per invalidare l'istanza del singleton
	 */
	public static void invalidate(){
		instance = null;
	}
	
	/***
	 * Costruttore privato
	 * @throws PluginInitializeException	: eccezione modellata
	 */
	private ParamControllerLoader() throws PluginInitializeException{
		try{
	        Mapping mapping = new Mapping();
	        
	        URL urlFile = PluginLoader.class.getResource("/paramControllerMapping.xml");
	        if (urlFile==null){
	        	throw new PluginInitializeException("Impossibile trovare il file di mapping");
	        }
	        // 1. Load the mapping information from the file
	        mapping.loadMapping(urlFile);
	
	        // 2. Unmarshal the data
	        Unmarshaller unmar = new Unmarshaller(mapping);
	        this.commands = (Commands)unmar.unmarshal(new InputSource(new FileReader("conf/commands.xml")));
	        
	        logger.info("Il numero di comandi caricati e' : "+commands.getCommandList().size());
	        
		}catch(MarshalException ex){
			logger.error("Eccezione :",ex);
			throw new PluginInitializeException("Problemi in inizializzazione plugin: "+ ex.getMessage());
		}catch(MappingException ex){
			logger.error("Eccezione :",ex);
			throw new PluginInitializeException("Problemi in inizializzazione plugin: "+ ex.getMessage());
		}catch(IOException ex){
			logger.error("Eccezione :",ex);
			throw new PluginInitializeException("Problemi in inizializzazione plugin: "+ ex.getMessage());
		}catch(ValidationException ex){
			logger.error("Eccezione :",ex);
			throw new PluginInitializeException("Problemi in inizializzazione plugin: "+ ex.getMessage());
		} catch (org.exolab.castor.xml.MarshalException ex) {
			logger.error("Eccezione :",ex);
			throw new PluginInitializeException("Problemi in inizializzazione plugin: "+ ex.getMessage());
		}
	}

	/***
	 * MEtodo per recuperare i comandi
	 * @return
	 */
	public Commands getCommands() {
		return commands;
	}
	
	/***
	 * getCommands
	 * 
	 * @param onlyAdmin
	 * @return
	 */
	public Commands getCommands (boolean onlyAdmin){
		if (onlyAdmin){
			List<Command> commandList = commands.getCommandList();
			Commands commandsToReturn = new Commands();
			List<Command> commandListToReturn = new ArrayList<Command>();
			
			for (int i = 0 ; i < commandList.size(); i++){
				Command currCommand = commandList.get(i);
				if (currCommand.getOnlyForAdmin().booleanValue()){
					commandListToReturn.add(currCommand);
				}
			}
			commandsToReturn.setCommandList(commandListToReturn);
			return commandsToReturn;
		}else{
			return getCommands();
		}
	}
	
	/***
	 * Metodo per settare i comandi
	 * @param commands
	 */
	public void setCommands(Commands commands) {
		this.commands = commands;
	}
}
