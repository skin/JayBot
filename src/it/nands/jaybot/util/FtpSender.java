package it.nands.jaybot.util;


import it.nands.jaybot.plugin.server.bean.Ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sun.net.ftp.FtpClient;

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
	public static void sendFile(String host, String user, String pwd,String filePath, String fileNameLocal,  String destPath, String fileNameRemote) throws IOException {
		
		try{
			//crea un client FTP per il trasferimento del file
			FtpClient ftp = new FtpClient();
			ftp.openServer(host);
			ftp.login(user, pwd);
			ftp.binary();
			
			//sposta nella directory di destinazione il file
			ftp.cd(destPath);
			putFile(filePath, fileNameLocal, fileNameRemote, ftp);
			//chiude la connessione
			ftp.closeServer();	
		}catch(IOException e){
			throw new IOException("IO Excepion");
		}
			
	}

	/**
	 * @param filePath
	 * @param fileNameLocal
	 * @param fileNameRemote
	 * @param ftp
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private static void putFile(String filePath, String fileNameLocal, String fileNameRemote, FtpClient ftp) throws IOException, FileNotFoundException {
		OutputStream fileOut = ftp.put(fileNameRemote);
		InputStream fileIn = new FileInputStream(filePath + File.separator + fileNameLocal);
		byte c[] = new byte[4096];
		int read = 0;
		while ((read = fileIn.read(c)) != -1 )
		{
		fileOut.write(c, 0, read);
		}
		fileIn.close();
		fileOut.close();
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
	public static void sendFile(Ftp serverFtp,String filePath, String fileNameLocal,  String destPath, String fileNameRemote) throws IOException{
		sendFile(serverFtp.getHost(),serverFtp.getUsername(),serverFtp.getPassword(),filePath,fileNameLocal,destPath,fileNameRemote);
	}

}

