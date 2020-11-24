package com.sneha.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sneha.model.UserDto;
import com.sneha.service.InvisionUserDetailsService;
import com.sneha.service.EmailService;
@RestController
@CrossOrigin()
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	private InvisionUserDetailsService userDetailsService;
	@Autowired
	private EmailService emailService;

    @RequestMapping(value = "/addAdmin", method = RequestMethod.POST)
	public ResponseEntity<?> saveAdmin(@RequestBody UserDto user) throws Exception {
    	
    	String password =  emailService.genaratePassword(6);
    	user.setPassword(password);
    	user.setRole("admin");
    	userDetailsService.save(user);
    	
    	String message = "Hi "+user.getName()+",\n\nClick on the following link: http://localhost:8080/loginpage \n\nYour Login credentials are:\nusername: "+ user.getUsername()+"\npassword: "+ user.getPassword();
    	
    	emailService.sendMail(user.getUsername(), "Test Subject",message);

		return ResponseEntity.ok("email sent successfully!!!");
	}
    
    @RequestMapping(value = "/addSuperAdmin", method = RequestMethod.POST)
	public ResponseEntity<?> saveSuperAdmin(@RequestBody UserDto user) throws Exception {
    	
    	String password = emailService.genaratePassword(6);
    	user.setPassword(password);
    	user.setRole("superAdmin");
    	userDetailsService.save(user);
    	
    	String message = "Hi "+user.getName()+",\nYour Login credentials are:\nusername: "+ user.getUsername()+"\npassword: "+ user.getPassword();
    	
    	emailService.sendMail(user.getUsername(), "Test Subject",message);

		return ResponseEntity.ok("email sent successfully!!!");
	}
    
    @RequestMapping(value = "/removeAdmin", method = RequestMethod.POST)
	public ResponseEntity<?> deleteAdmin(@RequestBody UserDto user) throws Exception {    	
    	
    	userDetailsService.delete(user);    	

		return ResponseEntity.ok("deleted successfully!!!");
	}
    
    @RequestMapping(value = "/changePassword", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestParam String employeeCode, String password) throws Exception {    	
    	
    	userDetailsService.update(employeeCode,password);    	

		return ResponseEntity.ok("updated successfully!!!");
	}

    
}