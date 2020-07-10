package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDAO {
	
	public Transfer getDetailsByTransferId(Long transferId);

	void saveTransfer(Long transferTypeId, Long transferStatusId, Long accountFrom, Long accountTo, Double amount);
	
	public List<Transfer> getAllPendingTransfers(Long userId);
	
	List<Transfer> getAllTransfersByUserId(Long userId);

	void updateTransferStatus(Long transferId, Long statusId);
}
