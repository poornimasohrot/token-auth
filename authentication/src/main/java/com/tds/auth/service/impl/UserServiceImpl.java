package com.tds.auth.service.impl;

import static com.tds.auth.literals.MessageConstant.USER_ADDED_SUCCESSFULLY;
import static com.tds.auth.literals.MessageConstant.USER_DELETED_SUCCESSFULLY;
import static com.tds.auth.literals.MessageConstant.USER_DOES_NOT_EXISTS;
import static com.tds.auth.literals.MessageConstant.USER_EXISTS;
import static com.tds.auth.literals.MessageConstant.USER_UPDATED_SUCCESSFULLY;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tds.auth.dao.AddressDao;
import com.tds.auth.dao.UserDao;
import com.tds.auth.domain.Address;
import com.tds.auth.domain.User;
import com.tds.auth.exception.UserDoesNotExistsException;
import com.tds.auth.exception.UserExistsException;
import com.tds.auth.form.AddressForm;
import com.tds.auth.form.MessageForm;
import com.tds.auth.form.UserForm;
import com.tds.auth.mapper.DomainFormMapper;
import com.tds.auth.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	@Autowired
	AddressDao addressDao;
	
	@Autowired
	DomainFormMapper mapper;
	
	@Override
	@Transactional
	public MessageForm addUser(UserForm userForm) {
		User user = mapper.map(userForm, User.class);
		
		if(userDao.IsExists(userForm.getUserName())) {
			throw new UserExistsException(USER_EXISTS);
		}
		
		int userId = userDao.insertUser(user);
		
		for(Address address : user.getAddressList()) {
			addressDao.insertAddress(userId, address);
		}
		
		return new MessageForm(USER_ADDED_SUCCESSFULLY,true);
	}


	@Override
	@Transactional
	public MessageForm updateUser(UserForm userForm) {
		User user = mapper.map(userForm, User.class);
		userDao.updateUser(user);
		
		for(AddressForm addressForm : userForm.getAddressList()) {
			Address address = mapper.map(addressForm, Address.class);
			
			if(addressForm.isNew()) {
				addressDao.insertAddress(user.getUserId(), address);
				
			} else if(addressForm.isUpdated()) {
				addressDao.updateAddress(address);
				
			} else if (addressForm.isDeleted()) {
				addressDao.deleteAddress(address.getAddressId());
				
			}
			
		}
		
		return new MessageForm(USER_UPDATED_SUCCESSFULLY,true);
	}


	@Override
	@Transactional
	public MessageForm deleteUser(String userName) {
		User user = userDao.getUser(userName);
		addressDao.deleteAddressesOfUser(user.getUserId());
		userDao.deleteUser(user.getUserId());
		return new MessageForm(USER_DELETED_SUCCESSFULLY,true);
	}


	@Override
	public UserForm getUser(String userName) {
		
		if(!userDao.IsExists(userName)) {
			throw new UserDoesNotExistsException(USER_DOES_NOT_EXISTS);
		}
		
		User user = userDao.getUser(userName);
		user.setAddressList(addressDao.getAddressesOfUser(user.getUserId()));
		return mapper.map(user, UserForm.class);
	}

}
