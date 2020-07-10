package com.techelevator.tenmo.services;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;
import com.techelevator.view.ConsoleService;

public class TransferService {

public static String AUTH_TOKEN = "";
private final String BASE_URL;
private final RestTemplate restTemplate = new RestTemplate();
private final ConsoleService console = new ConsoleService(System.in, System.out);

public TransferService(String url) {
    this.BASE_URL = url;
}

public Transfer startTransfer(Transfer transfer) {
	try {
		transfer = restTemplate.postForObject(BASE_URL + "transfers", makeTransferEntity(transfer), Transfer.class);
	} catch (RestClientResponseException e) {
		throw new AccountServiceException(e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
	} return transfer;
}

public void makeTransferUpdateAccounts(Transfer transfer) {
	try {
		transfer = restTemplate.exchange(BASE_URL + "transfers/", HttpMethod.PUT, makeTransferEntity(transfer), Transfer.class).getBody();
	} catch (RestClientResponseException e) {
		throw new TransferServiceException(e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
	}
}

public void updateTransferRejected (Transfer transfer, Long transferId) {
	try {
		restTemplate.exchange(BASE_URL + "transfers/" + transferId , HttpMethod.PUT, makeTransferEntity(transfer), Transfer.class);
	} catch (RestClientResponseException e) {
		throw new TransferServiceException(e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
	}
}

public Long getUserChoice() {
	Long choice = Long.parseLong(console.getUserInput("\nPlease enter transfer ID to view details (0 to cancel) "));
	return choice;
}

public Transfer getDetailsByTransferId(Long transferId) {
	Transfer transfer;
	try {
	transfer = restTemplate.exchange(BASE_URL + "transfers/" + transferId, HttpMethod.GET, makeAuthEntity(), Transfer.class).getBody();
	} catch (RestClientResponseException e) {
		throw new TransferServiceException(e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
	} return transfer;
}

public void printTransferDetails(Long transferId) {
	Transfer transfer = getDetailsByTransferId(transferId);
	System.out.println("------------------------------------------------");
	System.out.println("Transfer Details");
	System.out.println("------------------------------------------------");
	System.out.println("Id: " + transfer.getTransferId() + "\nFrom: " + getUserByUserId(transfer.getAccountFrom()).getUsername() + "\nTo: " + getUserByUserId(transfer.getAccountTo()).getUsername() + "\nType: " + transfer.returnType()
						+ "\nStatus: " + transfer.returnStatus()  + "\nAmount: " + formatBalanceToCurrency(transfer.getAmount()));
}

public List<Transfer> getAllTransfersByUserId(Long userId) {
    List<Transfer> transferList = new ArrayList<>();
    Transfer[] transferArray = null;
    try {
    transferArray = restTemplate.exchange(BASE_URL + "accounts/" + userId + "/transfers", HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
    } catch (RestClientResponseException e) {
    	throw new TransferServiceException(e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
    }
    transferList = Arrays.asList(transferArray);
    return transferList; 
}

public List<Transfer> getAllPendingTransfers(Long userId) {
    List<Transfer> transferList = new ArrayList<>();
    Transfer[] transferArray = null;
    try {
    transferArray = restTemplate.exchange(BASE_URL + "accounts/" + userId + "/transfers?pending", HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
    } catch (RestClientResponseException e) {
    	throw new TransferServiceException(e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
    }
    transferList = Arrays.asList(transferArray);
    return transferList; 
}

public void displayApproveRejectTransferMenu() {
	final String approve = "Approve";
	final String reject = "Reject";
	final String neither = "Don't approve or reject";
	final String[] menuArray = new String[]{approve, reject, neither};
	System.out.println("\n1: " + menuArray[0]);
	System.out.println("2: " + menuArray[1]);
	System.out.println("0: " + menuArray[2]);
	System.out.println("------------------");
}

public void displayTransfersByUserId(Long userId, List<Transfer> transferList) {
	
	System.out.println("------------------------------------------------");
	System.out.println("Transfers: ");
	System.out.printf("%-5s %-15s %-15s", "ID", "From/To", "amount");
	System.out.println("\n------------------------------------------------");
//	List<Transfer> list = getAllTransfersByUserId(userId);
	for(Transfer t:transferList) {
		String nameTo = getUserByUserId(t.getAccountTo()).getUsername();
		String nameFrom = getUserByUserId(t.getAccountFrom()).getUsername();
		if(t.getAccountFrom() == userId) {
			System.out.printf("%-5s %-15s %-15s\n", t.getTransferId(), "To: " +  nameTo, formatBalanceToCurrency(t.getAmount()));
			
		}
		else if(t.getAccountTo() == userId) {
			System.out.printf("%-5s %-15s %-15s\n", t.getTransferId(), "From: " + nameFrom, formatBalanceToCurrency(t.getAmount()));
		}
	}
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

public HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
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