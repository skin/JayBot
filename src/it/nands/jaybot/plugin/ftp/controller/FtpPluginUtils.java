package it.nands.jaybot.plugin.ftp.controller;

import it.nands.jaybot.plugin.ftp.bean.PutFileBean;

import java.util.List;

/***
 * Classe di utilita' per la gestione del plugin per l'ftp
 * 
 * @author a.franzi
 *
 */
public class FtpPluginUtils {
	/***
	 * Metodo per controllare se un utente ha già richieste pendenti in attesa
	 * @param queueFtpRequest
	 * @return
	 */
	protected boolean userHasPendingRequestInQueue(List<PutFileBean> queueFtpRequest,String UserID){
		return false;
	}
}
