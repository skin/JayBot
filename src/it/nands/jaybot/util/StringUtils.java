package it.nands.jaybot.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/***
 * Classe per la gestione delle stringhe che estende 
 * org.apache.commons.lang.StringUtils
 * 
 * @author a.franzi
 *
 */
public class StringUtils extends org.apache.commons.lang.StringUtils{
	private static Logger logger = Logger.getRootLogger();
	
	public static String removeFromStringAnotherString(String original,String toRemove){
		if (original.indexOf(toRemove)!=-1){
			// se ci sono occorrenze della stringa toRemove nella stringa original
			return original.substring(0,original.indexOf(toRemove))+original.substring(original.indexOf(toRemove)+toRemove.length()).trim();
		}
		return original;
	}
	
	/***
	 * Metodo per rimpiazzare tutte le occorrenze di una determinata stringa all'interno
	 * di un altra stringa
	 * 
	 * @param original		: stringa originale
	 * @param find			: stringa da cercare
	 * @param replacement	: stringa con cui sostituire le occorrenze
	 * @return				: stringa modificata
	 */
	public static String replaceAllWords(String original, String find, String replacement) {
	    String result = "";
	    String delimiters = "+-*/(),.?! ";
	    StringTokenizer st = new StringTokenizer(original, delimiters, true);
	    while (st.hasMoreTokens()) {
	        String w = st.nextToken();
	        if (w.equals(find)) {
	            result = result + replacement;
	        } else {
	            result = result + w;
	        }
	    }
	    return result;
	}
	/***
	 * Metodo per rimuovere tutte le occorrenze di un determinato parametro da una stringa
	 * @param s	: stringa
	 * @param c	: carattere da eliminare
	 * @return	: stringa ottenuta dall'eliminazione delle occorrenze del carattere
	 */
	public static String removeChar(String s, char c) {
	    String r = "";
	    for (int i = 0; i < s.length(); i ++) {
	       if (s.charAt(i) != c) r += s.charAt(i);
	       }
	    return r;
	}
	/***
	 * Metodo per concatenare delle stringhe scegliendo quale simboli far precedere e seguire
	 * a ciascuno degli stessi
	 * 
	 * @param strings		: array di parametri
	 * @param concatBefore	: stringa con cui far precedere il parametro
	 * @param concatAfter	: stringa da concatenare al parametro
	 * @return				: stringa ottenuta dalla concatenazione
	 */
	public static String concatenateStringsWithCaracter (String[] strings,String concatBefore,String concatAfter){
		String retString = "";
		if (concatBefore==null) concatBefore="";
		if (concatAfter==null) concatAfter="";
		if (strings!=null){
			for (int i = 0; i < strings.length; i++){
				retString = retString+concatBefore+strings[i]+concatAfter+" ";
			}
		}
		return retString;
		
	}
	/***
     * Metodo per ricavare l'estensione del file a partire dal nome
     * 
     * @param nomefile di cui ricavare l'estensione
     * @return l'estensione del file
     */
    public static String getEstensioneFile(String nomefile){
    	String ret = "";
    	if (nomefile!=null) ret = nomefile.substring(nomefile.lastIndexOf(".")+1);
    	return ret;
    }
    
    /***
     * Metodo per controllare l'estensione di un file
     * @param nomefile		: nome del file
     * @param estensione	: estensione con cui controllare
     * @return				: boolean indicante il risultato del contfronto
     */
    public static boolean checkEstensioneFile(String nomefile,String estensione){
    	boolean ret = false;
    	if (getEstensioneFile(nomefile).equalsIgnoreCase(estensione)){
    		ret = true; 
    	}
    	return ret;
    }
    
    /***
     * Metodo per controllare l'estensione di un file in un array di stringhe
     * @param nomefile		: nome del file
     * @param estensioni	: lista di estensioni tra cui controllare
     * @return				: boolean indicante il risultato del contfronto
     */
    public static boolean checkEstensioneFile(String nomefile,List<String> estensioni){
    	for (int i = 0 ; i < estensioni.size(); i++){
    		logger.debug("Estensione : "+getEstensioneFile(nomefile)+"con : "+estensioni.get(i));
    		if (getEstensioneFile(nomefile).equalsIgnoreCase(estensioni.get(i))){
    			logger.debug("BECCATA : Estensione : "+getEstensioneFile(nomefile)+"con : "+estensioni.get(i));
    			return true; 
    		}
    	}
    	return false;
    }
    
    
    /***
     * MEtodo per recuperare da una Stringa concatenata un arraylist di valori
     * @param property				: valore della property
     * @param concatenateString		: stringa di concatenazione
     * @return						: arraylist di valori
     */
    public static List<String> getValuesFromProperty(String property,String concatenateString){
    	List<String> retArray = new ArrayList<String>();
		String [] splittedProperty = null;
		if (property!=null){
			splittedProperty = property.split(concatenateString);
		}
		if (splittedProperty!=null){
			for (int i = 0 ; i< splittedProperty.length; i++){
				retArray.add(splittedProperty[i].trim());
			}
		}
		return retArray;
	}
    /***
     * MEtodo per recuperare da una Stringa concatenata un arraylist di valori
     * @param property			: valore della property
     * @param concatenateChar	: carattere di concatenazione
     * @return						: arraylist di valori
     */
    public static List<String> getValuesFromProperty(String property,char concatenateChar){
    	return getValuesFromProperty (property,concatenateChar+"");
    }
    
    /***
     * Esegue l'upperCase su una lista di stringhe
     * @param lista	: lista di stringhe
     * @return		: lista di stringhe maiuscole
     */
    public static List<String> upcaseAListOfStrings(List<String> lista){
    	List<String> upStringList = new ArrayList<String>();
    	if (lista!=null){
    		for (int i = 0 ; i < lista.size(); i++){
    			upStringList.add( defaultString(lista.get(i), "").toUpperCase());
    		}
    	}
    	return upStringList;
    }
    
    /***
     * Metodo che data una stringa ed una serie di marcatori %1,%2,...
     * sostitiusce gli stessi in ordine in base ai parametri passatogli in input
     * @param baseString	: stringa base
     * @param args			: stringhe con cui sostituire i token
     * @return				: stringa parsata
     */
    public static String fillString(String baseString,String ... args){
    	String retString =baseString;
    	int index= 0;
    	for (String stringa : args) {
    		index++;
    		retString = replaceAllWords(retString,"%"+index,stringa);
    	}
    	return retString;
    }
}
