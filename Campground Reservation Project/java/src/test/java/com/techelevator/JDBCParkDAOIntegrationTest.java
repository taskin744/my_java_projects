package com.techelevator;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.arif.Park;
 
public class JDBCParkDAOIntegrationTest {

	private static SingleConnectionDataSource dataSource;
	private static JDBCParkDAO pDao;
	private JdbcTemplate jdbcTemplate;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false); 
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		dataSource.destroy();
	}

	@Before
	public void setUp() throws Exception {
		pDao = new JDBCParkDAO(dataSource);
	}

	@After
	public void tearDown() throws Exception {
		dataSource.getConnection().rollback();
	}

	@Test
	public void returnAllParksList() {
		List<Park> parkList = pDao.showAllParks();
		
		assertNotNull(parkList);
		assertEquals(3, parkList.size());
	}
	
	@Test
	public void returnAllParksArray() {
		String [] parkArrayItems = pDao.getAllParks();
		
		assertNotNull(parkArrayItems);
		assertNotEquals(3, parkArrayItems.length);
		assertEquals(4, parkArrayItems.length);
		
	}

}


















