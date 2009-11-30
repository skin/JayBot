package it.nands.jaybot.plugin.properties.controller;

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
import it.nands.jaybot.plugin.properties.bean.Properties;
import it.nands.jaybot.plugin.properties.constant.XmlConstant;

/***
 * Classe per la gestione del plugin delle properties
 * @author Alessandro Franzi
 *
 */
public class PropertiesLoader extends SingletonPluginLoader{
	private static PropertiesLoader instance;
	public Properties properties;
	private static Logger logger = Logger.getRootLogger();
	
	public static PropertiesLoader getInstance() throws PluginInitializeException{
		if (instance == null) instance = new PropertiesLoader();
		return instance;
	}
	
	public static void invalidate(){
		instance = null;
	}
	
	private PropertiesLoader() throws PluginInitializeException{
		String error = "Problemi in inizializzazione properties: ";
		try{
	        Mapping mapping = new Mapping();
	        
	        URL urlFile = PluginLoader.class.getResource("/propertiesMapping.xml");
	        if (urlFile==null){
	        	throw new PluginInitializeException("Impossibile trovare il file di mapping");
	        }
	        // 1. Load the mapping information from the file
	        mapping.loadMapping(urlFile);
	
	        // 2. Unmarshal the data
	        Unmarshaller unmar = new Unmarshaller(mapping);
	        properties = (Properties)unmar.unmarshal(new InputSource(new FileReader("conf/properties.xml")));
	        
	        logger.info("Il numero di moduli caricati e' : "+properties.getModuleList().size());
	        
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
	
	protected String getValue (String module,String key){
		
		return PropertiesUtils.getProperty(properties.getModuleList(),module, key); 
	}
	protected String getValue(String key){
		return getValue(XmlConstant.DEFAULT_MODULE,key);
	}
}
