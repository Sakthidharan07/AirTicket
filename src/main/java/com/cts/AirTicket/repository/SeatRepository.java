package com.cts.AirTicket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.AirTicket.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long>{

	Seat findByFlightIdAndAvailability(Long id, boolean b);

	List<Seat> findByFlightId(Long flightId);

	Optional<Seat> findBySeatnumber(String id);

}
