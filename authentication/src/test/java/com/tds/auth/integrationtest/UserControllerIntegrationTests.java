package com.tds.auth.integrationtest;

import static com.tds.auth.literals.MessageConstant.USER_ADDED_SUCCESSFULLY;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tds.auth.form.AddressForm;
import com.tds.auth.form.UserForm;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	//@Transactional
	//@Rollback(true)
	public void addUser_whenUserAddedSuccessfully_thenReturnSuccessMessage() throws Exception {
		
		UserForm userForm = populateUserForm();
		String requestBody  = new ObjectMapper().writeValueAsString(userForm);
		
		MvcResult result = mvc.perform(post("/user").content(requestBody).contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
		String response = result.getResponse().getContentAsString();
		
		assertTrue(response.contains("\"success\":true"));
		assertTrue(response.contains("message\":\""+USER_ADDED_SUCCESSFULLY+"\""));
	}
    
    private UserForm populateUserForm() {
		UserForm user = new UserForm();
		user.setFirstName("Poornima");
		user.setLastName("Sohrot");
		user.setUserName("poornima.sohrot");
		user.setPassword("pwd");
		user.setHomePhoneNumber("+14164743431");
		user.setOfficePhoneNumber("+17057864565");
		user.setFaxNumber("23546");
		
		List<AddressForm> addressList = new ArrayList<>();
		AddressForm address = new AddressForm();
		address.setStreetName("4 Dencor Street");
		address.setCity("Brampton");
		address.setCountry("Canada");
		address.setPostalCode("L6X0M1");
		addressList.add(address);
		
		user.setAddressList(addressList);
		
		return user;
	}

}
