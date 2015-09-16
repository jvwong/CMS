package org.cms.exceptions;

public class MissingCredentialsException extends RuntimeException {
	private String message;
	private String className;
		
	public MissingCredentialsException(String message, String className){
		this.message = message;
		this.className = className;
	}
	
	public String getMessage(){
		return message;
	}
	
	public String getClassName(){
		return className;
	}
}
