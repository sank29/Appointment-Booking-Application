package com.sanket.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanket.entity.CurrentUserSession;
import com.sanket.entity.User;
import com.sanket.exception.UserException;
import com.sanket.repository.SessionDao;
import com.sanket.repository.UserDao;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	SessionDao sessionDao;

	@Override
	public User createCustomer(User user) throws UserException {
		
		User databaseUser = userDao.findByMobileNo(user.getMobileNo()); 
		
		if(databaseUser == null) { 
			
			userDao.save(user);
			
			return user;
			
		
		}else {	
			
			throw new UserException("User already register with this mobile no " + user.getMobileNo());
			
		}
	}

	@Override
	public User updateCustomer(User user, String key) throws UserException {
		

		CurrentUserSession loggedInUser = sessionDao.findByUuid(key);
		
		if(loggedInUser == null) {
			
			throw new UserException("Please provide the valid key to update the user");
		}
		
		if(user.getUserId() == loggedInUser.getUserId()) {
			
			return userDao.save(user);
	
		}else {
			throw new UserException("Invalid user details. Login first");
		}
	}

	@Override
	public User getUserByUuid(String uuid) throws UserException {
		
		CurrentUserSession customer = sessionDao.findByUuid(uuid);
		
		Optional<User> customers = userDao.findById(customer.getUserId());
		
		if(customers.isPresent()) {
			
			return customers.get();
		
		}else {
			
			throw new UserException("Customer not present by this uuid " + uuid);
		}
	}

}







