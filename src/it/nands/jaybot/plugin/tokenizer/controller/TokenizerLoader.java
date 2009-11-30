package it.nands.jaybot.plugin.tokenizer.controller;

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
import it.nands.jaybot.plugin.tokenizer.bean.Tokens;

/***
 * Classe Singleton per la gestione dei token
 * @author Alessandro Franzi
 *
 */
public class TokenizerLoader extends SingletonPluginLoader{
	private static TokenizerLoader instance;
	private static Logger logger = Logger.getRootLogger();
	private Tokens tokens;
	
	/***
	 * Metodo per recuperare l'istanza del singleton
	 * @return								: istanza del tokenizer
	 * @throws PluginInitializeException	: eccezione modellata per problemi di inizializzazione
	 */
	public static TokenizerLoader getInstance() throws PluginInitializeException{
		if (instance == null) instance = new TokenizerLoader();
		return instance;
	}
	
	/***
	 * Metodo per invalidare l'istanza del singleton
	 */
	public static void invalidate(){
		instance = null;
		// altro

	}
	
	/***
	 * Metodo che inizializza l'istanza del singleton
	 * 
	 * @throws PluginInitializeException	: eccezione modellata per problemi di inizializzazione
	 */
	private TokenizerLoader() throws PluginInitializeException{
		try{
	        Mapping mapping = new Mapping();
	        
	        URL urlFile = PluginLoader.class.getResource("/tokenizerMapping.xml");
	        if (urlFile==null){
	        	throw new PluginInitializeException("Impossibile trovare il file di mapping");
	        }
	        // 1. Load the mapping information from the file
	        mapping.loadMapping(urlFile);
	
	        // 2. Unmarshal the data
	        Unmarshaller unmar = new Unmarshaller(mapping);
	        tokens = (Tokens)unmar.unmarshal(new InputSource(new FileReader("conf/tokenizer.xml")));
	        
	        logger.info("Il numero di token caricati è : "+tokens.getTokenList().size());
	        
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
	 * Metodo per recuperare l'oggetto Tokens
	 * @return	:	oggetto Tokens
	 */
	public Tokens getTokens() {
		return tokens;
	}

	/***
	 * Metodo per settare l'oggetto Tokens
	 * @param tokens	: oggetto Tokens
	 */
	public void setTokens(Tokens tokens) {
		this.tokens = tokens;
	}
	
}
