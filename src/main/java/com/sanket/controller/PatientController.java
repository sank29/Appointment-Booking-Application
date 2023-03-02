package com.sanket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanket.entity.Patient;
import com.sanket.exception.PatientException;
import com.sanket.service.PatientService;


@RestController
public class PatientController {
	
	@Autowired
	PatientService userService;
	
	@PostMapping("/registerPatient")
	public ResponseEntity<Patient> saveCustomer(@RequestBody Patient patient) throws PatientException{
		
		Patient savedUser= userService.createPatient(patient);
		
		return new ResponseEntity<Patient>(savedUser, HttpStatus.CREATED);
		
	}
	
	
	@PutMapping("/updateCustomer")
	public ResponseEntity<Patient> updateCustomer(@RequestBody Patient patient, @RequestParam(required = false) String key) throws PatientException{
		
		Patient updateduser = userService.updatePatient(patient, key);
		
		return new ResponseEntity<Patient>(updateduser,HttpStatus.OK);
		
	}

}
