package it.nands.jaybot.plugin.server.bean;

/**
 * The Class Servers.
 * 
 * @author Alessandro Franzi
 */
public class Servers {
	private Ftps ftps;
	private Sshs sshs;
	
	/**
	 * Recupera il valore di : ftps.
	 * 
	 * @return ftps
	 */
	public Ftps getFtps() {
		return ftps;
	}
	
	/**
	 * Setta il valore di : ftps.
	 * 
	 * @param ftps nuovo valore di : ftps
	 */
	public void setFtps(Ftps ftps) {
		this.ftps = ftps;
	}
	
	/**
	 * Recupera il valore di : sshs.
	 * 
	 * @return sshs
	 */
	public Sshs getSshs() {
		return sshs;
	}
	
	/**
	 * Setta il valore di : sshs.
	 * 
	 * @param sshs nuovo valore di : sshs
	 */
	public void setSshs(Sshs sshs) {
		this.sshs = sshs;
	}
	
}
