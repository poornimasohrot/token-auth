package com.tds.auth.controller;

import static com.tds.auth.literals.MessageConstant.TOKEN_REVOKED;
import static com.tds.auth.literals.MessageConstant.USER_OR_PWD_INVALID;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.tds.auth.exception.InValidUserException;
import com.tds.auth.form.MessageForm;
import com.tds.auth.form.ValidForm;
import com.tds.auth.service.TokenService;

@WebMvcTest(TokenController.class)
public class TokenControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private TokenService tokenService;
	
	@Test
	public void requestToken_whenValidUser_thenReturnToken() throws Exception {
		
		when(tokenService.getToken(anyString(), anyString())).thenReturn("914deaf0cf9c40b28ca76df91e88fcc");
		
		MvcResult result = mvc.perform(get("/token").header("userName", "ali_dan").header("password", "alidan98").contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();
		
		assertNotNull(response);
	}
	
	@Test
	public void requestToken_whenInValidUser_thenReturnUnAuthorizedMessage() throws Exception {
		DataAccessException parentException = new EmptyResultDataAccessException(1);
		InValidUserException invalidUserException = new InValidUserException(USER_OR_PWD_INVALID, parentException);
		
		when(tokenService.getToken(anyString(), anyString())).thenThrow(invalidUserException);
		
		MvcResult result = mvc.perform(get("/token").header("userName", "ali_dan").header("password", "alidan98").contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized()).andReturn();
		String response = result.getResponse().getContentAsString();
		
		assertTrue(response.contains("\"success\":false"));
		assertTrue(response.contains("message\":\""+USER_OR_PWD_INVALID+"\""));
	}
	
	
	@Test
	public void validateToken_whenValidToken_thenReturnTrue() throws Exception {
		
		when(tokenService.validateToken(anyString())).thenReturn(new ValidForm(true));
		
		MvcResult result = mvc.perform(get("/token/914deaf0cf9c40b28ca76df91e88fcc/validate").contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();
		
		assertTrue(response.contains("\"valid\":true"));
	}
	
	@Test
	public void validateToken_whenInValidToken_thenReturnFalse() throws Exception {
		
		when(tokenService.validateToken(anyString())).thenReturn(new ValidForm(false));
		
		MvcResult result = mvc.perform(get("/token/914deaf0cf9c40b28ca76df91e88fcc/validate").contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();
		
		assertTrue(response.contains("\"valid\":false"));
	}
	
	@Test
	public void revokeToken_whenValidToken_thenReturnRevokeDoneSuccessfully() throws Exception {
		
		when(tokenService.revokeToken(anyString())).thenReturn(new MessageForm(TOKEN_REVOKED, true));
		
		MvcResult result = mvc.perform(put("/token/914deaf0cf9c40b28ca76df91e88fcc/revoke").contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();
		
		assertTrue(response.contains("\"success\":true"));
		assertTrue(response.contains("message\":\""+TOKEN_REVOKED+"\""));
	}
	
	
}
