package com.sanket.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanket.entity.Appointment;
import com.sanket.entity.CurrentPatientSession;
import com.sanket.entity.Doctor;
import com.sanket.entity.Patient;
import com.sanket.exception.AppointmentException;
import com.sanket.exception.DoctorException;
import com.sanket.exception.LoginException;
import com.sanket.exception.PatientException;
import com.sanket.repository.SessionDao;
import com.sanket.repository.AppointmentDao;
import com.sanket.repository.DoctorDao;
import com.sanket.repository.PatientDao;

@Service
public class PatientServiceImpl implements PatientService {
	
	@Autowired
	PatientDao userDao;
	
	@Autowired
	SessionDao sessionDao;
	
	@Autowired
	PatientDao patientDao;
	
	@Autowired
	AppointmentDao appointmentDao;
	
	@Autowired
	DoctorDao doctorDao;

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
		
		if(user.getPatientId() == loggedInUser.getUserId()) {
			
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

	@Override
	public CurrentPatientSession getCurrentUserByUuid(String uuid) throws LoginException {
		
		CurrentPatientSession currentUserSession = sessionDao.findByUuid(uuid);
		
		if(currentUserSession != null) {
			
			return currentUserSession;
			
		}else {
			
			throw new LoginException("Please enter valid key");
		}
	}

	@Override
	public Appointment bookAppointment(String key, Appointment appointment) throws AppointmentException, LoginException, DoctorException {
		
		CurrentPatientSession currentPatientSession = sessionDao.findByUuid(key); 
		
		Optional<Patient> patient = patientDao.findById(currentPatientSession.getUserId());
		
		if(patient.isPresent()) {
			
			appointment.setPatient(patient.get());
			
			Doctor doctor = appointment.getDoctor();
			
			System.out.println("********" + doctor);
					
			Optional<Doctor> registerDoctors = doctorDao.findById(doctor.getDoctorId());
			
			if(!registerDoctors.isEmpty()) {
				
				// mapping appointment in doctor and then saving doctor
				
				appointment.setDoctor(registerDoctors.get());
				
				registerDoctors.get().getListOfAppointments().add(appointment);
				
				doctorDao.save(registerDoctors.get());
				
				// mapping appointment in patient then saving patient
				
				patient.get().getListOfAppointments().add(appointment);
				
				patientDao.save(patient.get());
				
				return appointmentDao.save(appointment);
				
				
			}else {
				 
				throw new DoctorException("Please enter valid doctors details or doctor not present with thid id " + doctor.getDoctorId());
				
			}
			
			
			
		}else {
			
			throw new LoginException("Please enter valid key"); 
			
		}
	}

}









































