package com.mycompany.DAO;

import java.util.List;
import java.util.UUID;

import com.mycompany.model.Skill;

public interface SkillDAO {
	Skill getEmployeeSkillsByEmployeeId(UUID employeeId);
	Skill getEmployeeSkillBySkillId(UUID skillId);
	
}
