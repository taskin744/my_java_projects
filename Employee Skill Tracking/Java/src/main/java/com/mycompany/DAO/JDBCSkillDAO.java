package com.mycompany.DAO;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.mycompany.model.Skill;

@Component
public class JDBCSkillDAO implements SkillDAO {
	private JdbcTemplate template;
	public JDBCSkillDAO(DataSource dataSource) {
		template = new JdbcTemplate(dataSource);
	}
	
	@Override
	public Skill getEmployeeSkillsByEmployeeId(UUID employeeId) {
		Skill skill = new Skill();
		String sqlGetSkills = "SELECT skill.id, skill.field_id, skill.experience_in_month, skill.summary FROM skill JOIN employee "
				+ "ON skill.id = employee.skill_id WHERE employee.employee_id = ?";
		SqlRowSet result = template.queryForRowSet(sqlGetSkills, employeeId);
		if(result.next()) {
			skill = mapRowToSkill(result);
		}
		return skill;
	}
	
	@Override
	public Skill getEmployeeSkillBySkillId(UUID skillId) {
		Skill skill = new Skill();
		String sqlGetSkill = "SELECT * FROM skill WHERE id = ?";
		SqlRowSet result = template.queryForRowSet(sqlGetSkill, skillId);
		if(result.next()) {
			skill = mapRowToSkill(result);
		}
		return skill;
	}
	
	private Skill mapRowToSkill(SqlRowSet result) {
		Skill skill = new Skill();
		skill.setId(result.getString("id"));
		skill.setFieldId(result.getString("field_id"));
		skill.setExperienceInMonth(result.getInt("experience_in_month"));
		skill.setSummary(result.getString("summary"));
		return skill;
	}



}
