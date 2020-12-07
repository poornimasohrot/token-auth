package com.tds.auth.controller;

import static com.tds.auth.literals.MessageConstant.REQUESTD_ACTN_NOT_PERFORMED;
import static com.tds.auth.literals.MessageConstant.USER_ADDED_SUCCESSFULLY;
import static com.tds.auth.literals.MessageConstant.USER_DELETED_SUCCESSFULLY;
import static com.tds.auth.literals.MessageConstant.USER_DOES_NOT_EXISTS;
import static com.tds.auth.literals.MessageConstant.USER_EXISTS;
import static com.tds.auth.literals.MessageConstant.USER_UPDATED_SUCCESSFULLY;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tds.auth.exception.UserDoesNotExistsException;
import com.tds.auth.exception.UserExistsException;
import com.tds.auth.form.AddressForm;
import com.tds.auth.form.MessageForm;
import com.tds.auth.form.UserForm;
import com.tds.auth.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UserService userService;
	
	@Test
	public void addUser_whenUserAddedSuccessfully_thenReturnSuccessMessage() throws Exception {
		
		UserForm userForm = populateUserForm();
		MessageForm message = new MessageForm(USER_ADDED_SUCCESSFULLY, true);
		
		when(userService.addUser(any(UserForm.class))).thenReturn(message);
		
		String requestBody  = new ObjectMapper().writeValueAsString(userForm);
		
		MvcResult result = mvc.perform(post("/user").content(requestBody).contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
		String response = result.getResponse().getContentAsString();
		
		assertTrue(response.contains("\"success\":true"));
		assertTrue(response.contains("message\":\""+USER_ADDED_SUCCESSFULLY+"\""));
	}
	
	@Test
	public void addUser_whenUserAlreadyExists_thenReturnUserExistsMessage() throws Exception {
		
		UserForm userForm = populateUserForm();
		UserExistsException exception = new UserExistsException(USER_EXISTS);
		when(userService.addUser(any(UserForm.class))).thenThrow(exception);
		
		String requestBody  = new ObjectMapper().writeValueAsString(userForm);
		
		MvcResult result = mvc.perform(post("/user").content(requestBody).contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
		String response = result.getResponse().getContentAsString();
		
		assertTrue(response.contains("\"success\":false"));
		assertTrue(response.contains("message\":\""+USER_EXISTS+"\""));
	}
	
	@Test
	public void addUser_whenExceptionOccurs_thenReturnErrorMessage() throws Exception {
		
		UserForm userForm = populateUserForm();
		
		when(userService.addUser(any(UserForm.class))).thenThrow(RuntimeException.class);
		
		String requestBody  = new ObjectMapper().writeValueAsString(userForm);
		
		MvcResult result = mvc.perform(post("/user").content(requestBody).contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError()).andReturn();
		String response = result.getResponse().getContentAsString();
		
		assertTrue(response.contains("\"success\":false"));
		assertTrue(response.contains("message\":\""+REQUESTD_ACTN_NOT_PERFORMED+"\""));
	}
	
	@Test
	public void addUser_whenWrongURL_thenReturnNotFound() throws Exception {
		
		UserForm userForm = populateUserForm();
		
		String requestBody  = new ObjectMapper().writeValueAsString(userForm);
		
		mvc.perform(post("/users").content(requestBody).contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();
	}
	
	@Test
	public void updateUser_whenUserUpdatedSuccessfully_thenReturnSuccessMessage() throws Exception {
		
		UserForm userForm = populateUserForm();
		MessageForm message = new MessageForm(USER_UPDATED_SUCCESSFULLY, true);
		
		when(userService.updateUser(any(UserForm.class))).thenReturn(message);
		
		String requestBody  = new ObjectMapper().writeValueAsString(userForm);
		
		MvcResult result = mvc.perform(put("/user").content(requestBody).contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();
		
		assertTrue(response.contains("\"success\":true"));
		assertTrue(response.contains("message\":\""+USER_UPDATED_SUCCESSFULLY+"\""));
	}
	
	@Test
	public void deleteUser_whenUserDeletedSuccessfully_thenReturnSuccessMessage() throws Exception {
		
		MessageForm message = new MessageForm(USER_DELETED_SUCCESSFULLY, true);
		
		when(userService.deleteUser(anyString())).thenReturn(message);
		
		MvcResult result = mvc.perform(delete("/user/ali_dan").contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();
		
		assertTrue(response.contains("\"success\":true"));
		assertTrue(response.contains("message\":\""+USER_DELETED_SUCCESSFULLY+"\""));
	}
	
	@Test
	public void getUser_whenRequestedForUserBYUserName_thenReturnUserObject() throws Exception {
		
		UserForm expected = populateUserForm();
		
		when(userService.getUser(anyString())).thenReturn(expected);
		
		MvcResult result = mvc.perform(get("/user/ali_dan").contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();
		
		UserForm actual = new ObjectMapper().readValue(response, UserForm.class);
		Assertions.assertEquals(expected.getFirstName(), actual.getFirstName());
	}
	
	@Test
	public void getUser_whenRequestedForInvalidUserBYUserName_thenReturnUserDoesNotExistsException() throws Exception {
		
		UserDoesNotExistsException exception = new UserDoesNotExistsException(USER_DOES_NOT_EXISTS);
		when(userService.getUser(anyString())).thenThrow(exception);
		
		MvcResult result = mvc.perform(get("/user/ali_dan").contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andReturn();
		String response = result.getResponse().getContentAsString();
		
		assertTrue(response.contains("\"success\":false"));
		assertTrue(response.contains("message\":\""+USER_DOES_NOT_EXISTS+"\""));
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
	
	

}
