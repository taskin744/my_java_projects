package com.techelevator;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.arif.Campground;
import com.techelevator.arif.CampgroundDAO;

public class JDBCCampgroundDAO implements CampgroundDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCCampgroundDAO (DataSource dataSource) {
		
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Campground> showAllCampgrounds() {
		List<Campground> allCampgrounds = new ArrayList<>();
		String sqlGetAllCampgrounds = "SELECT * FROM campground";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllCampgrounds);
		while (results.next()) {
			Campground aCampground = mapRowToCampground(results);
			allCampgrounds.add(aCampground);
		}
		return allCampgrounds;
	}

	
	public List<Campground> getCampgroundByParkId(Long parkId) {
		List<Campground> campgroundList = new ArrayList<>();
		String sqlGetCampgroundByParkId = "SELECT * FROM campground WHERE park_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetCampgroundByParkId, parkId);
		
		while(results.next()) {
		Campground anotherCampground = mapRowToCampground(results);
		campgroundList.add(anotherCampground);
		}
		return campgroundList;
	}
		

	private Campground mapRowToCampground(SqlRowSet results) {	//realized this method was supposed to be a private method
		Campground aCampground = new Campground();
		aCampground.setId(results.getLong("campground_id"));
		aCampground.setParkId(results.getLong("park_id"));
		aCampground.setName(results.getString("name"));
		aCampground.setOpenFrom(results.getInt("open_from_mm"));
		aCampground.setClosedFrom(results.getInt("open_to_mm"));
		aCampground.setDailyFee(results.getBigDecimal("daily_fee"));
	
		return aCampground;
	}
	

	
	public String getMonthConversion(int MM) {
		String month = "";
		switch(MM) {
		case 1: month = "January";
		break;
		case 2: month = "February";
		break;
		case 3: month = "March";
		break;
		case 4: month = "April";
		break;
		case 5: month = "May"; 
		break;
		case 6: month = "June";
		break;
		case 7: month = "July";
		break;
		case 8: month = "August";
		break;
		case 9: month = "September";
		break;
		case 10: month = "October";
		break;
		case 11: month = "November";
		break;
		case 12: month = "December";
		break;
		}		
		return month;
	}
	

}

