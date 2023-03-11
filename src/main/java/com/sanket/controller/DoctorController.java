package com.sanket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanket.entity.Appointment;
import com.sanket.entity.CurrentSession;
import com.sanket.entity.Doctor;
import com.sanket.exception.AppointmentException;
import com.sanket.exception.DoctorException;
import com.sanket.exception.LoginException;
import com.sanket.exception.PatientException;
import com.sanket.service.DoctorLoginService;
import com.sanket.service.DoctorLoginServiceImpl;
import com.sanket.service.DoctorService;


@RestController
public class DoctorController {
	
	@Autowired
	DoctorLoginService doctorLoginService;
	
	@Autowired
	DoctorService doctorService;
	
	
	
	@GetMapping("/upcomingAppointments")
	public ResponseEntity<List<Appointment>> getUpcomingAppointments(@RequestParam String key) throws LoginException, PatientException, DoctorException, AppointmentException{ 
		
		if(doctorLoginService.checkUserLoginOrNot(key)) {
			
			CurrentSession currentUserSession = doctorService.getCurrentUserByUuid(key);
			
			Doctor registerDoctor = doctorService.getDoctorByUuid(key);
			
			if(!currentUserSession.getUserType().equals("doctor")) { 
				
				throw new LoginException("Please login as doctor");
				
			}
			
			if(registerDoctor != null) {
				
				List<Appointment> listOfUpCommingAppointment = doctorService.getUpcommingDoctorAppointment(registerDoctor);
				
				return new ResponseEntity<List<Appointment>>(listOfUpCommingAppointment, HttpStatus.ACCEPTED);
				
				
			}else {
				
				throw new DoctorException("Please enter valid doctor details");
				
			}
		
		}else {
			
			throw new LoginException("Please enter valid key");
			
		}
		
	}

}
