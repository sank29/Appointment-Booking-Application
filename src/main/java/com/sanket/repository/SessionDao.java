package com.sanket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanket.entity.CurrentSession;


public interface SessionDao extends JpaRepository<CurrentSession, Integer> {
	
	public CurrentSession findByUuid(String uuid);
	
}
