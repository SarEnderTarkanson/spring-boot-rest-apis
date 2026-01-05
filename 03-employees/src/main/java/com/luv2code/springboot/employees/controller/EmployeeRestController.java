package com.luv2code.springboot.employees.controller;

import com.luv2code.springboot.employees.entity.Employee;
import com.luv2code.springboot.employees.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Employee Rest API Endpoints", description = "Operations related to employees")
public class EmployeeRestController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService theEmployeeService) {
        this.employeeService = theEmployeeService;
    }

    @Operation(summary = "Get all employees", description = "Retrieve a list of all employees")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/employees")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @Operation(summary = "Fetch simple employee", description = "Get a single employee by their ID")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable @Min(value = 1) long employeeId) {
        Employee theEmployee = employeeService.findById(employeeId);
        return theEmployee;
    }

}
