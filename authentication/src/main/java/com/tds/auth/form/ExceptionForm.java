package com.tds.auth.form;

import java.util.UUID;

public class ExceptionForm {

	private String errorId;
	private String message;
	private String errorDetail;
	private boolean success;
	
	public ExceptionForm(String message, String errorDetail, boolean success) {
		this.message = message;
		this.errorDetail = errorDetail;
		this.success = success;
		this.errorId = UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
	}
	
	public String getErrorId() {
		return errorId;
	}
	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrorDetail() {
		return errorDetail;
	}
	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		StringBuilder message = new StringBuilder();
		message.append("{ message :").append(this.message).append(" , errorId :").append(this.errorId)
		.append(", errorDetail :").append(this.errorDetail).append(" , success : ")
		.append(this.success).append("} ");
		return message.toString();
	}
	
	
}
