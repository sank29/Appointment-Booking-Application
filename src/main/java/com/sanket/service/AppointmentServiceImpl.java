package com.sanket.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanket.entity.Doctor;
import com.sanket.exception.DoctorException;
import com.sanket.repository.DoctorDao;

@Service
public class AppointmentServiceImpl implements AppointmentService{
	
	@Autowired
	DoctorDao doctorDao;

	

	@Override
	public void refreshAppointment() {
		
		List<Doctor> listOfDoctors = doctorDao.findAll();
		
		LocalDateTime localDateTime = LocalDateTime.now();
		
		System.out.println(localDateTime);
		
		listOfDoctors.forEach(doctors -> {	
			
		});
	}

}
