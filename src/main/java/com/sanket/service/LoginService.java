package com.sanket.service;

import com.sanket.entity.LoginDTO;
import com.sanket.exception.LoginException;

public interface LoginService {
	
public String logIntoAccount(LoginDTO loginDTO) throws LoginException;
	
	public String logoutFromAccount(String key) throws LoginException;
	
	public Boolean checkUserLoginOrNot(String key) throws LoginException;

}
