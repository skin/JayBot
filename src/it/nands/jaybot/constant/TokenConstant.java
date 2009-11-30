package it.nands.jaybot.constant;

/***
 * Classe per la gestione delle costanti relative ai token dei template
 * 
 * @author a.franzi
 *
 */
public class TokenConstant {
	public static final char CHAR_TOKEN = '%';
	// token per il nickname
	public static final String TOKEN_NICKNAME = "%NickName";
	// token per l'ambiente
	public static final String TOKEN_ENVIRONMENT = "%environment";
	// token per il nickName dell'owner di un operazione
	public static final String TOKEN_NICKNAME_OWNER = "%NickOwnerStatus";
	// token per il nome dell'ear
	public static final String TOKEN_EARNAME = "%earName";
	// token per il nome del file di log
	public static final String TOKEN_LOG_FILENAME = "%logFilename";
}
