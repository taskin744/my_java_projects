package com.techelevator.tenmo.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserServiceException extends RuntimeException {

	public UserServiceException() {
		super("User ID not found.");
	}
	
	public UserServiceException(String message) {
		super("User ID not found.");
	}


}
