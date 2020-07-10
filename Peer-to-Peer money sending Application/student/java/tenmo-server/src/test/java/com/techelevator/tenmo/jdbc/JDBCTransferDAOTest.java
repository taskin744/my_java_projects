package com.techelevator.tenmo.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.techelevator.tenmo.model.Transfer;

public class JDBCTransferDAOTest {

	private static SingleConnectionDataSource dataSource;
	private JDBCTransferDAO dao = null;
	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	
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
	
	String sqlCreateTransfer = "INSERT INTO transfers(transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
							  "VALUES(5000, 1, 1, 1, 2, 500)";
	jdbcTemplate.update(sqlCreateTransfer);
	dao = new JDBCTransferDAO(jdbcTemplate);
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback(); 
	}
	
	@Test
	public void get_all_transfer_by_user_id() {
		List<Transfer> transferList = new ArrayList<>();
		Long userId = 1L;
		String sqlGetAllTransfers = "SELECT COUNT(*) FROM transfers join accounts on accounts.account_id = transfers.account_to OR  "
				+ "accounts.account_id = transfers.account_from where user_id = ?"; 
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllTransfers, userId);
		results.next();
		transferList = dao.getAllTransfersByUserId(userId);
		assertNotNull(transferList);
		assertEquals(results.getInt(1), transferList.size());
	}
	
	@Test 
	public void get_All_pending_transfers() {
	
		List<Transfer> pendingList = new ArrayList<>();
		String sqlGetAllTransfers = "SELECT COUNT(*) FROM transfers join accounts on "
				+ "accounts.account_id = transfers.account_from where user_id = ? AND transfers.transfer_status_id = 1"; 
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllTransfers, id);
		results.next(); 
			pendingList = dao.getAllPendingTransfers(id);	
		assertNotNull(pendingList);
		assertEquals(results.getInt(1), pendingList.size());
	}
	
	@Test
	public void get_details_by_transfer_id() {
		Transfer transfer = new Transfer();
		transfer.setTransferId(5000L);
		transfer.setTransferTypeId(1L);
		transfer.setTransferStatusId(1L);
		transfer.setAccountFrom(1L);
		transfer.setAccountTo(2L);
		transfer.setAmount(500);
		Transfer test = dao.getDetailsByTransferId(5000L);
		assertTransferSame(transfer, test);
	}
	
	@Test
	public void test_save_transfer_sort_of() {
		assertNotNull(saveTransfer(1L, 1L, 1L, 2L, 500.00));
	}

	private int saveTransfer(Long transferTypeId, Long transferStatusId, Long accountFrom, Long accountTo, Double amount) {
		String sqlSaveTransfer = "INSERT INTO transfers(transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
								"VALUES(?, ?, ?, ?, ?)";
		int test = jdbcTemplate.update(sqlSaveTransfer, transferTypeId, transferStatusId, accountFrom, accountTo, amount);
		return test;
	} 
	
	private void assertTransferSame(Transfer e, Transfer a) {
	assertEquals(e.getTransferId(), a.getTransferId());
	assertEquals(e.getTransferTypeId(), a.getTransferTypeId());
	assertEquals(e.getTransferStatusId(), a.getTransferStatusId());
	assertEquals(e.getAccountFrom(), a.getAccountFrom());
	assertEquals(e.getAccountTo(), a.getAccountTo());
	assertEquals(e.getAmount(), a.getAmount(), 0.05);
	}	
}

