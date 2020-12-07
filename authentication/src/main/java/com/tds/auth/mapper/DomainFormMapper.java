package com.tds.auth.mapper;

import org.springframework.stereotype.Component;

import com.tds.auth.domain.Address;
import com.tds.auth.domain.User;
import com.tds.auth.form.AddressForm;
import com.tds.auth.form.UserForm;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

@Component
public class DomainFormMapper extends ConfigurableMapper {
	
	protected void configure(MapperFactory factory) {
		
		factory.classMap(User.class, UserForm.class).byDefault().register();  
		
		factory.classMap(Address.class, AddressForm.class).byDefault().register();
		
	}
	
	
}
