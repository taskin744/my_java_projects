package com.techelevator.arif;

import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public interface ParkDAO {

	
	public List<Park> showAllParks();
	
	public String[] getAllParks();
	
}
