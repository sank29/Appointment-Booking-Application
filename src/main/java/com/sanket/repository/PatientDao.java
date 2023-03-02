package com.sanket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanket.entity.Patient;



public interface PatientDao extends JpaRepository<Patient, Integer> {
	
	public Patient findByMobileNo(String mobileNo);
}
