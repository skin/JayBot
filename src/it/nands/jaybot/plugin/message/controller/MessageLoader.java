package it.nands.jaybot.plugin.message.controller;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.rmi.MarshalException;

import org.apache.log4j.Logger;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.InputSource;

import it.nands.jaybot.plugin.configurator.controller.PluginLoader;
import it.nands.jaybot.plugin.configurator.exception.PluginInitializeException;
import it.nands.jaybot.plugin.impl.SingletonPluginLoader;
import it.nands.jaybot.plugin.message.bean.Messages;
import it.nands.jaybot.util.StringUtils;

/***
 * Classe Singleton per la gestione dei messaggi
 * @author Alessandro Franzi
 *
 */
public class MessageLoader extends SingletonPluginLoader{
	private static MessageLoader instance;
	private static Logger logger = Logger.getRootLogger();
	private Messages messages;
	

	public static MessageLoader getInstance() throws PluginInitializeException{
		if (instance == null) instance = new MessageLoader();
		return instance;
	}
	
	public static void invalidate(){
		instance = null;
		// altro

	}
	
	private MessageLoader() throws PluginInitializeException{
		try{
	        Mapping mapping = new Mapping();
	        
	        URL urlFile = PluginLoader.class.getResource("/messageMapping.xml");
	        if (urlFile==null){
	        	throw new PluginInitializeException("Impossibile trovare il file di mapping");
	        }
	        // 1. Load the mapping information from the file
	        mapping.loadMapping(urlFile);
	
	        // 2. Unmarshal the data
	        Unmarshaller unmar = new Unmarshaller(mapping);
	        messages = (Messages)unmar.unmarshal(new InputSource(new FileReader("conf/message.xml")));
	        
	        logger.info("Messaggi caricati");
	        
		}catch(MarshalException ex){
			throw new PluginInitializeException(MessagePlugin.getPluginError(ex));
		}catch(MappingException ex){
			throw new PluginInitializeException(MessagePlugin.getPluginError(ex));
		}catch(IOException ex){
			throw new PluginInitializeException(MessagePlugin.getPluginError(ex));
		}catch(ValidationException ex){
			throw new PluginInitializeException(MessagePlugin.getPluginError(ex));
		} catch (org.exolab.castor.xml.MarshalException ex) {
			throw new PluginInitializeException(MessagePlugin.getPluginError(ex));
		}
	}


	public Messages getMessages() {
		return messages;
	}

	public void setMessages(Messages messages) {
		this.messages = messages;
	}
	
	protected String getMessage(String nomeMessaggio,String ... parametri){
		String retString = "";
		String messaggio = this.getMessages().getHashMessage().get(nomeMessaggio);
		if (messaggio!=null){
			retString = StringUtils.fillString(messaggio, parametri);
		}
		return retString;
	}
	
}
