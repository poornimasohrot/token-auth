package com.tds.auth.form;

public class ExceptionForm {

	private String message;
	private String errorDetail;
	private boolean success;
	
	public ExceptionForm(String message, String errorDetail, boolean success) {
		this.message = message;
		this.errorDetail = errorDetail;
		this.success = success;
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
	
	
}
