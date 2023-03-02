package com.sanket.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

public class GlobalExceptionHandler {
	
	@ExceptionHandler(PatientException.class)
	public ResponseEntity<MyErrorDetails> userExceptiionHandler(PatientException patientException, WebRequest webRequest){
		
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		
		myErrorDetails.setDetails(webRequest.getDescription(false));
		myErrorDetails.setErrorMsg(patientException.getMessage());
		myErrorDetails.setLocalDateTime(LocalDateTime.now());
		
		return new ResponseEntity<MyErrorDetails>(myErrorDetails,HttpStatus.BAD_REQUEST);
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
