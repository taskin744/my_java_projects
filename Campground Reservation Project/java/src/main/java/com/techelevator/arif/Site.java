package com.techelevator.arif;

import java.math.BigDecimal;

public class Site {

	private Long id;
	private Long campgroundId;
	private int siteNumber;
	private int maxOccupancy;
	private boolean accessible;
	private int maxRVLength;
	private boolean utilities;
	private BigDecimal cost;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(Long campgroundId) {
		this.campgroundId = campgroundId;
	}
	public int getSiteNumber() {
		return siteNumber;
	}
	public void setSiteNumber(int siteNumber) {
		this.siteNumber = siteNumber;
	}
	public int getMaxOccupancy() {
		return maxOccupancy;
	}
	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	public boolean isAccessible() {
		return accessible;
	}
	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}
	public int getMaxRVLength() {
		return maxRVLength;
	}
	public void setMaxRVLength(int maxRVLength) {
		this.maxRVLength = maxRVLength;
	}
	public boolean isUtilities() {
		return utilities;
	}
	public void setUtilities(boolean utilities) {
		this.utilities = utilities;
	}
	@Override
	public String toString() {
		String siteInfo = ("" + siteNumber +"                "+ maxOccupancy +"                   "+ accessible +"             "+ maxRVLength +"     "
				+ "       "+ utilities +"       $"+ cost);
	
		return siteInfo;
	}
	
	
	public BigDecimal getCost() {
		return cost;
	}
	
	public void setCost(BigDecimal cost) {
		this.cost = cost;
		this.cost = this.cost.setScale(2);
	}

	
}
