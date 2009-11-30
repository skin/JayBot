package it.nands.jaybot.util;


import it.nands.jaybot.constant.CommandConstant;
import it.nands.jaybot.constant.MessagesConstant;
import it.nands.jaybot.constant.PluginConstant;
import it.nands.jaybot.plugin.configurator.bean.Plugin;
import it.nands.jaybot.plugin.configurator.bean.Plugins;
import it.nands.jaybot.plugin.configurator.controller.PluginLoader;
import it.nands.jaybot.plugin.configurator.exception.PluginException;
import it.nands.jaybot.plugin.configurator.exception.PluginInitializeException;
import it.nands.jaybot.plugin.impl.MessageHandlerPluginInterface;
import it.nands.jaybot.plugin.message.controller.MessagePlugin;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import net.sf.jml.MsnContact;
import net.sf.jml.MsnSwitchboard;
import net.sf.jml.message.MsnInstantMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;




/**
 * Classe per la gestione dei messaggi
 * 
 * @author a.franzi
 *
 */
public class ChatMessageUtils {
	private static Logger logger = Logger.getRootLogger();
	
	
	/***
	 * Metodo per gestire i messaggi in ingresso.
	 * Il metodo richiama mediante Reflection tutti i metodi in ascolto
	 * sull'evento "OnMessageReceived"
	 * 
	 * @param chat			: chat
	 * @param message		: messaggio
	 * @param jabber		: client jabber
	 * @throws IOException	: eccezione di I/O
	 */
	public static void manageReceivedMessage(MsnSwitchboard switchboard,MsnInstantMessage message, MsnContact contact) throws IOException{
		
		logger.info("INIZIO");
		 // boolean per controllare se e' un messaggio conosciuto
		 boolean riconosciuto = false;
		 
		 try{
			 
			 // recupero i plugin interessati al message received
			 Plugins pluginInteressati =  PluginLoader.getInstance().getOnMessageReceivedPlugins();
			 if (pluginInteressati!=null){
				 
				 List<Plugin> pluginList = pluginInteressati.getPluginList();
				 
				 if (pluginList!=null){
					 // se la lista non è nulla
					 for (int i = 0; i<pluginList.size(); i++){
						Plugin currPlugin = pluginList.get(i);
						String classCurr = "";
						for (int j = 0 ; j < currPlugin.getClassList().size(); j++){
							classCurr = currPlugin.getClassList().get(j);
							// controllo se la classe e' definita, altrimenti pu˜ anche non essere un problema
							if (!StringUtils.isEmpty(classCurr)){
								// controllo dell'esistenza della classe mediante Reflection
								Class classToIstanciate;
								
								classToIstanciate = Class.forName(classCurr);
								if (ReflectionUtils.checkIfClassIsImplementationOfAnInterface(classToIstanciate,MessageHandlerPluginInterface.class)){								
									// costruisco l'oggetto
									 Class partypesC[] = new Class[0];
									 Constructor ct = classToIstanciate.getConstructor(partypesC);
									 Object arglistC[] = new Object[0];
									 Object retobj = ct.newInstance(arglistC);
									 // chiamo il metodo getIstance()
									 Class partypes[] = new Class[3];
									 partypes[0] = MsnSwitchboard.class;
									 partypes[1] = MsnInstantMessage.class;
									 partypes[2] = MsnContact.class;
									 Method meth = classToIstanciate.getMethod(PluginConstant.METHOD_MESSAGE_RECEIVED,partypes);
									 Object arglist[] = new Object[3];
									 arglist[0] = switchboard;
									 arglist[1] = message;
									 arglist[2] = contact;
									 // invoco il metodo
									 Object retObj = meth.invoke(retobj, arglist);
									 if (retObj!=null && !riconosciuto){
										 riconosciuto = (Boolean)retObj;
										 logger.debug("Returned riconosciuto : "+riconosciuto+" from "+classToIstanciate.getName());
									 }
								}								
							}
						}
					 } 
				 }
			 }
		 }catch(PluginInitializeException e){
			 logger.error("Eccezione :",e);
			 String errorMessage = e.getMessage();
			// invio messaggio
			MessageUtils.sendMessage(errorMessage, switchboard);
		 }catch (IllegalArgumentException e) {
			 logger.error("Eccezione :",e);
			 String errorMessage = e.getMessage();
			// invio messaggio
				MessageUtils.sendMessage(errorMessage, switchboard);
		}catch (IllegalStateException e) {
			 logger.error("Eccezione :",e);
			 String errorMessage = e.getMessage();
			// invio messaggio
				MessageUtils.sendMessage(errorMessage, switchboard);
		}catch(ClassNotFoundException e){
			logger.error("Eccezione :",e);
			String errorMessage = e.getMessage();
			// invio messaggio
			MessageUtils.sendMessage(errorMessage, switchboard);
		 }catch(NoSuchMethodException e){
			 logger.error("Eccezione :",e);
			 String errorMessage = e.getMessage();
			// invio messaggio
				MessageUtils.sendMessage(errorMessage, switchboard);
		 }catch(IllegalAccessException e){
			 logger.error("Eccezione :",e);
			 String errorMessage = e.getMessage();
			// invio messaggio
				MessageUtils.sendMessage(errorMessage, switchboard);
		 }catch(InstantiationException e){
			 logger.error("Eccezione :",e);
			 String errorMessage = e.getMessage();
			// invio messaggio
				MessageUtils.sendMessage(errorMessage, switchboard);
		 }catch(InvocationTargetException e){
			 String errorMessage = e.getCause().getMessage();
			// invio messaggio
				MessageUtils.sendMessage(errorMessage, switchboard);
			 riconosciuto = true;
		 }
		 if (!riconosciuto){
			 try {
				MessageUtils.sendMessage(MessagePlugin.getMessage(MessagesConstant.MESSAGE_UNKNOWN_COMMAND, CommandConstant.HELP), switchboard);
			} catch (PluginException e) {
				logger.error("Eccezione : ",e);
			}
		 }

		 
		 logger.info("FINE - riconosciuto : "+riconosciuto);
		
	}
	
}
