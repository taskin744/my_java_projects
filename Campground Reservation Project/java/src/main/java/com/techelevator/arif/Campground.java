package com.techelevator.arif;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class Campground {

	private Long id;
	private Long parkId;
	private String name;
	private int openFrom;
	private int closedFrom;
	private BigDecimal dailyFee;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParkId() {
		return parkId;
	}
	public void setParkId(Long parkId) {
		this.parkId = parkId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOpenFrom() {
		return openFrom;
	}
	public void setOpenFrom(int openFrom) {
		this.openFrom = openFrom;
	}
	public int getClosedFrom() {
		return closedFrom;
	}
	public void setClosedFrom(int closedFrom) {
		this.closedFrom = closedFrom;
	}
	public BigDecimal getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(BigDecimal dailyFee) {
		this.dailyFee = dailyFee;
		this.dailyFee = this.dailyFee.setScale(2);
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
	
	@Override
	public String toString() {
		return String.format("%s. %s: Open %s - %s, $%.2f daily", parkId, name,
				getMonthConversion(openFrom), getMonthConversion(closedFrom), dailyFee);
	}
	
	public void printCampgroundInfo() {	//make copies of the for loop for id, and both dates
		System.out.println();
		String nameFormat = name;
		int nameLength = name.length();
		for(int i = 0; i < (33 - nameLength); i++) {
			nameFormat += " ";
		}
		String idFormat = String.valueOf(id);
		int idLength = idFormat.length();
		for(int i = 0; i < (5 - idLength); i++) {
			idFormat += " ";
		}
		String openFromFormat = getMonthConversion(openFrom);
		int openFromLength = openFromFormat.length();
		for(int i = 0; i < (9 - openFromLength); i++) {
			openFromFormat += " ";
		}
		String closedFromFormat = getMonthConversion(closedFrom);
		int closedFromLength = closedFromFormat.length();
		for(int i = 0; i < (15 - closedFromLength); i++) {
			closedFromFormat += " ";
		}
		
		String display = ("#" + idFormat + nameFormat + openFromFormat + closedFromFormat
				+ "$" + dailyFee);
			
			System.out.println(display);
		
		
	}
	}











