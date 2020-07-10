package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_END = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE,
			MAIN_MENU_OPTION_END };
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY,
			PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION };

	private Menu menu;
	private List<Item> inventory;
	private BigDecimal balance = new BigDecimal(0);

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
		inventory = new ArrayList<>();

	}

	public void getBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public void insertMoney(BigDecimal money) throws IOException {
		Scanner moneyScanner = new Scanner(System.in);
		int moneyInput = moneyScanner.nextInt();
		if (moneyInput == 1 || moneyInput == 2 || moneyInput == 5 || moneyInput == 10) {

			balance = balance.add(BigDecimal.valueOf(moneyInput));
			System.out.println("Inserted $" + moneyInput + " dollars.");
		} else {
			System.out.println("Sorry invalid input try using only 1 or 2 or 5 or 10");
		}
		// ****************************log feeding money*************************

		File transactionLog = new File("Log.txt");

		if (!transactionLog.exists()) {
			transactionLog.createNewFile();
		}

		PrintWriter logWriter = new PrintWriter(new FileWriter(transactionLog, true));
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-uuuu hh:mm:ss a");
		logWriter.println(formatter.format(dateTime) + " FEED MONEY: $" + moneyInput + ".00 $" + balance + ".00");

		logWriter.flush();
		logWriter.close();
	}

	public void purchaseItem() throws IOException {
		for (Item i : inventory) {
			System.out.println(i.getSlot() + " | " + i.getName() + " | $" + i.getPrice());
		}
		System.out.println("Please choose a slot number: ");
		Scanner slotScanner = new Scanner(System.in);
		String slotInput = slotScanner.nextLine().toUpperCase();

		int itemIndex = -1;
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).getSlot().equals(slotInput)) {
				itemIndex = i;
			}
		}
		if (itemIndex == -1) {
			System.out.println("invalid entry for an item slot");
		} else {
			Item selectedItem = inventory.get(itemIndex);
			System.out.println("selected item is " + selectedItem.getName());
			if (selectedItem.isSoldOut()) {
				System.out.println("The item you chose is sold out, please make another selection");
			} else {
				if (balance.compareTo(selectedItem.getPrice()) >= 0) {

					balance = balance.subtract(selectedItem.getPrice());
					selectedItem.decreaseQuantity();

					System.out.println(selectedItem.getConsumeMessage());

					/**********************************
					 * log purchase item
					 ***********************************/

					File transactionLog = new File("Log.txt");

					if (!transactionLog.exists()) {
						transactionLog.createNewFile();
					}

					PrintWriter logWriter = new PrintWriter(new FileWriter(transactionLog, true));
					LocalDateTime dateTime = LocalDateTime.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-uuuu hh:mm:ss a");
					logWriter.println(formatter.format(dateTime) + " " + selectedItem.getName() + " "
							+ selectedItem.getSlot() + "  $" + balance.add(selectedItem.getPrice()) + " $" + balance);

					logWriter.flush();
					logWriter.close();

				} else {

					System.out.println("Insufficient balance, please insert more money");
					System.out.println("The price of the item you selected is: $" + selectedItem.getPrice()
							+ " You need $" + (selectedItem.getPrice().subtract(balance) + ", more."));
				}
			}

		}

	}

	public void returnChange(BigDecimal money) throws IOException {
		BigDecimal quarter, dime, nickel;

		money = money.multiply(BigDecimal.valueOf(100));
		quarter = money.divide(BigDecimal.valueOf(25));
		quarter = quarter.setScale(0, RoundingMode.DOWN);
		money = money.subtract(quarter.multiply(BigDecimal.valueOf(25)));
		dime = money.divide(BigDecimal.valueOf(10));
		dime = dime.setScale(0, RoundingMode.DOWN);
		money = money.subtract(dime.multiply(BigDecimal.valueOf(10)));
		nickel = money.divide(BigDecimal.valueOf(5));
		nickel = nickel.setScale(0, RoundingMode.DOWN);

		System.out.println(quarter + " quarters");
		System.out.println(dime + " dimes");
		System.out.println(nickel + " nickels");

		/**************************************
		 * log return change *****************************************
		 */

		File transactionLog = new File("Log.txt");

		if (!transactionLog.exists()) {
			transactionLog.createNewFile();
		}

		PrintWriter logWriter = new PrintWriter(new FileWriter(transactionLog, true));
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-uuuu hh:mm:ss a");
		logWriter.println(formatter.format(dateTime) + " GIVE CHANGE: $" + balance + " $0.00");

		logWriter.flush();
		logWriter.close();

	}

	public void run() throws IOException {
		File vendingMachineItems = new File("vendingmachine.csv");
		Scanner fileInput = new Scanner(vendingMachineItems);
		while (fileInput.hasNextLine()) {
			String[] lineInput = fileInput.nextLine().split("\\|");
			String name = lineInput[1];
			String slot = lineInput[0];
			BigDecimal price = new BigDecimal(lineInput[2]);
			String type = lineInput[3];

			if (type.contentEquals("Chip")) {
				Chip chip = new Chip(name, price, slot, type, 5);
				inventory.add(chip);
			} else if (type.contentEquals("Gum")) {
				Gum gum = new Gum(name, price, slot, type, 5);
				inventory.add(gum);
			} else if (type.contentEquals("Candy")) {
				Candy candy = new Candy(name, price, slot, type, 5);
				inventory.add(candy);
			} else {
				Beverage beverage = new Beverage(name, price, slot, type, 5);
				inventory.add(beverage);
			}

		}

		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				for (Item i : inventory) {
					System.out.println(i.getSlot() + " | " + i.getName() + " | $" + i.getPrice());
				}
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				while (true) {
					System.out.println("Your balance is: $" + balance);
					String options = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
					if (options.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
						System.out.println("Please insert money in amounts of ($1, $2, $5, or $10)");
						insertMoney(balance);

					} else if (options.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
						purchaseItem();

					} else if (options.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
						returnChange(balance);
						balance = BigDecimal.ZERO;
						break;
					}
				}
			} else if (choice.equals(MAIN_MENU_OPTION_END)) {
				System.out.println("Good Bye!");
				System.exit(1);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
