package it.reply.technology.customJabber;


import java.io.File;
import java.io.InputStream;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import it.reply.technology.constant.PropertiesConstant;
import it.reply.technology.constant.StatusConstant;
import it.reply.technology.listener.ReplyListener;
import it.reply.technology.plugin.configurator.exception.PluginException;
import it.reply.technology.plugin.ftp.bean.PutFileBean;
import it.reply.technology.plugin.properties.constant.XmlConstant;
import it.reply.technology.plugin.properties.controller.PropertiesPlugin;
import it.reply.technology.util.FileUtils;
import it.reply.technology.util.GroupUtils;
import it.reply.technology.util.ImageUtils;

import net.sf.jml.example.BasicMessenger;

import org.apache.log4j.Logger;

import com.sun.corba.se.impl.protocol.JIDLLocalCRDImpl;


/***
 * Implementazione della classe BasicMessenger
 * 
 * @author a.franzi
 *
 */
public class MsnBotMessenger extends BasicMessenger {
	private static Logger logger = Logger.getRootLogger();
	
	public static String serverXMMP;
	public int stato = StatusConstant.NOT_LOGGED;
	public String nickOwnerStatus = "";
	public String environmentTouched = "";
	public String earName ="";
	
	
	private ReplyListener replyListener;
	private String userID ="";
	private String nickBot = "";
	private VCard vCard = new VCard();
	
	// FTP
	private List<PutFileBean> codaFtp;
	


	/***
	 * Costruttore ReplyMessenger
	 * 
	 * @param userName			: username
	 * @param password
	 * @param serverXMMP
	 * @throws XMPPException
	 */
	public ReplyJabber(String userName, String password, String serverXMMP) throws XMPPException {
		
		logger.debug("Creo il listener");
		replyListener = new ReplyListener(this,serverXMMP);
		logger.debug("Eseguo il login");
		replyListener.login(userName, password);
		logger.debug("loggato");
		this.setStato(StatusConstant.LOGGED);
		
		try {
			this.nickBot = PropertiesPlugin.getValue
			(
					XmlConstant.DEFAULT_MODULE,
					XmlConstant.PROP_BOTNAME
			);
		} catch (PluginException e) {
			this.nickBot ="";
			logger.error("Eccezione : ",e);
		}
		
		System.out.println("Press enter to disconnect");
		
		
		List<String> contacts =new ArrayList<String>(); 
		contacts.add("a.pellegatta@collabtech.replynet.prv/spark");
		contacts.add("a.franzi@collabtech.replynet.prv/spark");
		//MultiUserChat muc = createMUCAndInvite("deploy",contacts);

		
		
		
		//replyListener.sendMessage("Scrivimi polenta!","a.pellegatta@collabtech.replynet.prv");
		replyListener.sendMessage("son qui","a.franzi@collabtech.replynet.prv");
        //replyListener.sendBroadCastMessageToRoster("prova broadcast");
//        try {
//			replyListener.setAvatar("c:/logo.jpg");
//		} catch (ProtocolException e) {
//			logger.error("Eccezione : ",e);
//		}
		//super(username, password);
		
		//this.addMsnListener(new MsnReplyAdapter());
		//logger.debug("Ci sono "+this.getListenerCount()+" listner");
		//this.login();
		//this.setInitialStatus(UserStatus.ONLINE);
//		this.setMyFriendlyName("ciao");
		
	}

	
	

	/***
	 * Metodo per recuperare lo stato del bot
	 * @return
	 */
	public int getStato() {
		return stato;
	}

	/***
	 * Metodo per settare lo stato del bot
	 * @param stato	: stato bot
	 */
	public void setStato(int stato) {
		this.stato = stato;
	}



	public String getNickOwnerStatus() {
		return nickOwnerStatus;
	}



	public void setNickOwnerStatus(String nickOwnerStatus) {
		this.nickOwnerStatus = nickOwnerStatus;
	}	
	
	public XMPPConnection getConnection(){
		return this.replyListener.getConnection();
	}



	public void setConnection(XMPPConnection connection) {
		this.replyListener.setConnection(connection);
	}
	
	public String getEarName() {
		return earName;
	}



	public void setEarName(String earName) {
		this.earName = earName;
	}



	public String getEnvironmentTouched() {
		return environmentTouched;
	}



	public void setEnvironmentTouched(String environmentTouched) {
		this.environmentTouched = environmentTouched;
	}
	
	public String getNickBot(){
		return this.nickBot;
	}
	public String getUserID() {
		return userID;
	}




	public void setUserID(String userID) {
		this.userID = userID;
	}
	public MultiUserChat createMUCAndInvite(String chatRoomName,List<String> contacts) throws XMPPException{
		
			String nameBot = this.getNickBot();
			
			
			
			//String mucName = chatRoomName+"@conference."+PropertiesPlugin.getValue(PropertiesConstant.OPENFIRE_SERVER);
			String mucName = resolveRoom(chatRoomName);
			logger.debug("Tento di creare la stanza : "+mucName);
			
			System.out.println("Connesso : "+replyListener.getConnection().isConnected());
			
			
			
			MultiUserChat muc  = new MultiUserChat(replyListener.getConnection(), mucName);
			
			muc.create(nameBot);

		    // Get the the room's configuration form
	          Form form = muc.getConfigurationForm();
	          // Create a new form to submit based on the original form
	          Form submitForm = form.createAnswerForm();
	          // Add default answers to the form to submit
	          for (Iterator fields = form.getFields(); fields.hasNext();) {
	              FormField field = (FormField) fields.next();
	              if (!FormField.TYPE_HIDDEN.equals(field.getType()) && field.getVariable() != null) {
	                  // Sets the default value as the answer
	                  submitForm.setDefaultAnswer(field.getVariable());
	              }
	          }
	          // Sets the new owner of the room
	          List<String> owners = new ArrayList<String>();
	          owners.add(this.getUserID());
	          submitForm.setAnswer("muc#roomconfig_roomowners", owners);
	          // Send the completed form (with default values) to the server to configure the room
	          muc.sendConfigurationForm(submitForm);
	          
	         

		      
		      // aggiungo un listner per gli inviti
				muc.addInvitationRejectionListener(new InvitationRejectionListener() {
			        public void invitationDeclined(String invitee, String reason) {
			            logger.debug("L'utente : "+invitee+" ha rifiutato per : "+reason);
			        }
			    });
				
				// invito gli utenti
				for (String currUser : contacts){
					muc.invite(currUser, "Sei stato invitato in chat da "+nameBot);
				}
			
		
		return muc;
	}
	
	public void joinMUC(MultiUserChat muc) throws XMPPException{
		String nameBot = this.getNickBot();
		// joino la multichat
		muc.join(nameBot);
	}
	
	/***
	 * Metodo per inviare un messaggio broadcast al Roster
	 * @param textMessage	: messaggio
	 */
	public void sendBroadCastMessageToRoster(String textMessage){
		StringBuffer buf = new StringBuffer();

		final Map<String, Message> broadcastMessages = new HashMap<String, Message>();

		List<RosterEntry> rosterEntries = GroupUtils.getRosterEntries(replyListener.getConnection());
		
		for(RosterEntry entry : rosterEntries) {
	        final Message message = new Message();
            message.setTo(entry.getUser());
            message.setProperty("broadcast", true);
            message.setBody(textMessage);
            if (!broadcastMessages.containsKey(entry.getUser())) {
                buf.append(entry.getName()).append("\n");
                broadcastMessages.put(entry.getUser(), message);
            }
	    }
	    
	    for (Message message : broadcastMessages.values()) {
	    	replyListener.getConnection().sendPacket(message);
            logger.debug("Inviato messaggio di broadcast : "+message);
	    }
	}
	
	/***
	 * Recupero l'avatar di un utente
	 * @param clientID
	 * @return
	 * @throws ProtocolException
	 */
	public byte[] getAvatar(String clientID) throws ProtocolException {

        try {
                vCard.load(replyListener.getConnection(), clientID);
        } catch (XMPPException e) {
                throw new ProtocolException(e.toString());
        }
        return vCard.getAvatar();
	
	}

	/***
	 * Setto il mio avatar
	 * @param fileName				: filename avatar
	 * @throws ProtocolException
	 */
	public void setAvatar(String fileName) throws ProtocolException {

	        try {
	        		
	        			File fileTemp = new File(fileName);
	        			if (fileTemp!=null){
	        				URL url = fileTemp.toURL();
		        			InputStream stream = url.openStream();
	        				
		        			InputStream scaledImg = ImageUtils.scaleImage(stream,200,200);

	        				byte[] byteArr = FileUtils.getBytesFromInputStreams(scaledImg);
	        				logger.debug("Resize eseguito");
//
//	        			String str=stream.toString();
//	        			  byte[] b3=str.getBytes();
	        			  
	        				vCard.setAvatar(byteArr);
	        				vCard.save(replyListener.getConnection());
	        				logger.debug("Immagine personale settata");
	        			}
	        } catch (Exception e) {
	                throw new ProtocolException(e.toString());
	        }
		
	}




	public VCard getVCard() {
		return vCard;
	}




	public void setVCard(VCard card) {
		vCard = card;
	}
	
	public String resolveRoom(String room) throws XMPPException{
		   	if (room == null) {
		    		throw new IllegalArgumentException("room is not specified");
		        }
		
		        if (room.indexOf('@', 0) != -1) {
		    		return room;
		        }
		
		        XMPPConnection conn = getConnection();
		    	Iterator<String> iterator = MultiUserChat.getServiceNames(conn).iterator();
		    	if(iterator.hasNext()) {
		    		String chatServer = iterator.next();
		    		System.out.println("Serv : "+chatServer);
		    		return room + "@" + chatServer;
		       }else{
		    	   throw new XMPPException("Servizio MUC non presente");
		       }
		        
		    	
		        
		    }




	public List<PutFileBean> getCodaFtp() {
		return codaFtp;
	}




	public void setCodaFtp(List<PutFileBean> codaFtp) {
		this.codaFtp = codaFtp;
	}
	
	public void addInCodaFtp(PutFileBean requestFtp){
		
		if (codaFtp==null){
			// se la coda è nulla (all'inizio)
			codaFtp = new ArrayList<PutFileBean>();
		}
		// controllo se l'utente ha già richieste ftp in coda
		
		
	}

}
