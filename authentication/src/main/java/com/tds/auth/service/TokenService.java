package com.tds.auth.service;

import com.tds.auth.form.MessageForm;
import com.tds.auth.form.ValidForm;

public interface TokenService {

	String getToken(String userName, String password);

	ValidForm validateToken(String token);

	MessageForm revokeToken(String token);

}
