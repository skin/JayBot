package it.nands.jaybot.plugin.resource.bean;

/***
 * Resource class
 * 
 * @author a.franzi
 *
 */
public class Resource {
	
	private String name;
	private String type;
	private String server;
	private String pathName;
	private String path;
	private String file;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getPathName() {
		return pathName;
	}
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	
	/***
	 * equals method (an object is equal to another if the name is the same)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof String) {
			if (this.getName().equalsIgnoreCase((String)obj)){
				return true;
			}
		}else if (obj instanceof Resource){
			if (this.getName().equalsIgnoreCase(((Resource)obj).getName())){
				return true;
			}
		}
		return false;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
}
