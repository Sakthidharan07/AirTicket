package com.cts.AirTicket.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.AirTicket.dto.SeatDTO;
import com.cts.AirTicket.exception.ResourceNotFoundException;
import com.cts.AirTicket.exception.SeatUnavailableException;
import com.cts.AirTicket.model.Flight;
import com.cts.AirTicket.model.Seat;
import com.cts.AirTicket.repository.FlightRepository;
import com.cts.AirTicket.repository.SeatRepository;

@Service
public class SeatService {
	private static final Logger logger = LoggerFactory.getLogger(SeatService.class);

    @Autowired
    private SeatRepository seatRepository;
    
    @Autowired
    private FlightRepository flightRepository;
    
    public Seat createSeat(SeatDTO seatDTO) {
    	logger.info("Creating seat");
        Flight flight = flightRepository.findById(seatDTO.getFlightId())
        		.orElseThrow(() -> new RuntimeException("Invalid flight number"));
        int capacity = flight.getSeats().size();
        if(capacity < flight.getTotalCapacity()) {
        Seat seat = new Seat();
        seat.setAvailability(true);
        seat.setFlight(flight);
        seat.setPrice(seatDTO.getPrice());
        seat.setSeatnumber(seatDTO.getSeatnumber());
        flight.getSeats().add(seat);
    	return seatRepository.save(seat);
        }
        else {
        	logger.warn("Flight capacity has been reached for flight: {}", seatDTO.getFlightId());
        	throw new SeatUnavailableException("Flight Capacity has been reached");
        }
    }

    public Seat updateSeat(Long id, SeatDTO seatDTO) {
    	logger.info("Updating seat with id: {}", id);
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
        seat.setSeatnumber(seatDTO.getSeatnumber());
        seat.setAvailability(seatDTO.isAvailability());
        seat.setPrice(seatDTO.getPrice());
        Seat updatedSeat = seatRepository.save(seat);
        logger.info("Seat updated successfully");
        return updatedSeat;
    }

    public void deleteSeat(Long id) {
    	logger.info("Deleting seat with id: {}", id);
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
        seatRepository.delete(seat);
        logger.info("Seat deleted successfully: {}", id);
    }

    public Seat getSeatById(Long id) {
    	logger.info("Fetching seat with id: {}", id);
        return seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
    }

    public List<Seat> getSeatsByFlightId(Long flightId) {
    	logger.info("Fetching seats for flight id: {}", flightId);
        return seatRepository.findByFlightId(flightId);
    }
}
