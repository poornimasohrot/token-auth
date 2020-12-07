package com.tds.auth.rowmapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tds.auth.domain.Token;
import com.tds.auth.domain.User;

public class TokenRowMapper implements RowMapper<Token> {

	@Override
	public Token mapRow(ResultSet rs, int rowNum) throws SQLException {
		Token token = new Token();
		token.setToken(rs.getString("token"));
		token.setIssuedDateTime(rs.getTimestamp("issued_date_time").toLocalDateTime());
		token.setExpiryDateTime(rs.getTimestamp("expired_date_time").toLocalDateTime());
		
		User user = new User();
		user.setUserId(rs.getInt("user_id"));
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		user.setUserName(rs.getString("user_name"));
		user.setHomePhoneNumber(rs.getString("home_phone_no"));
		user.setOfficePhoneNumber(rs.getString("office_phone_no"));
		user.setFaxNumber(rs.getString("fax_no"));
		
		token.setUser(user);
		return token;
	}

}
