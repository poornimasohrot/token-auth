package com.tds.auth.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tds.auth.dao.AddressDao;
import com.tds.auth.domain.Address;
import com.tds.auth.rowmapper.AddressRowMapper;

@Repository
public class AddressDaoImpl implements AddressDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	@Override
	public int insertAddress(int userId, Address address) {
		String sql = "INSERT INTO ADDRESS (user_id, street_name, city, country, postal_code) VALUES (?, ?, ?, ?, ?)";
		return jdbcTemplate.update(sql,userId, address.getStreetName(),
				address.getCity(), address.getCountry(), address.getPostalCode());
	}


	@Override
	public int updateAddress(Address address) {
		String sql = "UPDATE ADDRESS set street_name = ?, city = ?, country = ?, postal_code = ? where id = ? ";
		return jdbcTemplate.update(sql,address.getStreetName(),
				address.getCity(), address.getCountry(), address.getPostalCode(), address.getAddressId());
	}


	@Override
	public int deleteAddress(int addressId) {
		String sql = "DELETE FROM ADDRESS where id = ? ";
		return jdbcTemplate.update(sql,addressId);
	}


	@Override
	public int deleteAddressesOfUser(int userId) {
		String sql = "DELETE FROM ADDRESS where user_id = ? ";
		return jdbcTemplate.update(sql,userId);
	}


	@Override
	public List<Address> getAddressesOfUser(int userId) {
		String sql = "SELECT street_name, city, country, postal_code, id from ADDRESS where user_id = ?";
		return jdbcTemplate.query(sql, new AddressRowMapper(), userId);
	}

}
