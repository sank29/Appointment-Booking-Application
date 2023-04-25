package com.sanket.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.print.Doc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.sanket.entity.Review;
import com.sanket.exception.AppointmentException;
import com.sanket.exception.DoctorException;
import com.sanket.exception.LoginException;
import com.sanket.exception.PatientException;
import com.sanket.exception.ReviewException;
import com.sanket.exception.TimeDateException;
import com.sanket.service.DoctorService;
import com.sanket.service.PatientAndAdminLoginService;
import com.sanket.service.PatientService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;


@RestController
public class PatientController {
	
	@Autowired
	PatientService patientService; 
	
	@Autowired
	PatientAndAdminLoginService loginService;
	
	@Autowired
	DoctorService doctorService;
	
	
	@CrossOrigin
	@PostMapping("/registerPatient")
	public ResponseEntity<Patient> saveCustomer(@Valid @RequestBody Patient patient) throws PatientException{
		
		Patient savedUser= patientService.createPatient(patient);
		
		return new ResponseEntity<Patient>(savedUser, HttpStatus.CREATED);
		
	}
	
	
	@PutMapping("/updatePatient")
	public ResponseEntity<Patient> updateCustomer(@RequestBody Patient patient, @RequestParam(required = false) String key) throws PatientException{
		
		Patient updateduser = patientService.updatePatient(patient, key);
		
		return new ResponseEntity<Patient>(updateduser,HttpStatus.OK);
		
	}
	
	@GetMapping("/patientDetails")
	@CrossOrigin
	public ResponseEntity<Patient> getPatientDetails(@RequestParam String key) throws LoginException, PatientException{
		
		if(loginService.checkUserLoginOrNot(key)) {
			
			Patient returnedPatient = patientService.getPatientDetails(key);
			
			return new ResponseEntity<Patient>(returnedPatient, HttpStatus.ACCEPTED);
			
			
		}else {
			
			throw new LoginException("Invalid key or please login first");
			
		}
		
	}
	
	
	@GetMapping("/doctors")
	@CrossOrigin
	public ResponseEntity<List<Doctor>> getAllDoctorsFromDataBase(@RequestParam String key) throws LoginException, DoctorException{
		
		if(loginService.checkUserLoginOrNot(key)) {
			
			List<Doctor> returnedListOfDoctors = doctorService.getAllDoctorsRegisterFromDatabase();
			
			return new ResponseEntity<List<Doctor>>(returnedListOfDoctors, HttpStatus.ACCEPTED);
			
			
		}else {
			
			throw new LoginException("Invalid key or please login first");
			
		}
	}
	
	@PostMapping("/bookAppointment") 
	@CrossOrigin
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
	@CrossOrigin
	public ResponseEntity<List<Appointment>> getAllAppointmenPatientWise(@RequestParam String key) throws AppointmentException, PatientException, LoginException{
		
		if(loginService.checkUserLoginOrNot(key)) {
			
			List<Appointment> listOfAppointments = patientService.getAllAppointmenPatientWise(key); 
			
			return new ResponseEntity<List<Appointment>>(listOfAppointments, HttpStatus.ACCEPTED);
			
			
		}else {
			
			throw new LoginException("Invalid key or please login first");
			
		}
		
	}
	
	@PutMapping("/updateAppointment")
	@CrossOrigin
	public ResponseEntity<Appointment> updateAppointment(@RequestParam String key, @RequestBody Appointment newAppointment) throws LoginException, AppointmentException, PatientException, DoctorException, IOException, TimeDateException{
		
		if(loginService.checkUserLoginOrNot(key)) {
			
			Appointment updatedAppointment = patientService.updateAppointment(key, newAppointment); 
			
			
			return new ResponseEntity<Appointment>(updatedAppointment, HttpStatus.ACCEPTED);
			
			
		}else {
			
			throw new LoginException("Invalid key or please login first");
			
		}
		
	}
	
	@PostMapping("/availableTiming")
	@CrossOrigin
	public ResponseEntity<List<LocalDateTime>> getAvailbleTimingOfDoctor(@RequestParam String key, @RequestBody Doctor doctor) throws IOException, TimeDateException, LoginException, DoctorException{
		
		if(loginService.checkUserLoginOrNot(key)) {
			
			List<LocalDateTime> listOfAvailable = doctorService.getDoctorAvailableTimingForBooking(key, doctor);
			
			return new ResponseEntity<List<LocalDateTime>>(listOfAvailable, HttpStatus.ACCEPTED);
			
			 
		}else {
			
			throw new LoginException("Invalid key or please login first");
			
		}
	}
	
	@GetMapping("/getAllDoctors")
	@CrossOrigin
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
	
	@PostMapping("/review")
	public ResponseEntity<Review> makeReviewToDoctorAppointment(@RequestParam String key, @RequestBody Review review) throws LoginException, AppointmentException, DoctorException, ReviewException{
		
		if(loginService.checkUserLoginOrNot(key)) {
			
			Review returnReiew = patientService.makeReviewToDoctorAppointment(key, review);  
			
			return new ResponseEntity<Review>(returnReiew, HttpStatus.ACCEPTED);
			
			 
		}else {
			
			throw new LoginException("Invalid key or please login first");
			
		}
	}
	
	@CrossOrigin
	@GetMapping("/rating")
	public ResponseEntity<Float> getDoctorRating(@RequestParam String key, @RequestBody Doctor doctor) throws LoginException, DoctorException, ReviewException{
		
		if(loginService.checkUserLoginOrNot(key)) {
			
			Float rating = patientService.getDoctorRating(key, doctor);  
			
			
			return new ResponseEntity<Float>(rating, HttpStatus.ACCEPTED);
			
			 
		}else {
			
			throw new LoginException("Invalid key or please login first");
			
		}
	}
	

}


