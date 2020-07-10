package com.techelevator.tenmo.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.User;

public class UserService {

	public static String AUTH_TOKEN = "";
	private final String BASE_URL;
	private final RestTemplate restTemplate = new RestTemplate();
	
	public UserService(String url) {
		this.BASE_URL = url;
	}
	
	
	public List<User> getAllUsers() {
		List<User> userList = new ArrayList<>();
		User[] userArray = null;
		try {
			userArray = restTemplate.exchange(BASE_URL + "users", HttpMethod.GET, makeAuthEntity(), User[].class).getBody();
		} catch (RestClientResponseException e) {
			throw new AccountServiceException(e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
		} userList = Arrays.asList(userArray);
		return userList;
	}
	
	public List<User> displayUsers(String message) {
		System.out.println(message);
		System.out.println("------------------------------------------------");
		System.out.printf("%-10s %-15s", "ID", "Name");
		System.out.println("\n------------------------------------------------");

		List<User> userList = new ArrayList<>();
		userList = getAllUsers();
		for (User user : userList) {
			System.out.printf("%-10s %-15s\n", user.getId(), user.getUsername());
		}
		System.out.println("-------------");
		return userList;
	}
	
	public User getUserByUserId(Long userId) {
		User user;
		try {
		user = restTemplate.exchange(BASE_URL + "users/" + userId, HttpMethod.GET, makeAuthEntity(), User.class).getBody();
		} catch (RestClientResponseException e) {
			throw new UserServiceException(e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
		}
		return user;
	}

	@SuppressWarnings("rawtypes")
	public HttpEntity makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }
}
