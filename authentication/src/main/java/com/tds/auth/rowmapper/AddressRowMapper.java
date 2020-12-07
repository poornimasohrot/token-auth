package com.tds.auth.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tds.auth.domain.Address;

public class AddressRowMapper implements RowMapper<Address> {

	@Override
	public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
		Address address = new Address();
		address.setAddressId(rs.getInt("id"));
		address.setStreetName(rs.getString("street_name"));
		address.setCity(rs.getString("city"));
		address.setCountry(rs.getString("country"));
		address.setPostalCode(rs.getString("postal_code"));
		return address;
	}

}
