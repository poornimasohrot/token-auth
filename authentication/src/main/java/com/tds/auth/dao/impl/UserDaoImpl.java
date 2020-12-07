package com.tds.auth.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.tds.auth.dao.UserDao;
import com.tds.auth.domain.User;
import com.tds.auth.rowmapper.UserRowMapper;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	@Override
	public int insertUser(User user) {
		String sql = "INSERT INTO USER (first_name, last_name, user_name, password, home_phone_no, office_phone_no, fax_no) VALUES (?, ?, ?, ?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

	    jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection
	          .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	          ps.setString(1, user.getFirstName());
	          ps.setString(2, user.getLastName());
	          ps.setString(3, user.getUserName());
	          ps.setString(4, user.getPassword());
	          ps.setString(5, user.getHomePhoneNumber());
	          ps.setString(6, user.getOfficePhoneNumber());
	          ps.setString(7, user.getFaxNumber());
	          return ps;
	        }, keyHolder);

	        return (int) keyHolder.getKey();
	}


	@Override
	public int updateUser(User user) {
		String sql = "update USER set first_name =?, last_name = ?, user_name = ?, password = ?, home_phone_no =?, office_phone_no=?, fax_no=? where id=?";
		return jdbcTemplate.update(sql,user.getFirstName(), user.getLastName(),
				  user.getUserName(), user.getPassword(), user.getHomePhoneNumber(), user.getOfficePhoneNumber(),
				  user.getFaxNumber(), user.getUserId());
	}


	@Override
	public int deleteUser(int userId) {
		String sql = "DELETE FROM USER where id = ? ";
		return jdbcTemplate.update(sql,userId);
	}


	@Override
	public User getUser(String userName) {
		String sql = "select id, first_name, last_name, user_name, home_phone_no, office_phone_no, fax_no from USER where user_name = ?";
		return jdbcTemplate.queryForObject(sql, new UserRowMapper(), userName);
		
	}
	
	@Override
	public User getUser(String userName, String password) {
		String sql = "select id, first_name, last_name, user_name, home_phone_no, office_phone_no, fax_no from USER where user_name = ? and password = ?";
		
		return jdbcTemplate.queryForObject(sql, new UserRowMapper(), userName, password);
		
	}
	
	@Override
	public boolean IsExists(String userName) {
		String sql = "select id, first_name, last_name, user_name, home_phone_no, office_phone_no, fax_no from USER where user_name = ?";
		
		try {
			jdbcTemplate.queryForObject(sql, new UserRowMapper(), userName);
			return true;
		} catch (DataAccessException e) {
			return false;
		}
	}

}
