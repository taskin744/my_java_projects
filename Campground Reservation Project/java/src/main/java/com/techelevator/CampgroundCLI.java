package com.techelevator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.arif.Campground;
import com.techelevator.arif.CampgroundDAO;
import com.techelevator.arif.Park;
import com.techelevator.arif.ParkDAO;
import com.techelevator.arif.Reservation;
import com.techelevator.arif.ReservationDAO;
import com.techelevator.arif.Site;
import com.techelevator.arif.SiteDAO;
import com.techelevator.view.Menu;

public class CampgroundCLI {

	private static final String MAIN_MENU_OPTION_QUIT = "Quit";

	private static final String MENU_OPTION_RETURN_TO_MAIN = "Return to Previous Screen";

	private static final String CAMPGROUND_MENU_OPTION_DISPLAY_CAMPGROUND = "View Campgrounds";
	private static final String CAMPGROUND_MENU_OPTION_SEARCH_FOR_RESERVATION = "Search for Reservation";
	private static final String[] CAMPGROUND_MENU_OPTIONS = { CAMPGROUND_MENU_OPTION_DISPLAY_CAMPGROUND,
			CAMPGROUND_MENU_OPTION_SEARCH_FOR_RESERVATION, MENU_OPTION_RETURN_TO_MAIN };

	private static final String AVAILABLE_RESERVATION = "Search for Available Reservation";
	private static final String RETURN_TO_PREVIOUS = "Return to Previous Screen";
	private static final String[] RESERVATION_MENU = { AVAILABLE_RESERVATION, RETURN_TO_PREVIOUS };

	private Menu menu;
	private CampgroundDAO cDao;
	private ParkDAO pDao;
	private ReservationDAO rDao;
	private SiteDAO sDao;
	private String[] parkNamesArray;
	private List<Park> parkObjects;
	private List<Campground> campgroundObjects;
	private Scanner userInput;
	private List<Site> siteList;

	public static void main(String[] args) {

		CampgroundCLI application = new CampgroundCLI();
		application.run();

	}

	public CampgroundCLI() {
		this.menu = new Menu(System.in, System.out);

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		cDao = new JDBCCampgroundDAO(dataSource);
		pDao = new JDBCParkDAO(dataSource);
		rDao = new JDBCReservationDAO(dataSource);
		sDao = new JDBCSiteDAO(dataSource);

		parkNamesArray = pDao.getAllParks();
		parkObjects = pDao.showAllParks();

		campgroundObjects = cDao.showAllCampgrounds();
		// campgroundNamesArray = cDao.getAllCampgrounds();
		userInput = new Scanner(System.in);
	}

	public void run() {
		// If the banner is not good we can get rid of it
		System.out.println(
				"$$$$$$$$$              $$$$            $$$$$$$$$             $$$$            $$$$$$$$$      $$      $$    ");
		System.out.println(
				"$$                   $$    $$          $$       \\\\         $$    $$          $$       \\\\    $$    $$      ");
		System.out.println(
				"$$                  $$      $$         $$        \\\\       $$      $$         $$        \\\\   $$  $$        ");
		System.out.println(
				"$$                 $$ $$$$$  $$        $$        //      $$ $$$$$  $$        $$        //   $$$$         ");
		System.out.println(
				"$$$$$$$           $$  ______  $$       $$ _____ $$      $$  ______  $$       $$ _____ $$    $$$\\\\        ");
		System.out.println(
				"$$               $$            $$      $$              $$            $$      $$      \\\\     $$   \\\\      ");
		System.out.println(
				"$$              $$              $$     $$             $$              $$     $$       \\\\    $$     \\\\    ");
		System.out.println(
				"$$             $$                $$    $$            $$                $$    $$        \\\\   $$       \\\\  ");
		System.out.println(
				"$$$$$$$$$_____$$                  $$___$$           $$                  $$   $$         \\\\  $$         \\\\");

		while (true) {
			System.out.println();
			displayBanner();
			printHeading("Select a Park for Further Details");
			String choice = (String) menu.getChoiceFromOptions(parkNamesArray);

			if (choice.equals("Quit")) {
				System.out.println("\nGoodBye!");
				System.exit(0);
			}

			for (Park temp : parkObjects) {

				if (choice.equals(temp.toString())) {
					temp.printParkInfo();
					displayCampgroundMenu(temp.getId());

				}
			}
		}
	}

	public void displayCampgroundMenu(Long id) {

		printHeading("Select a Command");
		String secondChoice = (String) menu.getChoiceFromOptions(CAMPGROUND_MENU_OPTIONS);
		campgroundObjects = cDao.getCampgroundByParkId(id);

		if (secondChoice.equals(CAMPGROUND_MENU_OPTION_DISPLAY_CAMPGROUND)) {
			System.out.println("      Name                 \t       Open      Close      Daily Fee");
			for (Campground aCamp : campgroundObjects) {
				aCamp.printCampgroundInfo();

			}
			displayReservationMenu();
		} else if (secondChoice.equals(CAMPGROUND_MENU_OPTION_SEARCH_FOR_RESERVATION)) {
			System.out.println("What name is the reservation under?");
			String name = userInput.nextLine();
			List<Reservation> ResInfo = rDao.getReservationByName(name);

			for (Reservation res : ResInfo) {
				res.printReservationInfoByName();
			}
		}

	}

	public void displayReservationMenu() {
		printHeading("Select a Command");
		String thirdChoice = (String) menu.getChoiceFromOptions(RESERVATION_MENU);

		if (thirdChoice.equals(RETURN_TO_PREVIOUS)) {
			menu.getChoiceFromOptions(CAMPGROUND_MENU_OPTIONS);
		}

		else if (thirdChoice.equals(AVAILABLE_RESERVATION)) {

			System.out.println("Which campground (enter 0 to cancel)? __");
			String answer = userInput.nextLine();
			long campId = Long.parseLong(answer);
			System.out.println("What is the arrival date? YYYY-MM-DD");

			LocalDate userArrivalAsLocalDate = null; // <------ extracted these outside of the try to be able to use
														// after catch
			LocalDate userDepartureAsLocalDate = null;
			try { // <------- catches an invalid form of date input and returns previous menu
				String userArrival = userInput.nextLine();
				LocalDate userArrivalAsLocalDateTRY = LocalDate.parse(userArrival);
				userArrivalAsLocalDate = userArrivalAsLocalDateTRY;
				System.out.println("What is the departure date? YYYY-MM-DD");
				String userDeparture = userInput.nextLine();
				LocalDate userDepartureAsLocalDateTRY = LocalDate.parse(userDeparture);
				userDepartureAsLocalDate = userDepartureAsLocalDateTRY;
			} catch (Exception e) {
				System.out.println();
				System.out.println("Invalid date format or selection! returning previous menu.....");
				System.out.println();
				displayReservationMenu();
			}
			try {
				if (userDepartureAsLocalDate.isBefore(userArrivalAsLocalDate)) { // <----- check to see if the
																					// arrivalDate is before
																					// departureDate
					System.out
							.println("The departure date cannot be before arrival date! Returning to previous menu...");
					displayReservationMenu();

				} else {
					List<Site> availableSiteList = sDao.returnAvailableSitesList(campId, userArrivalAsLocalDate,
							userDepartureAsLocalDate);
					siteList = availableSiteList;

					System.out.println(
							"Site Number:     Max Occupancy:    Accessible?    Max RV Length:    Utilities?   Cost:");
					for (Site site : siteList) {
						System.out.println(site.toString());
					}

					System.out.println("Which site should be reserved (enter 0 to cancel)?");
					String siteToReserve = userInput.nextLine();
					int siteInput = Integer.parseInt(siteToReserve);

					System.out.println("What name should the reservation be made under?");
					String userName = userInput.nextLine();
					if (userName.isEmpty() || userName.equals(" ") || userName.contains("  ")) {
						System.out.println("Invalid name entry! Returning reservation search menu...");
						displayReservationMenu();
					}

					else {
						Long siteId = sDao.getSiteIdBySiteNumber(siteInput);
						rDao.createReservation(siteId, userName, userArrivalAsLocalDate, userDepartureAsLocalDate);
						System.out.println("The reservation has been made and " + "the confirmation id is "
								+ rDao.getMostRecentReservationId());
						System.out.println("Enjoy your stay!");
					}
				}
			} catch (Exception e) {
				// comsume the exception.
			}
		}
	}

	private LocalDate dateFormatter(String dateInput) { // <----- Made this if we have time left maybe we can use
														// it to reformat date input from user
		String[] departure = dateInput.split("/");
		int[] intArrayD = new int[departure.length];
		for (int i = 0; i < departure.length; i++) {
			intArrayD[i] = Integer.parseInt(departure[i]);
		}
		LocalDate departureDate = LocalDate.of(intArrayD[2], intArrayD[0], intArrayD[1]);
		return departureDate;
	}

	private void printHeading(String headingText) {
		System.out.println("\n" + headingText);
		for (int i = 0; i < headingText.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
	}

	public void displayBanner() {
		System.out.println("Greetings! Welcome to the...");
		printHeading("National Campsite Reservation System");
	}

}
