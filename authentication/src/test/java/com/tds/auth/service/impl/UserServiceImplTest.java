package com.tds.auth.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tds.auth.dao.AddressDao;
import com.tds.auth.dao.UserDao;
import com.tds.auth.domain.Address;
import com.tds.auth.domain.User;
import com.tds.auth.exception.UserDoesNotExistsException;
import com.tds.auth.exception.UserExistsException;
import com.tds.auth.form.AddressForm;
import com.tds.auth.form.MessageForm;
import com.tds.auth.form.UserForm;
import com.tds.auth.literals.MessageConstant;
import com.tds.auth.mapper.DomainFormMapper;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

	@InjectMocks
	private UserServiceImpl userService;
	
	@Mock
	private UserDao userDao;
	
	@Mock
	private AddressDao addressDao;
	
	@Mock
	DomainFormMapper mapper;
	
	
	@Test
	public void addUser_whenNewUser_thenReturnUserAdded() {
		MessageForm expected = new MessageForm(MessageConstant.USER_ADDED_SUCCESSFULLY, true);
		when(mapper.map(any(UserForm.class), any())).thenReturn(populateUserwithAddress());
		when(userDao.IsExists(anyString())).thenReturn(false);
		when(userDao.insertUser(any(User.class))).thenReturn(4);
		when(addressDao.insertAddress(anyInt(), any(Address.class))).thenReturn(2);
		
		MessageForm actual = userService.addUser(populateUserForm());
		
		assertEquals(expected.isSuccess(), actual.isSuccess());
		assertEquals(expected.getMessage(), actual.getMessage());
	}
	
	@Test
	public void addUser_whenUserAlreadyExists_thenThrowException() {
		
		
		assertThrows(UserExistsException.class, () -> {
			when(mapper.map(any(UserForm.class), any())).thenReturn(populateUserwithAddress());
			when(userDao.IsExists(anyString())).thenReturn(true);
			
			userService.addUser(populateUserForm());
		});
		
	}
	
	@Test
	public void addUser_whenSomeExceptionOccurs_thenExceptionisThrowned() {
		
		assertThrows(RuntimeException.class, () -> {
			when(mapper.map(any(UserForm.class), any())).thenReturn(null);
			when(userDao.IsExists(anyString())).thenReturn(false);
			when(userDao.insertUser(any(User.class))).thenReturn(4);
			when(addressDao.insertAddress(anyInt(), any(Address.class))).thenReturn(2);
			
			userService.addUser(populateUserForm());
		});
	}
	
	@Test
	public void updateUser_whenUserToBeUpdatedAndNewAddressAdded_thenReturnSuccessMessageForm() {
		MessageForm expected = new MessageForm(MessageConstant.USER_UPDATED_SUCCESSFULLY, true);
		
		User user = populateUserwithAddress();
		when(mapper.map(any(UserForm.class), any())).thenReturn(user);
		when(userDao.updateUser(any(User.class))).thenReturn(4);
		when(mapper.map(any(AddressForm.class), any())).thenReturn(user.getAddressList().get(0));
		when(addressDao.insertAddress(anyInt(), any(Address.class))).thenReturn(2);
		
		UserForm inputUserForm = populateUserForm();
		inputUserForm.getAddressList().get(0).setNew(true);
		MessageForm actual = userService.updateUser(inputUserForm);
		
		assertEquals(expected.isSuccess(), actual.isSuccess());
		assertEquals(expected.getMessage(), actual.getMessage());
	}
	
	@Test
	public void updateUser_whenUserToBeUpdatedAndAddressToBeUpdated_thenReturnSuccessMessageForm() {
		MessageForm expected = new MessageForm(MessageConstant.USER_UPDATED_SUCCESSFULLY, true);
		
		User user = populateUserwithAddress();
		when(mapper.map(any(UserForm.class), any())).thenReturn(user);
		when(userDao.updateUser(any(User.class))).thenReturn(4);
		when(mapper.map(any(AddressForm.class), any())).thenReturn(user.getAddressList().get(0));
		when(addressDao.updateAddress(any(Address.class))).thenReturn(2);
		
		
		UserForm inputUserForm = populateUserForm();
		inputUserForm.getAddressList().get(0).setUpdated(true);
		
		MessageForm actual = userService.updateUser(inputUserForm);
		
		assertEquals(expected.isSuccess(), actual.isSuccess());
		assertEquals(expected.getMessage(), actual.getMessage());
	}
	
	@Test
	public void updateUser_whenUserToBeUpdatedAndAddressToBeDeleted_thenReturnSuccessMessageForm() {
		MessageForm expected = new MessageForm(MessageConstant.USER_UPDATED_SUCCESSFULLY, true);
		
		User user = populateUserwithAddress();
		when(mapper.map(any(UserForm.class), any())).thenReturn(user);
		when(userDao.updateUser(any(User.class))).thenReturn(4);
		when(mapper.map(any(AddressForm.class), any())).thenReturn(user.getAddressList().get(0));
		when(addressDao.deleteAddress(anyInt())).thenReturn(2);
		
		
		UserForm inputUserForm = populateUserForm();
		inputUserForm.getAddressList().get(0).setDeleted(true);
		
		MessageForm actual = userService.updateUser(inputUserForm);
		
		assertEquals(expected.isSuccess(), actual.isSuccess());
		assertEquals(expected.getMessage(), actual.getMessage());
	}
	
	@Test
	public void updateUser_whenUserToBeUpdatedAndAddressNotToBeUpdated_thenReturnSuccessMessageForm() {
		MessageForm expected = new MessageForm(MessageConstant.USER_UPDATED_SUCCESSFULLY, true);
		
		User user = populateUserwithAddress();
		when(mapper.map(any(UserForm.class), any())).thenReturn(user);
		when(userDao.updateUser(any(User.class))).thenReturn(4);
		when(mapper.map(any(AddressForm.class), any())).thenReturn(user.getAddressList().get(0));
		
		MessageForm actual = userService.updateUser(populateUserForm());
		
		assertEquals(expected.isSuccess(), actual.isSuccess());
		assertEquals(expected.getMessage(), actual.getMessage());
	}
	
	@Test
	public void deleteUser_whenUserToBeDeleted_thenReturnSuccessMessageFormOnDelete() {
		MessageForm expected = new MessageForm(MessageConstant.USER_DELETED_SUCCESSFULLY, true);
		when(userDao.getUser(anyString())).thenReturn(populateUser());
		when(addressDao.deleteAddressesOfUser(anyInt())).thenReturn(2);
		when(userDao.deleteUser(anyInt())).thenReturn(1);
		
		MessageForm actual = userService.deleteUser("ali_dan");
		
		assertEquals(expected.isSuccess(), actual.isSuccess());
		assertEquals(expected.getMessage(), actual.getMessage());
	}
	
	@Test
	public void deleteUser_whenDeletingUserIfExceptionOccurs_thenThrowException() {
		
		assertThrows(RuntimeException.class, () -> {
			when(userDao.getUser(anyString())).thenReturn(populateUser());
			when(addressDao.deleteAddressesOfUser(anyInt())).thenReturn(2);
			when(userDao.deleteUser(anyInt())).thenThrow(RuntimeException.class);
			
			userService.deleteUser("ali_dan");
		});
		
	}
	
	@Test
	public void getUser_whenValidUserNameGiven_thenReturnUserDetails() {
		UserForm expected = populateUserForm();
		when(userDao.IsExists(anyString())).thenReturn(true);
		when(userDao.getUser(anyString())).thenReturn(populateUser());
		when(addressDao.getAddressesOfUser(anyInt())).thenReturn(populateAddress());
		when(mapper.map(any(User.class), any())).thenReturn(populateUserForm());
		
		UserForm actual = userService.getUser("ali_dan");
		
		assertEquals(expected.getFirstName(), actual.getFirstName());
		assertEquals(expected.getAddressList().size(), actual.getAddressList().size());
	}
	
	@Test
	public void getUser_whenInValidUserNameGiven_thenThrowUserDoesNotExistsException() {
		
		
		assertThrows(UserDoesNotExistsException.class, () -> {
			when(userDao.IsExists(anyString())).thenReturn(false);
			
			userService.getUser("2.poornima");
		});
		
	}
	
	
	private UserForm populateUserForm() {
		UserForm user = new UserForm();
		user.setFirstName("Poornima");
		user.setLastName("Sohrot");
		user.setUserName("poornima.sohrot");
		user.setHomePhoneNumber("+14164743431");
		user.setOfficePhoneNumber("+17057864565");
		user.setFaxNumber("23546");
		
		List<AddressForm> addressList = new ArrayList<>();
		AddressForm address = new AddressForm();
		address.setStreetName("4 Dencor Street");
		address.setCity("Brampton");
		address.setCountry("Canada");
		address.setPostalCode("L6X0M1");
		addressList.add(address);
		
		user.setAddressList(addressList);
		
		return user;
	}
	private User populateUserwithAddress() {
		User user = new User();
		user.setFirstName("Poornima");
		user.setLastName("Sohrot");
		user.setUserName("poornima.sohrot");
		user.setHomePhoneNumber("+14164743431");
		user.setOfficePhoneNumber("+17057864565");
		user.setFaxNumber("23546");
		user.setAddressList(populateAddress());
		
		return user;
	}
	
	private List<Address> populateAddress() {
		List<Address> addressList = new ArrayList<>();
		Address address = new Address();
		address.setStreetName("4 Dencor Street");
		address.setCity("Brampton");
		address.setCountry("Canada");
		address.setPostalCode("L6X0M1");
		addressList.add(address);
		
		return addressList;
	}
	
	private User populateUser() {
		User user = new User();
		user.setFirstName("Poornima");
		user.setLastName("Sohrot");
		user.setUserName("poornima.sohrot");
		user.setHomePhoneNumber("+14164743431");
		user.setOfficePhoneNumber("+17057864565");
		user.setFaxNumber("23546");
		
		return user;
	}
}
