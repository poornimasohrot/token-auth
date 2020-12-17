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

import com.tds.auth.domain.Address;

@ExtendWith(MockitoExtension.class)
public class AddressRowMapperTest {
	
	@InjectMocks
	AddressRowMapper mapper;
	
	@Mock
	ResultSet resultSet;
	
	@Test
	public void mapRow_whenGivenResultSet_thenReturnAddress() throws SQLException {
		Address expected = populateAddress();
		when(resultSet.getInt("id")).thenReturn(1);
		when(resultSet.getString("street_name")).thenReturn("4 Clyde Street");
		when(resultSet.getString("city")).thenReturn("Brampton");
		when(resultSet.getString("country")).thenReturn("Canada");
		when(resultSet.getString("postal_code")).thenReturn("L6XOT9");
		
		Address actual = mapper.mapRow(resultSet, 1);
		
		assertEquals(expected.getAddressId(), actual.getAddressId());
		assertEquals(expected.getStreetName(), actual.getStreetName());
		assertEquals(expected.getCity(), actual.getCity());
		assertEquals(expected.getCountry(), actual.getCountry());
		assertEquals(expected.getPostalCode(), actual.getPostalCode());
		
	}
	
	private Address populateAddress() {
		Address address = new Address();
		address.setAddressId(1);
		address.setStreetName("4 Clyde Street");
		address.setCity("Brampton");
		address.setCountry("Canada");
		address.setPostalCode("L6XOT9");
		
		return address;
	}
	
	

}
