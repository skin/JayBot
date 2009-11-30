package it.nands.jaybot.plugin.properties.bean;

import java.util.Map;

/**
 * The Class Module.
 * 
 * @author Alessandro Franzi
 */
public class Module {
	
	private Map<String,String> hashProperties;
	private String name;

	/**
	 * Recupera il valore di : name.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setta il valore di : name.
	 * 
	 * @param name nuovo valore di : name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Recupera il valore di : hash properties.
	 * 
	 * @return hash properties
	 */
	public Map<String, String> getHashProperties() {
		return hashProperties;
	}

	/**
	 * Sets the hash properties.
	 * 
	 * @param hashProperties the hash properties
	 */
	public void setHashProperties(Map<String, String> hashProperties) {
		this.hashProperties = hashProperties;
	}
}
