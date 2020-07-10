package com.techelevator.arif;

import java.util.List;

public interface CampgroundDAO {

	
	public List<Campground> showAllCampgrounds();
		
	public List<Campground> getCampgroundByParkId(Long parkId); // added this method to display all camp grounds at a specific park

}
