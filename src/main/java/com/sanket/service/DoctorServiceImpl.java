package com.sanket.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanket.entity.Appointment;
import com.sanket.entity.CurrentSession;
import com.sanket.entity.Doctor;
import com.sanket.entity.Patient;
import com.sanket.exception.DoctorException;
import com.sanket.exception.LoginException;
import com.sanket.exception.PatientException;
import com.sanket.exception.TimeDateException;
import com.sanket.repository.DoctorDao;
import com.sanket.repository.SessionDao;

@Service
public class DoctorServiceImpl implements DoctorService{
	
	@Autowired
	DoctorDao doctorDao;
	
	@Autowired
	SessionDao sessionDao;

	@Override
	public List<Doctor> getAllDoctorsRegisterFromDatabase() throws DoctorException {
		
		List<Doctor> listOfDoctors = doctorDao.findAll();
		
		if(!listOfDoctors.isEmpty()) {
			
			return listOfDoctors;
			
		}else {
			
			throw new DoctorException("No doctors register till now.");
		}
		
		
	}

	@Override
	public List<LocalDateTime> getDoctorAvailableTimingForBooking(String key, Doctor doctor) throws IOException, TimeDateException, DoctorException {
		
		Optional<Doctor> registerDoctor = doctorDao.findById(doctor.getDoctorId());
		
		List<LocalDateTime> doctorAvailableTiming = new ArrayList<>();
		
		if(registerDoctor.isPresent()) {
			
			PatientServiceImpl.getAppointmentDates(registerDoctor.get().getAppointmentFromTime(), registerDoctor.get().getAppointmentToTime());
			
		    Map<String, LocalDateTime> myTimeDate = PatientServiceImpl.myTimeDate;
		    
		    List<Appointment> listOfDoctorAppointment = registerDoctor.get().getListOfAppointments();
		    
		    
		    
		    
		    
		    for(String str: myTimeDate.keySet()) {
		    	
		    	Boolean flag = false;
		    	
		    	for(Appointment eachAppointment: listOfDoctorAppointment) {
		    		
		    		LocalDateTime localDateTime = myTimeDate.get(str);
		    		
		    		if(localDateTime.isEqual(eachAppointment.getAppointmentDateAndTime())) {
		    			
		    			flag = true;
		    			break;
		    			
		    		}
		    	}
		    	
		    	if(flag == false) {
		    		
		    		doctorAvailableTiming.add(myTimeDate.get(str));
		    		
		    	}
		    }
		    
			if(!doctorAvailableTiming.isEmpty()) {
				
				return doctorAvailableTiming;
				
			}else {
				
				throw new DoctorException("No time and date available to book appointment. Please try again");
			}
			
			
		}else {
			
			throw new DoctorException("No doctor found with this id " + doctor.getDoctorId());
		}
		
		
		
	}

	@Override
	public Doctor getDoctorByUuid(String uuid) throws PatientException {
		
		CurrentSession currentPatient = sessionDao.findByUuid(uuid);
		
		Optional<Doctor> patient = doctorDao.findById(currentPatient.getUserId());
		
		if(patient.isPresent()) {
			
			return patient.get();
		
		}else {
			
			throw new PatientException("Patient not present by this uuid " + uuid);
		}
	}
	
	@Override
	public CurrentSession getCurrentUserByUuid(String uuid) throws LoginException {
		
		CurrentSession currentUserSession = sessionDao.findByUuid(uuid);
		
		if(currentUserSession != null) {
			
			return currentUserSession;
			
		}else {
			
			throw new LoginException("Please enter valid key");
		}
	}

}
































