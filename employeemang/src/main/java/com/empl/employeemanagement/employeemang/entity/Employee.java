package com.empl.employeemanagement.employeemang.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "employees")
@Data
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "employees_id")
	private int id;
	@Column(name = "employees_firstname")
	private String firstName;
	@Column(name = "employees_lastname")
	private String lastName;
	@Column(name = "employees_email")
	private String email;

}

