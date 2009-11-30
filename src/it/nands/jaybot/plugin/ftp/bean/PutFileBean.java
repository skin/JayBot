package it.nands.jaybot.plugin.ftp.bean;

import it.nands.jaybot.bean.User;
import it.nands.jaybot.plugin.server.bean.Server;

/***
 * Class PutFileBean.
 * 
 * @author a.franzi
 *
 */
public class PutFileBean {
	private Server server ;
	private User user;
	private String dirPath;
	private String dirName;
	
	
	/**
	 * Metodo che restituisce il valore: server.
	 * 
	 * @return Restituisce il valore: server
	 */
	public Server getServer() {
		return server;
	}
	
	/**
	 * Metodo che setta il valore: server.
	 * 
	 * @param server - parametro che serve per settare il valore: server
	 */
	public void setServer(Server server) {
		this.server = server;
	}
	
	/**
	 * Metodo che restituisce il valore: user.
	 * 
	 * @return Restituisce il valore: user
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * Metodo che setta il valore: user.
	 * 
	 * @param user - parametro che serve per settare il valore: user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * Metodo che restituisce il valore: dir path.
	 * 
	 * @return Restituisce il valore: dir path
	 */
	public String getDirPath() {
		return dirPath;
	}
	
	/**
	 * Metodo che setta il valore: dir path.
	 * 
	 * @param dirPath - parametro che serve per settare il valore: dir path
	 */
	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}
	
	/**
	 * Metodo che restituisce il valore: dir name.
	 * 
	 * @return Restituisce il valore: dir name
	 */
	public String getDirName() {
		return dirName;
	}
	
	/**
	 * Metodo che setta il valore: dir name.
	 * 
	 * @param dirName - parametro che serve per settare il valore: dir name
	 */
	public void setDirName(String dirName) {
		this.dirName = dirName;
	}
}
