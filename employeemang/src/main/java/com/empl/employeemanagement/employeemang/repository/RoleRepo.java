package com.empl.employeemanagement.employeemang.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.empl.employeemanagement.employeemang.entity.Role;
import com.empl.employeemanagement.employeemang.entity.User;

public interface RoleRepo extends JpaRepository<Role,Integer> {

}
