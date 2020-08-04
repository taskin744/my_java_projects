package com.mycompany.DAO;

import java.util.List;
import java.util.UUID;

import com.mycompany.model.Employee;

public interface EmployeeDAO {

	List<Employee> getAllEmployees();

	Employee getAnEmployeeInfoById(UUID Id);

	void createAnEmployee(Employee employee);
	
	void removeAnEmployee(UUID Id);

}