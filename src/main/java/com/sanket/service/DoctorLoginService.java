package com.sanket.service;

import com.sanket.entity.LoginDTO;
import com.sanket.entity.LoginUUIDKey;
import com.sanket.exception.LoginException;

public interface DoctorLoginService {
	
	LoginUUIDKey logIntoAccount(LoginDTO loginDTO) throws LoginException;
	
	String logoutFromAccount(String key) throws LoginException;
	
	Boolean checkUserLoginOrNot(String key) throws LoginException;

}
