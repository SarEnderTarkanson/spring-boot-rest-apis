package com.luv2code.springboot.employees.service;

import com.luv2code.springboot.employees.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();

}
