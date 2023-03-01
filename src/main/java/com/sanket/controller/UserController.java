package com.sanket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanket.entity.User;
import com.sanket.exception.UserException;
import com.sanket.service.UserService;


@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/registerCustomer")
	public ResponseEntity<User> saveCustomer(@RequestBody User user) throws UserException{
		
		User savedUser= userService.createCustomer(user);
		
		return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
		
	}
	
	
	@PutMapping("/updateCustomer")
	public ResponseEntity<User> updateCustomer(@RequestBody User user, @RequestParam(required = false) String key) throws UserException{
		
		User updateduser = userService.updateCustomer(user, key);
		
		return new ResponseEntity<User>(updateduser,HttpStatus.OK);
		
	}

}
