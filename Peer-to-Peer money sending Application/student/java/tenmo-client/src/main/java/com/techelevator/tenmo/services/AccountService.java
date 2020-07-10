package com.techelevator.tenmo.services;

import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import com.techelevator.tenmo.models.Transfer;

public class AccountService {

	public static String AUTH_TOKEN = "";
	private final String BASE_URL;
	private final RestTemplate restTemplate = new RestTemplate();

	public AccountService(String url) {
		this.BASE_URL = url;
	}
	
	public Double getBalanceByUserId(Long id) {
		Double account = 0.00; 
		try {
			account = restTemplate.exchange(BASE_URL + "accounts/"+ id + "?balance" , HttpMethod.GET, makeAuthEntity(), Double.class).getBody();
		} catch (RestClientResponseException e) {
			throw new AccountServiceException(e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
		} 
		return account; 
	}
	
	@SuppressWarnings("rawtypes")
	public HttpEntity makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }
	
	public HttpEntity<Transfer> makeAccountEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        return entity;
    }
	
	public String formatBalanceToCurrency(Double balance) {
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
		return numberFormat.format(balance);
	}
	
}	
	

