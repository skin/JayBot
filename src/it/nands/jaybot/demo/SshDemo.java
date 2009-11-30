package it.nands.jaybot.demo;

import java.io.IOException;
import java.io.StringBufferInputStream;



public class SshDemo {

	
// public static void main(String[] args) {
//	 try{
//		 SshConnectionProperties sshConnectionProperties = new SshConnectionProperties();
//			sshConnectionProperties.setHost("trwiki.replynet.prv");
//			PasswordAuthenticationClient pwd = new PasswordAuthenticationClient();
//			pwd.setUsername("root");
//			pwd.setPassword("technology");
//			// Authenticate the user
//			SshClient sshClient = new SshClient();
//			sshClient.connect(sshConnectionProperties);
//			int result = sshClient.authenticate(pwd);
//			if(result==AuthenticationProtocolState.COMPLETE) {
//				
//
//				// The connection is authenticated we can now do some real work!
//		        SessionChannelClient sessionChannelClient = sshClient.openSessionChannel();
//		        //if(!sessionChannelClient.requestPseudoTerminal("vt100", 80, 24, 0, 0, ""))
//			      //    System.out.println("Failed to allocate a pseudo terminal");
//		        if (sessionChannelClient.startShell()) {
//		        	
//		          IOStreamConnector input = new IOStreamConnector();
//		          IOStreamConnector output = new IOStreamConnector();
//		          IOStreamConnector error = new IOStreamConnector();
//		          output.setCloseOutput(false);
//		          input.setCloseInput(false);
//		          error.setCloseOutput(false);
//		          StringBufferInputStream stringBufferInputStream = new StringBufferInputStream("cat umm");
//		          input.connect(stringBufferInputStream, sessionChannelClient.getOutputStream());
//		          output.connect(sessionChannelClient.getInputStream(), System.out);
//		          error.connect(sessionChannelClient.getStderrInputStream(), System.out);
//		          //Thread.sleep(10000);
//		          sessionChannelClient.getState().waitForStateUpdate();
//		          //sessionChannelClient.getState().waitForState(ChannelState.CHANNEL_CLOSED);
//		        }else
//		          System.out.println("Failed to start the users shell");
//		        sshClient.disconnect();
//
//			}		
//	 }
//	catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (InvalidStateException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} /*catch (InterruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}*/ catch (InterruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//}
 
 

}
