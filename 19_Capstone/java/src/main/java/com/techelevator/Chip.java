package com.techelevator;

import java.math.BigDecimal;

public class Chip extends Item {

	public Chip(String name, BigDecimal price, String slot, 
			String type, int quantity) {
		super(name, price, slot, type, quantity);
		
	}
	
	@Override
	public String getConsumeMessage() {
		return "Crunch Crunch, Yum!";
	}

	
}
