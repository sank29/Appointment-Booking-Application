package com.sanket.service;



import com.sanket.entity.User;
import com.sanket.exception.UserException;


public interface UserService {
	
	public User createCustomer(User customer) throws UserException;
	
	public User updateCustomer(User user, String key) throws UserException;
	
	public User getUserByUuid(String uuid) throws UserException;
	
}
