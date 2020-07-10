package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

public class Account {
	
	private Long accountId;
	@Min(value = 1, message = "field 'userID' is required.")
	private Long userId;
	@DecimalMin(value = "0.0", inclusive = false)
	private BigDecimal balance;
	
	public Account() {
	}
	
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public String formatBalanceToCurrency() {
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
		return numberFormat.format(balance.doubleValue());
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
	

}
