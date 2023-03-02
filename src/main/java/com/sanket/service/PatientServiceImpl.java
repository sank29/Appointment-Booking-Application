package com.sanket.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanket.entity.CurrentPatientSession;
import com.sanket.entity.Patient;
import com.sanket.exception.PatientException;
import com.sanket.repository.SessionDao;
import com.sanket.repository.PatientDao;

@Service
public class PatientServiceImpl implements PatientService {
	
	@Autowired
	PatientDao userDao;
	
	@Autowired
	SessionDao sessionDao;

	@Override
	public Patient createPatient(Patient patient) throws PatientException {
		
		Patient databaseUser = userDao.findByMobileNo(patient.getMobileNo()); 
		
		if(databaseUser == null) { 
			
			userDao.save(patient);
			
			return patient; 
			
		
		}else {	
			
			throw new PatientException("Patient already register with this mobile no " + patient.getMobileNo());
			
		}
	}

	@Override
	public Patient updatePatient(Patient user, String key) throws PatientException {
		

		CurrentPatientSession loggedInUser = sessionDao.findByUuid(key);
		
		if(loggedInUser == null) {
			
			throw new PatientException("Please provide the valid key to update the user");
		}
		
		if(user.getUserId() == loggedInUser.getUserId()) {
			
			return userDao.save(user);
	
		}else {
			throw new PatientException("Invalid user details. Login first");
		}
	}

	@Override
	public Patient getPatientByUuid(String uuid) throws PatientException {
		
		CurrentPatientSession currentPatient = sessionDao.findByUuid(uuid);
		
		Optional<Patient> patient = userDao.findById(currentPatient.getUserId());
		
		if(patient.isPresent()) {
			
			return patient.get();
		
		}else {
			
			throw new PatientException("Customer not present by this uuid " + uuid);
		}
	}

}







