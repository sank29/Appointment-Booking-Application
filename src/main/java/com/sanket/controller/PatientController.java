package com.sanket.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.print.Doc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanket.entity.Appointment;
import com.sanket.entity.Doctor;
import com.sanket.entity.Patient;
import com.sanket.exception.AppointmentException;
import com.sanket.exception.DoctorException;
import com.sanket.exception.LoginException;
import com.sanket.exception.PatientException;
import com.sanket.exception.TimeDateException;
import com.sanket.service.DoctorService;
import com.sanket.service.PatientAndAdminLoginService;
import com.sanket.service.PatientService;

import jakarta.mail.MessagingException;


@RestController
public class PatientController {
	
	@Autowired
	PatientService patientService; 
	
	@Autowired
	PatientAndAdminLoginService loginService;
	
	@Autowired
	DoctorService doctorService;
	
	
	
	@PostMapping("/registerPatient")
	public ResponseEntity<Patient> saveCustomer(@RequestBody Patient patient) throws PatientException{
		
		Patient savedUser= patientService.createPatient(patient);
		
		return new ResponseEntity<Patient>(savedUser, HttpStatus.CREATED);
		
	}
	
	
	@PutMapping("/updatePatient")
	public ResponseEntity<Patient> updateCustomer(@RequestBody Patient patient, @RequestParam(required = false) String key) throws PatientException{
		
		Patient updateduser = patientService.updatePatient(patient, key);
		
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
	
	@PostMapping("/bookAppointment") 
	public ResponseEntity<Appointment> bookAppointment(@RequestParam String key, @RequestBody Appointment appointment) throws LoginException, AppointmentException, DoctorException, IOException, TimeDateException, MessagingException{
		
		if(appointment == null) {
			throw new AppointmentException("Please enter valid appointment");
		}
		
		if(loginService.checkUserLoginOrNot(key)) {
			
			Appointment registerAppointment = patientService.bookAppointment(key, appointment);
			
			return new ResponseEntity<Appointment>(registerAppointment, HttpStatus.CREATED);
			
		}else {
			
			throw new LoginException("Invalid key or please login first");
			
		}
		
	}
	
	@GetMapping("/allAppointment")
	public ResponseEntity<List<Appointment>> getAllAppointmenPatientWise(@RequestParam String key) throws AppointmentException, PatientException, LoginException{
		
		if(loginService.checkUserLoginOrNot(key)) {
			
			List<Appointment> listOfAppointments = patientService.getAllAppointmenPatientWise(key);
			
			return new ResponseEntity<List<Appointment>>(listOfAppointments, HttpStatus.ACCEPTED);
			
			
		}else {
			
			throw new LoginException("Invalid key or please login first");
			
		}
		
	}
	
	@PostMapping("/updateAppointment")
	public ResponseEntity<Appointment> updateAppointment(@RequestParam String key, @RequestBody Appointment newAppointment) throws LoginException, AppointmentException, PatientException, DoctorException, IOException, TimeDateException{
		
		if(loginService.checkUserLoginOrNot(key)) {
			
			Appointment updatedAppointment = patientService.updateAppointment(key, newAppointment); 
			
			
			return new ResponseEntity<Appointment>(updatedAppointment, HttpStatus.ACCEPTED);
			
			
		}else {
			
			throw new LoginException("Invalid key or please login first");
			
		}
		
	}
	
	@GetMapping("/availableTiming")
	public ResponseEntity<List<LocalDateTime>> getAvailbleTimingOfDoctor(@RequestParam String key, @RequestBody Doctor doctor) throws IOException, TimeDateException, LoginException, DoctorException{
		
		if(loginService.checkUserLoginOrNot(key)) {
			
			List<LocalDateTime> listOfAvailable = doctorService.getDoctorAvailableTimingForBooking(key, doctor);
			
			return new ResponseEntity<List<LocalDateTime>>(listOfAvailable, HttpStatus.ACCEPTED);
			
			 
		}else {
			
			throw new LoginException("Invalid key or please login first");
			
		}
	}
	
	@GetMapping("/getAllDoctors")
	public ResponseEntity<List<Doctor>> getAllDoctors(@RequestParam String key) throws LoginException, DoctorException{
		if(loginService.checkUserLoginOrNot(key)) {
			
			List<Doctor> listOfDoctors = patientService.getAllDoctors();
			
			return new ResponseEntity<List<Doctor>>(listOfDoctors, HttpStatus.ACCEPTED);
			
			 
		}else {
			
			throw new LoginException("Invalid key or please login first");
			
		}
	}
	
	@DeleteMapping("/appointment")
	public ResponseEntity<Appointment> deleteAppointment(@RequestParam String key, @RequestBody Appointment appointment) throws AppointmentException, DoctorException, Exception{
		
		if(loginService.checkUserLoginOrNot(key)) {
			
			Appointment listOfDoctors = patientService.deleteAppointment(appointment); 
			
			return new ResponseEntity<Appointment>(listOfDoctors, HttpStatus.ACCEPTED);
			
			 
		}else {
			
			throw new LoginException("Invalid key or please login first");
			
		}
	}
	

}


