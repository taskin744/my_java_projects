package com.techelevator.tenmo.models;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Account {

	private Long accountId;
	private Long userId;
	private BigDecimal balance;
	
	public Long getAccountId() {
		return accountId;
	}

	public Long getUserId() {
		return userId;
	}

	public BigDecimal getBalance() {
		return balance;
	}
	
	public String formatBalanceToCurrency() {
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
		return numberFormat.format(balance.doubleValue());
	}
	
	@Override
	public String toString() {
		return formatBalanceToCurrency();
	}

}
