package com.techelevator.arif;

import java.time.LocalDate;
import java.util.List;


public interface SiteDAO {

	
	public List<Site> returnAvailableSitesList(long campgroundID, LocalDate arrival, LocalDate departure);
	
	public Long getSiteIdBySiteNumber(int siteNumber);
	
}
