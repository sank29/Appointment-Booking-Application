package com.sanket.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanket.entity.CurrentPatientSession;
import com.sanket.entity.LoginDTO;
import com.sanket.entity.Patient;
import com.sanket.exception.LoginException;
import com.sanket.repository.SessionDao;
import com.sanket.repository.PatientDao;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	PatientDao userdao;
	
	@Autowired
	SessionDao sessionDao;

	@Override
	public String logIntoAccount(LoginDTO loginDTO) throws LoginException {
		
		Patient existingPatient = userdao.findByMobileNo(loginDTO.getMobileNo()); 
		
		if(existingPatient == null) {
			throw new LoginException("Please enter valid mobile number " + loginDTO.getMobileNo());
		}
		
		
		Optional<CurrentPatientSession> validPatientSessionOpt = sessionDao.findById(existingPatient.getUserId());
		
		if(validPatientSessionOpt.isPresent()) {
			
			throw new LoginException("User already login");
			
		}
		
		if(existingPatient.getPassword().equals(loginDTO.getPassword())) {
			
			String key = generateRandomString();
			
			CurrentPatientSession currentPatientSession = new CurrentPatientSession(existingPatient.getUserId(), key, LocalDateTime.now());
			
			
			userdao.save(existingPatient);
			
			sessionDao.save(currentPatientSession); 
			
			return "Login Successful as Patient with this key "+ key;
		
		}else {
			
			throw new LoginException("Please enter valid password");
			
		}
	}

	@Override
	public String logoutFromAccount(String key) throws LoginException {
		
		CurrentPatientSession currentPatientOptional = sessionDao.findByUuid(key);
		
		if(currentPatientOptional != null) {
			
			sessionDao.delete(currentPatientOptional); 
			
			return "Logout successful";
			
		}else {
			
			throw new LoginException("Please enter valid key");
			
		}
	}
	
	@Override
	public Boolean checkUserLoginOrNot(String key) throws LoginException {
		
		CurrentPatientSession currentPatientSession = sessionDao.findByUuid(key); 
		
		if(currentPatientSession != null) {
			
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
