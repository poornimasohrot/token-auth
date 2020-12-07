package com.tds.auth.integrationtest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class TokenControllerIntegrationTests {

	@Autowired
	private MockMvc mvc;
	
	@Test
	public void requestToken_whenValidUser_thenReturnToken() throws Exception {
		
		MvcResult result = mvc.perform(post("/token?userName=ali_dan").content("alidan98").contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();
		System.out.println(response);
		assertNotNull(response);
	}
	
}
