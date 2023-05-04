package com.sanket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanket.entity.CurrentSession;
import com.sanket.entity.Doctor;
import com.sanket.entity.Patient;
import com.sanket.exception.DoctorException;
import com.sanket.exception.LoginException;
import com.sanket.service.AdminDoctorService;
import com.sanket.service.PatientAndAdminLoginService;
import com.sanket.service.PatientService;

import jakarta.validation.Valid;

@RestController
public class AdminController {
	
	@Autowired
	AdminDoctorService adminDoctorService;
	
	@Autowired
	PatientAndAdminLoginService patientAndAdminLoginService;
	
	@Autowired
	PatientService patientService;
	
	
	@PostMapping("/registerDoctor")
	@CrossOrigin
	public ResponseEntity<Doctor> registerDocotor(@RequestParam String key, @RequestBody Doctor doctor) throws DoctorException, LoginException {
		
		if(patientAndAdminLoginService.checkUserLoginOrNot(key)) {
			
			CurrentSession currentUserSession = patientService.getCurrentUserByUuid(key);
			
			System.out.println(currentUserSession);
			
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
			
			throw new LoginException("Please enter valid key.");
		}
		
		
	}
	
	@DeleteMapping("/deleteDoctor")
	public ResponseEntity<Doctor> deleteDoctor(@RequestParam String key, @RequestBody Doctor doctor) throws LoginException, DoctorException{ 
		
		if(patientAndAdminLoginService.checkUserLoginOrNot(key)) {
			
			CurrentSession currentUserSession = patientService.getCurrentUserByUuid(key);
			
			if(!currentUserSession.getUserType().equals("admin")) { 
				
				throw new LoginException("Please login as admin");
				
			}
			
				Doctor deletedDoctor = adminDoctorService.deleteDoctor(doctor);
				
				return new ResponseEntity<Doctor>(deletedDoctor, HttpStatus.CREATED);
				

		
		}else {
			
			throw new LoginException("Please enter valid key.");
		}
	}

}

	
	
	
	
	
	
	
	
	
	
	
	
	