package com.cts.AirTicket.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.AirTicket.dto.PassengerDTO;
import com.cts.AirTicket.exception.ResourceNotFoundException;
import com.cts.AirTicket.model.Passenger;
import com.cts.AirTicket.repository.PassengerRepository;
@Service
public class PassengerService {
	private static final Logger logger = LoggerFactory.getLogger(PassengerService.class);
	
    @Autowired
    private PassengerRepository passengerRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    public Passenger registerPassenger(Passenger passenger) {
    	logger.info("Registering passenger");
    	passenger.setPassword(passwordEncoder.encode(passenger.getPassword()));
        Passenger savedPassenger = passengerRepository.save(passenger);
        logger.info("Passenger registered successfully");
        return savedPassenger;

    }

    public Passenger updatePassenger(Long id, Passenger passengerDetails) {
    	logger.info("Updating passenger with id: {}", id);
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));
        passenger.setPassword(passwordEncoder.encode(passenger.getPassword()));
        passenger.setName(passengerDetails.getName());
        passenger.setUsername(passengerDetails.getUsername());
        passenger.setEmail(passengerDetails.getEmail());
        passenger.setRole(passengerDetails.getRole());
        Passenger updatedPassenger = passengerRepository.save(passenger);
        logger.info("Passenger updated successfully");
        return updatedPassenger;
    }

    public void deletePassenger(Long id) {
    	logger.info("Deleting passenger with id: {}", id);
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));
        passengerRepository.delete(passenger);
        logger.info("Passenger deleted successfully: {}", id);
    }

    public Passenger getPassengerById(Long id) {
    	logger.info("Fetching passenger with id: {}", id);
        return passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));
    }
}