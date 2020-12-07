package com.tds.auth.service;

import com.tds.auth.form.MessageForm;
import com.tds.auth.form.UserForm;

public interface UserService {
	
	MessageForm addUser(UserForm userForm) ;

	MessageForm updateUser(UserForm userForm);

	MessageForm deleteUser(String userName);

	UserForm getUser(String userName);

}
