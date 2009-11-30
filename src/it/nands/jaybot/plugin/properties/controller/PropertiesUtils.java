package it.nands.jaybot.plugin.properties.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import it.nands.jaybot.plugin.properties.bean.Module;
import it.nands.jaybot.util.StringUtils;

/***
 * Classe di utilita' per la gestione delle properties
 * 
 * @author Alessandro Franzi
 *
 */
public class PropertiesUtils {
	private static Logger logger = Logger.getRootLogger();
	
	/***
	 * Metodo per recuperare un modulo mediante il nome
	 * @param moduli	: lista di moduli
	 * @param moduleName		: nome del modulo da cercare
	 * @return			: il modulo trovato altrimenti null
	 */
	public static Module getModule(List<Module> moduli,String moduleName){
		if (moduli!=null && !StringUtils.isEmpty(moduleName)){
			for (Module currModulo : moduli){
				if (moduleName.equalsIgnoreCase(currModulo.getName())){
					// se matcha il nome con quello che st˜ cercando
					logger.debug("Modulo "+moduleName+" trovato");
					return currModulo;
				}
			}
		}
		return null;
	}
	
	/***
	 * Metodo per recuperare una property da un determinato modulo
	 * @param modulo		: modulo
	 * @param propertyName	: nome della property
	 * @return				: valore della property oppure null
	 */
	public static String getProperty (Module modulo,String propertyName){
		if (modulo!=null){
			if (!StringUtils.isEmpty(propertyName)){
				Map<String,String> hashProperties = modulo.getHashProperties();
				if (hashProperties!=null){
					logger.debug("Proprietˆ "+propertyName+" trovata : "+hashProperties.get(propertyName));
					return hashProperties.get(propertyName);
				}
			}
		}
		return null;
	}
	
	/***
	 * MEtodo per recuperare un property da un determinato modulo preso da una lista
	 * @param moduli		: lista di moduli
	 * @param moduleName	: nome del modulo
	 * @param propertyName	: nome della property
	 * @return				: valore della property oppure null
	 */
	public static String getProperty(List<Module> moduli,String moduleName,String propertyName){
		Module modulo = getModule(moduli, moduleName);
		if (modulo!=null){
			
			return getProperty(modulo, propertyName);
		}else{
			return null;
		}
	}
}
