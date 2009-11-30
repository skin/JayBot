package it.nands.jaybot.plugin.resource.controller;

import it.nands.jaybot.plugin.configurator.exception.PluginException;
import it.nands.jaybot.plugin.configurator.exception.PluginInitializeException;
import it.nands.jaybot.plugin.resource.bean.Resource;


/***
 * Classe per la gestione del plugin delle risorse
 * @author a.franzi
 *
 */
public class ResourcesPlugin {
	
	/****
	 * Metodo per recuperare il valore di una risorsa
	 * 
	 * @param name			: nome della risorsa
	 * @return				: risorsa recuperata altrimenti null
	 * @throws PluginException	: eccezione modellata del plugin
	 */
	public static Resource getResource (String name) throws PluginException{
		
		try {
			return ResourcesLoader.getInstance().getResource(name);
		} catch (PluginInitializeException e) {
			throw new PluginException(e.getMessage());
		}
	}
	
	
	
	
}
