package it.nands.jaybot.plugin.configurator.util;

import it.nands.jaybot.plugin.configurator.bean.Plugin;
import it.nands.jaybot.plugin.configurator.bean.Plugins;
import it.nands.jaybot.plugin.configurator.exception.PluginException;
import it.nands.jaybot.plugin.configurator.exception.PluginInitializeException;
import it.nands.jaybot.plugin.configurator.exception.ReplicatePluginException;
import it.nands.jaybot.plugin.impl.MessageHandlerPluginInterface;
import it.nands.jaybot.plugin.impl.SingletonPluginLoader;
import it.nands.jaybot.plugin.properties.constant.PropertiesConstant;
import it.nands.jaybot.plugin.properties.controller.PropertiesPlugin;
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
	
	
}
