package com.sanket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanket.entity.Doctor;
import com.sanket.exception.DoctorException;
import com.sanket.repository.DoctorDao;

@Service
public class DoctorServiceImpl implements DoctorService{
	
	@Autowired
	DoctorDao doctorDao;

	@Override
	public List<Doctor> getAllDoctorsRegisterFromDatabase() throws DoctorException {
		
		List<Doctor> listOfDoctors = doctorDao.findAll();
		
		if(!listOfDoctors.isEmpty()) {
			
			return listOfDoctors;
			
		}else {
			
			throw new DoctorException("No doctors register till now.");
		}
		
		
	}

}
