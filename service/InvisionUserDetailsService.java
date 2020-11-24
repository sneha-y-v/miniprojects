package com.sneha.service;

import com.sneha.model.UserDao;
import com.sneha.model.UserDto;
import com.sneha.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@Service
public class InvisionUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDao user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		UserDetails u = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),new ArrayList<>());
		System.out.println(u);
		return u;
		
	}

	public void save(UserDto user) {
		UserDao newUser = new UserDao();
		newUser.setName(user.getName());
		newUser.setEmployeeCode(user.getEmployeeCode());
		newUser.setUsername(user.getUsername());
		newUser.setRole(user.getRole());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		userRepo.save(newUser);
	}
	
	public void delete(UserDto user) {
		userRepo.deleteById(user.getEmployeeCode());
	}
	
	public void update(String empCode, String password) {
		
        Optional<UserDao> updateUserOptional = userRepo.findById(empCode);
        UserDao updatedUser = updateUserOptional.get();
        updatedUser.setPassword(bcryptEncoder.encode(password));
        userRepo.save(updatedUser);

	}
}