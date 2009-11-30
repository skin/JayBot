package it.nands.jaybot.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;



/**
 * @author a.pellegatta
 *
 */
public class SshController {

//	private static Logger logger = Logger.getRootLogger();
//
//	private static final long TIMEOUT = 2000;
//	
//	private String hostname;
//	private String username;
//	private String password;
//	private SshClient sshClient;
//	private PasswordAuthenticationClient passwordAuthenticationClient;
//	private SessionChannelClient sessionChannelClient;
//	private InputStream inputStream;
//	private OutputStream outputStream;
//	private OutputStream errorStream;
//	private IOStreamConnector inputStreamConnector;
//	private IOStreamConnector outputStreamConnector;
//	private IOStreamConnector errorStreamConnector;
//
//	/**
//	 * @param aHostname
//	 * @param aUsername
//	 * @param aPassword
//	 * @param aInputStream
//	 */
//	public SshController(String aHostname, String aUsername, String aPassword, 
//			InputStream aInputStream, OutputStream aOutputStream, OutputStream aErrorStream) {
//
//		this.hostname = aHostname;
//		this.username = aUsername;
//		this.password = aPassword;
//		this.inputStream = aInputStream;
//		this.outputStream = aOutputStream;
//		this.errorStream = aErrorStream;
//		this.sshClient = new SshClient();
//		this.passwordAuthenticationClient = new PasswordAuthenticationClient();
//		this.sessionChannelClient = new SessionChannelClient();
//	}
//
//
//	public void start(){
//		try{
//			// creazione connessione ssh
//			connectSshClient();
//			// verifica che la connessione ssh sia autenticata
//			int result = sshClient.authenticate(passwordAuthenticationClient);
//			if(result == AuthenticationProtocolState.COMPLETE) 
//				sessionChannelClient = sshClient.openSessionChannel();
//			if (sessionChannelClient.startShell()) {
//				// setta gli stream di input/output/error
//				setStream();
//				// chiude il canale dopo l'esecuzione dei comandi
//				sessionChannelClient.getState().waitForState(ChannelState.CHANNEL_CLOSED,TIMEOUT);
//			}
//			else{
//				logger.error("Failed to start the users shell");
//			}
//			disconnectSshClient();
//			closeStream();
//		} catch (IOException ioException) {
//			logger.error("IOException : " + ioException.getMessage());
//		} catch (InterruptedException interruptedException) {
//			logger.error("InterruptedException : " + interruptedException.getMessage());
//		}
//	}
//
//
//	private void closeStream() throws IOException {
//		inputStreamConnector.close();
//		outputStreamConnector.close();
//		errorStreamConnector.close();
//		inputStream.close();
//		outputStream.close();
//		errorStream.close();
//	}
//
//
//	private void disconnectSshClient() throws IOException {
//		sessionChannelClient.close();
//		sshClient.disconnect();
//	}
//
//	/**
//	 * @throws IOException
//	 */
//	private void setStream() throws IOException {
//		inputStreamConnector = new IOStreamConnector();
//		outputStreamConnector = new IOStreamConnector();
//		errorStreamConnector = new IOStreamConnector();
//		inputStreamConnector.setCloseInput(false);
//		outputStreamConnector.setCloseOutput(false);
//		errorStreamConnector.setCloseOutput(false);
//		inputStreamConnector.connect(this.inputStream, this.sessionChannelClient.getOutputStream());
//		outputStreamConnector.connect(this.sessionChannelClient.getInputStream(), this.outputStream);
//		errorStreamConnector.connect(this.sessionChannelClient.getStderrInputStream(), this.errorStream);
//	}
//
//	/**
//	 * @throws IOException
//	 */
//	private void connectSshClient() throws IOException {
//		SshClient sshClient = new SshClient();
//		SshConnectionProperties sshConnectionProperties = new SshConnectionProperties();
//		sshConnectionProperties.setHost(this.hostname);
//		PasswordAuthenticationClient passwordAuthenticationClient = new PasswordAuthenticationClient();
//		passwordAuthenticationClient.setUsername(this.username);
//		passwordAuthenticationClient.setPassword(this.password);
//		this.passwordAuthenticationClient = passwordAuthenticationClient;
//		sshClient.connect(sshConnectionProperties);
//		this.sshClient = sshClient;
//	}
//
//	/**
//	 * @return the hostname
//	 */
//	public String getHostname() {
//		return hostname;
//	}
//
//	/**
//	 * @param hostname the hostname to set
//	 */
//	public void setHostname(String hostname) {
//		this.hostname = hostname;
//	}
//
//	/**
//	 * @return the username
//	 */
//	public String getUsername() {
//		return username;
//	}
//
//	/**
//	 * @param username the username to set
//	 */
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	/**
//	 * @return the password
//	 */
//	public String getPassword() {
//		return password;
//	}
//
//	/**
//	 * @param password the password to set
//	 */
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//
//	/**
//	 * @return the outputStream
//	 */
//	public OutputStream getOutputStream() {
//		return outputStream;
//	}
//
//
//	/**
//	 * @return the errorStream
//	 */
//	public OutputStream getErrorStream() {
//		return errorStream;
//	}
//


}
