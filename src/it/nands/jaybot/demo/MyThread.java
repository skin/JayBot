package it.nands.jaybot.demo;


import it.nands.jaybot.bot.JavaBotMessenger;
import it.nands.jaybot.exception.JavaBotInitializingExeption;


import org.apache.log4j.Logger;

/***
 * Thread parallelo per l'esecuzione del bot
 * @author a.franzi
 *
 */
public class MyThread extends Thread{
	private static Logger logger = Logger.getRootLogger();
	
	/***
	 * Metodo run del thread
	 */
	public void run() { 
		
		
        JavaBotMessenger jbmessenger;
		try {
			jbmessenger = JavaBotMessenger.getInstance();
			jbmessenger.start();
		} catch (JavaBotInitializingExeption e) {
			logger.error("Eccezione : ",e);
		}
        
        
        //Email emailToAdd = Email.parseStr("franziale@hotmail.it");
        //jbmessenger.addFriend(MsnList.AL,emailToAdd,"fritzzz");
   
        
    }


}
