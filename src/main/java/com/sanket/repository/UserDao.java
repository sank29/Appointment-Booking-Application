package com.sanket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanket.entity.User;



public interface UserDao extends JpaRepository<User, Integer> {
	
	public User findByMobileNo(String mobileNo);
}
