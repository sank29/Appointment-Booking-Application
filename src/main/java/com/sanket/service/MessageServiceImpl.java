package com.sanket.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanket.entity.CurrentSession;
import com.sanket.entity.Doctor;
import com.sanket.entity.Message;
import com.sanket.entity.Patient;
import com.sanket.exception.DoctorException;
import com.sanket.exception.PatientException;
import com.sanket.repository.DoctorDao;
import com.sanket.repository.MessageDao;
import com.sanket.repository.PatientDao;
import com.sanket.repository.SessionDao;

@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	SessionDao sessionDao;
	
	@Autowired
	PatientDao patientDao;
	
	@Autowired
	DoctorDao doctorDao;
	
	@Autowired
	MessageDao messageDao;

	@Override
	public Message sendMessageFromPatientToDoctor(String key, Message message) throws PatientException, DoctorException {
		
		CurrentSession currentPatientSession = sessionDao.findByUuid(key); 
		
		Optional<Patient> registerPatient = patientDao.findById(currentPatientSession.getUserId());
		
		// check doctor is present in database or not
		
		Optional<Doctor> registerDoctor = doctorDao.findById(message.getDoctor().getDoctorId());
		
		if(registerDoctor.isEmpty()) {
			
			throw new DoctorException("Doctor not found in database");
			
		}
		
		if(registerPatient.isPresent()) {
			
			// set patient and doctor in message
			
			message.setPatient(registerPatient.get());
			message.setDoctor(registerDoctor.get());
			
			// set sender and receiver id in message entity
			
			message.setSender(registerPatient.get().getPatientId());
			message.setReceiver(message.getDoctor().getDoctorId());
			
			
			
			// set time and date
			
			message.setMessageTimeAndDate(LocalDateTime.now());
			

			
			
			
//			// save message and return
			
			Message registerMessage = messageDao.save(message);
			
			// set message in patinent and doctor
			
			registerDoctor.get().getListOfMessage().add(registerMessage);
			doctorDao.save(registerDoctor.get());
			
			
			
			registerPatient.get().getListOfMessage().add(registerMessage);
			patientDao.save(registerPatient.get());
			
			System.out.println("****");
			
			return registerMessage;
			
			
		}else {
			
			throw new PatientException("Please enter valid patient details");
		}
		
	}

}
