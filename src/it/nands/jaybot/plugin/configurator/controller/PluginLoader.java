package it.nands.jaybot.plugin.configurator.controller;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.rmi.MarshalException;

import it.nands.jaybot.plugin.configurator.bean.Plugins;
import it.nands.jaybot.plugin.configurator.exception.PluginChekerException;
import it.nands.jaybot.plugin.configurator.exception.PluginInitializeException;
import it.nands.jaybot.plugin.configurator.util.PluginChecker;
import it.nands.jaybot.plugin.configurator.util.PluginUtils;
import it.nands.jaybot.plugin.impl.SingletonPluginLoader;
import it.nands.jaybot.plugin.message.controller.MessagePlugin;

import org.apache.log4j.Logger;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.InputSource;

/***
 * Classe che gestisce il singleton dei plugin
 * @author Alessandro Franzi
 *
 */
public class PluginLoader extends SingletonPluginLoader{
	private static PluginLoader instance;
	private static Logger logger = Logger.getRootLogger();
	private Plugins plugins;
	private Plugins onMessageReceivedPlugins;
	
	/***
	 * Metodo per creare un istanza statica del singleton
	 * @return								: istanza
	 * @throws PluginInitializeException	: eccezione in fase di inizializzazione
	 */
	public static PluginLoader getInstance() throws PluginInitializeException{
		if (instance == null) instance = new PluginLoader();
		return instance;
	}
	
	/***
	 * Metodo per invalidare l'istanza
	 */
	public static void invalidate(){
		instance = null;
	}
	
	/***
	 * Costruttore privato
	 * @throws PluginInitializeException
	 */
	private PluginLoader() throws PluginInitializeException{
		try{
	        Mapping mapping = new Mapping();
	        
	        URL urlFile = PluginLoader.class.getResource("/pluginMapping.xml");
	        if (urlFile==null){
	        	throw new PluginInitializeException("Impossibile trovare il file di mapping");
	        }
	        // 1. Load the mapping information from the file
	        mapping.loadMapping(urlFile);
	
	        // 2. Unmarshal the data
	        Unmarshaller unmar = new Unmarshaller(mapping);
	        plugins = (Plugins)unmar.unmarshal(new InputSource(new FileReader("conf/plugin.xml")));
	        
	        logger.info("Il numero di plugin caricati e' : "+plugins.getPluginList().size());
	        
	        // controllo l'esistenza delle classi dei plugins
	        PluginChecker.checkProblemInClassDefinition(plugins);
	        // controllo le dipendenze tra plugins e la replicazione
	        PluginChecker.checkDependencies(plugins);
	        
	        /***
	         * Eseguo la chiamata del metodo getInstance delle classi registrate
	         * nel file plugin.xml che implementano l'interfaccia SingletonPluginLoader.
	         */
	        PluginUtils.forceIstanceOfAllPlugins(plugins);
	        
	        /***
	         * Registro i plugin che implementano l'interfaccia
	         */
	        onMessageReceivedPlugins = PluginUtils.getOnMessageReceivedPlugins(plugins);
	        logger.debug("Il numero di plugin da eseguire OnMessageReceived e' :"+onMessageReceivedPlugins.getPluginList().size());
	        
		}catch(MarshalException ex){
			logger.error("Eccezione :",ex);
			throw new PluginInitializeException(MessagePlugin.getPluginError(ex));
		}catch(MappingException ex){
			logger.error("Eccezione :",ex);
			throw new PluginInitializeException(MessagePlugin.getPluginError(ex));
		}catch(IOException ex){
			logger.error("Eccezione :",ex);
			throw new PluginInitializeException(MessagePlugin.getPluginError(ex));
		}catch(ValidationException ex){
			logger.error("Eccezione :",ex);
			throw new PluginInitializeException(MessagePlugin.getPluginError(ex));
		} catch (org.exolab.castor.xml.MarshalException ex) {
			logger.error("Eccezione :",ex);
			throw new PluginInitializeException(MessagePlugin.getPluginError(ex));
		} catch (PluginChekerException ex){
			logger.error("Eccezione :",ex);
			System.out.println(ex.getMessage());
			throw new PluginInitializeException(MessagePlugin.getPluginError(ex));
		}

	}
	
	/***
	 * Metodo per eecuperare i plugin
	 * @return	: bean contenitore di plugin
	 */
	public Plugins getPlugins() {
		if (plugins==null) return new Plugins();
		return plugins;
	}

	/***
	 * Metodo per recupare i plugin associati all'evento message Handler
	 * @return
	 */
	public Plugins getOnMessageReceivedPlugins() {
		return onMessageReceivedPlugins;
	}

}

