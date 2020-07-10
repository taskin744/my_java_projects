package com.techelevator;

import java.math.BigDecimal;

public class Beverage extends Item {

	public Beverage(String name, BigDecimal price, String slot, String type, int quantity) {
		super(name, price, slot, type, quantity);
		
	}

	@Override
	public String getConsumeMessage() {
		return "Glug Glug, Yum!";
	}
}
