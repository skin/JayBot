package it.nands.jaybot.plugin.paramcontroller.bean;


import java.util.List;

/**
 * The Class Commands.
 * 
 * @author Alessandro Franzi
 */
public class Commands {
	private List<Command> commandList;

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
