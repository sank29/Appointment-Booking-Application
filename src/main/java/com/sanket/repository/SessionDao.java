package com.sanket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanket.entity.CurrentPatientSession;


public interface SessionDao extends JpaRepository<CurrentPatientSession, Integer> {
	
	public CurrentPatientSession findByUuid(String uuid);
	
}
