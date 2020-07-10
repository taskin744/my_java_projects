package com.techelevator.arif;

import java.time.LocalDate;

public class Park {
	
	private Long id;
	private String name;
	private String location;
	private LocalDate established;
	private int area;
	private int annualVisitors;
	private String description;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public LocalDate getEstablished() {
		return established;
	}
	public void setEstablished(LocalDate established) {
		this.established = established;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public int getAnnualVisitors() {
		return annualVisitors;
	}
	public void setAnnualVisitors(int annualVisitors) {
		this.annualVisitors = annualVisitors;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {	//have this only return name
		return name;
	}
	
	public void printParkInfo() {
		System.out.println("\n" + name + " National Park");
		System.out.println("Location:\t" + location);
		System.out.println("Established:\t" + established);
		System.out.println("Area:\t\t" + area);
		System.out.println("Annual \nVisitors:\t" + annualVisitors);		
		System.out.println(description);
	
	}
	
	
}
