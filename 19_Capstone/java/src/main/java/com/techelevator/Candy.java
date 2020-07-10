package com.techelevator;

import java.math.BigDecimal;

public class Candy extends Item {

	public Candy(String name, BigDecimal price, String slot, String type, int quantity) {
		super(name, price, slot, type, quantity);
		
	}

	@Override
	public String getConsumeMessage() {
		return "Munch Munch, Yum!";
	}
}
