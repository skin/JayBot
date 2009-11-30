package it.nands.jaybot.plugin.server.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import it.nands.jaybot.plugin.configurator.exception.PluginInitializeException;
import it.nands.jaybot.plugin.server.bean.Ftp;
import it.nands.jaybot.plugin.server.bean.Server;
import it.nands.jaybot.plugin.server.bean.Ssh;
import it.nands.jaybot.plugin.server.constant.ServerPluginConstant;

/***
 * Classe per l'accesso alle informazioni del plugin relativo ai server ftp e ssh
 * @author Alessandro Franzi
 *
 */
public class ServerPlugin {
	private static Logger logger = Logger.getRootLogger();
	
	/***
	 * Metodo per recuperare un server in base al nome e alla tipologia
	 * @param nome	: nome del server
	 * @param type	: tipo del server
	 * @return		: un bean di tipo server
	 */
	public static Server getServer(String nome,String type){
		try {
			if (ServerPluginConstant.FTP_SERVER.equalsIgnoreCase(type)){
				// se si cerca un server ftp
				List<Ftp> ftpList = ServerLoader.getInstance().getServers().getFtps().getFtpList();
				Ftp serverFtp = ServerUtils.getFtpServerFromList(ftpList, nome);
				return serverFtp;
			}
			if (ServerPluginConstant.SSH_SERVER.equalsIgnoreCase(type)){
				// se si cerca un server ssh
				List<Ssh> sshList = ServerLoader.getInstance().getServers().getSshs().getSshList();
				Ssh serverSsh = ServerUtils.getSshServerFromList(sshList, nome);
				return serverSsh;
			}
		} catch (PluginInitializeException e) {
			logger.error("Eccezione : ",e);
		}
		return null;
	}
	
	/***
	 * MEtodo per recuperare un determinato server ssh cercandolo per nome
	 * @param nome	: nome del server
	 * @return		: bean contenente le informazioni del server, altrimenti null
	 */
	public static Ssh getSshServer (String nome){
		Server retServer = getServer(nome,ServerPluginConstant.SSH_SERVER);
		if (retServer!=null){
			return (Ssh)retServer;
		}
		return null;
	}
	
	/***
	 * MEtodo per recuperare un determinato server ftp cercandolo per nome
	 * @param nome	: nome del server
	 * @return		: bean contenente le informazioni del server, altrimenti null
	 */
	public static Ftp getFtpServer (String nome){
		Server retServer = getServer(nome,ServerPluginConstant.FTP_SERVER);
		if (retServer!=null){
			return (Ftp)retServer;
		}
		return null;
	}
	
	/***
	 * MEtodo per recuperare il path di default da un determinato server, cercato
	 * per nome.
	 * @param serverName		: nome del server
	 * @return					: path oppure null
	 */
	public static String getDefaultPathFromFtpServer (String serverName){
		return getPathFromServerFromFtpServer(serverName,ServerPluginConstant.DEFAULT_PATH);
	}
	
	/***
	 * Metodo per recuperare il percorso dal nome di un server e dal nome del path
	 * 
	 * @param serverName	: nome del server
	 * @param pathName		: nome del path
	 * @return				: path oppure null
	 */
	public static String getPathFromServerFromFtpServer (String serverName, String pathName){
		Server serverFtp = getServer(serverName,ServerPluginConstant.FTP_SERVER);
		if (serverFtp!=null){
			return getPathFromServer(serverFtp,pathName);
		}
		return null;
	}
	
	/***
	 * Metodo per recuperare il path da un server ricercandolo per nome
	 * @param server		: bean del server
	 * @param namePath		: nome del path
	 * @return				: path oppure null
	 */
	public static String getPathFromServer(Server server, String namePath){
		Map<String,String> hashPath =  server.getHashPath();
		if (hashPath!=null){
			String path = hashPath.get(namePath);
			return path;
		}
		return null;
	}
	
	/***
	 * Metodo per recuperare il nome e host relativo ad un server
	 * @param serverName	:	nome del server
	 * @param type			: 	tipo di server
	 * @return				: 	nome e host del server altrimenti null
	 */
	public static String getNameAndHostServer(String serverName,String type){
		Server server = getServer(serverName,type);
		if (server!=null){
			return server.getName()+"("+server.getHost()+")";
		}
		return null;
	}
	
	/***
	 * MEtodo per recuperare il nome e host relativo ad un server ftp
	 * @param serverName	: 	nome del server ftp
	 * @return				:	nome e host del server altrimenti null
	 */
	public static String getNameAndHostFtpServer(String serverName){
		return getNameAndHostServer(serverName,ServerPluginConstant.FTP_SERVER);
	}
}
