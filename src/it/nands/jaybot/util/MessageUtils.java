package it.nands.jaybot.util;

import org.apache.log4j.Logger;

import it.nands.jaybot.bean.User;
import net.sf.jml.MsnSwitchboard;
import net.sf.jml.message.MsnInstantMessage;

/***
 * Message utils
 * 
 * @author a.franzi
 *
 */
public class MessageUtils {
	private static Logger logger = Logger.getRootLogger();
	
	/***
	 * Send message method
	 * 
	 * @param messaggio		: message
	 * @param switchboard	: switchboard
	 */
	public static void sendMessage(String messaggio,MsnSwitchboard switchboard){
		MsnInstantMessage msgToSend = new MsnInstantMessage();
		msgToSend.setContent(messaggio);
		switchboard.sendMessage(msgToSend);
		
		String debugStr = "Sent message : "+messaggio+ " to : "+UserUtils.getStringUserList(switchboard.getAllContacts());
		logger.debug(debugStr);
		System.out.println(debugStr);
	}
}
