package com.tds.auth.exception;

public class InValidUserException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String message;
	private Exception parentException ;
	
	public InValidUserException(String message, Exception e) {
		this.setMessage(message);
		this.setParentException(e);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Exception getParentException() {
		return parentException;
	}

	public void setParentException(Exception parentException) {
		this.parentException = parentException;
	}


}
