package com.sanket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanket.entity.Doctor;
import com.sanket.entity.Patient;

public interface DoctorDao extends JpaRepository<Doctor, Integer> {
	
	public Doctor findByMobileNo(String mobileNo);
}
