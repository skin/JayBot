package it.nands.jaybot.plugin.resource.bean;

import java.util.List;

/***
 * Resources class
 * 
 * @author a.franzi
 *
 */
public class Resources {
	List<Resource> resourceList;

	/**
	 * Metodo che restituisce il valore: resource list.
	 * 
	 * @return Restituisce il valore: resource list
	 */
	public List<Resource> getResourceList() {
		return resourceList;
	}

	/**
	 * Metodo che setta il valore: resource list.
	 * 
	 * @param resourceList - parametro che serve per settare il valore: resource list
	 */
	public void setResourceList(List<Resource> resourceList) {
		this.resourceList = resourceList;
	}
}
