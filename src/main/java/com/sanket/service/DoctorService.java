package com.sanket.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sanket.entity.Doctor;
import com.sanket.exception.DoctorException;


public interface DoctorService {
	
	public List<Doctor> getAllDoctorsRegisterFromDatabase() throws DoctorException;
}
