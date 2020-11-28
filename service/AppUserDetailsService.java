package com.sneha.service;

import com.sneha.model.UserDao;
import com.sneha.model.UserRoles;
import com.sneha.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
public class AppUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	 @Override
	 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 UserDao user = userRepo.findByUserName(username)
					.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

			return AppUserDetails.builds(user);
	 }

	public void save(UserDao user) {
		UserDao newUser = new UserDao();
		newUser.setName(user.getName());
		newUser.setEmployeeCode(user.getEmployeeCode());
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		userRepo.save(newUser);
	}
	
	public void delete(UserDao user) {
		userRepo.deleteById(user.getEmployeeCode());
	}
	
	public void update(String empCode, String password) {
		
        Optional<UserDao> updateUserOptional = userRepo.findById(empCode);
        UserDao updatedUser = updateUserOptional.get();
        updatedUser.setPassword(bcryptEncoder.encode(password));
        userRepo.save(updatedUser);

	}
}