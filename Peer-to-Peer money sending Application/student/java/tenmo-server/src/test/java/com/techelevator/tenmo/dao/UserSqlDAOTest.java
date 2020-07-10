package com.techelevator.tenmo.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.tenmo.model.User;

public class UserSqlDAOTest {

	private static SingleConnectionDataSource dataSource;
	private UserSqlDAO dao = null;
	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	Long id;

	@BeforeClass
	public static void setUpDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/tenmo");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}

	@AfterClass
	public static void tearDownAfterClass() throws SQLException {
		dataSource.destroy();
	}
	
	@Before
	public void setup() {
	dao = new UserSqlDAO(jdbcTemplate);
	dao.create("tester", "tester");
	id = (long)dao.findIdByUsername("tester");
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	@Test
	public void find_all_users() {
		String sql = "SELECT COUNT(*) FROM users";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		results.next();
		List<User> testList = dao.findAll();
		assertNotNull(testList);
		assertEquals(results.getInt(1), testList.size());
	}
	
	@Test
	public void get_user_by_user_id() {
		String sql = "SELECT * FROM users WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
		results.next();
		User user = mapRowToUser(results);
		assertTrue(user.getUsername().equals("tester"));
	}
	
	 private User mapRowToUser(SqlRowSet rs) {
	        User user = new User();
	        user.setId(rs.getLong("user_id"));
	        user.setUsername(rs.getString("username"));
	        user.setPassword(rs.getString("password_hash"));
	        user.setActivated(true);
	        user.setAuthorities("ROLE_USER");
	        return user;
	    }

}
