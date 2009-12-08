package it.nands.jaybot.util;

import java.util.ArrayList;
import java.util.List;

import it.nands.jaybot.bean.User;
import it.nands.jaybot.plugin.configurator.exception.PluginException;
import it.nands.jaybot.plugin.properties.constant.PropertiesConstant;
import it.nands.jaybot.plugin.properties.controller.PropertiesPlugin;

import net.sf.jml.MsnContact;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/***
 * Classe per la gestione delle utilita' per gli utenti
 * @author a.franzi
 *
 */
public class UserUtils {
	private static Logger logger = Logger.getRootLogger();
	
	/***
	 * Method that returns a list of administrators
	 * @return
	 * @throws PluginException
	 */
	public static List<User> getAdministrators () throws PluginException{
		
		List<User> retUserList = new ArrayList<User>();
		
		String administratorStringList =  PropertiesPlugin.getValue(PropertiesConstant.PROP_ADMINISTRATOR_LIST);
		if (!StringUtils.isEmpty(administratorStringList)){
			String[] splittedAdminString = administratorStringList.split(";");
			if (splittedAdminString !=null && splittedAdminString.length>0){
				for (int i = 0 ; i < splittedAdminString.length; i++){
					User currUser = new User();
					currUser.setEmail(splittedAdminString[i]);
					currUser.setAdmin(true);
				}
			}
		}
		
		return retUserList;
	}
	
	public static boolean isAdmin(User utente){
		try {
			List<User> amministratori = getAdministrators();
			
			if (amministratori.contains(utente)){
				return true;
			}
			
		} catch (PluginException e) {
			logger.error("Eccezione : ",e);
		}
		
		return false;
		
	}
	
	/***
	 * 
	 * @param emailUser
	 * @return
	 */
	public static boolean isAdmin(String emailUser){
		User utente = new User();
		utente.setEmail(emailUser);
		return isAdmin(utente);
	}
	
	public static User getUser(MsnContact contact){
		User utente = new User();
		utente.setEmail(contact.getEmail().toString());
		utente.setUserName(contact.getDisplayName());
		utente.setAdmin(isAdmin(contact));
		return utente;
	}
	
	public static boolean isAdmin(MsnContact contact){
		User utente = new User();
		utente.setEmail(contact.getEmail().toString());
		return isAdmin(utente);
	}
	
	public static String getStringUserList(MsnContact[] elencoContatti){
		String retString = "";
		if (elencoContatti!=null){
			for (int i = 0 ; i < elencoContatti.length; i++){
				retString += (i!=0)?",":"";
				retString += elencoContatti[i].getEmail();
			}
		}
		return retString;
	}
}
