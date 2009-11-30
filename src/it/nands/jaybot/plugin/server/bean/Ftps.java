package it.nands.jaybot.plugin.server.bean;

import java.util.List;

/**
 * The Class Ftps.
 * 
 * @author Alessandro Franzi
 */
public class Ftps {
	private List<Ftp> ftpList;

	/**
	 * Recupera il valore di : ftp list.
	 * 
	 * @return ftp list
	 */
	public List<Ftp> getFtpList() {
		return ftpList;
	}

	/**
	 * Setta il valore di : ftp list.
	 * 
	 * @param ftpList nuovo valore di : ftp list
	 */
	public void setFtpList(List<Ftp> ftpList) {
		this.ftpList = ftpList;
	}
}
