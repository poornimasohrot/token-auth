package com.tds.auth.dao;

import com.tds.auth.domain.User;

public interface UserDao {
	
	int insertUser(User user);

	int updateUser(User user);

	int deleteUser(int userId);

	User getUser(String userName);
	
	User getUser(String userName, String password);

	boolean IsExists(String userName);

}
