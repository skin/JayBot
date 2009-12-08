package it.nands.jaybot.plugin.configurator.bean;

import java.util.List;

/***
 * Bean contenitore delle informazioni relative ad un singolo comando
 * @author Alessandro Franzi
 *
 */
public class Command {
	private String name;
	private String commandDescription;
	private Boolean onlyForAdmin = false;
	private Boolean visible = true;
	private List<CommandItem> commandItemList;
	
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
	 * Recupera il valore di : command item list.
	 * 
	 * @return command item list
	 */
	public List<CommandItem> getCommandItemList() {
		return commandItemList;
	}

	/**
	 * Setta il valore di : command item list.
	 * 
	 * @param commandItemList nuovo valore di : command item list
	 */
	public void setCommandItemList(List<CommandItem> commandItemList) {
		this.commandItemList = commandItemList;
	}

	/**
	 * Recupera il valore di : command description.
	 * 
	 * @return command description
	 */
	public String getCommandDescription() {
		return commandDescription;
	}

	/**
	 * Setta il valore di : command description.
	 * 
	 * @param commandDescription nuovo valore di : command description
	 */
	public void setCommandDescription(String commandDescription) {
		this.commandDescription = commandDescription;
	}

	public Boolean getOnlyForAdmin() {
		return onlyForAdmin;
	}

	public void setOnlyForAdmin(Boolean onlyForAdmin) {
		this.onlyForAdmin = onlyForAdmin;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	
	
}
