package com.sanket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanket.entity.Doctor;
import com.sanket.exception.DoctorException;
import com.sanket.repository.DoctorDao;

@Service
public class AdminDoctorServiceImpl implements AdminDoctorService {
	
	@Autowired
	DoctorDao doctorDao;

	@Override
	public Doctor registerDoctor(Doctor doctor) throws DoctorException {
		
		if(doctor != null) {
			
			return doctorDao.save(doctor);
			
		}else {
			
			throw new DoctorException("Please enter valid doctor exception");
		}
		
		
	}

}
