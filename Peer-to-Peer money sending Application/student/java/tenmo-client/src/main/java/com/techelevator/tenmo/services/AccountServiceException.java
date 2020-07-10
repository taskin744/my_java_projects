package com.techelevator.tenmo.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountServiceException extends RuntimeException {

	public AccountServiceException(String message) {
		super("Account or User ID not found, please try again");
	}
}
