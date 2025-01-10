package com.cts.AirTicket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.AirTicket.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	List<Booking> findByPassengerId(Long passengerId);
    
}

