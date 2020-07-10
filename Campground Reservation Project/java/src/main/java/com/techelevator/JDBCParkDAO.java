package com.techelevator;

//import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;


import com.techelevator.arif.Park;
import com.techelevator.arif.ParkDAO;

public class JDBCParkDAO implements ParkDAO {
	
	private JdbcTemplate jdbcTemplate;
	 
	public JDBCParkDAO (DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Park> showAllParks() {
		List<Park> allParks = new ArrayList<>();
		String sqlGetAllParks = "SELECT * FROM park ORDER BY name";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllParks);
		while(results.next()) {
			Park aPark = mapRowToParks(results);
			allParks.add(aPark);
		}
		return allParks;
	}
	
			//select name sql statement
			//make a list and add all the name sqlrowset results
			//at the end, turn the list into an array
			//return the array and call the index in the menu
	public String[] getAllParks(){ //make this return a list maybe and do .toArray in the menu
		List<Park> parkList = new ArrayList<>();
		String prkNames = "SELECT * "
						+ "FROM park "
						+ "ORDER BY name";
		SqlRowSet results = jdbcTemplate.queryForRowSet(prkNames);
		while(results.next()) {
			Park park = mapRowToParks(results);
			parkList.add(park);
		}
		String [] parkArray = new String[parkList.size() + 1];
		for(int i = 0; i < parkList.size(); i++) {
			parkArray[i] = parkList.get(i).getName();
		}
		parkArray[parkArray.length - 1] = "Quit";
		return parkArray;
	}
	
	
	private Park mapRowToParks(SqlRowSet results) {
		Park newPark = new Park();
		newPark.setId(results.getLong("park_id"));
		newPark.setName(results.getString("name"));
		newPark.setLocation(results.getString("location"));
		newPark.setEstablished(results.getDate("establish_date").toLocalDate());
		newPark.setArea(results.getInt("area"));
		newPark.setAnnualVisitors(results.getInt("visitors"));
		newPark.setDescription(results.getString("description"));
		return newPark;
	}

}












