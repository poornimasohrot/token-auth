package com.tds.auth.service.impl;

import static com.tds.auth.literals.MessageConstant.TOKEN_ALREADY_EXPIRED;
import static com.tds.auth.literals.MessageConstant.TOKEN_INVALID;
import static com.tds.auth.literals.MessageConstant.TOKEN_REVOKED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import com.tds.auth.dao.TokenDao;
import com.tds.auth.dao.UserDao;
import com.tds.auth.domain.Token;
import com.tds.auth.domain.User;
import com.tds.auth.exception.InValidUserException;
import com.tds.auth.form.MessageForm;
import com.tds.auth.form.ValidForm;

@ExtendWith(MockitoExtension.class)
public class TokenServiceImplTest {

	@InjectMocks
	TokenServiceImpl tokenService;
	
	@Mock
	UserDao userDao;
	
	@Mock
	TokenDao tokenDao;
	
	@Test
	public void getToken_whenValidUserNamePasswordGiven_thenCreateNewTokenAndReturn() {
		
		when(userDao.getUser(anyString(), anyString())).thenReturn(populateUser());
		
		String actual = tokenService.getToken("ali_dan", "alidan98");
		
		assertNotNull(actual);
	}
	
	@Test
	public void getToken_whenInValidUserNamePasswordGiven_thenThrowInValidUserException() {
		
		assertThrows(InValidUserException.class, () -> {
			when(userDao.getUser(anyString(), anyString())).thenThrow(EmptyResultDataAccessException.class);
			
			tokenService.getToken("ali_dan", "alidan98");
		});
	}
	
	@Test
	public void validateToken_whenValidTokenGiven_thenReturnTrue() {
		
		when(tokenDao.getToken(anyString())).thenReturn(populateToken());
		
		ValidForm actual = tokenService.validateToken("914deaf0cf9c40b28ca76df91e88fcc");
		
		assertTrue(actual.isValid());
	}
	
	@Test
	public void validateToken_whenInValidTokenGiven_thenReturnFalse() {
		
		when(tokenDao.getToken(anyString())).thenThrow(EmptyResultDataAccessException.class);
		
		ValidForm actual = tokenService.validateToken("914deaf0cf9c40b28ca76df91e88fcc");
		
		assertFalse(actual.isValid());
	}
	
	@Test
	public void validateToken_whenValidTokenhasExpired_thenReturnFalse() {
		
		when(tokenDao.getToken(anyString())).thenReturn(populateExpiredToken());
		
		ValidForm actual = tokenService.validateToken("914deaf0cf9c40b28ca76df91e88fcc");
		
		assertFalse(actual.isValid());
	}
	
	@Test
	public void revokeToken_whenGivenValidToken_thenRevokeTokenAndReturnSuccessfulMessage() {
		MessageForm expected = new MessageForm(TOKEN_REVOKED, true);
		when(tokenDao.getToken(anyString())).thenReturn(populateToken());
		
		MessageForm actual = tokenService.revokeToken("914deaf0cf9c40b28ca76df91e88fcc");
		
		assertEquals(expected.getMessage(), actual.getMessage());
		assertEquals(expected.isSuccess(), actual.isSuccess());
	}
	
	@Test
	public void revokeToken_whenGivenInValidToken_thenReturnInvalidTokenMessage() {
		MessageForm expected = new MessageForm(TOKEN_INVALID, false);
		when(tokenDao.getToken(anyString())).thenThrow(EmptyResultDataAccessException.class);
		
		MessageForm actual = tokenService.revokeToken("914deaf0cf9c40b28ca76df91e88fcc");
		
		assertEquals(expected.getMessage(), actual.getMessage());
		assertEquals(expected.isSuccess(), actual.isSuccess());
	}
	
	@Test
	public void revokeToken_whenGivenTokenAlreadyExpired_thenReturnAlreadyExpiredMessage() {
		MessageForm expected = new MessageForm(TOKEN_ALREADY_EXPIRED, false);
		when(tokenDao.getToken(anyString())).thenReturn(populateExpiredToken());
		
		MessageForm actual = tokenService.revokeToken("914deaf0cf9c40b28ca76df91e88fcc");
		
		assertEquals(expected.getMessage(), actual.getMessage());
		assertEquals(expected.isSuccess(), actual.isSuccess());
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
	private Token populateToken() {
		Token token = new Token();
		token.setToken("914deaf0cf9c40b28ca76df91e88fcc");
		token.setIssuedDateTime(LocalDateTime.now());
		token.setExpiryDateTime(LocalDateTime.now().plusMinutes(15));
		token.setUser(populateUser());
		return token;
		
	}
	private Token populateExpiredToken() {
		Token token = new Token();
		token.setToken("914deaf0cf9c40b28ca76df91e88fcc");
		token.setIssuedDateTime(LocalDateTime.now().minusMinutes(30));
		token.setExpiryDateTime(LocalDateTime.now().minusMinutes(15));
		token.setUser(populateUser());
		return token;
		
	}
	
}
