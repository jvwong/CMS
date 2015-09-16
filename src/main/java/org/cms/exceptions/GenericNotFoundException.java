package org.cms.exceptions;

public class GenericNotFoundException extends RuntimeException {
	private final long id;
	private final String className;
	
	public GenericNotFoundException(long id, String className){
		this.id = id;
		this.className = className;
	}
	
	public long getId(){
		return id;
	}
	
	public String getClassName(){
		return className;
	}
}
