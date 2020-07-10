package com.techelevator;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.arif.Reservation;


public class JDBCReservationDAOIntegrationTest extends DAOIntegrationTest{

	private static final Long TEST_SITE_ID = (long) 7;
	private static final String TEST_NAME = "Ellen";	 
	private static SingleConnectionDataSource dataSource;
	private JDBCReservationDAO rDao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	} 

	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}
	
	@Before
	public void setup() {
		//JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		rDao = new JDBCReservationDAO(dataSource);
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	
	@Test
	public void returnReservationListFromSiteId() {
		List<Reservation> testList = new ArrayList<>();
		testList = (rDao.getReservationsFromSiteID(TEST_SITE_ID));
		
		assertNotNull(testList);
		assertEquals(1, testList.size());
		
	}
	
	@Test
	public void returnReservationListFromName() {
		List<Reservation> reList = rDao.getReservationByName(TEST_NAME);	
		assertEquals(3, reList.size());
		
		List<Reservation> resList = rDao.getReservationByName("Sarah");	
		assertEquals(1, resList.size());

	}
	
	@Test
	public void testGetMostRecentResId() {
		long resList = rDao.getMostRecentReservationId();
		
		assertEquals(53, resList);
		
	}
	


}
