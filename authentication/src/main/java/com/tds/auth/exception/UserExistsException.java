package com.tds.auth.exception;

public class UserExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;
	
	public UserExistsException(String message) {
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
