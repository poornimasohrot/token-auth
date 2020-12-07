package com.tds.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tds.auth.form.MessageForm;
import com.tds.auth.form.UserForm;
import com.tds.auth.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/{userName}" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserForm> getUser(@PathVariable String userName) {
		UserForm user = userService.getUser(userName);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@PostMapping(value="" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageForm> addUser(@RequestBody UserForm user){
		MessageForm messageForm = userService.addUser(user);
		return new ResponseEntity<>(messageForm,HttpStatus.CREATED);
	}
	
	@PutMapping(value="" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageForm> updateUser(@RequestBody UserForm user){
		MessageForm messageForm = userService.updateUser(user);
		return new ResponseEntity<>(messageForm,HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageForm> deleteUser(@PathVariable String userName){
		MessageForm messageForm = userService.deleteUser(userName);
		return new ResponseEntity<>(messageForm,HttpStatus.OK);
	}
	

}
