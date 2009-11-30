package it.nands.jaybot.plugin.tokenizer.bean;

import java.util.List;
/**
 * The Class Tokens.
 * 
 * @author Alessandro Franzi
 */
public class Tokens {
	private List<Token> tokenList;
	private String delimitator;
	
	/**
	 * Recupera il valore di : token list.
	 * 
	 * @return token list
	 */
	public List<Token> getTokenList() {
		return tokenList;
	}
	
	/**
	 * Setta il valore di : token list.
	 * 
	 * @param tokenList nuovo valore di : token list
	 */
	public void setTokenList(List<Token> tokenList) {
		this.tokenList = tokenList;
	}

	/**
	 * Recupera il valore di : delimitator.
	 * 
	 * @return delimitator
	 */
	public String getDelimitator() {
		return delimitator;
	}

	/**
	 * Setta il valore di : delimitator.
	 * 
	 * @param delimitator nuovo valore di : delimitator
	 */
	public void setDelimitator(String delimitator) {
		this.delimitator = delimitator;
	}
	
	
}
