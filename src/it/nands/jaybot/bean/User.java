package it.nands.jaybot.bean;

import it.nands.jaybot.util.StringUtils;


/***
 * Classe utente
 * 
 * @author a.franzi
 *
 */
public class User {
	
	private String userName;
	private String email;
	private boolean isAdmin;
	
	/**
	 * Metodo che restituisce il valore: user name.
	 * 
	 * @return Restituisce il valore: user name
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * Metodo che setta il valore: user name.
	 * 
	 * @param userName - parametro che serve per settare il valore: user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	/***
	 * Metodo per confrontare l'oggetto.
	 * Confronta solo l'email
	 */
	public boolean equals(Object o) {
		User objToConfront = (User)o;
		if (!StringUtils.isEmpty(objToConfront.getEmail())  &&
			objToConfront.getEmail().equalsIgnoreCase(this.email)
			){
			return true;
		}
		return false;	
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}
