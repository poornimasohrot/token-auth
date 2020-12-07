package com.tds.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tds.auth.form.MessageForm;
import com.tds.auth.form.ValidForm;
import com.tds.auth.service.TokenService;

@RestController
@RequestMapping("/token")
public class TokenController {
	
	@Autowired
	TokenService tokenService;

	@GetMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<String> requestToken(@RequestHeader String userName, @RequestHeader String password ){
		String token = tokenService.getToken(userName, password);
		return new ResponseEntity<>(token, HttpStatus.OK);
		
	}
	
	@GetMapping(value="/{token}/validate", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<ValidForm> validateToken(@PathVariable String token){
		ValidForm valid = tokenService.validateToken(token);
		return new ResponseEntity<>(valid, HttpStatus.OK);
		
	}
	
	@PutMapping(value="/{token}/revoke", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<MessageForm> revokeToken(@PathVariable String token){
		MessageForm message = tokenService.revokeToken(token);
		return new ResponseEntity<>(message, HttpStatus.OK);
		
	}
}
