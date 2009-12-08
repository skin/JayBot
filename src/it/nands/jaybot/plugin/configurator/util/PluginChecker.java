package it.nands.jaybot.plugin.configurator.util;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.nands.jaybot.plugin.configurator.bean.Plugin;
import it.nands.jaybot.plugin.configurator.bean.Plugins;
import it.nands.jaybot.plugin.configurator.exception.PluginChekerException;
import it.nands.jaybot.plugin.configurator.exception.ReplicatePluginException;

/***
 * Classe per la gestione del controllo dei plugins
 * @author Alessandro Franzi
 *
 */
public class PluginChecker {
	private static Logger logger = Logger.getRootLogger();
	
	/***
	 * Metodo per controllare problemi nella definizione delle classi specificate nel file di configurazione
	 * @param plugins					: bean di tipo Plugins
	 * @throws PluginChekerException	: eccezione in caso di errore
	 */
	public static void checkProblemInClassDefinition(Plugins plugins) throws PluginChekerException{
		if (checkIfThereIsSomePlugin(plugins)){
			// per tutti i plugin controllo che le classi associate esistano
			
			// recupero l'elenco dei plugin
			List<Plugin> listaPlugin = plugins.getPluginList();
			
			Plugin currPlugin = null;
			String classCurr ="";
			try{
				// scorro i plugin per individuarne eventuali problemi
				for (int i = 0 ; i < listaPlugin.size(); i++){
					currPlugin = listaPlugin.get(i);
					
					if (currPlugin!=null){
						// per ogni plugin prendo tutte le classi associate
						for (int j = 0 ; j < currPlugin.getClassList().size(); j++){
							classCurr = currPlugin.getClassList().get(j);
							// controllo se la classe e' definita, altrimenti pu˜ anche non essere un problema
							if (!StringUtils.isEmpty(classCurr)){
								// controllo dell'esistenza della classe mediante Reflection
								Object objToControl = Class.forName(classCurr);
							}
						}
					}
				}
			}catch(ClassNotFoundException ex){
				logger.error("Eccezione : il plugin "+currPlugin.getName()+" classe "+classCurr+" non esistente : ",ex);
				throw new PluginChekerException("Problemi con il plugin "+currPlugin.getName()+" classe "+classCurr+" non esistente");
			}
			
		}else{
			logger.error("Nessun plugin caricato : Il bean e' nullo");
			throw new PluginChekerException("Nessun plugin caricato");
		}
	}
	
	/***
	 * Metodo per controllare le dipendenze e la replicazione dei plugin
	 * @param plugins					: bean di tipo Plugins
	 * @throws PluginChekerException	: eccezione in caso di errori
	 */
	public static void checkDependencies(Plugins plugins) throws PluginChekerException{
		try{
			if (checkIfThereIsSomePlugin(plugins)){
				StringBuffer problemi = new StringBuffer("");
				// recupero l'elenco dei plugin
				List<Plugin> listaPlugin = plugins.getPluginList();
				
				Plugin currPlugin = null;
				
				// scorro i plugin generandomi una lista che li distingua
				// nel caso esistano 2 istanze dello stesso plugin genero errore
				List<String> listaPluginStr = PluginUtils.getNamesOfPlugins(plugins, true);
				for (int i = 0 ; i < listaPlugin.size(); i++){
					currPlugin = listaPlugin.get(i);
					// controllo per ciascun plugin che le dipendenze siano soddisfatte
					if (!StringUtils.isEmpty(currPlugin.getDependencies())){
						// se non e' vuoto ha senso controllare le dipendenze
						// splitto la stringa
						String dipendenze = currPlugin.getDependencies().trim();
						String[] splittedDipendenze = dipendenze.split(",");
						if (splittedDipendenze!=null){
							List<String> listaDipendenze = Arrays.asList(splittedDipendenze);
							for (int j = 0 ; j < listaDipendenze.size(); j++){
								// per ciascuna dipendenza controllo che sia in lista
								if (!listaPluginStr.contains(listaDipendenze.get(j))){
									// nel caso in cui una dipendenza non sia contenuta devo segnalarla
									problemi.append("Chek dependencies for plugin "+currPlugin.getName()+" failed! Required : "+listaDipendenze.get(j)+"\n");
								}
							}
						}
					}
				}
				// controllo la lista di problemi
				if (problemi.length()>0){
					// se ho avuto problemi
					throw new PluginChekerException(problemi.toString());
				}
			}
		}catch(ReplicatePluginException ex){
			logger.error("Eccezione : ",ex);
			throw new PluginChekerException(ex.getMessage());
		}
				
	}
	
	/***
	 * Metodo per controllare che esista almeno un plugin nella lista dei plugin caricati
	 * @param plugins	: bean di tipo Plugins
	 * @return			: boolean (true in caso di esistenza di almeno un plugin)
	 */
	public static boolean checkIfThereIsSomePlugin(Plugins plugins){
		return (plugins!=null && plugins.getPluginList()!=null && plugins.getPluginList().size() >0 );
	}
}
