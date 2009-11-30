package it.nands.jaybot.bot;

import java.io.File;
import java.io.IOException;

import it.nands.jaybot.constant.StatusConstant;
import it.nands.jaybot.exception.JavaBotInitializingExeption;
import it.nands.jaybot.listener.JavaBotMessengerListener;
import it.nands.jaybot.plugin.configurator.exception.PluginException;
import it.nands.jaybot.plugin.properties.constant.XmlConstant;
import it.nands.jaybot.plugin.properties.controller.PropertiesPlugin;
import it.nands.jaybot.util.ChatMessageUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import net.sf.jml.Email;
import net.sf.jml.MsnContact;
import net.sf.jml.MsnFileTransfer;
import net.sf.jml.MsnList;
import net.sf.jml.MsnMessenger;
import net.sf.jml.MsnProtocol;
import net.sf.jml.MsnSwitchboard;
import net.sf.jml.MsnUserStatus;
import net.sf.jml.event.MsnContactListAdapter;
import net.sf.jml.event.MsnFileTransferListener;
import net.sf.jml.event.MsnMessageAdapter;
import net.sf.jml.impl.BasicMessenger;
import net.sf.jml.impl.MsnFileTransferImpl;
import net.sf.jml.impl.MsnMessengerFactory;
import net.sf.jml.message.MsnControlMessage;
import net.sf.jml.message.MsnDatacastMessage;
import net.sf.jml.message.MsnInstantMessage;

/***
 * JavaBot class
 * Extends 
 * @author a.franzi
 *
 */
public class JavaBotMessenger{
	private static Logger logger = Logger.getRootLogger();
	private static JavaBotMessenger instance = null;
	
	
	private String email;
	private String password;
	public int status = StatusConstant.INITIALIZING; 
	private BasicMessenger messenger;


	/***
	 * Costruttore 
	 * @throws JavaBotInitializingExeption : eccezione di inizializzazione
	 */
	private JavaBotMessenger() throws JavaBotInitializingExeption{
		
		try {
			email = PropertiesPlugin.getValue(XmlConstant.PROP_USERNAME);
			password = PropertiesPlugin.getValue(XmlConstant.PROP_PASSWORD);
		} catch (PluginException e) {
			throw new JavaBotInitializingExeption(e.getMessage());
		}
		
	}

	/***
	 * Metodo per recuperare l'istanza del Javabot
	 * @throws JavaBotInitializingExeption	: eccezione di inizializzazione
	 */
	public static JavaBotMessenger getInstance() throws JavaBotInitializingExeption{
		if (instance==null){
			instance = new JavaBotMessenger();
		}
		return instance;
	}
	
	/***
	 * Invalidate instance
	 */
	public static void invalidate(){
		instance = null;
	}
	/***
	 * Inith method
	 * @param messenger	: messenger
	 */
	protected void initMessenger(MsnMessenger messenger) {
		logger.info("Initializing..");
		System.out.println("Initializing..");
		this.setStatus(StatusConstant.INITIALIZING);

		//TODO : ripristinare questo
		messenger.addMessengerListener(new JavaBotMessengerListener());
		
		messenger.addFileTransferListener(new MsnFileTransferListener(){

			public void fileTransferFinished(MsnFileTransfer arg0) {
				logger.info("fileTransferFinished");
				
				
			}

			public void fileTransferProcess(MsnFileTransfer arg0) {
				logger.info("fileTransferProcess : started "+arg0.isStarted());
				logger.info("fileTransferProcess : getState "+arg0.getState().toString());
				logger.info("fileTransferProcess : getTransferredSize" + arg0.getTransferredSize());
				logger.info("fileTransferProcess : getFileTotalSize" + arg0.getFileTotalSize());
				logger.info("fileTransferProcess : getID" + arg0.getID());
				
				
			}

			public void fileTransferRequestReceived(MsnFileTransfer arg0) {
				logger.info("fileTransferRequestReceived");
				arg0.setFile(new File("c:/file.ricevuto"));
				arg0.start();
				
				
			}

			public void fileTransferStarted(MsnFileTransfer arg0) {
				logger.info("fileTransferStarted");
				
				
			}
			
		});
		
		messenger.addContactListListener(new MsnContactListAdapter() {

			public void contactListInitCompleted(MsnMessenger messenger) {
				//get contacts in allow list
				
				
//				MsnContact[] contacts = messenger.getContactList()
//				.getContactsInList(MsnList.AL);
//
//				logger.info("Saluto");
//				for (MsnContact contact : contacts) {
//					//don't send message to offline contact
//					if (contact.getStatus() != MsnUserStatus.OFFLINE) {
//						//this is the simplest way to send text
//						logger.info("Saluto : "+contact.getEmail());
//						messenger.sendText(contact.getEmail(), "hello ;)");
//					}
//				}

			}

		});
		
		messenger.addMessageListener(new MsnMessageAdapter() {

			public void instantMessageReceived(MsnSwitchboard switchboard,
					MsnInstantMessage message, MsnContact contact) {
				
				//TODO:TOGLIERE
				try {
					logger.info("SENDING FILE");
					 if(contact != null && contact.getStatus().equals(MsnUserStatus.OFFLINE)){
						 logger.info("eheeeeeeeeee contact is offline ");
						 System.out.println("eheeeeeeeeee contact is offline");
					 }else{
						 logger.info("Status : "+contact.getStatus());
						 System.out.println("Status : "+contact.getStatus());
					 }
					 
					MsnFileTransfer myFileTransfer = JavaBotMessenger.getInstance().messenger.getFileTransferManager().sendFile(contact.getEmail(), new File("c:/prova.html"));
					//myFileTransfer.start();
					
					
				} catch (JavaBotInitializingExeption e1) {
					logger.error("Eccezione : ",e1);
				}
				//TODO:TOGLIERE
				
				//text message received
				logger.info(contact.getEmail().toString()+" says : "+message.getContent());
				System.out.println(contact.getEmail().toString()+" says : "+message.getContent());
			
//				try {
//					ChatMessageUtils.manageReceivedMessage(switchboard,message,contact);
//				} catch (IOException e) {
//					logger.error("Eccezione : ",e);
//				} 
				
			}

			public void controlMessageReceived(MsnSwitchboard switchboard,
					MsnControlMessage message, MsnContact contact) {
				//such as typing message and recording message
				String contactEmail =contact.getEmail().toString();
				if (!StringUtils.isEmpty(message.getRecordingUser())){
					logger.info("User "+contactEmail+" wants to Record you as a friend");
					System.out.println("User "+contactEmail+" wants to Record you as a friend");
				}else if (!StringUtils.isEmpty(message.getTypingUser())){
					logger.debug("User "+contactEmail+" is typing a message");
				}
			}

			public void datacastMessageReceived(MsnSwitchboard switchboard,
					MsnDatacastMessage message, MsnContact contact) {
				//such as Nudge
				//switchboard.sendMessage(message);
			}

		});
		logger.info("Initialized");
		System.out.println("Initialized");
		this.setStatus(StatusConstant.INITIALIZED);

	}
	
	/***
	 * Start the bot
	 */
	public void start() {
		//create MsnMessenger instance
		messenger = (BasicMessenger)MsnMessengerFactory.createMsnMessenger(email,
				password);
		
		messenger.setSupportedProtocol(MsnProtocol.getAllSupportedProtocol());
		//MsnMessenger support all protocols by default
		//messenger.setSupportedProtocol(new MsnProtocol[] { MsnProtocol.MSNP8 });

		//default init status is online, 
		setInitOnlineStatus (); 

		//log incoming message
		//messenger.setLogIncoming(true);

		//log outgoing message
		//messenger.setLogOutgoing(true);

		initMessenger(messenger);
		messenger.login();
	}
	
	
	/**
	 * This method change status and pm to quit mode and disconnect the bot client
	 */
	public void quit(){
		this.setStatus(StatusConstant.QUIT);
		try{
			String pm="";
			try {
				pm = PropertiesPlugin.getValue(XmlConstant.PROP_QUIT_PERSONAL_MESSAGE);
				setPersonalMessage(pm);
			} catch (PluginException e) {
				pm ="";
				logger.error("Eccezione : ",e);
			}
			messenger.logout();
		}catch(Exception e){
			logger.error("Problema shutDown : ",e);
		}
	}

	public void setInitOnlineStatus(){
		logger.info("online init status");
		messenger.getOwner().setInitStatus(MsnUserStatus.ONLINE);
	}

	public void setDefaultNick(){
		String defaultNick = getDefaultNickName ();
		messenger.getOwner().setDisplayName(defaultNick);
		logger.info("Default Nick : "+defaultNick);
		System.out.println("Default Nick : "+defaultNick);
	}
	
	public void setDefaultPersonalMessage(){
		String defaultPM = getDefaultPersonalMessage ();
		setPersonalMessage(defaultPM);
	}
	public void setPersonalMessage(String pm){
		messenger.getOwner().setPersonalMessage(pm);
		logger.info("PM : "+pm);
		System.out.println("PM : "+pm);
	}
	public void setOnlineStatus(){
		logger.info("online status");
		messenger.getOwner().setStatus(MsnUserStatus.ONLINE);
		logger.info("JML status : "+messenger.getOwner().getStatus());	
		System.out.println("JML status : "+messenger.getOwner().getStatus());	
	}
	
	
	
	public void addFriend(MsnList list, Email email, String friendlyName) {

		if (list == null || email == null || list == MsnList.RL
				|| list == MsnList.PL)
			return;
		MsnContact contact = messenger.getContactList().getContactByEmail(email);
		// added for null pointer problem
		if(contact!=null){
			if (contact.isInList(list)){
				return;
			}
		}

	}
	public String getDefaultNickName(){
		String nickName ="";
		try {
			nickName = PropertiesPlugin.getValue(XmlConstant.PROP_BOTNAME);
		} catch (PluginException e) {
			nickName ="";
			logger.error("Eccezione : ",e);
		}
		return nickName;
	}
	
	public String getDefaultPersonalMessage(){
		String pm ="";
		try {
			pm = PropertiesPlugin.getValue(XmlConstant.PROP_PERSONAL_MESSAGE);
		} catch (PluginException e) {
			pm ="";
			logger.error("Eccezione : ",e);
		}
		return pm;
	}
	
	
	public boolean isIdle(){
		if (StatusConstant.IDLE == this.status){
			return true;
		}
		return false;
	}
	public String getNick(){
		return messenger.getOwner().getDisplayName();
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
