package com.tds.auth.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.jdbc.core.JdbcTemplate;

import com.tds.auth.domain.Address;
import com.tds.auth.rowmapper.AddressRowMapper;

@ExtendWith(MockitoExtension.class)
public class AddressDaoImplTest {

	@InjectMocks
	AddressDaoImpl addressDao;
	
	@Mock
	JdbcTemplate jdbcTemplate;
	
	@Test
	public void insertAddress_whenAddressGiven_thenReturnNumberOfRowsInserted() {
		int expected = 1;
		when(jdbcTemplate.update(anyString(),anyInt(),anyString(),anyString(),anyString(), anyString())).thenReturn(1);
		
		int actual = addressDao.insertAddress(1,populateAddress());
		assertEquals(expected, actual);
	}
	
	@Test
	public void updateAddress_whenAddressGiven_thenReturnNumberOfRowsUpdated() {
		int expected = 1;
		when(jdbcTemplate.update(anyString(),anyString(),anyString(),anyString(), anyString(), anyInt())).thenReturn(1);
		
		int actual = addressDao.updateAddress(populateAddress());
		assertEquals(expected, actual);
	}
	
	@Test
	public void deleteAddress_whenAddressIdGiven_thenReturnNumberOfRowsDeleted() {
		int expected = 1;
		when(jdbcTemplate.update(anyString(), anyInt())).thenReturn(1);
		
		int actual = addressDao.deleteAddress(1);
		assertEquals(expected, actual);
	}
	
	@Test
	public void deleteAddressesOfUser_whenUserIdGiven_thenReturnNumberOfRowsDeleted() {
		int expected = 2;
		when(jdbcTemplate.update(anyString(), anyInt())).thenReturn(2);
		
		int actual = addressDao.deleteAddressesOfUser(1);
		assertEquals(expected, actual);
	}
	
	@Test
	public void getAddressesOfUser_whenUserIdGiven_thenReturnAddressList() {
		List<Address> expected = populateAddressList();
		when(jdbcTemplate.query(anyString(),any(AddressRowMapper.class), anyInt())).thenReturn(populateAddressList());
		
		List<Address> actual = addressDao.getAddressesOfUser(1);
		assertEquals(expected.size(), actual.size());
	}
	
	public Address populateAddress() {
		Address address = new Address();
		address.setStreetName("4 Dencor Street");
		address.setCity("Brampton");
		address.setCountry("Canada");
		address.setPostalCode("L6X0M1");
		
		return address;
	}
	private List<Address> populateAddressList() {
		List<Address> addressList = new ArrayList<>();
		Address address = new Address();
		address.setStreetName("4 Dencor Street");
		address.setCity("Brampton");
		address.setCountry("Canada");
		address.setPostalCode("L6X0M1");
		addressList.add(address);
		
		return addressList;
	}
}
