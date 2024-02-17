package com.empl.employeemanagement.employeemang.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.empl.employeemanagement.employeemang.entity.User;

public interface UserRepo extends JpaRepository<User,Integer> {
	
	@Query(value = " select * from users where users_username = ?1", nativeQuery = true)
	Optional<User> findByUsername(String username) ;
}
