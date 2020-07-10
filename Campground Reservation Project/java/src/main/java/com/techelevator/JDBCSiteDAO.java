package com.techelevator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.arif.Campground;
import com.techelevator.arif.Park;
import com.techelevator.arif.Reservation;
import com.techelevator.arif.Site;
import com.techelevator.arif.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {
	
	private JdbcTemplate jdbcTemplate;

	
	public JDBCSiteDAO (DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	Campground campground = new Campground();
	
	
	public List<Site> returnAvailableSitesList(long campgroundID, LocalDate arrival, LocalDate departure) {
		List<Site> availSiteList = new ArrayList<>();
		
		String sitesAvailable = "SELECT s.*, c.daily_fee FROM site s JOIN campground c ON s.campground_id = c.campground_id WHERE s.campground_id = ?"
				+ " AND site_id NOT IN (SELECT site_id FROM reservation r WHERE (to_date BETWEEN ?::date AND ?::date) OR "
				+ "(from_date BETWEEN ?::date AND ?::date)) GROUP BY site_id, c.daily_fee LIMIT 5";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sitesAvailable, campgroundID, arrival, departure, arrival, departure);
		while(results.next()) {
			Site openSite = mapRowToSites(results);
			availSiteList.add(openSite);
		}
			return availSiteList;
	}
	 

	
	@Override
	public Long getSiteIdBySiteNumber(int siteNumber) {
		String sqlGetSite = "SELECT site_id FROM site where  site_number = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetSite, siteNumber);
		Long site_id = (long) 0;
		if (results.next()) {
			site_id = results.getLong("site_id");
		}
		return site_id;
	}

	
	private Site mapRowToSites(SqlRowSet results) {
		Site newSite = new Site();
		newSite.setId(results.getLong("site_id"));
		newSite.setCampgroundId(results.getLong("campground_id"));
		newSite.setSiteNumber(results.getInt("site_number"));
		newSite.setMaxOccupancy(results.getInt("max_occupancy"));
		newSite.setAccessible(results.getBoolean("accessible"));
		newSite.setMaxRVLength(results.getInt("max_rv_length"));
		newSite.setUtilities(results.getBoolean("utilities"));
		newSite.setCost(results.getBigDecimal("daily_fee"));
		return newSite;
	}




}
