package com.sanket.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanket.entity.Message;
import com.sanket.exception.DoctorException;
import com.sanket.exception.LoginException;
import com.sanket.exception.PatientException;
import com.sanket.service.MessageService;
import com.sanket.service.PatientAndAdminLoginService;

@RestController
@RequestMapping("/message")
public class MessageController {
	
	@Autowired
	PatientAndAdminLoginService loginService;
	
	@Autowired
	MessageService messageService;
	
	@PostMapping("/patientToDoctor")
	public ResponseEntity<Message> sendMessageFromPatientToDoctor(@RequestParam String key, @RequestBody Message message) throws LoginException, PatientException, DoctorException{
		
		if(loginService.checkUserLoginOrNot(key)) {
			
			Message sendMessage = messageService.sendMessageFromPatientToDoctor(key, message);
			
			return new ResponseEntity<Message>(sendMessage, HttpStatus.OK);
			
			
		}else {
			
			throw new LoginException("Invalid key or please login first"); 
			
		}
		
		
	}

}
