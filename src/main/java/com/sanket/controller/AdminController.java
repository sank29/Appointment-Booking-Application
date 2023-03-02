package com.sanket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanket.entity.CurrentPatientSession;
import com.sanket.entity.Doctor;
import com.sanket.exception.DoctorException;
import com.sanket.exception.LoginException;
import com.sanket.service.AdminDoctorService;
import com.sanket.service.LoginService;
import com.sanket.service.PatientService;

@RestController
public class AdminController {
	
	@Autowired
	AdminDoctorService adminDoctorService;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	PatientService patientService;
	
	@PostMapping("/registerDoctor")
	public ResponseEntity<Doctor> registerDocotor(@RequestParam String key, @RequestBody Doctor doctor) throws DoctorException, LoginException {
		
		
		
		if(loginService.checkUserLoginOrNot(key)) {
			
			CurrentPatientSession currentUserSession = patientService.getCurrentUserByUuid(key);
			
			if(!currentUserSession.getUserType().equals("admin")) {
				
				throw new LoginException("Please login as admin");
				
			}
			
			if(doctor != null) {
				
				Doctor registerDoctor = adminDoctorService.registerDoctor(doctor);
				
				return new ResponseEntity<Doctor>(registerDoctor, HttpStatus.CREATED);
				
			}else {
				
				throw new DoctorException("Please enter valid doctor details");
			}
		
		}else {
			
			throw new LoginException("Please enter valid key");
		}
		
		
	}

}

	
	
	
	
	
	
	
	
	
	
	
	
	