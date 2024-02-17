package com.empl.employeemanagement.employeemang.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.empl.employeemanagement.employeemang.entity.Employee;
import com.empl.employeemanagement.employeemang.entity.Role;
import com.empl.employeemanagement.employeemang.entity.User;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
	List<Employee> findByFirstNameContaining(String firstName);
	List<Employee> findAllByOrderByFirstNameAsc();
	List<Employee> findAllByOrderByFirstNameDesc();

}