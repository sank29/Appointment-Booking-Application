package com.sanket.service;


import java.util.List;

import com.sanket.entity.Doctor;
import com.sanket.entity.Patient;
import com.sanket.exception.DoctorException;

public interface AdminDoctorService {
	
	
	Doctor registerDoctor(Doctor doctor) throws DoctorException;

	Doctor revokePermissionOfDoctor(Doctor doctor) throws DoctorException; 
	
	Doctor grantPermissionOfDoctor(Doctor doctor) throws DoctorException;
 
	List<Doctor> getAllValidInValidDoctors(String key) throws DoctorException;

}
