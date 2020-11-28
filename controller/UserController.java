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
	private UserRoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AppUserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private EmailService emailService;
	
	 @RequestMapping(value = "/add", method = RequestMethod.POST)
		public ResponseEntity<?> addUser(@RequestBody JwtRequest request) throws Exception {
			
		 String password =  emailService.genaratePassword(6);
	     UserDao user = new UserDao(request.getEmployeeCode(),request.getName(),request.getUsername(),bcryptEncoder.encode(password));    
	     Set<String> strRoles = request.getRole();
		 Set<UserRoles> roles = new HashSet<>(); 
		 strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					UserRoles adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "superadmin":
					UserRoles superadminRole = roleRepository.findByName(RoleEnum.ROLE_SUPERADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(superadminRole);

					break;
				default:
					UserRoles userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		user.setRole(roles);
		userRepository.save(user);
		 
        String message = "Hi "+user.getName()+",\n\nClick on the following link: http://localhost:8080/loginpage \n\nYour Login credentials are:\nusername: "+ user.getUsername()+"\npassword: "+ password;
    	
    	emailService.sendMail(user.getUsername(), "Test Subject",message);

		return ResponseEntity.ok("email sent successfully!!!");
	}
	 
	 
	 @RequestMapping(value = "/remove", method = RequestMethod.POST)
		public ResponseEntity<?> deleteAdmin(@RequestBody UserDao user) throws Exception {    	
	    	
	    	userDetailsService.delete(user);    	

			return ResponseEntity.ok("deleted successfully!!!");
		}
 
	   @RequestMapping(value = "/changePassword", method = RequestMethod.PUT)
		public ResponseEntity<?> update(@RequestParam String employeeCode, String password) throws Exception {    	
	    	
	    	userDetailsService.update(employeeCode,password);    	

			return ResponseEntity.ok("updated successfully!!!");
		}


}
