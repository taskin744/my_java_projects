package com.techelevator.tenmo.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.model.Transfer;

@Service
@Component
public class JDBCTransferDAO implements TransferDAO {

	private JdbcTemplate jdbcTemplate = new JdbcTemplate();
	
	public JDBCTransferDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<Transfer> getAllTransfersByUserId(Long userId) {
		List<Transfer> transferList = new ArrayList<>();
		String sqlGetAllTransfers = "SELECT * FROM transfers join accounts on accounts.account_id = transfers.account_to OR  "
				+ "accounts.account_id = transfers.account_from where user_id = ?"; 
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllTransfers, userId);
		while(results.next()) {
			Transfer newTransfer = mapRowToTransfer(results);
			transferList.add(newTransfer);
		}
		return transferList;
	}
	
	@Override
	public List<Transfer> getAllPendingTransfers(Long userId) {
		List<Transfer> transferList = new ArrayList<>();
		String sqlGetAllTransfers = "SELECT * FROM transfers join accounts on "
				+ "accounts.account_id = transfers.account_from where user_id = ? AND transfers.transfer_status_id = 1"; 
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllTransfers, userId);
		while(results.next()) {
			Transfer newTransfer = mapRowToTransfer(results);
			transferList.add(newTransfer);
		}
		return transferList;
	}
	
	@Override
	public Transfer getDetailsByTransferId(Long transferId) {
		Transfer transfer;
		String sqlGetSingleTransfer = "SELECT * FROM transfers WHERE transfer_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetSingleTransfer, transferId);
		results.next();
		transfer = mapRowToTransfer(results);
		return transfer;
	}
	
	@Override
	public void updateTransferStatus(Long transferId, Long statusId) {
		String sqlUpdateTransfer = "UPDATE transfers SET transfer_status_id = ? WHERE transfer_id = ?";
		jdbcTemplate.update(sqlUpdateTransfer, statusId, transferId);
	}
	
	@Override
	public void saveTransfer(Long transferTypeId, Long transferStatusId, Long accountFrom, Long accountTo, Double amount) {
		String sqlSaveTransfer = "INSERT INTO transfers(transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
								"VALUES(?, ?, ?, ?, ?)";
		jdbcTemplate.update(sqlSaveTransfer, transferTypeId, transferStatusId, accountFrom, accountTo, amount);
	} 

	private Transfer mapRowToTransfer(SqlRowSet rs) {
		Transfer x = new Transfer();
		x.setTransferId(rs.getLong("transfer_id"));
		x.setTransferTypeId(rs.getLong("transfer_type_id"));
		x.setTransferStatusId(rs.getLong("transfer_status_id"));
		x.setAccountFrom(rs.getLong("account_from"));
		x.setAccountTo(rs.getLong("account_to"));
		x.setAmount(rs.getDouble("amount"));
		return x;
	}

	
	
}
