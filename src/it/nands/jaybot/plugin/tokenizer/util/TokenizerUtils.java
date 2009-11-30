package it.nands.jaybot.plugin.tokenizer.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


import it.nands.jaybot.constant.TokenConstant;
import it.nands.jaybot.exception.ControlParamExeption;
import it.nands.jaybot.exception.ParamException;
import it.nands.jaybot.plugin.configurator.exception.PluginInitializeException;
import it.nands.jaybot.plugin.paramcontroller.controller.ParamControllerPlugin;
import it.nands.jaybot.plugin.server.bean.Server;
import it.nands.jaybot.plugin.server.controller.ServerPlugin;
import it.nands.jaybot.plugin.tokenizer.bean.Token;
import it.nands.jaybot.plugin.tokenizer.bean.Tokens;
import it.nands.jaybot.plugin.tokenizer.constant.TokenizerPluginConstant;
import it.nands.jaybot.plugin.tokenizer.controller.TokenizerLoader;
import it.nands.jaybot.plugin.tokenizer.controller.TokenizerPlugin;
import it.nands.jaybot.util.StringUtils;

/***
 * Classe per la gestione dei controlli sui token
 * 
 * @author a.franzi
 *
 */
public class TokenizerUtils {

	public static final String EAR_EXTENSION = "ear";
	public static final String ENVIRONMENTS = "SVIL,TEST,CERT1,CERT2,PROD1,PROD2";
	public static Logger logger = Logger.getRootLogger();
	/***
	 * MEtodo per controllare il valore di un determinato parametro
	 * @param value	: valore
	 * @param param	: nome del parametro
	 * @throws ControlParamExeption	: eccezione per un controllo errato del parametro
	 */
	public static void controlParam (String value,String param) throws ControlParamExeption{
		logger.debug("Controllo il token : "+value+" del parametro : "+param);
		String paramWithoutChar = StringUtils.removeChar(param, TokenConstant.CHAR_TOKEN);
		// se il valore e nullo o vuoto
		if (StringUtils.isEmpty(value)){
			throw new ControlParamExeption("Parametro ["+paramWithoutChar+"] non valorizzato");
		}
		
		
		// devo recuperare dal file cio'che mi serve
		try {
			Tokens tokens = TokenizerLoader.getInstance().getTokens();
			
			// recupero la lista dei token
			List<Token> tokenList = tokens.getTokenList();
			
			// recuper il token appropriato dalla lista di token
			Token token  = searchTokenInList(tokenList, paramWithoutChar);
			if (token!=null){
				logger.debug("Il token e' : "+token.getKey());
				// in base al tipo faccio le mie cose
				if (token.getType().equalsIgnoreCase("enumeration")){
					// enumeration
					List<String> ambienti = StringUtils.getValuesFromProperty(token.getValues(),TokenizerPluginConstant.CONCAT_CHAR);
					List<String> ambientiUpper = StringUtils.upcaseAListOfStrings(ambienti);
					if (!ambientiUpper.contains(value.toUpperCase())){
						throw new ControlParamExeption("Parametro "+paramWithoutChar+" non valido : non esiste un "+token.getKey()+" di nome "+value+"\n possibili valori: "+token.getValues());
					}
				}
				if (token.getType().equalsIgnoreCase("fileExtension")){
					// fileExtension
					if (!StringUtils.isEmpty(token.getValues())){
						
						//splitto la stringa
						List<String> estensioni = StringUtils.getValuesFromProperty(token.getValues(),TokenizerPluginConstant.CONCAT_CHAR);
						
						if (!StringUtils.checkEstensioneFile(value, estensioni)){
							logger.debug("Controllo : "+StringUtils.checkEstensioneFile(value, estensioni));
							throw new ControlParamExeption("Parametro "+paramWithoutChar+" non valido : non presenta un estensione di tipo "+token.getValues());
						}
					}
				}
				if (token.getType().equalsIgnoreCase("string")){
					// string
					if (StringUtils.isEmpty(value)){
						throw new ControlParamExeption("Parametro "+paramWithoutChar+" non valorizzato.");
					}
				}
				if (token.getType().equalsIgnoreCase("ServerType")){
					logger.debug("ServerType token : "+token.getValues());
					Server ftpServer =  ServerPlugin.getServer(value,token.getValues());
					if (ftpServer==null){
						throw new ControlParamExeption("Parametro "+paramWithoutChar+" non valido : non esiste un server ftp di nome "+value);
					}
				}
			}else{
				logger.debug("Token non trovato");
			}
			
		} catch (PluginInitializeException e) {
			logger.error("Eccezione : ",e);
			throw new ControlParamExeption(e.getMessage());
		}
		
		
		
	}
	
	/***
	 * Metodo per recuperare un determinato token da una lista
	 * @param tokenList	: lista di token
	 * @param toSearch	: token da cercare
	 * @return			: token trovato o null
	 */
	public static Token searchTokenInList(List<Token> tokenList,String toSearch){
		Token tokenret = null;
		if (tokenList!=null){
			for (int i = 0 ; i< tokenList.size(); i++){
				if (toSearch.equalsIgnoreCase(tokenList.get(i).getKey())){
					tokenret = tokenList.get(i);
				}
			}
		}
		return tokenret;
	}
	
	/***
	 * Metodo per controllare i parametri di un messaggio e restituire un hashMap associativo
	 * (parametro, valore)
	 * @param messaggio			: messaggio da controllare
	 * @param template			: template da gestire sul messaggio
	 * @param control			: boolean per il controllo di tipo e range sui parametri
	 * @return					: hashMap contenente i parametri
	 * @throws ParamException	: Eccezione in caso di numero errato di parametri
	 * @throws ControlParamExeption : Eccezione in caso di controllo errato di un parametro
	 */
	public static Map<String,String> controlAndSplitMessage (String messaggio,String template,boolean control) throws ParamException,ControlParamExeption{
		String delimitatore = TokenizerPlugin.getDelimitator();
		Map<String,String> retHash = null;
		
		// prendo dal template solamente la parte relativa al comando
		String commandWithoutParams = ParamControllerPlugin.getCommandWithoutParamsFromTemplate(template);
		// prendo dal template solamente la parte relativa ai parametri
		String commandParameters = ParamControllerPlugin.getParametersFromTemplate(template);
		
		String messaggioWithoutFirsPart="";
		if (!StringUtils.isEmpty(commandWithoutParams)){
			// se il comando senza parametri non e' vuoto cerco di eliminare dal comando 
			// ricevuto in input quella stringa
			messaggioWithoutFirsPart = StringUtils.removeFromStringAnotherString(messaggio.toUpperCase().trim(), commandWithoutParams.toUpperCase().trim());
		}
		
		// splitto il messaggio
		String[] splitMsg = messaggioWithoutFirsPart.split(" ");
		// splitto il template
		String[] splitTemplate = commandParameters.split(" ");
		
		if (splitTemplate.length>splitMsg.length){
			
			String concatParamParsed = getPrintableTemplate(template,delimitatore);
			throw new ParamException("Problema con il comando "+commandWithoutParams+" parametri necessari : "+concatParamParsed);
		}else{
			retHash = new HashMap<String,String>();
			for (int i = 0; i<splitMsg.length; i++){
				if (!StringUtils.isEmpty(splitTemplate[i])){
					if (control){
						// controllo splitTemplate[i-1]
						TokenizerUtils.controlParam(splitMsg[i], splitTemplate[i]);
					}
					logger.debug("Inserisco nella mappa : "+splitTemplate[i]+"-"+splitMsg[i]);
					retHash.put(StringUtils.removeChar(splitTemplate[i], TokenizerPlugin.getDelimitator().charAt(0)), splitMsg[i]);
				}
			}
		}
		return retHash;
	}
	
	/***
	 * Metodo per ottenere un template stampabile
	 * @param template
	 * @param delimitatore
	 * @return
	 */
	public static String getPrintableTemplate(String template,String delimitatore){
		String soloParam = ParamControllerPlugin.getParametersFromTemplate(template);
		String templateToPrint = StringUtils.removeChar(soloParam,delimitatore.charAt(0));
		String concatParamParsed = StringUtils.concatenateStringsWithCaracter(templateToPrint.split(" "),"[","]");
		return concatParamParsed;
	}
	
	/***
	 * Method to check if a value of a specified token name is correct
	 * @param tokenName		: name of token
	 * @param tokenValue	: value to check
	 * @param tokenList		: list of token getted from loader
	 * @return				: boolean (true if correct)
	 */
	public static boolean isCorrect(String tokenName,String tokenValue,List<Token> tokenList){
		logger.info("tokenName "+tokenName+" tokenValue "+tokenValue);
		Token token = searchTokenInList(tokenList,tokenName);
		if (token != null){
			if (token.getType().equalsIgnoreCase(TokenizerPluginConstant.TYPE_ENUMERATION)){
				logger.info("tipo enumeration");
				// splitto la stringa
				List<String> tokenSplitted = StringUtils.getValuesFromProperty(token.getValues(),TokenizerPluginConstant.CONCAT_CHAR);
				List<String> tokenSplittedUpper = StringUtils.upcaseAListOfStrings(tokenSplitted);
				if (tokenSplittedUpper.contains(tokenValue.toUpperCase())){
					return true;
				}
			}else if (token.getType().equalsIgnoreCase("fileExtension")){
				// fileExtension TODO: complete
//				if (!StringUtils.isEmpty(token.getValues())){
//					
//					//splitto la stringa
//					List<String> estensioni = StringUtils.getValuesFromProperty(token.getValues(),TokenizerPluginConstant.CONCAT_CHAR);
//					
//					if (!StringUtils.checkEstensioneFile(value, estensioni)){
//						logger.debug("Controllo : "+StringUtils.checkEstensioneFile(value, estensioni));
//						throw new ControlParamExeption("Parametro "+paramWithoutChar+" non valido : non presenta un estensione di tipo "+token.getValues());
//					}
//				}
			}else if (token.getType().equalsIgnoreCase("string")){
				// string TODO: complete
//				if (StringUtils.isEmpty(value)){
//					throw new ControlParamExeption("Parametro "+paramWithoutChar+" non valorizzato.");
//				}
			}else if (token.getType().equalsIgnoreCase("ServerType")){
//				logger.debug("ServerType token : "+token.getValues()); TODO: complete
//				Server ftpServer =  ServerPlugin.getServer(value,token.getValues());
//				if (ftpServer==null){
//					throw new ControlParamExeption("Parametro "+paramWithoutChar+" non valido : non esiste un server ftp di nome "+value);
//				}
			}
		}
		return false;
	}
	
}
