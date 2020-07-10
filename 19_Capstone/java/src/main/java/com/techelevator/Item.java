package com.techelevator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Item {

		private String name;
		private BigDecimal price;
		private String slot;
		private String type;
		private int quantity;
		
		public Item(String name, BigDecimal price,
				String slot, String type, int quantity) {
			this.name = name;
			this.price = price;
			this.slot = slot;
			this.type = type;
			this.quantity = 5;
		}

		public String getName() {
			return name;
		}

		public BigDecimal getPrice() {
			return price;
		}

		public String getSlot() {
			return slot;
		}

		public String getType() {
			return type;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		
		public int decreaseQuantity() {
			return quantity--;
		}
		
		public boolean isSoldOut() {
			if (quantity == 0) {
				return true;
			} else {
				return false;
			}
		} 
		
		public String getConsumeMessage() {
			return "";
		
		}
}
