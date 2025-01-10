package com.cts.AirTicket.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.AirTicket.model.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

	Optional<Passenger> findByUsername(String username);
    
}



