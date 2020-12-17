package com.tds.auth.rowmapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tds.auth.domain.Token;
import com.tds.auth.domain.User;

@ExtendWith(MockitoExtension.class)
public class TokenRowMapperTest {
	
	@InjectMocks
	private TokenRowMapper mapper;
	
	@Mock
	private ResultSet resultSet;
	
	@Test
	public void mapRow_whenGivenResultSet_thenReturnToken() throws SQLException {
		Token expected = populateToken();
		when(resultSet.getString("token")).thenReturn("914deaf0cf9c40b28ca76df91e88fcc");
		when(resultSet.getTimestamp("issued_date_time")).thenReturn(Timestamp.valueOf(LocalDateTime.of(2020, 12, 07, 6, 10)));
		when(resultSet.getTimestamp("expired_date_time")).thenReturn(Timestamp.valueOf(LocalDateTime.of(2020, 12, 07, 6, 25)));
		
		when(resultSet.getInt("user_id")).thenReturn(1);
		when(resultSet.getString("first_name")).thenReturn("ali");
		when(resultSet.getString("last_name")).thenReturn("dan");
		when(resultSet.getString("user_name")).thenReturn("ali_dan");
		when(resultSet.getString("home_phone_no")).thenReturn("+1(657)876-4331");
		when(resultSet.getString("office_phone_no")).thenReturn("7059876852");
		when(resultSet.getString("fax_no")).thenReturn("657DWK43JH");
		
		Token actual = mapper.mapRow(resultSet, 1);
		
		assertEquals(expected.getToken(), actual.getToken());
		assertEquals(expected.getIssuedDateTime(), actual.getIssuedDateTime());
		assertEquals(expected.getExpiryDateTime(), actual.getExpiryDateTime());
		
		assertEquals(expected.getUser().getUserId(), actual.getUser().getUserId());
		assertEquals(expected.getUser().getFirstName(), actual.getUser().getFirstName());
		assertEquals(expected.getUser().getLastName(), actual.getUser().getLastName());
		assertEquals(expected.getUser().getUserName(), actual.getUser().getUserName());
		assertEquals(expected.getUser().getHomePhoneNumber(), actual.getUser().getHomePhoneNumber());
		assertEquals(expected.getUser().getOfficePhoneNumber(), actual.getUser().getOfficePhoneNumber());
		assertEquals(expected.getUser().getFaxNumber(), actual.getUser().getFaxNumber());
		
	}
	
	
	private Token populateToken() {
		Token token = new Token();
		token.setToken("914deaf0cf9c40b28ca76df91e88fcc");
		token.setIssuedDateTime(LocalDateTime.of(2020, 12, 07, 6, 10));
		token.setExpiryDateTime(LocalDateTime.of(2020, 12, 07, 6, 25));
		token.setUser(populateUser());
		return token;
		
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
