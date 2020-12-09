package com.sneha.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sneha.model.JwtRequest;
import com.sneha.model.RoleEnum;
import com.sneha.model.UserDao;
import com.sneha.model.UserRoles;
import com.sneha.repository.UserRepository;
import com.sneha.repository.UserRoleRepository;
import com.sneha.service.AppUserDetailsService;
import com.sneha.service.EmailService;

@RestController
@RequestMapping(value="/user")
public class UserController {
	@Autowired
	private AppUserDetailsService userDetailsService;
	
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<String> addUser(@RequestBody JwtRequest request) throws Exception {
		return userDetailsService.save(request);
	
	}
	 
	 
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ResponseEntity<?> deleteAdmin(@RequestBody UserDao user) throws Exception {    	
	    	
	    	userDetailsService.delete(user);    	

			return ResponseEntity.ok("deleted successfully!!!");
	}
 
   @RequestMapping(value = "/changePassword", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestParam String employeeCode, String password) throws Exception {       	
    	
		return userDetailsService.update(employeeCode,password);  
	}


}
