package com.empl.employeemanagement.employeemang.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.empl.employeemanagement.employeemang.entity.Role;
import com.empl.employeemanagement.employeemang.entity.User;

public class OurUserInfoDetails implements UserDetails {

	private String username;

	private String password;

	private List<GrantedAuthority> roles;

	public OurUserInfoDetails(User user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.roles = user.getRoles().stream()
				.map(role -> 
				new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());

	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.roles;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
