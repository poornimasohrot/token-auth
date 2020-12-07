package com.tds.auth.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tds.auth.domain.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setUserId(rs.getInt("id"));
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		user.setUserName(rs.getString("user_name"));
		user.setHomePhoneNumber(rs.getString("home_phone_no"));
		user.setOfficePhoneNumber(rs.getString("office_phone_no"));
		user.setFaxNumber(rs.getString("fax_no"));
		return user;
	}

}
