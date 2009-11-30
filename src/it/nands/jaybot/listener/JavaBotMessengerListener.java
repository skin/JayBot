package it.nands.jaybot.listener;

import org.apache.log4j.Logger;

import it.nands.jaybot.bot.JavaBotMessenger;
import it.nands.jaybot.constant.StatusConstant;
import it.nands.jaybot.exception.JavaBotInitializingExeption;
import net.sf.jml.MsnMessenger;
import net.sf.jml.event.MsnMessengerAdapter;

/***
 * Listener del JavaBotMessenger
 * 
 * @author a.franzi
 *
 */
public class JavaBotMessengerListener extends MsnMessengerAdapter {
	private static Logger logger = Logger.getRootLogger();
	
	/***
	 * Login completo
	 */
	public void loginCompleted(MsnMessenger messenger) {
		try { 
			JavaBotMessenger javaBotMSN = JavaBotMessenger.getInstance();
		
			logger.info(messenger.getOwner().getEmail() + " login, status LOGGED");
			System.out.println(messenger.getOwner().getEmail() + " LOGGED");
			
			// setto lo stato ad ONLINE
			javaBotMSN.setOnlineStatus();
	
			// cambio il nick a quello di default specificato nel file properties.xml
			javaBotMSN.setDefaultNick();
			
			// cambio lo status a quello di default specificato nel file properties.xml
			javaBotMSN.setDefaultPersonalMessage();
			
			// cambio lo stato e lo metto in IDLE
			javaBotMSN.setStatus(StatusConstant.IDLE);
			
		} catch (JavaBotInitializingExeption e) {
			logger.error("Eccezione : ",e);
		}

	}

	/***
	 * Logout
	 */
	public void logout(MsnMessenger messenger) {
		logger.info(messenger.getOwner().getEmail() + " logout");
		System.exit(0);
	}

	/***
	 * Eccezioni
	 */
	public void exceptionCaught(MsnMessenger messenger,
			Throwable throwable) {
		logger.info("caught exception: " + throwable);
	}
}
