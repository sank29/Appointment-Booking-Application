package com.sanket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanket.entity.Doctor;
import com.sanket.entity.Patient;
import com.sanket.exception.DoctorException;
import com.sanket.exception.PatientException;
import com.sanket.repository.DoctorDao;

@Service
public class AdminDoctorServiceImpl implements AdminDoctorService {
	
	@Autowired
	DoctorDao doctorDao;

	@Override
	public Doctor registerDoctor(Doctor doctor) throws DoctorException {
		
		Doctor databaseDoctor = doctorDao.findByMobileNo(doctor.getMobileNo());
		
		if(databaseDoctor == null) {
			
			doctor.setType("Doctor"); 
			
			doctor.setPassword(PatientServiceImpl.bCryptPasswordEncoder.encode(doctor.getPassword()));
			
			return doctorDao.save(doctor);
			
		}else {
			
			throw new DoctorException("Doctor already register with is mobile no. " + doctor.getMobileNo());
		}
		
		
	}

}
