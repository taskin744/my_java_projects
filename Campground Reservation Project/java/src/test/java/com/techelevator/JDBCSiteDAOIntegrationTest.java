package com.techelevator;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.arif.Site;

public class JDBCSiteDAOIntegrationTest {

	private static SingleConnectionDataSource dataSource;
	private JDBCSiteDAO sDao;
	private JdbcTemplate jdbcTemplate;
	private static final long CAMPGROUND_TEST_ID = 5;
	private static final LocalDate ARR_TEST = LocalDate.now();
	private static final LocalDate DEP_TEST = LocalDate.now();
	private static final LocalDate DEP_TEST_TWO = DEP_TEST.plusDays(1);
	
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
		sDao = new JDBCSiteDAO(dataSource);
	}

	@After
	public void tearDown() throws Exception {
		dataSource.getConnection().rollback();
	}

	@Test
	public void showAllSitesInSiteList() {
		List<Site> siteList = sDao.returnAvailableSitesList(CAMPGROUND_TEST_ID, ARR_TEST, DEP_TEST);
		
		assertNotNull(siteList);
		assertEquals(1, siteList.size());
		
		List<Site> newList = sDao.returnAvailableSitesList(4, ARR_TEST, DEP_TEST_TWO);
		
		assertNotNull(newList); 
		assertEquals(5, newList.size());
	}
	
	@Test
	public void testShowSiteIdBySiteNumber() {
		long siteId = sDao.getSiteIdBySiteNumber(11);
		
		assertEquals(11, siteId);
		
		long siteID = sDao.getSiteIdBySiteNumber(5);
		assertEquals(5, siteID);
	}

}
















