package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

public interface AccountDAO {
	
	double getBalanceByUserId(Long userId);

	Account updateBalanceGetMoney(double transferAmount, Long userId);

	Account updateBalanceSendMoney(double transferAmount, Long userId);

	
}
