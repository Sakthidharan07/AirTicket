package com.cts.AirTicket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.AirTicket.model.Airport;

public interface AirportRepository extends JpaRepository<Airport, Long> {
	
}
