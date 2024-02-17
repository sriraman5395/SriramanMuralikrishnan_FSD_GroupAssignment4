package com.empl.employeemanagement.employeemang.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.empl.employeemanagement.employeemang.entity.User;
import com.empl.employeemanagement.employeemang.repository.UserRepo;


@Configuration
public class UserInfoUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo ourUserRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = ourUserRepo.findByUsername(username); // Corrected method call
		return user.map(OurUserInfoDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
	}
}