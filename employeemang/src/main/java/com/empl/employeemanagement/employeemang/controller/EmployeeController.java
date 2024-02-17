package com.empl.employeemanagement.employeemang.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.empl.employeemanagement.employeemang.entity.Employee;
import com.empl.employeemanagement.employeemang.entity.Role;
import com.empl.employeemanagement.employeemang.entity.User;
import com.empl.employeemanagement.employeemang.repository.EmployeeRepo;
import com.empl.employeemanagement.employeemang.repository.RoleRepo;
import com.empl.employeemanagement.employeemang.repository.UserRepo;

@RestController
@RequestMapping
public class EmployeeController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/")
	public String goHome(){
		return "this can accessed publicily";
	}

	@GetMapping("/employee/all")
	public ResponseEntity<Object> getAllEmployee(){
		return ResponseEntity.ok(employeeRepo.findAll());
	}

	@PostMapping("/employee/save")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
		try {
			Employee savedEmployee = employeeRepo.save(employee);
			return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee/{id}")
	public ResponseEntity<Object> getEmployeeById(@PathVariable int id) {
		Employee employee = employeeRepo.findById(id).orElse(null);
		if (employee != null) {
			return ResponseEntity.ok(employee);
		} else {
			return ResponseEntity.notFound().build();
		}
	}


	@PutMapping("/employee/{id}")
	public ResponseEntity<Object> updateEmployee(@PathVariable int id, @RequestBody Employee updatedEmployee) {
		Employee existingEmployee = employeeRepo.findById(id).orElse(null);
		if (existingEmployee != null) {
			// Update existing employee fields with the provided data
			existingEmployee.setFirstName(updatedEmployee.getFirstName());
			existingEmployee.setLastName(updatedEmployee.getLastName());
			existingEmployee.setEmail(updatedEmployee.getEmail());
			// Set other fields as needed

			// Save the updated employee
			Employee updated = employeeRepo.save(existingEmployee);
			return ResponseEntity.ok(updated);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/employee/{id}")
	public ResponseEntity<Object> deleteEmployeeById(@PathVariable int id) {
		if (employeeRepo.existsById(id)) {
			employeeRepo.deleteById(id);
			return ResponseEntity.ok("Deleted employee id - " + id );
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/employee/search")
	public ResponseEntity<List<Employee>> searchEmployeesByFirstName(@RequestParam("firstName") String firstName) {
		List<Employee> employees = employeeRepo.findByFirstNameContaining(firstName);
		return ResponseEntity.ok(employees);
	}

	@GetMapping("employee/sorting")
	public ResponseEntity<List<Employee>> getAllEmployeesSortedByName(
			@RequestParam(value = "sortBy", defaultValue = "ASC") String sortBy) {
		List<Employee> employees;
		if (sortBy.equalsIgnoreCase("DESC")) {
			employees = employeeRepo.findAllByOrderByFirstNameDesc();
		} else {
			employees = employeeRepo.findAllByOrderByFirstNameAsc();
		}
		return ResponseEntity.ok(employees);
	}

	@GetMapping("/user/all")
	public ResponseEntity<Object> getAllUsers(){
		return ResponseEntity.ok(userRepo.findAll());

	}

	@PostMapping("/user/save")
	public ResponseEntity<Object> saveUser(@RequestBody User user){
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User result=userRepo.save(user);
		if(result.getId()>0) {
			return ResponseEntity.ok("User was saved");
		}
		return ResponseEntity.status(404).body("Error, USer Not Saved");

	}

	@GetMapping("/user/single")
	public ResponseEntity<Object> getMyDetails(){
		return ResponseEntity.ok(userRepo.findByUsername(getLoggedInUserDetails().getUsername()));

	}

	public UserDetails getLoggedInUserDetails(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.getPrincipal() instanceof UserDetails){
			return (UserDetails) authentication.getPrincipal();
		}
		return null;
	}

	@PostMapping("/roles")
	public ResponseEntity<Role> saveRole(@RequestBody Role role) {
		try {
			Role savedRole = roleRepo.save(role);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
