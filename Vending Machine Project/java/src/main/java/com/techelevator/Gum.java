package com.techelevator;

import java.math.BigDecimal;

public class Gum extends Item {

	public Gum(String name, BigDecimal price, String slot, String type, int quantity) {
		super(name, price, slot, type, quantity);
	
		
	}
	
	@Override
	public String getConsumeMessage() {
		return "Chew Chew, Yum!";
	}
}
