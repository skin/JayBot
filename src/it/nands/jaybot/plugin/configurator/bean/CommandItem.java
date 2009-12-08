package it.nands.jaybot.plugin.configurator.bean;

public class CommandItem {
	private String template;
	private String description;
	
	
	/**
	 * Recupera il valore di : description.
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setta il valore di : description.
	 * 
	 * @param description nuovo valore di : description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Recupera il valore di : template.
	 * 
	 * @return template
	 */
	public String getTemplate() {
		return template;
	}
	
	/**
	 * Setta il valore di : template.
	 * 
	 * @param template nuovo valore di : template
	 */
	public void setTemplate(String template) {
		this.template = template;
	}
}
