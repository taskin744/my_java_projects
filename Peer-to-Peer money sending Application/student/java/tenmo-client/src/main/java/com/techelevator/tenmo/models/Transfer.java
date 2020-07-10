package com.techelevator.tenmo.models;

public class Transfer {

	private Long transferId = 0l;
	private Long transferTypeId = 0L;
	private Long transferStatusId = 0L;
	private Long accountFrom;
	private Long accountTo;
	private double amount;
	
	public Transfer() {
	}
	
	public Transfer(Long transferTypeId, Long transferStatusId, Long accountFrom, Long accountTo, double amount) {
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
		this.amount = amount;
		this.transferTypeId = transferTypeId;
		this.transferStatusId = transferStatusId;
	}

	public Long getTransferId() {
		return transferId;
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
	
	public String returnStatus() {
		String status;
		if (transferStatusId == 1) {
			status = "Pending";
		} else if (transferStatusId == 2) {
			status = "Approved";
		} else {
			status = "Rejected";
		} return status;
	}
	
	public String returnType() {
		String type;
		if (transferTypeId == 1) {
			type = "Request";
		} else {
			type = "Send";
		} return type;
	}
}