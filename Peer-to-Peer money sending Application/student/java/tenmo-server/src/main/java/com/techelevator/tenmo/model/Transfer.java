package com.techelevator.tenmo.model;

import java.text.NumberFormat;
import java.util.Locale;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

public class Transfer {
	
	private Long transferId = 0L;
	@Min(value = 1)
	private Long transferTypeId = 2L;
	@Min(value = 1)
	private Long transferStatusId = 2L;
	@Min(value = 1)
	private Long accountFrom;
	@Min(value = 1)
	private Long accountTo;
	@DecimalMin(value = "0.0", inclusive = false)
	private double amount;

	public Transfer() {	
	}
	
	public Long getTransferId() {
		return transferId;
	}
	public void setTransferId(Long transferId) {
		this.transferId = transferId;
	}
	public Long getTransferTypeId() {
		return transferTypeId;
	}
	public void setTransferTypeId(Long transferTypeId) {
		this.transferTypeId = transferTypeId;
	}
	public Long getTransferStatusId() {
		return transferStatusId;
	}
	public void setTransferStatusId(Long transferStatusId) {
		this.transferStatusId = transferStatusId;
	}
	public Long getAccountFrom() {
		return accountFrom;
	}
	public void setAccountFrom(Long accountFrom) {
		this.accountFrom = accountFrom;
	}
	public Long getAccountTo() {
		return accountTo;
	}
	public void setAccountTo(Long accountTo) {
		this.accountTo = accountTo;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String formatBalanceToCurrency() {
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
		return numberFormat.format(amount);
	}
}
