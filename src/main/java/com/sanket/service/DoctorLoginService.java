package com.sanket.service;

import com.sanket.entity.LoginDTO;
import com.sanket.entity.LoginUUIDKey;
import com.sanket.exception.LoginException;

public interface DoctorLoginService {
	
	public LoginUUIDKey logIntoAccount(LoginDTO loginDTO) throws LoginException;
	
	public String logoutFromAccount(String key) throws LoginException;

}
