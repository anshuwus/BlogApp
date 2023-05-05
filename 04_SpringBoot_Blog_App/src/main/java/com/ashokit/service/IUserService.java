package com.ashokit.service;

import com.ashokit.binding.LoginForm;
import com.ashokit.binding.RegistrationForm;

public interface IUserService {
	
	public boolean saveUserRegistration(RegistrationForm registrationFrm);
	public boolean login(LoginForm loginFrm);
	
}
