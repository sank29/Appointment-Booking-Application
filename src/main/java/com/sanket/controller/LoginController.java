package com.sanket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanket.entity.LoginDTO;
import com.sanket.entity.LoginResponse;
import com.sanket.entity.LoginUUIDKey;
import com.sanket.exception.LoginException;
import com.sanket.service.DoctorLoginService;
import com.sanket.service.PatientAndAdminLoginService;

import jakarta.validation.Valid;



@RestController
public class LoginController {
	
	@Autowired
	private PatientAndAdminLoginService patientAndAdminLoginService; 
	
	@Autowired
	private DoctorLoginService doctorLoginService;
	
	
	@PostMapping("/login")
	@CrossOrigin
	public ResponseEntity<LoginUUIDKey> loginPatient(@RequestBody LoginDTO loginDTO) throws LoginException{
		
		LoginUUIDKey result = patientAndAdminLoginService.logIntoAccount(loginDTO);
		
		
		return new ResponseEntity<LoginUUIDKey>(result,HttpStatus.OK);
		
	}
	
	@PostMapping("/loginDoctor")
	@CrossOrigin
	public ResponseEntity<LoginUUIDKey> loginDoctor(@RequestBody LoginDTO loginDTO) throws LoginException{
		
		LoginUUIDKey result = doctorLoginService.logIntoAccount(loginDTO);
		
		
		return new ResponseEntity<LoginUUIDKey>(result,HttpStatus.OK);
		
	}
	
	@CrossOrigin
	@GetMapping("/checkLogin/{uuid}")
	public ResponseEntity<LoginResponse> checkUserLoginORNot(@PathVariable String uuid) throws LoginException{
		
		Boolean loginResult = patientAndAdminLoginService.checkUserLoginOrNot(uuid); 
		
		LoginResponse loginResponse = new LoginResponse(loginResult);
		
		return new ResponseEntity<LoginResponse>(loginResponse,HttpStatus.OK);
		
	}
	
	@PostMapping("/logout")
	@CrossOrigin
	public String logoutPatient(@RequestParam(required = false) String key) throws LoginException {
		
		return patientAndAdminLoginService.logoutFromAccount(key);
		
	}
	
	@DeleteMapping("/logoutDoctor")
	public String logoutDoctor(@RequestParam(required = false) String key) throws LoginException {
		
		return doctorLoginService.logoutFromAccount(key);
		
	}
	
	
	
	
	
}






