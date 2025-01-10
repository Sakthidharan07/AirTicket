package com.cts.AirTicket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.AirTicket.model.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {

	List<Flight> findBySourceContainingAndDestinationContaining(String source, String destination);

}

