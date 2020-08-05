package com.mycompany.controller;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.DAO.SkillDAO;
import com.mycompany.model.Skill;

@RestController
@CrossOrigin
public class SkillController {
	
	@Autowired
	SkillDAO skill;
	
	@GetMapping(path= "/employees/{employeeId}/skills")
	public Skill returnAllSkills(@PathVariable UUID employeeId){
		Skill allSkills = new Skill(); 
				allSkills = skill.getEmployeeSkillsByEmployeeId(employeeId);
		return allSkills;
	}
	
	@GetMapping(path= "/employees/{employeeId}/skills/{skillId}")
	public Skill returnAllSkills(@PathVariable UUID employeeId, UUID skillId){
		Skill aSkill = new Skill(); 
				aSkill = skill.getEmployeeSkillBySkillId(skillId);
		return aSkill;
	}
	
	
	 
}
