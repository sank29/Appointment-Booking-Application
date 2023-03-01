package com.sanket.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanket.entity.CurrentUserSession;
import com.sanket.entity.LoginDTO;
import com.sanket.entity.User;
import com.sanket.exception.LoginException;
import com.sanket.repository.SessionDao;
import com.sanket.repository.UserDao;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	UserDao userdao;
	
	@Autowired
	SessionDao sessionDao;

	@Override
	public String logIntoAccount(LoginDTO loginDTO) throws LoginException {
		
		User existingUser = userdao.findByMobileNo(loginDTO.getMobileNo());
		
		if(existingUser == null) {
			throw new LoginException("Please enter valid mobile number " + loginDTO.getMobileNo());
		}
		
		
		Optional<CurrentUserSession> validCustomerSessionOpt = sessionDao.findById(existingUser.getUserId());
		
		if(validCustomerSessionOpt.isPresent()) {
			
			throw new LoginException("User already login");
			
		}
		
		if(existingUser.getPassword().equals(loginDTO.getPassword())) {
			
			String key = generateRandomString();
			
			CurrentUserSession currentUserSession = new CurrentUserSession(existingUser.getUserId(), key, LocalDateTime.now());
			
			
			userdao.save(existingUser);
			
			sessionDao.save(currentUserSession);
			
			return "Login Successful as customer with this key "+ key;
		
		}else {
			
			throw new LoginException("Please enter valid password");
			
		}
	}

	@Override
	public String logoutFromAccount(String key) throws LoginException {
		
		CurrentUserSession currentSessionOptional = sessionDao.findByUuid(key);
		
		if(currentSessionOptional != null) {
			
			sessionDao.delete(currentSessionOptional);
			
			return "Logout successful";
			
		}else {
			
			throw new LoginException("Please enter valid key");
			
		}
	}
	
	@Override
	public Boolean checkUserLoginOrNot(String key) throws LoginException {
		
		CurrentUserSession currentUserSession = sessionDao.findByUuid(key);
		
		if(currentUserSession != null) {
			
			return true;
			
		}else {
			
			return false;
		}
		
		
		
	}
	
	public static String generateRandomString() {
		
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
	}

}
