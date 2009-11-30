package it.nands.jaybot.plugin.resource.controller;

import java.util.List;

import org.apache.log4j.Logger;

import it.nands.jaybot.plugin.resource.bean.Resource;
import it.nands.jaybot.plugin.tokenizer.constant.TokenizerPluginConstant;
import it.nands.jaybot.plugin.tokenizer.controller.TokenizerPlugin;

/***
 * Resource utils class
 * @author a.franzi
 *
 */
public class ResourcesUtils {
	private static Logger logger = Logger.getRootLogger();
	
	/***
	 * Get resource object by name
	 * @param resourceList	: resourceList
	 * @param name			: resource Name
	 * @return				: resource
	 */
	public static Resource getResource (List<Resource> resourceList,String name){
		if (resourceList!=null){
			if (resourceList.contains(name)){
				for (int i = 0 ; i<resourceList.size(); i++){
					Resource risorsa = resourceList.get(i);
					if (risorsa.equals(name)){
						return risorsa;
					}
				}
			}
		}
		return null;
	}
	
	/***
	 * checkResources methos 
	 * @param resourceList
	 * @return
	 */
	public static boolean checkResources(List<Resource> resourceList){
		if (resourceList!=null){
			for (int i = 0 ; i<resourceList.size(); i++){
				Resource risorsa = resourceList.get(i);
				// check type
				if (!TokenizerPlugin.checkIfTokenIsCorrect(TokenizerPluginConstant.TOKEN_RESOURCE_TYPE,risorsa.getType())){
					logger.error("Problema con la risorsa "+risorsa.getName()+" il tipo "+risorsa.getType()+" non e' corretto");
					return false;
				}
				// check server avaliability
				
				// check pathName if set
			}
		}
		return true;
	}
}
