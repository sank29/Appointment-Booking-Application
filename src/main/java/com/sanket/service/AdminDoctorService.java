package com.sanket.service;


import com.sanket.entity.Doctor;
import com.sanket.entity.Patient;
import com.sanket.exception.DoctorException;

public interface AdminDoctorService {
	
	
	Doctor registerDoctor(Doctor doctor) throws DoctorException;

	Doctor deleteDoctor(Doctor doctor) throws DoctorException; 

}
