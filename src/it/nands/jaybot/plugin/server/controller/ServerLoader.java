package it.nands.jaybot.plugin.server.controller;

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
import it.nands.jaybot.plugin.server.bean.Servers;

/***
 * Classe Singleton per la gestione dei server
 * @author Alessandro Franzi
 *
 */
public class ServerLoader extends SingletonPluginLoader{
	private static ServerLoader instance;
	private static Logger logger = Logger.getRootLogger();
	private Servers servers;
	
	public static ServerLoader getInstance() throws PluginInitializeException{
		if (instance == null) instance = new ServerLoader();
		return instance;
	}
	
	public static void invalidate(){
		instance = null;
		// altro

	}
	
	private ServerLoader() throws PluginInitializeException{
		try{
	        Mapping mapping = new Mapping();
	        
	        URL urlFile = PluginLoader.class.getResource("/serverMapping.xml");
	        if (urlFile==null){
	        	throw new PluginInitializeException("Impossibile trovare il file di mapping");
	        }
	        // 1. Load the mapping information from the file
	        mapping.loadMapping(urlFile);
	
	        // 2. Unmarshal the data
	        Unmarshaller unmar = new Unmarshaller(mapping);
	        servers = (Servers)unmar.unmarshal(new InputSource(new FileReader("conf/server.xml")));
	        
	        logger.info("Il numero di server ssh caricati e' : "+servers.getSshs().getSshList().size());
	        logger.info("Il numero di server ftp caricati e' : "+servers.getFtps().getFtpList().size());
	        
		}catch(MarshalException ex){
			throw new PluginInitializeException("Problemi in inizializzazione plugin: "+ ex.getMessage());
		}catch(MappingException ex){
			throw new PluginInitializeException("Problemi in inizializzazione plugin: "+ ex.getMessage());
		}catch(IOException ex){
			throw new PluginInitializeException("Problemi in inizializzazione plugin: "+ ex.getMessage());
		}catch(ValidationException ex){
			throw new PluginInitializeException("Problemi in inizializzazione plugin: "+ ex.getMessage());
		} catch (org.exolab.castor.xml.MarshalException ex) {
			throw new PluginInitializeException("Problemi in inizializzazione plugin: "+ ex.getMessage());
		}
	}

	protected Servers getServers() {
		return servers;
	}

	protected void setServers(Servers servers) {
		this.servers = servers;
	}
	
}
