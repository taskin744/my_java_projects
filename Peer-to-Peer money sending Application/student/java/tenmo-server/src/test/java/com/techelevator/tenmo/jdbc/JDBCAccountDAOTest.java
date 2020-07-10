package com.techelevator.tenmo.jdbc;

import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.techelevator.tenmo.dao.UserSqlDAO;
import com.techelevator.tenmo.model.Account;


public class JDBCAccountDAOTest {

	private static SingleConnectionDataSource dataSource;
	private JDBCAccountDAO dao = null;
	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	private UserSqlDAO userSqlDao = new UserSqlDAO(jdbcTemplate);
	Long id;

	@BeforeClass
	public static void setUpDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/tenmo");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}

	@AfterClass
	public static void tearDownAfterClass() throws SQLException {
		dataSource.destroy();
	}
	
	@Before
	public void setup() {
	userSqlDao.create("tester", "tester");
	id = (long)userSqlDao.findIdByUsername("tester");
	
	String sqlCreateAccount = "INSERT INTO accounts(account_id, user_id, balance) " +
							  "VALUES(5000, ?, 1000)";
	jdbcTemplate.update(sqlCreateAccount, id);
	dao = new JDBCAccountDAO(jdbcTemplate);
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void get_balance_by_user_id() {
		String sql = "SELECT balance FROM accounts WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
		results.next();
		double balance = dao.getBalanceByUserId(id);
		assertEquals(results.getDouble(1), balance, .05);
}
	
	@Test
	public void get_account_by_user_id() {
		Account account = new Account();
		account.setAccountId(5000L);
		account.setUserId(id);
		account.setBalance(BigDecimal.valueOf(1000.00));
		Account test = dao.getAccountByUserId(id);
		assertAccountSame(account, test);
		}
	
	@Test
	public void update_balance_send_money() {
		double start = dao.getAccountByUserId(id).getBalance().doubleValue(); 
		dao.updateBalanceSendMoney(500, id);
		assertEquals(1000, start, 0.05);
		assertEquals(500, dao.getAccountByUserId(id).getBalance().doubleValue(), 0.05);
	}
	
	@Test
	public void update_balance_get_money() {
		double start = dao.getAccountByUserId(id).getBalance().doubleValue(); 
		dao.updateBalanceGetMoney(500, id);
		assertEquals(1000, start, 0.05);
		assertEquals(1500, dao.getAccountByUserId(id).getBalance().doubleValue(), 0.05);
	}
	
	
	private void assertAccountSame(Account expected, Account actual) {
		assertEquals(expected.getAccountId(), actual.getAccountId());
		assertEquals(expected.getUserId(), actual.getUserId());
		assertEquals(expected.getBalance().doubleValue(), actual.getBalance().doubleValue(), 0.05);
	}
							
}

