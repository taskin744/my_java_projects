package com.mycompany.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.DAO.EmployeeDAO;
import com.mycompany.model.Employee;


@RestController
@CrossOrigin
public class EmployeeController {
	
	@Autowired
	EmployeeDAO employees;
	
	
	@GetMapping(path= {"/employees", "/"})
	public List<Employee> returnEmployees() {
		List<Employee> allEmployees = employees.getAllEmployees();
		return allEmployees;
	}
	
	@GetMapping(path= "/employees/{id}")
	public Employee returnAnEmployeeInfo(@PathVariable UUID id) {
		Employee anEmployee = employees.getAnEmployeeInfoById(id);
		return anEmployee;
	}
	
	@PostMapping(path= {"/employees"})
	@ResponseStatus(HttpStatus.CREATED)
	public void newEmployee(@RequestBody Employee anEmployee) {
		employees.createAnEmployee(anEmployee);
	}
	
	@DeleteMapping("/employees/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteAnEmployee(@PathVariable UUID id) {
		employees.removeAnEmployee(id);
	}
	
	
	}
	

