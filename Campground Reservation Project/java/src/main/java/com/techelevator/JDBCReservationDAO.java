package com.techelevator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.arif.Reservation;
import com.techelevator.arif.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Reservation> getReservationsFromSiteID(long siteID) {
		List<Reservation> availableRes = new ArrayList<>();
		String showRes = "SELECT * FROM reservation WHERE site_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(showRes, siteID);
		while (results.next()) {
			Reservation res = mapRowToReservation(results);
			availableRes.add(res);
		}
		return availableRes;
	}


	@Override
	public void createReservation(Long siteId, String reservationName, LocalDate arrivalDate, LocalDate departureDate) {
		String createReservation = "INSERT INTO reservation (site_id, name, from_date, to_date, create_date) VALUES ( ?, ?, ?::DATE, ?::DATE, CURRENT_DATE)";

		jdbcTemplate.update(createReservation, siteId, reservationName, arrivalDate, departureDate);

	}

	@Override
	public List<Reservation> getReservationByName(String searchedName) {
		List<Reservation> allReservationsByName = new ArrayList<>();
		String sqlGetReservationByName = "SELECT * FROM reservation WHERE name LIKE ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetReservationByName, "%" + searchedName + "%");
		while (results.next()) {
			Reservation aReservation = mapRowToReservation(results);
			allReservationsByName.add(aReservation);
		}
		return allReservationsByName;
	}

	public long getMostRecentReservationId() { // <---------- I changed this. it was getReservatinId(Reservation
												// newReservation)
		String sqlGetReservationId = "SELECT reservation_id FROM reservation ORDER BY reservation_id DESC LIMIT 1";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetReservationId);
		long reservationId = 0;
		if (results.next()) {
			reservationId = results.getLong("reservation_id");
		}
		return reservationId;
	}


	private Reservation mapRowToReservation(SqlRowSet results) {
		Reservation newReservation = new Reservation();
		newReservation.setId(results.getLong("reservation_id"));
		newReservation.setSiteId(results.getLong("site_id"));
		newReservation.setName(results.getString("name"));
		newReservation.setArrival(results.getDate("from_date").toLocalDate());
		newReservation.setDeparture(results.getDate("to_date").toLocalDate());
		newReservation.setCreated(results.getDate("create_date").toLocalDate());
		return newReservation;
	}

}
