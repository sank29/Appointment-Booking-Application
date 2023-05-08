package com.sanket.service;

import com.sanket.entity.Message;
import com.sanket.exception.DoctorException;
import com.sanket.exception.PatientException;

public interface MessageService {
	
	Message sendMessageFromPatientToDoctor(String key, Message message) throws PatientException, DoctorException;

	Message sendMessageFromDoctorToPatient(String key, Message message) throws PatientException, DoctorException;

}
