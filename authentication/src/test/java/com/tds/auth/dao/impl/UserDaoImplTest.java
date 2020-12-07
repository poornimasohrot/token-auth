package com.tds.auth.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tds.auth.domain.User;
import com.tds.auth.rowmapper.UserRowMapper;


@ExtendWith(MockitoExtension.class)
public class UserDaoImplTest {

	@InjectMocks
	UserDaoImpl userDao;
	
	@Mock
	JdbcTemplate jdbcTemplate;
	
	@Test
	public void updateUser_whenUserGiven_thenReturnNumberOfRowsUpdated() {
		int expected = 1;
		when(jdbcTemplate.update(anyString(),anyString(),anyString(),anyString(),anyString(),anyString(),anyString(),anyString(), anyInt())).thenReturn(1);
		
		int actual = userDao.updateUser(populateUser());
		assertEquals(expected, actual);
	}
	
	@Test
	public void deleteUser_whenValidUserId_thenReturnNumberOfUsersDeleted() {
		int expected = 1;
		when(jdbcTemplate.update(anyString(), anyInt())).thenReturn(1);
		
		int actual = userDao.deleteUser(1);
		assertEquals(expected, actual);
	}
	
	@Test
	public void getUser_whenGivenUserName_thenReturnUserObject() {
		User expected = populateUser();
		when(jdbcTemplate.queryForObject(anyString(), any(UserRowMapper.class), anyString())).thenReturn(populateUser());
		User actual = userDao.getUser("ali_dan");
		assertEquals(expected.getFirstName(), actual.getFirstName());
	}
	
	@Test
	public void getUser_whenGivenUserNameAndPassword_thenReturnUserObject() {
		User expected = populateUser();
		when(jdbcTemplate.queryForObject(anyString(), any(UserRowMapper.class), anyString(), anyString())).thenReturn(populateUser());
		User actual = userDao.getUser("ali_dan","pwd");
		assertEquals(expected.getFirstName(), actual.getFirstName());
	}
	
	@Test
	public void IsExists_whenGivenValidUserName_thenReturnTrue() {
		when(jdbcTemplate.queryForObject(anyString(), any(UserRowMapper.class), anyString())).thenReturn(populateUser());
		boolean actual = userDao.IsExists("ali_dan");
		assertTrue(actual);
	}
	
	@Test
	public void IsExists_whenGivenInValidUserName_thenReturnFalse() {
		when(jdbcTemplate.queryForObject(anyString(), any(UserRowMapper.class), anyString())).thenThrow(EmptyResultDataAccessException.class);
		boolean actual = userDao.IsExists("ali_dan");
		assertFalse(actual);
	}
	
	private User populateUser() {
		User user = new User();
		user.setFirstName("Poornima");
		user.setLastName("Sohrot");
		user.setUserName("poornima.sohrot");
		user.setPassword("pwd");
		user.setHomePhoneNumber("+14164743431");
		user.setOfficePhoneNumber("+17057864565");
		user.setFaxNumber("23546");
		
		return user;
	}
}
