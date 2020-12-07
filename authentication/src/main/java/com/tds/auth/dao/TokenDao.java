package com.tds.auth.dao;

import com.tds.auth.domain.Token;

public interface TokenDao {

	int insertToken(Token token);

	Token getToken(String token);

	int invalidateToken(String token);

}
