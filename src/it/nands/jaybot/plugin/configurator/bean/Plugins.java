package it.nands.jaybot.plugin.configurator.bean;


import java.util.List;

/**
 * The Class Plugins.
 * 
 * @author Alessandro Franzi
 */
public class Plugins {
	private List<Plugin> pluginList;

	/**
	 * Recupera il valore di : plugin list.
	 * 
	 * @return plugin list
	 */
	public List<Plugin> getPluginList() {
		return pluginList;
	}

	/**
	 * Setta il valore di : plugin list.
	 * 
	 * @param pluginList nuovo valore di : plugin list
	 */
	public void setPluginList(List<Plugin> pluginList) {
		this.pluginList = pluginList;
	}
}
