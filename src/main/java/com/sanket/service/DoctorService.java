package com.sanket.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sanket.entity.CurrentSession;
import com.sanket.entity.Doctor;
import com.sanket.entity.Patient;
import com.sanket.exception.DoctorException;
import com.sanket.exception.LoginException;
import com.sanket.exception.PatientException;
import com.sanket.exception.TimeDateException;


public interface DoctorService {
	
	public List<Doctor> getAllDoctorsRegisterFromDatabase() throws DoctorException;
	
	public Doctor getDoctorByUuid(String uuid) throws PatientException;
	
	public CurrentSession getCurrentUserByUuid(String uuid) throws LoginException;
	
	public List<LocalDateTime> getDoctorAvailableTimingForBooking(String key, Doctor doctor) throws IOException, TimeDateException, DoctorException;
	
}
