package com.tds.auth.rowmapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tds.auth.domain.User;

@ExtendWith(MockitoExtension.class)
public class UserRowMapperTest {
	
	@InjectMocks
	UserRowMapper mapper;
	
	@Mock
    private ResultSet resultSet;
	
	@Test
	public void mapRow_whenGivenResultSet_thenReturnUser() throws SQLException {
		User expected = populateUser();
		when(resultSet.getInt("id")).thenReturn(1);
		when(resultSet.getString("first_name")).thenReturn("ali");
		when(resultSet.getString("last_name")).thenReturn("dan");
		when(resultSet.getString("user_name")).thenReturn("ali_dan");
		when(resultSet.getString("home_phone_no")).thenReturn("+1(657)876-4331");
		when(resultSet.getString("office_phone_no")).thenReturn("7059876852");
		when(resultSet.getString("fax_no")).thenReturn("657DWK43JH");
		
		User actual = mapper.mapRow(resultSet, 1);
		
		assertEquals(expected.getUserId(), actual.getUserId());
		assertEquals(expected.getFirstName(), actual.getFirstName());
		assertEquals(expected.getLastName(), actual.getLastName());
		assertEquals(expected.getUserName(), actual.getUserName());
		assertEquals(expected.getHomePhoneNumber(), actual.getHomePhoneNumber());
		assertEquals(expected.getOfficePhoneNumber(), actual.getOfficePhoneNumber());
		assertEquals(expected.getFaxNumber(), actual.getFaxNumber());
		
	}
	
	private User populateUser() {
		User user = new User();
		user.setUserId(1);
		user.setFirstName("ali");
		user.setLastName("dan");
		user.setUserName("ali_dan");
		user.setHomePhoneNumber("+1(657)876-4331");
		user.setOfficePhoneNumber("7059876852");
		user.setFaxNumber("657DWK43JH");
		
		return user;
	}

}
