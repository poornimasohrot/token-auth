package com.tds.auth.service.impl;

import static com.tds.auth.literals.MessageConstant.TOKEN_ALREADY_EXPIRED;
import static com.tds.auth.literals.MessageConstant.TOKEN_INVALID;
import static com.tds.auth.literals.MessageConstant.TOKEN_REVOKED;
import static com.tds.auth.literals.MessageConstant.USER_OR_PWD_INVALID;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.tds.auth.dao.TokenDao;
import com.tds.auth.dao.UserDao;
import com.tds.auth.domain.Token;
import com.tds.auth.domain.User;
import com.tds.auth.exception.InValidUserException;
import com.tds.auth.form.MessageForm;
import com.tds.auth.form.ValidForm;
import com.tds.auth.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	UserDao userDao;
	
	@Autowired
	TokenDao tokenDao;
	
	@Override
	public String getToken(String userName, String password) {
		
		User user = null;
		
		try {
			user = userDao.getUser(userName, password);
		} catch (EmptyResultDataAccessException e) {
			throw new InValidUserException(USER_OR_PWD_INVALID, e);
		}
		
		Token token = new Token();
		token.setToken(UUID.randomUUID().toString().replace("-", ""));
		token.setIssuedDateTime(LocalDateTime.now());
		token.setExpiryDateTime(LocalDateTime.now().plusMinutes(15));
		token.setUser(user);
		
		tokenDao.insertToken(token);
		
		return token.getToken();
	}

	@Override
	public ValidForm validateToken(String token) {
		// this should validate for login user id who has requestd it with the token object user id as well
		Token tokenObject = null;
		try {
			tokenObject = tokenDao.getToken(token);
		} catch (EmptyResultDataAccessException e) {
			return new ValidForm(false);
		}
		
		boolean valid = LocalDateTime.now().isBefore(tokenObject.getExpiryDateTime()) ? true : false;
		
		
		return new ValidForm(valid);
	}

	@Override
	public MessageForm revokeToken(String token) {
		
		Token tokenObject;
		try {
			tokenObject = tokenDao.getToken(token);
		} catch (EmptyResultDataAccessException e) {
			return new MessageForm(TOKEN_INVALID, false);
		}
		
		if(LocalDateTime.now().isAfter(tokenObject.getExpiryDateTime()))
			return new MessageForm(TOKEN_ALREADY_EXPIRED, false);
		
		tokenDao.invalidateToken(token);
		
		return new MessageForm(TOKEN_REVOKED, true);
	}

}
