package com.techelevator.arif;

import java.time.LocalDate;

public class Reservation {

	private Long id;
	private Long siteId;
	private LocalDate arrival;
	private LocalDate departure;
	private String name;
	private LocalDate created;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public LocalDate getArrival() {
		return arrival;
	}
	public void setArrival(LocalDate arrival) {
		this.arrival = arrival;
	}
	public LocalDate getDeparture() {
		return departure;
	}
	public void setDeparture(LocalDate departure) {
		this.departure = departure;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getCreated() {
		return created;
	}
	public void setCreated(LocalDate created) {
		this.created = created;
	}
	
	public void printReservationInfoByName() {
		System.out.println("\nReservation #" + id + ": Site No. " + siteId + "- Dates of Reservation: " + arrival + " to " + departure
				+ " Reservation for: " + name);

	}

}


















