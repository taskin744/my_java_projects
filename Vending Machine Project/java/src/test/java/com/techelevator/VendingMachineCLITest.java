package com.techelevator;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

import com.techelevator.view.Menu;

public class VendingMachineCLITest {

	VendingMachineCLI testMachine = new VendingMachineCLI(null);
	Item testItem = new Item(null, null, null, null, 0);
	BigDecimal testNum = new BigDecimal(2);
	File testFile = new File("vendinmachine.csv");
		///change test
	@Test
		public void have_2dollars_buy_CowTales_returns_2Quarters() throws IOException {
		BigDecimal n = new BigDecimal(2);
		testMachine.insertMoney(n);
		
		testMachine.purchaseItem();
		
		
		
		}
	}


