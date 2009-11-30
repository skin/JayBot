package it.nands.jaybot.plugin.message.bean;

import java.util.HashMap;

/**
 * The Class Messages.
 * 
 * @author Alessandro Franzi
 */
public class Messages {
	private HashMap<String, String> hashMessage;

	/**
	 * Recupera il valore di : hash message.
	 * 
	 * @return hash message
	 */
	public HashMap<String, String> getHashMessage() {
		return hashMessage;
	}

	/**
	 * Sets the hash message
	 * 
	 * @param hashMessages the hash message
	 */
	public void setHashMessage(HashMap<String, String> hashMessage) {
		this.hashMessage = hashMessage;
	}
	
}
