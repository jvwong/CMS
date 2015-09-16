package org.cms.exceptions;

public class MissingEntityException extends RuntimeException {
	private final String className;
	
	public MissingEntityException(String className){
		this.className = className;
	}
		
	public String getClassName(){
		return className;
	}
}
