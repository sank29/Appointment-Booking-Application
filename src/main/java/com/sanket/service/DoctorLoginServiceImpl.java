package com.sanket.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanket.entity.CurrentPatientSession;
import com.sanket.entity.Doctor;
import com.sanket.entity.LoginDTO;
import com.sanket.entity.LoginUUIDKey;
import com.sanket.exception.LoginException;
import com.sanket.repository.DoctorDao;
import com.sanket.repository.SessionDao;

@Service
public class DoctorLoginServiceImpl implements DoctorLoginService {
	
	@Autowired
	DoctorDao doctorDao;
	
	@Autowired
	SessionDao sessionDao;

	@Override
	public LoginUUIDKey logIntoAccount(LoginDTO loginDTO) throws LoginException {
		
		LoginUUIDKey loginUUIDKey = new LoginUUIDKey();
		
		Doctor existingDoctor = doctorDao.findByMobileNo(loginDTO.getMobileNo());
		
		if(existingDoctor == null) {
			throw new LoginException("Please enter valid mobile number " + loginDTO.getMobileNo());
		}
		
		
		Optional<CurrentPatientSession> validCustomerSessionOpt = sessionDao.findById(existingDoctor.getDoctorId());
		
		if(validCustomerSessionOpt.isPresent()) {
			
			throw new LoginException("Doctor already login");
			
		}
		
		if(existingDoctor.getPassword().equals(loginDTO.getPassword())) {
			
			String key = generateRandomString();
			
			CurrentPatientSession currentPatientSession = new CurrentPatientSession(existingDoctor.getDoctorId(), key, LocalDateTime.now()); 
			
			
				
			existingDoctor.setType("doctor");
			currentPatientSession.setUserId(existingDoctor.getDoctorId());
			currentPatientSession.setUserType("doctor");
					
			doctorDao.save(existingDoctor);
			
			sessionDao.save(currentPatientSession);
			
			loginUUIDKey.setMsg("Login Successful as doctor with this key");
			
			loginUUIDKey.setUuid(key);
			
			return loginUUIDKey;
		
		}else {
			
			throw new LoginException("Please enter valid password");
			
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

	@Override
	public String logoutFromAccount(String key) throws LoginException {
		
		
		
		CurrentPatientSession currentDoctorOptional = sessionDao.findByUuid(key);
		
		if(currentDoctorOptional != null) {
			
			sessionDao.delete(currentDoctorOptional); 
			
			return "Logout successful";
			
		}else {
			
			throw new LoginException("Please enter valid key");
			
		}
	}


}
