package com.techelevator.tenmo.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.model.Account;

@Service
@Component
public class JDBCAccountDAO implements AccountDAO {

	private JdbcTemplate jdbcTemplate = new JdbcTemplate();
	
	public JDBCAccountDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public double getBalanceByUserId(Long userId) {
		double balance = 0.00;
		String sqlGetBalance = "SELECT balance FROM accounts WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetBalance, userId);
		results.next();
		balance = results.getDouble(1); 
		return balance;
	}
	
	public Account getAccountByUserId(Long userId) {
		Account account = null;
		String sqlGetAccount = "SELECT * FROM accounts WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAccount, userId);
		while (results.next()) {
			account = mapRowToAccount(results);
		} return account;
	}
	
	@Override
	public Account updateBalanceSendMoney(double transferAmount, Long userId) {
		Account account = null;
		String sqlSendMoney = "UPDATE accounts SET balance = (balance - ?) WHERE user_id = ?";
		jdbcTemplate.update(sqlSendMoney, transferAmount, userId);
		account = getAccountByUserId(userId);
		return account;
	}
	
	@Override
	public Account updateBalanceGetMoney(double transferAmount, Long userId) {
		Account account = null;
		String sqlGetMoney = "UPDATE accounts SET balance = (balance + ?) WHERE user_id = ?";
		jdbcTemplate.update(sqlGetMoney, transferAmount, userId);
		account = getAccountByUserId(userId);
		return account;
	}
	
		
	private Account mapRowToAccount(SqlRowSet results) {
		Account account = new Account();
		account.setAccountId(results.getLong("account_id"));
		account.setUserId(results.getLong("user_id"));
		account.setBalance(results.getBigDecimal("balance"));
		return account;
	}

}
