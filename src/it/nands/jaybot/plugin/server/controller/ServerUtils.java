package it.nands.jaybot.plugin.server.controller;

import java.util.List;

import org.apache.log4j.Logger;

import it.nands.jaybot.plugin.server.bean.Ftp;
import it.nands.jaybot.plugin.server.bean.Ssh;
import it.nands.jaybot.util.StringUtils;

/**
 * Classe di utilita' per la gestione del plugin dei server
 * 
 * @author Alessandro Franzi
 *
 */
public class ServerUtils {
	private static Logger logger = Logger.getRootLogger();
	
	/***
	 * Metodo per recuperare da un elenco di server ssh quello con il nome cercato
	 * @param list	: lista di server ssh
	 * @param nome	: nome del server
	 * @return		: bean contenente il server cercato altrimenti null
	 */
	protected static Ssh getSshServerFromList(List<Ssh> list,String nome){
		if (list!=null){
			for (Ssh currSsh : list){
				if (currSsh!=null){
					if (!StringUtils.isEmpty(currSsh.getName()) && !StringUtils.isEmpty(nome)){
						// se non sono vuoti i 2 nomi
						if (currSsh.getName().equalsIgnoreCase(nome)){
							// se i nomi sono uguali restituisco il server
							return currSsh;
						}
					}
				}
			}
		}
		return null;
	}
	
	/***
	 * Metodo per recuperare da un elenco di server ftp quello con il nome cercato
	 * @param list	: lista di server ftp
	 * @param nome	: nome del server
	 * @return		: bean contenente il server cercato altrimenti null
	 */
	protected static Ftp getFtpServerFromList(List<Ftp> list,String nome){
		if (list!=null){
			for (Ftp currFtp : list){
				if (currFtp!=null){
					if (!StringUtils.isEmpty(currFtp.getName()) && !StringUtils.isEmpty(nome)){
						// se non sono vuoti i 2 nomi
						if (currFtp.getName().equalsIgnoreCase(nome)){
							// se i nomi sono uguali restituisco il server
							return currFtp;
						}
					}
				}
			}
		}
		return null;
	}
	
}
