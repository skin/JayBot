package it.nands.jaybot.plugin.tokenizer.controller;

import org.apache.log4j.Logger;

import it.nands.jaybot.plugin.configurator.exception.PluginInitializeException;
import it.nands.jaybot.plugin.tokenizer.util.TokenizerUtils;

/***
 * Classe incaricata della gestione del plugin tokenizer
 * 
 * @author Alessandro Franzi
 *
 */
public class TokenizerPlugin {
	private static Logger logger = Logger.getRootLogger();
	
	/***
	 * Metodo per recuperare il delimitatore
	 * @return
	 */
	public static String getDelimitator(){
		try {
			return TokenizerLoader.getInstance().getTokens().getDelimitator();
		} catch (PluginInitializeException e) {
			logger.error("Eccezione : ",e);
		}
		return "";
	}
	
	/***
	 * Method that checks if a token of a specified token name is correct or not
	 * @param tokenName		: token name
	 * @param tokenValue	: token value 
	 * @return				: boolean (true if correct)
	 */
	public static boolean checkIfTokenIsCorrect(String tokenName, String tokenValue){
		try {
			return TokenizerUtils.isCorrect(tokenName, tokenValue, TokenizerLoader.getInstance().getTokens().getTokenList());
		} catch (PluginInitializeException e) {
			logger.error("Eccezione : ",e);
		}
		return false;
	}
}
