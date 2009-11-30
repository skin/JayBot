package it.nands.jaybot.plugin.resource.controller;

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
import it.nands.jaybot.plugin.resource.bean.Resource;
import it.nands.jaybot.plugin.resource.bean.Resources;

/***
 * Classe per la gestione del plugin delle resources
 * @author Alessandro Franzi
 *
 */
public class ResourcesLoader extends SingletonPluginLoader{
	private static ResourcesLoader instance;
	public Resources resources;
	private static Logger logger = Logger.getRootLogger();
	
	/***
	 * Metodo per recuperare l'istanza del singleton
	 * @return	: istanza
	 * @throws PluginInitializeException
	 */
	public static ResourcesLoader getInstance() throws PluginInitializeException{
		if (instance == null) instance = new ResourcesLoader();
		return instance;
	}
	
	/***
	 * Invalida l'istanza
	 */
	public static void invalidate(){
		instance = null;
	}
	
	/***
	 * ResourcesLoader
	 * 
	 * @throws PluginInitializeException
	 */
	private ResourcesLoader() throws PluginInitializeException{
		String error = "Problemi in inizializzazione resources: ";
		try{
	        Mapping mapping = new Mapping();
	        
	        URL urlFile = PluginLoader.class.getResource("/resourceMapping.xml");
	        if (urlFile==null){
	        	throw new PluginInitializeException("Impossibile trovare il file di mapping");
	        }
	        // 1. Load the mapping information from the file
	        mapping.loadMapping(urlFile);
	
	        // 2. Unmarshal the data
	        Unmarshaller unmar = new Unmarshaller(mapping);
	        resources = (Resources)unmar.unmarshal(new InputSource(new FileReader("conf/resources.xml")));
	        
	        logger.info("Il numero di risorse caricate e' : "+resources.getResourceList().size());
	        
	        // 3. eseguo un check sulle risorse caricate
	        if (!ResourcesUtils.checkResources(resources.getResourceList())){
	        	throw new PluginInitializeException("Problema nel checking delle risorse");
	        }
	        
		}catch(MarshalException ex){
			logger.error("Eccezione :",ex);
			throw new PluginInitializeException(error+ ex.getMessage());
		}catch(MappingException ex){
			logger.error("Eccezione :",ex);
			throw new PluginInitializeException(error+ ex.getMessage());
		}catch(IOException ex){
			logger.error("Eccezione :",ex);
			throw new PluginInitializeException(error+ ex.getMessage());
		}catch(ValidationException ex){
			logger.error("Eccezione :",ex);
			throw new PluginInitializeException(error+ ex.getMessage());
		} catch (org.exolab.castor.xml.MarshalException ex) {
			logger.error("Eccezione :",ex);
			throw new PluginInitializeException(error+ ex.getMessage());
		} 
	}
	
	protected Resource getResource (String resource){
		
		return ResourcesUtils.getResource(this.resources.getResourceList(), resource);
	}
}
