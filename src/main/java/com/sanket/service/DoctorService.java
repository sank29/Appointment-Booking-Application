package com.sanket.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sanket.entity.Doctor;
import com.sanket.exception.DoctorException;
import com.sanket.exception.TimeDateException;


public interface DoctorService {
	
	public List<Doctor> getAllDoctorsRegisterFromDatabase() throws DoctorException;
	
	public List<LocalDateTime> getDoctorAvailableTimingForBooking(String key, Doctor doctor) throws IOException, TimeDateException, DoctorException;
	
}
