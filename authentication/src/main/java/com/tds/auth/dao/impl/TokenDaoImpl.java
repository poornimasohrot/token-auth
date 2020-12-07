package com.tds.auth.dao.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tds.auth.dao.TokenDao;
import com.tds.auth.domain.Token;
import com.tds.auth.rowmapper.TokenRowMapper;

@Repository
public class TokenDaoImpl implements TokenDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public int insertToken(Token token) {
		String sql = "INSERT INTO TOKEN (user_id, token, issued_date_time, expired_date_time) VALUES (?, ?, ?, ?)";
		return jdbcTemplate.update(sql,token.getUser().getUserId(), token.getToken(),
				token.getIssuedDateTime(), token.getExpiryDateTime());
	}

	@Override
	public Token getToken(String token) {
		String sql = "SELECT token, issued_date_time, expired_date_time, user_id, first_name, last_name, user_name, home_phone_no, office_phone_no, fax_no from TOKEN T JOIN USER U ON T.user_id = U.id where token = ?";
		return jdbcTemplate.queryForObject(sql, new TokenRowMapper(), token);
	}

	@Override
	public int invalidateToken(String token) {
		String sql = "UPDATE TOKEN SET expired_date_time = ? where token = ?";
		return jdbcTemplate.update(sql, LocalDateTime.now(), token);
	}

}
