package com.ashokit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ashokit.binding.LoginForm;
import com.ashokit.binding.RegistrationForm;
import com.ashokit.service.IUserService;

@Controller
public class UserController {
	@Autowired
	private IUserService userService;
	
	@GetMapping("/registration")
	public String userRegistration(@ModelAttribute("registrationFrm")RegistrationForm registrationFrm) {
		System.out.println("UserController.userRegistration()");
		return "registrationPage";
	}
	
	@PostMapping("/registration")
	public String userRegistrationProcess(@ModelAttribute("registrationFrm")RegistrationForm registrationFrm,RedirectAttributes attr) {
		System.out.println("UserController.userRegistrationProcess()"+registrationFrm);
	
		boolean status = userService.saveUserRegistration(registrationFrm);
		if(status)
			attr.addFlashAttribute("succMsg","User Registered successfully");
		else
			attr.addFlashAttribute("errMsg", "Email is already registered, Please enter unique email");
		return "redirect:registration";
	}
	
	@GetMapping("/login")
	public String userLogin(@ModelAttribute("loginFrm")LoginForm loginFrm) {	
		return "loginPage";
	}
	
	@PostMapping("/login")
	public String userLoginProcess(@ModelAttribute("loginFrm")LoginForm loginFrm,RedirectAttributes attr) {
		System.out.println("UserController.userLoginProcess()"+loginFrm);
		if(loginFrm!=null) {
			boolean status = userService.login(loginFrm);
			if(status) {
				//redirect to dashboard
				return "redirect:dashboard";
			}
			else
				attr.addFlashAttribute("errMsg", "Invalid Credentials");
		}
		return "redirect:login";
	}
	
	
	
}
