package com.sneha.service;

import com.sneha.model.JwtRequest;
import com.sneha.model.RoleEnum;
import com.sneha.model.UserDao;
import com.sneha.model.UserRoles;
import com.sneha.repository.UserRepository;
import com.sneha.repository.UserRoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserRoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private EmailService emailService;	
	
	 @Override
	 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 UserDao user = userRepo.findByUserName(username)
					.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

			return AppUserDetails.builds(user);
	 }

	public ResponseEntity<String> save(JwtRequest request) {
		 String password =  emailService.genaratePassword(6);
		   UserDao user = new UserDao(request.getEmployeeCode(),request.getName(),request.getUsername(),bcryptEncoder.encode(password));    
		     Set<String> strRoles = request.getRole();
			 Set<UserRoles> roles = new HashSet<>(); 
			 strRoles.forEach(role -> {
					switch (role.toLowerCase()) {
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
			user.setRoles(roles);
	    	
			if(userRepo.findByUserName(user.getUsername()) == null) {
				userRepo.save(user);
				 
		        String message = "Hi "+user.getName()+",\n\nClick on the following link: http://localhost:8080/loginpage \n\nYour Login credentials are:\nusername: "+ user.getUsername()+"\npassword: "+ password;
		    	
		    	emailService.sendMail(user.getUsername(), "Test Subject",message);
				return ResponseEntity.ok("user added successfully!!!");
			}
			else {
				 return new ResponseEntity<>("UserName Exists!!!", HttpStatus.BAD_REQUEST);
			}
   	}
	
	public void delete(UserDao user) {
		userRepo.deleteById(user.getEmployeeCode());
	}
	
	public ResponseEntity<?> update(String empCode, String password) {
		try {
			Optional<UserDao> updateUserOptional = userRepo.findById(empCode);
	        UserDao updatedUser = updateUserOptional.get();	       
	        updatedUser.setPassword(bcryptEncoder.encode(password));
	        userRepo.save(updatedUser);
	        return new ResponseEntity<>("Changed password successfully!!!", HttpStatus.OK);		
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<>("Invalid userId!!!",HttpStatus.BAD_REQUEST);
		}		
	
	}

}