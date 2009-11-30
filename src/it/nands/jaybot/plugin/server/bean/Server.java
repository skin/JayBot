package it.nands.jaybot.plugin.server.bean;

import java.util.HashMap;


/**
 * The Class Server
 * 
 * @author Alessandro Franzi
 */
public class Server {
	
	private String name;
	private String host;
	private String username;
	private String password;
	private HashMap<String,String> hashPath;
	
	/**
	 * Recupera il valore di : host.
	 * 
	 * @return host
	 */
	public String getHost() {
		return host;
	}
	
	/**
	 * Setta il valore di : host.
	 * 
	 * @param host nuovo valore di : host
	 */
	public void setHost(String host) {
		this.host = host;
	}
	
	/**
	 * Recupera il valore di : username.
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Setta il valore di : username.
	 * 
	 * @param username nuovo valore di : username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Recupera il valore di : password.
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Setta il valore di : password.
	 * 
	 * @param password nuovo valore di : password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Recupera il valore di : hash path.
	 * 
	 * @return hash path
	 */
	public HashMap<String,String> getHashPath() {
		return hashPath;
	}
	
	/**
	 * Setta il valore di : hash path.
	 * 
	 * @param hashPath nuovo valore di : hash path
	 */
	public void setHashPath(HashMap<String,String> hashPath) {
		this.hashPath = hashPath;
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
}
