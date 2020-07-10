package com.techelevator.arif;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO {

	public List<Reservation> getReservationsFromSiteID(long siteID);
		
	public List<Reservation> getReservationByName(String searchedName);
	
	public long getMostRecentReservationId();
	
	public void createReservation (Long siteId,String reservationName, LocalDate arrivalDate, LocalDate departureDate);
	
}
