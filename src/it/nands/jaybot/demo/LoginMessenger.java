package it.nands.jaybot.demo;


import java.io.IOException;

import it.nands.jaybot.plugin.configurator.controller.PluginLoader;
import it.nands.jaybot.plugin.configurator.exception.PluginInitializeException;

import org.apache.log4j.Logger;

/***
 * Classe di prova per lanciare il bot
 * 
 * @author a.franzi
 *
 */
public class LoginMessenger{
	
	public static Logger logger = Logger.getRootLogger();
	
	
	/***
	 * Main 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) {
		try {
			// carico le istanze dei plugin
			PluginLoader.getInstance();
		
			
			logger.info("Bot inizializzato");
			
				LoginMessenger login;
				MyThread myThread = new MyThread();
				try {
					myThread.start();
		            System.in.read();
		        } catch (IOException ex) {
		            //ex.printStackTrace();
		        }
			
		

		} catch (PluginInitializeException e) {
			logger.error("Eccezione : "+e);
		}
		
	}
}
