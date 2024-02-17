package com.empl.employeemanagement.employeemang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empl.employeemanagement.employeemang.entity.Employee;
import com.empl.employeemanagement.employeemang.repository.EmployeeRepo;

@Service
public class EmployeeService {

	private final EmployeeRepo employeeRepo;

	@Autowired
	public EmployeeService(EmployeeRepo employeeRepo) {
		this.employeeRepo = employeeRepo;
	}

	public List<Employee> findByFirstNameContaining(String firstName) {
		return employeeRepo.findByFirstNameContaining(firstName);
	}

	public List<Employee> findAllByOrderByFirstNameAsc() {
		return employeeRepo.findAllByOrderByFirstNameAsc();
	}

	public List<Employee> findAllByOrderByFirstNameDesc() {
		return employeeRepo.findAllByOrderByFirstNameDesc();
	}
}