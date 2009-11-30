package it.nands.jaybot.plugin.impl;

import it.nands.jaybot.plugin.configurator.exception.PluginInitializeException;

/**
 * Classe base utilizzata per i loader delle configurazioni dei plugin
 * 
 * @author a.franzi
 *
 */
public class SingletonPluginLoader{
	
	private static SingletonPluginLoader instance;
	
	/***
	 * Metodo per creare un istanza statica del singleton
	 * @return		: istanza
	 * @throws PluginInitializeException	: eccezione in fase di inizializzazione
	 */
	public static SingletonPluginLoader getInstance() throws PluginInitializeException{
		if (instance == null) instance = new SingletonPluginLoader();
		return instance;
	}
	
	/***
	 * Metodo per invalidare l'istanza
	 */
	public static void invalidate(){
		instance = null;
	}
	
}
