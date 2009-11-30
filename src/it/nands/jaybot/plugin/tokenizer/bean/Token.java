package it.nands.jaybot.plugin.tokenizer.bean;

/***
 * Bean contenente le informazioni su un singolo Token
 * @author Alessandro Franzi
 *
 */
public class Token {
	private String key;
	private String values;
	private String type;
	
	/**
	 * Recupera il valore di : key.
	 * 
	 * @return key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * Setta il valore di : key.
	 * 
	 * @param key nuovo valore di : key
	 */
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * Recupera il valore di : values.
	 * 
	 * @return values
	 */
	public String getValues() {
		return values;
	}
	
	/**
	 * Setta il valore di : values.
	 * 
	 * @param values nuovo valore di : values
	 */
	public void setValues(String values) {
		this.values = values;
	}
	
	/**
	 * Recupera il valore di : type.
	 * 
	 * @return type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Setta il valore di : type.
	 * 
	 * @param type nuovo valore di : type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
}
