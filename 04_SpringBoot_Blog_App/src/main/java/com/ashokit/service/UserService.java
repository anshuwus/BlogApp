package com.ashokit.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.binding.LoginForm;
import com.ashokit.binding.RegistrationForm;
import com.ashokit.entity.User;
import com.ashokit.repository.IUserRepo;

@Service
public class UserService implements IUserService {
	@Autowired
	private IUserRepo userRepo;
	@Autowired
	private HttpSession session;

	@Override
	public boolean saveUserRegistration(RegistrationForm registrationFrm) {
		boolean status=false;
		User user = userRepo.findByEmail(registrationFrm.getEmail());
		if(user!=null) {
			return status;
		}
		User entity=new User();
		BeanUtils.copyProperties(registrationFrm, entity);
		if(entity!=null) {
			userRepo.save(entity);
			status=true;
		}
		return status;
	}

	@Override
	public boolean login(LoginForm loginFrm) {
		boolean status=false;
		User entity = userRepo.findByEmail(loginFrm.getEmail());
		System.out.println("UserService.login()"+entity);
		if(entity!=null) {
			if(loginFrm.getPwd().equals(entity.getPwd())) {
				status=true;
				//create session and store user id in session object
				session.setAttribute("userId", entity.getUserId());
			}
		}
		return status;
	}
}
//ashokit.classes@gmail.com
