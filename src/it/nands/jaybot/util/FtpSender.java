package it.nands.jaybot.util;


import it.nands.jaybot.plugin.server.bean.Ftp;

import java.io.File;
import java.io.IOException;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

/***
 * Classe per la gestione dell'invio di file tramite ftp
 * 
 * @author a.franzi
 *
 */
public class FtpSender {
		
	/**
	 * Crea la connessione FTP e invia il file
	 * 
	 * @param host 				: host per la connessione FTP
	 * @param user 				: user per la connessione FTP
	 * @param pwd 				: password per la connessione FTP
	 * @param fileNameLocal 	: nome completo del file da inviare
	 * @param filePath 			: pathname della directory contenente il file da inviare
	 * @param destPath 			: pathname di destinazione del file
	 * @param fileNameRemote 	: filename remoto	
	 * @throws IOException		: eccezione di I/O
	 * @throws FTPException		: eccezione FTP
	 */
	public static void sendFile(String host, String user, String pwd,String filePath, String fileNameLocal,  String destPath, String fileNameRemote) throws IOException, FTPException{
		
		try{
			//crea un client FTP per il trasferimento del file
			FTPClient ftp = new FTPClient(host);
			ftp.debugResponses(false);
			ftp.login(user, pwd);
			ftp.setType(FTPTransferType.BINARY);
			
			//sposta nella directory di destinazione il file
			ftp.chdir(destPath);
			ftp.put(filePath + File.separator + fileNameLocal, fileNameRemote);
			
			//chiude la connessione
			ftp.quit();	
		}catch(FTPException e){
			throw new FTPException("Transfer non riuscito.");			
		}catch(IOException e){
			throw new IOException("IO Excepion");
		}
			
	}
	
	/***
	 * Crea la connessione FTP e invia il file
	 * 
	 * @param serverFtp			: bean di tipo Ftp
	 * @param filePath 			: pathname della directory contenente il file da inviare
	 * @param destPath 			: pathname di destinazione del file
	 * @param fileNameRemote 	: filename remoto	
	 * @throws IOException		: eccezione di I/O
	 * @throws FTPException		: eccezione FTP
	 */
	public static void sendFile(Ftp serverFtp,String filePath, String fileNameLocal,  String destPath, String fileNameRemote) throws IOException, FTPException{
		sendFile(serverFtp.getHost(),serverFtp.getUsername(),serverFtp.getPassword(),filePath,fileNameLocal,destPath,fileNameRemote);
	}

}

