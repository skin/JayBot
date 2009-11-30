package it.nands.jaybot.plugin.server.bean;

import java.util.List;

/**
 * The Class Ftps.
 * 
 * @author Alessandro Franzi
 */
public class Sshs {
	private List<Ssh> sshList;

	/**
	 * Recupera il valore di : ssh list.
	 * 
	 * @return ssh list
	 */
	public List<Ssh> getSshList() {
		return sshList;
	}

	/**
	 * Setta il valore di : ssh list.
	 * 
	 * @param sshList nuovo valore di : ssh list
	 */
	public void setSshList(List<Ssh> sshList) {
		this.sshList = sshList;
	}


}
