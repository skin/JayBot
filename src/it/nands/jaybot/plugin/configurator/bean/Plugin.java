package it.nands.jaybot.plugin.configurator.bean;

import java.util.List;

/***
 * The Class Plugin.
 * @author Alessandro Franzi
 *
 */
public class Plugin {
	
	private String name;
	private String dependencies;
	private Boolean blocking = new Boolean(false);
	private List<String> classList;
	private List<Command> commandList;
	
	

	
	/**
	 * Recupera il valore di : class list.
	 * 
	 * @return class list
	 */
	public List<String> getClassList() {
		return classList;
	}

	/**
	 * Setta il valore di : class list.
	 * 
	 * @param classList nuovo valore di : class list
	 */
	public void setClassList(List<String> classList) {
		this.classList = classList;
	}

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
	 * Recupera il valore di : dependencies.
	 * 
	 * @return dependencies
	 */
	public String getDependencies() {
		return dependencies;
	}
	
	/**
	 * Setta il valore di : dependencies.
	 * 
	 * @param dependencies nuovo valore di : dependencies
	 */
	public void setDependencies(String dependencies) {
		this.dependencies = dependencies;
	}
	
	public Boolean getBlocking() {
		return blocking;
	}

	public void setBlocking(Boolean blocking) {
		this.blocking = blocking;
	}
	/**
	 * Recupera il valore di : command list.
	 * 
	 * @return command list
	 */
	public List<Command> getCommandList() {
		return commandList;
	}

	/**
	 * Setta il valore di : command list.
	 * 
	 * @param commandList nuovo valore di : command list
	 */
	public void setCommandList(List<Command> commandList) {
		this.commandList = commandList;
	}

}
