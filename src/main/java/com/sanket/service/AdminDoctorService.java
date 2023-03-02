package com.sanket.service;

import com.sanket.entity.Doctor;
import com.sanket.exception.DoctorException;

public interface AdminDoctorService {
	
	public Doctor registerDoctor(Doctor doctor) throws DoctorException;

}
