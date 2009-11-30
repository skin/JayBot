package it.nands.jaybot.plugin.properties.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.nands.jaybot.bean.User;
import it.nands.jaybot.plugin.configurator.exception.PluginException;
import it.nands.jaybot.plugin.configurator.exception.PluginInitializeException;
import it.nands.jaybot.plugin.properties.constant.XmlConstant;


/***
 * Classe per la gestione del plugin delle properties
 * @author a.franzi
 *
 */
public class PropertiesPlugin {
	
	/****
	 * Metodo per recuperare il valore di una property relativa ad un determinato
	 * modulo.
	 * @param module		: nome del modulo
	 * @param key			: nome della property
	 * @return				: valore recuperato altrimenti null
	 * @throws PluginException	: eccezione modellata del plugin
	 */
	public static String getValue (String module,String key) throws PluginException{
		
		try {
			return PropertiesLoader.getInstance().getValue(module, key) ;
		} catch (PluginInitializeException e) {
			throw new PluginException(e.getMessage());
		}
	}
	
	/****
	 * Metodo per recuperare il valore di una property relativa ad un determinato
	 * modulo.
	 * @param key			: nome della property
	 * @return				: valore recuperato altrimenti null
	 * @throws PluginException	: eccezione modellata del plugin
	 */
	public static String getValue(String key) throws PluginException{
		try {
			return PropertiesLoader.getInstance().getValue(key);
		} catch (PluginInitializeException e) {
			throw new PluginException(e.getMessage());
		}
	}
	
	
}
