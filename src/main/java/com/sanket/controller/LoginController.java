package com.sanket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanket.entity.LoginDTO;
import com.sanket.entity.LoginResponse;
import com.sanket.entity.LoginUUIDKey;
import com.sanket.exception.LoginException;
import com.sanket.service.LoginService;



@RestController
public class LoginController {
	
	@Autowired
	private LoginService customerLogin;
	
	@Autowired
	private LoginService loginService;
	
	
	@PostMapping("/login")
	@CrossOrigin
	public ResponseEntity<String> loginCustomer(@RequestBody LoginDTO loginDTO) throws LoginException{
		
		String result = customerLogin.logIntoAccount(loginDTO);
		
		
		return new ResponseEntity<String>(result,HttpStatus.OK);
		
	}
	
	@CrossOrigin
	@GetMapping("/checkLogin/{uuid}")
	public ResponseEntity<LoginResponse> checkUserLoginORNot(@PathVariable String uuid) throws LoginException{
		
		Boolean loginResult = loginService.checkUserLoginOrNot(uuid);
		
		LoginResponse loginResponse = new LoginResponse(loginResult);
		
		return new ResponseEntity<LoginResponse>(loginResponse,HttpStatus.OK);
		
	}
	
	@PostMapping("/logout")
	public String logoutCustomer(@RequestParam(required = false) String key) throws LoginException {
		
		return customerLogin.logoutFromAccount(key);
		
	}
	
}






