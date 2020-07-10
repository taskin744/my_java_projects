package com.techelevator.tenmo;

import java.util.ArrayList;
import java.util.List;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.tenmo.services.UserService;
import com.techelevator.view.ConsoleService;

public class App {

	private static final String API_BASE_URL = "http://localhost:8080/";

	private static final String MENU_OPTION_EXIT = "Exit";
	private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN,
			MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS,
			MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS,
			MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };

	private AuthenticatedUser currentUser;
	private ConsoleService console;
	private AuthenticationService authenticationService;
	private AccountService accountService;
	private TransferService transferService;
	private UserService userService;
	private Long selectedUserId;
	private double transferAmount;
	private Long currentUserId;
	private Long choice = 0L;
	private Long requestTransferId = 0L;
	private Transfer transferForSend;
	private Transfer transferForRequest;
	
	
	public static void main(String[] args) {
		App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL),
				new AccountService(API_BASE_URL), new TransferService(API_BASE_URL), new UserService(API_BASE_URL));
		app.run();
	}

	public App(ConsoleService console, AuthenticationService authenticationService, AccountService accountService,
			TransferService transferService, UserService userService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.accountService = accountService;
		this.transferService = transferService;
		this.userService = userService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");

		registerAndLogin();
		currentUserId = (long) currentUser.getUser().getId();
		mainMenu();
	}

	private void mainMenu() {
		while (true) {
			String choice = (String) console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if (MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if (MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if (MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if (MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if (MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if (MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
				currentUserId = (long) currentUser.getUser().getId();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}
	//Displays current balance of logged in user**************
	private void viewCurrentBalance() {
		Double balance = accountService.getBalanceByUserId(currentUserId);
		System.out.println("Your current balance is: " + accountService.formatBalanceToCurrency(balance));
	}
	//Initiates transfer to send another user monies****************************
	private void sendBucks() {
		while (true) {
		List<User> userList = userService.displayUsers("Please select a user to send bucks to:");
		List<Long> idList = new ArrayList<>();
		for (User u: userList) {
			idList.add((long)u.getId());
		}	

		try {
			choice = Long.parseLong(console.getUserInput("Enter ID of user you are sending to (0 to cancel)"));
		} catch (NumberFormatException e) {
			System.out.println("\n*****Please enter a number!*****\n");
			sendBucks();
		}
		if (choice == 0) {
			mainMenu();
		}
		else if (choice == currentUserId || !(idList.contains(choice))) {
			System.out.println("\n*****Please select a valid Id! (You can not send money to yourself!)*****\n");
			sendBucks();
		}
		else {
			selectedUserId = choice;
		}
		try {
			transferAmount = Double.parseDouble(console.getUserInput("Enter amount"));
		} catch (NumberFormatException e) {
			sendBucks();
		}
		Transfer transfer = new Transfer(2L, 2L, currentUserId, selectedUserId, transferAmount);
		if (transfer.getAmount() <= accountService.getBalanceByUserId(currentUserId)) {
			transferForSend = transferService.startTransfer(transfer);
			transferService.makeTransferUpdateAccounts(transferForSend);
			System.out.println("\nTransfering " + accountService.formatBalanceToCurrency(transferAmount)
					+ " from User Id: " + currentUserId + " to " + "User Id: " + selectedUserId);
			System.out.println("\nTRANSACTION APPROVED, you have been returned to the main menu.\n");
			mainMenu();
		} else {
			System.out.println("\nInsufficient Funds\n");
		}

	}
	}
	//Displays full transfer history*****************
	private void viewTransferHistory() {
		List<Transfer> myList = transferService.getAllTransfersByUserId(currentUserId);
		int exitChoice = 0;
		transferService.displayTransfersByUserId(currentUserId, myList);
		
		List<Long> idList = new ArrayList<>();
		for (Transfer t: myList) {
			idList.add(t.getTransferId());
	}
		
		try {
			choice = transferService.getUserChoice();
		} catch (NumberFormatException e) {
			System.out.println("\n**That wasn't a number Junior, try again.**");
			viewTransferHistory();
	}
		if (choice == 0) {
			mainMenu();
		} else if (!(idList.contains(choice))) {
			System.out.println("\n**Please select a valid Transfer ID number.**");
			viewTransferHistory();
	}

		transferService.getDetailsByTransferId(choice);
		transferService.printTransferDetails(choice);
		try {
			exitChoice = Integer.parseInt(
					console.getUserInput("\nWould you like to return to main menu (enter any key), or exit (2)?"));
		} catch (NumberFormatException e) {
			mainMenu();
	}
		if (exitChoice == 2) {
			System.out.println("\nGoodbye Darling <(^^<)<(^^)>(>^^>)");
			System.exit(1);
		} else {
			mainMenu();
	}
}
	//requests funds from another user***************************
	private void requestBucks() {
		List<User> userList = userService.getAllUsers();
		List<Long> idList = new ArrayList<>();
		for (User u: userList) {
			idList.add((long)u.getId());
		}	
		while(true) {
		userService.displayUsers("Please select a user to request bucks from:");
		try {
		selectedUserId = Long.parseLong(console.getUserInput("Enter ID of user you are requesting from (0 to cancel)"));
		} catch (NumberFormatException e) {
			System.out.println("\n***Invalid selection, please select a number***\n");
			requestBucks();
		}
			
		if (selectedUserId == 0) {
				System.out.println("\nReturning to main menu");
				mainMenu();
			}
		else if (!(idList.contains(selectedUserId)) || selectedUserId == currentUserId) {
			System.out.println("\n**Must select valid USER ID (Cannot select yourself)**\n");
			requestBucks();
		}
		try {
		transferAmount = Double.parseDouble(console.getUserInput("Enter amount"));
		} catch (NumberFormatException e) {
			System.out.println("\n*****Invalid selection, please enter only numbers.*****\n");
			requestBucks();
		}

		Transfer transfer = new Transfer(1L, 1L, selectedUserId, currentUserId, transferAmount);
		transferForRequest = transferService.startTransfer(transfer);
		System.out.println("\nPENDING REQUEST FOR " + accountService.formatBalanceToCurrency(transferAmount)
		+ " from " + userService.getUserByUserId(selectedUserId).getUsername());
		System.out.println("Awaiting approval from " + userService.getUserByUserId(selectedUserId).getUsername() + ", you have been return to the main menu.");
		mainMenu();
	}	
}
	//view all currently pending requests (only those that are asking for money from logged in user*************************
	private void viewPendingRequests() {
		List<Transfer> pendingList = transferService.getAllPendingTransfers(currentUserId);
		List<Long> pendingListId = new ArrayList<>();
		for (Transfer t : pendingList) {
		pendingListId.add(t.getTransferId());
		}

		    while (true) {
		    if (pendingList.size() < 1) {
		        System.out.println("**No pending transfers, returning to main menu**");
		        mainMenu();
		    }
		    transferService.displayTransfersByUserId(currentUserId, pendingList);
		    try {
		    requestTransferId = Long.parseLong(console.getUserInput("\nPlease enter a transfer ID to approve/reject (0 to cancel)"));
		    } catch (NumberFormatException e) {
		        System.out.println("\n**Not a number, try again**\n");
		        viewPendingRequests();
		 
		    } if (requestTransferId == 0) {
		        System.out.println("\nReturning to main menu");
		        mainMenu();
		    }
		      else if (!(pendingListId.contains(requestTransferId))) {
		        	System.out.println("\n*****Please select a VALID ID*****\n");
		        	viewPendingRequests();
		    } approveOrDenyPendingRequests(); 
		}
	}

	//Menu to adjust request status*********************
	private void approveOrDenyPendingRequests() {
		transferForRequest = transferService.getDetailsByTransferId(requestTransferId);
		while (true) {
		transferService.displayApproveRejectTransferMenu();
		try {
		choice = Long.parseLong(console.getUserInput("Please choose an option"));
		} catch (NumberFormatException e) {
			System.out.println("\n*****Not a number, please try again\n*****");
			approveOrDenyPendingRequests(); 
		}
		
		if (choice != 0 && choice != 1 && choice != 2) {
			System.out.println("\n*****Please select a valid option*****\n");
			viewPendingRequests();
		}
		else if (choice == 0) {
			System.out.println("*****Pending transfer has not been affected, returning to main menu*****");
			mainMenu();
		} else if (choice == 1) {
			if (accountService.getBalanceByUserId(currentUserId) >= transferForRequest.getAmount()) {
			System.out.println("\n*****Transfer Request Has Been Approved.  Returned to pending transfer list.****\n");
			transferService.makeTransferUpdateAccounts(transferForRequest);
			viewPendingRequests();
			}
			else {
				System.out.println("\n*****You don't have enough funds to fulfill this request*****");
				viewPendingRequests();
			}
		} else if (choice == 2) {
			System.out.println("\n*****Selected transfer request has been denied.  Returning to pending transfer list.*****");
			transferService.updateTransferRejected(transferForRequest, transferForRequest.getTransferId());
			viewPendingRequests();
		}
	}
}

	//*************PROVIDED METHODS*********************
	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while (!isAuthenticated()) {
			String choice = (String) console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
		while (!isRegistered) // will keep looping until user is registered
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				authenticationService.register(credentials);
				isRegistered = true;
				System.out.println("Registration successful. You can now login.");
			} catch (AuthenticationServiceException e) {
				System.out.println("REGISTRATION ERROR: " + e.getMessage());
				System.out.println("Please attempt to register again.");
			}
		}
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) // will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: " + e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}

	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
		
	}
}
