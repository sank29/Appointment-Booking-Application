package com.sanket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanket.entity.CurrentUserSession;


public interface SessionDao extends JpaRepository<CurrentUserSession, Integer> {
	
	public CurrentUserSession findByUuid(String uuid);
}
