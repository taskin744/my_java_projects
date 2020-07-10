package com.techelevator.tenmo.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TransferServiceException extends RuntimeException {

	public TransferServiceException() {
		super("Gender ID might be fluid, but Transfer ID is not, please try again");
	}
	
	public TransferServiceException(String message) {
		super("Gender ID might be fluid, but Transfer ID is not, please try again");
	}
	
}
