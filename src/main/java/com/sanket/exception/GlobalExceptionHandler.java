package com.sanket.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(PatientException.class)
	public ResponseEntity<MyErrorDetails> patientExceptiionHandler(PatientException patientException, WebRequest webRequest){
		
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		
		myErrorDetails.setDetails(webRequest.getDescription(false));
		myErrorDetails.setErrorMsg(patientException.getMessage());
		myErrorDetails.setLocalDateTime(LocalDateTime.now());
		
		return new ResponseEntity<MyErrorDetails>(myErrorDetails,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(LoginException.class)
	public ResponseEntity<MyErrorDetails> loginExceptiionHandler(LoginException loginException, WebRequest webRequest){
		
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		
		myErrorDetails.setDetails(webRequest.getDescription(false));
		myErrorDetails.setErrorMsg(loginException.getMessage());
		myErrorDetails.setLocalDateTime(LocalDateTime.now());
		
		return new ResponseEntity<MyErrorDetails>(myErrorDetails,HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(AppointmentException.class)
	public ResponseEntity<MyErrorDetails> AppointmentExceptiionHandler(AppointmentException appointmentException, WebRequest webRequest){
		
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		
		myErrorDetails.setDetails(webRequest.getDescription(false));
		myErrorDetails.setErrorMsg(appointmentException.getMessage());
		myErrorDetails.setLocalDateTime(LocalDateTime.now());
		
		return new ResponseEntity<MyErrorDetails>(myErrorDetails,HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(DoctorException.class)
	public ResponseEntity<MyErrorDetails> doctorExceptiionHandler(DoctorException doctorException, WebRequest webRequest){
		
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		
		myErrorDetails.setDetails(webRequest.getDescription(false));
		myErrorDetails.setErrorMsg(doctorException.getMessage());
		myErrorDetails.setLocalDateTime(LocalDateTime.now());
		
		return new ResponseEntity<MyErrorDetails>(myErrorDetails,HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(TimeDateException.class)
	public ResponseEntity<MyErrorDetails> timeDateExceptiionHandler(TimeDateException timeDateException, WebRequest webRequest){
		
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		
		myErrorDetails.setDetails(webRequest.getDescription(false));
		myErrorDetails.setErrorMsg(timeDateException.getMessage());
		myErrorDetails.setLocalDateTime(LocalDateTime.now());
		
		return new ResponseEntity<MyErrorDetails>(myErrorDetails,HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MyErrorDetails> constraintExceptionHandler(MethodArgumentNotValidException constraintViolationException, WebRequest webRequest){
		
		List<String> details = new ArrayList<>();
		
		for(ObjectError error : constraintViolationException.getBindingResult().getAllErrors()) {
			
		      details.add(error.getDefaultMessage());
		      
		}
		
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		
		myErrorDetails.setDetails(webRequest.getDescription(false));
		
		
		myErrorDetails.setErrorMsg(details.get(0));
		
		
		myErrorDetails.setLocalDateTime(LocalDateTime.now());
		
		return new ResponseEntity<MyErrorDetails>(myErrorDetails,HttpStatus.UNAUTHORIZED);
		
	}
	
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetails> handleAllException(Exception exception, WebRequest webRequest) {
		
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		
		myErrorDetails.setDetails(webRequest.getDescription(false));
		myErrorDetails.setErrorMsg(exception.getMessage());
		myErrorDetails.setLocalDateTime(LocalDateTime.now());
		
		return new ResponseEntity<MyErrorDetails>(myErrorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

}
