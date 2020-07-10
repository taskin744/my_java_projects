package com.techelevator.tenmo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransferDAO;

import com.techelevator.tenmo.dao.UserSqlDAO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

//@PreAuthorize("isAuthenticated()")
@RestController
public class TenmoController {

	private UserSqlDAO userSqlDao;
	private AccountDAO accountDao;
	private TransferDAO transferDao;
	
	public TenmoController(UserSqlDAO userSqlDao, AccountDAO accountDao, TransferDAO transferDao) {
		this.userSqlDao = userSqlDao;
		this.accountDao = accountDao;
		this.transferDao = transferDao; 
	}
	
	//return view balance
	@RequestMapping(path = "accounts/" + "{id}", method = RequestMethod.GET)
	public double getBalance(@Valid @PathVariable Long id, @RequestParam(required = false) String balance) {
		return accountDao.getBalanceByUserId(id);
	}
	
	//List users
	@RequestMapping(path = "users", method = RequestMethod.GET)
	public List<User> listAll() {
		return userSqlDao.findAll();				
	}
	 //create transfer object
	@RequestMapping(path = "transfers", method = RequestMethod.POST)
	public Transfer transfer(@Valid @RequestBody Transfer transfer) {
		transferDao.saveTransfer(transfer.getTransferTypeId(), transfer.getTransferStatusId(), transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
		return transfer;
	}
	//update balances after transfer object creation?
	@RequestMapping(path = "transfers/",  method = RequestMethod.PUT)
	public Transfer transferUpdateAccountsAndStatusIdApproved(@Valid @RequestBody Transfer transfer) {
		accountDao.updateBalanceSendMoney(transfer.getAmount(), transfer.getAccountFrom());
		accountDao.updateBalanceGetMoney(transfer.getAmount(), transfer.getAccountTo());
		transferDao.updateTransferStatus(transfer.getTransferId(), 2L);
		return transfer;
	}
	
	@RequestMapping(path = "transfers/" + "{id}", method = RequestMethod.PUT)
	public void transferUpdateStatusRejected(@Valid @PathVariable Long id) {
		transferDao.updateTransferStatus(id, 3L);
	}
	
	// List all transfers by userId
	@RequestMapping(path = "accounts/" +"{id}" + "/transfers", method = RequestMethod.GET)
	public List<Transfer> transferList(@Valid @PathVariable Long id, @RequestParam(required = false) String pending){
		if (pending != null) {
			return transferDao.getAllPendingTransfers(id);
		} 
		return transferDao.getAllTransfersByUserId(id);
	}
	
	@RequestMapping(path = "transfers/" + "{id}", method = RequestMethod.GET)
	public Transfer getDetails(@Valid @PathVariable Long id) {
		return transferDao.getDetailsByTransferId(id);
	}
	
	@RequestMapping(path = "users/" + "{id}", method = RequestMethod.GET)
	public User userName(@Valid @PathVariable Long id) {
		return userSqlDao.getUserByUserId(id);			
	}

	
}

