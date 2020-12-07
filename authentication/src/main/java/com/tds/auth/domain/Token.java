package com.tds.auth.domain;

import java.time.LocalDateTime;

public class Token {
	
	private String token;
	private LocalDateTime issuedDateTime;
	private LocalDateTime expiryDateTime;
	private User user;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LocalDateTime getIssuedDateTime() {
		return issuedDateTime;
	}
	public void setIssuedDateTime(LocalDateTime issuedDateTime) {
		this.issuedDateTime = issuedDateTime;
	}
	public LocalDateTime getExpiryDateTime() {
		return expiryDateTime;
	}
	public void setExpiryDateTime(LocalDateTime expiryDateTime) {
		this.expiryDateTime = expiryDateTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	

}
