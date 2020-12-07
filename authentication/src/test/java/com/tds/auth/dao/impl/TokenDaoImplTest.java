package com.tds.auth.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tds.auth.domain.Token;
import com.tds.auth.domain.User;
import com.tds.auth.rowmapper.TokenRowMapper;

@ExtendWith(MockitoExtension.class)
public class TokenDaoImplTest {

	@InjectMocks
	TokenDaoImpl tokenDao;
	
	@Mock
	JdbcTemplate jdbcTemplate;
	
	@Test
	public void insertToken_whenTokenGiven_thenReturnNumberOfRowsInserted() {
		int expected = 1;
		when(jdbcTemplate.update(anyString(),anyInt(),anyString(),any(LocalDateTime.class),any(LocalDateTime.class))).thenReturn(1);
		
		int actual = tokenDao.insertToken(populateToken());
		assertEquals(expected, actual);
	}
	
	@Test
	public void getToken_whenValidTokenGiven_thenReturnToken() {
		Token expected = populateToken();
		when(jdbcTemplate.queryForObject(anyString(),any(TokenRowMapper.class), anyString())).thenReturn(expected);
		
		Token actual = tokenDao.getToken("914deaf0cf9c40b28ca76df91e88fcc");
		assertEquals(expected.getToken(), actual.getToken());
		assertEquals(expected.getUser().getUserId(), actual.getUser().getUserId());
		assertTrue(expected.getIssuedDateTime().isEqual(actual.getIssuedDateTime()));
	}
	
	@Test
	public void getToken_whenInValidTokenGiven_thenThrowException() {
		assertThrows(RuntimeException.class, () -> {
			when(jdbcTemplate.queryForObject(anyString(),any(TokenRowMapper.class), anyString())).thenThrow(RuntimeException.class);
			
			tokenDao.getToken("914deaf0cf9c40b28ca76df91e88fcc");
		});
		
	}
	
	@Test
	public void invalidateToken_whenTokenGiven_thenReturnNumberOfRowsInserted() {
		int expected = 1;
		when(jdbcTemplate.update(anyString(),any(LocalDateTime.class), anyString())).thenReturn(1);
		
		int actual = tokenDao.invalidateToken("914deaf0cf9c40b28ca76df91e88fcc");
		assertEquals(expected, actual);
	}
	
	private Token populateToken() {
		Token token = new Token();
		token.setToken("914deaf0cf9c40b28ca76df91e88fcc");
		token.setIssuedDateTime(LocalDateTime.now());
		token.setExpiryDateTime(LocalDateTime.now().plusMinutes(15));
		token.setUser(populateUser());
		return token;
		
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
