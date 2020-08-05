package com.mycompany.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.mycompany.model.Employee;

@Component
public class JDBCEmployeeDAO implements EmployeeDAO {

	private JdbcTemplate template;
	
	public JDBCEmployeeDAO(DataSource dataSource) {
			template = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> allEmployees = new ArrayList<>();
		String sqlGetEmployees = "SELECT * FROM employee JOIN address ON employee.address_id = address.id";
		SqlRowSet result = template.queryForRowSet(sqlGetEmployees);
		while(result.next()) {
			Employee anEmp = mapRowToEmployee(result);
			allEmployees.add(anEmp);
		}		
		return allEmployees;
	}
	
	@Override
	public Employee getAnEmployeeInfoById(UUID id) {
		String sqlGetAnEmp = "SELECT * FROM employee WHERE employee_id=?";
		SqlRowSet result = template.queryForRowSet(sqlGetAnEmp, id);
		if(result.next()) {
			return mapRowToEmployee(result);
		}
		else return null;
			
	}
	
	@Override
	public void createAnEmployee(Employee emp) {
		String sqlAddEmployee = "INSERT INTO employee(address_id, firstname, lastname, contactemail, companyemail, "
				+ "birthdate, hireddate, emp_role, businessunit, skill_id, assignedto) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		template.update(sqlAddEmployee, emp.getAddressId(), emp.getFirstName(), emp.getLastName(),
				emp.getContactEmail(), emp.getCompanyEmail(), emp.getBirthDate(), emp.getHiredDate(),
				emp.getRole(), emp.getBusinessUnit(), emp.getSkillId(), emp.getAssignedTo());
	}
	
	@Override
	public void removeAnEmployee(UUID Id) {
		String sqlDelete = "DELETE FROM employee WHERE employee_id = ?";
		template.update(sqlDelete, Id);
	}
	
	private Employee mapRowToEmployee(SqlRowSet result) {
		Employee emp = new Employee();
		
		emp.setEmployeeId(result.getString("employee_id"));
		emp.setAddressId(result.getString("address_id"));
		emp.setSkillId(result.getString("skill_id"));
		emp.setFirstName(result.getString("firstname"));
		emp.setLastName(result.getString("lastname"));
		emp.setContactEmail(result.getString("contactemail"));
		emp.setCompanyEmail(result.getString("companyemail"));
		emp.setBirthDate(result.getDate("birthdate"));
		emp.setHiredDate(result.getDate("hireddate"));
		emp.setAssignedTo(result.getString("assignedto"));
		emp.setRole(result.getString("emp_role"));
		emp.setBusinessUnit(result.getString("businessunit"));
		
		return emp;
	}

	
	
	
}
