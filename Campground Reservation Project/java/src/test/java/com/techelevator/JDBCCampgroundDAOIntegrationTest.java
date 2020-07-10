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

import com.techelevator.arif.Campground;
 
public class JDBCCampgroundDAOIntegrationTest {
	
	private static SingleConnectionDataSource dataSource;
	private JDBCCampgroundDAO cDao;
	private static final String TEST_CAMPGROUND = "Test Campground";
	private static final Long TEST_PARK_ID = (long) 3;
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
		cDao = new JDBCCampgroundDAO(dataSource);
	}

	@After
	public void tearDown() throws Exception {
		dataSource.getConnection().rollback();
	}

	@Test
	public void testing_campground_count_showAllCampgrounds_return_7() {
		List<Campground> actualCamps = cDao.showAllCampgrounds();

		assertNotNull(actualCamps);
		assertEquals(7, actualCamps.size());
	}
	
	@Test
	public void getCampgroundByParkIdTest() {
		List<Campground> campList = cDao.getCampgroundByParkId(TEST_PARK_ID);
		
		assertEquals(1, campList.size());
		assertNotNull(campList);	
		
		List<Campground> campList2 = cDao.getCampgroundByParkId((long) 2);
		assertEquals(3, campList2.size());
		assertNotNull(campList2);
		}
	
	@Test
	public void testMonthConv() { 
		
		assertEquals("January", cDao.getMonthConversion(1));
		assertEquals("October", cDao.getMonthConversion(10));
		assertEquals("November", cDao.getMonthConversion(11));
		assertNotEquals("January", cDao.getMonthConversion(44));
	}
	
}












