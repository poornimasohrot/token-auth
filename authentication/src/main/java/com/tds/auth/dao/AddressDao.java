package com.tds.auth.dao;

import java.util.List;

import com.tds.auth.domain.Address;

public interface AddressDao {
	
	int insertAddress(int userId, Address address) ;

	int updateAddress(Address address);

	int deleteAddress(int addressId);

	int deleteAddressesOfUser(int userId);

	List<Address> getAddressesOfUser(int userId);

}
