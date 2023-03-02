package com.sanket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.sanket.entity.Doctor;
import com.sanket.entity.Patient;
import com.sanket.exception.DoctorException;
import com.sanket.exception.LoginException;
import com.sanket.exception.PatientException;
import com.sanket.service.DoctorService;
import com.sanket.service.LoginService;
import com.sanket.service.PatientService;


@RestController
public class PatientController {
	
	@Autowired
	PatientService userService;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	DoctorService doctorService;
	
	
	
	@PostMapping("/registerPatient")
	public ResponseEntity<Patient> saveCustomer(@RequestBody Patient patient) throws PatientException{
		
		Patient savedUser= userService.createPatient(patient);
		
		return new ResponseEntity<Patient>(savedUser, HttpStatus.CREATED);
		
	}
	
	
	@PutMapping("/updatePatient")
	public ResponseEntity<Patient> updateCustomer(@RequestBody Patient patient, @RequestParam(required = false) String key) throws PatientException{
		
		Patient updateduser = userService.updatePatient(patient, key);
		
		return new ResponseEntity<Patient>(updateduser,HttpStatus.OK);
		
	}
	
	
	@GetMapping("/doctors")
	public ResponseEntity<List<Doctor>> getAllDoctorsFromDataBase(@RequestParam String key) throws LoginException, DoctorException{
		
		if(loginService.checkUserLoginOrNot(key)) {
			
			List<Doctor> returnedListOfDoctors = doctorService.getAllDoctorsRegisterFromDatabase();
			
			return new ResponseEntity<List<Doctor>>(returnedListOfDoctors, HttpStatus.ACCEPTED);
			
			
		}else {
			
			throw new LoginException("Invalid key or please login first");
		}
	}
	

}























