package com.sanket.service;

import java.util.List;

import com.sanket.entity.Doctor;
import com.sanket.entity.Message;
import com.sanket.exception.DoctorException;
import com.sanket.exception.PatientException;

public interface MessageService {
	
	Message sendMessageFromPatientToDoctor(String key, Message message) throws PatientException, DoctorException;

	Message sendMessageFromDoctorToPatient(String key, Message message) throws PatientException, DoctorException;

	List<Message> getMessageOfPatientParticularDoctor(String key, Doctor doctor) throws DoctorException, PatientException;

}
