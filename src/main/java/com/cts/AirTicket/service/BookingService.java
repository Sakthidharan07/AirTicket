package com.cts.AirTicket.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.AirTicket.dto.BookingDTO;
import com.cts.AirTicket.dto.FlightDTO;
import com.cts.AirTicket.dto.PassengerDTO;
import com.cts.AirTicket.exception.ResourceNotFoundException;
import com.cts.AirTicket.exception.SeatUnavailableException;
import com.cts.AirTicket.model.Airport;
import com.cts.AirTicket.model.Booking;
import com.cts.AirTicket.model.Flight;
import com.cts.AirTicket.model.Passenger;
import com.cts.AirTicket.model.Seat;
import com.cts.AirTicket.repository.AirportRepository;
import com.cts.AirTicket.repository.BookingRepository;
import com.cts.AirTicket.repository.FlightRepository;
import com.cts.AirTicket.repository.PassengerRepository;
import com.cts.AirTicket.repository.SeatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class BookingService {
	private static final Logger logger = LoggerFactory.getLogger(BookingService.class);
	
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private SeatRepository seatRepository;
    
    @Autowired
    private FlightRepository flightRepository;
    
    @Autowired
    private PassengerRepository passengerRepository;
    
    public Booking createBooking(Booking booking) {
       logger.info("Creating booking");
       
       Flight flight= flightRepository.findById(booking.getFlight().getId())
    		   .orElseThrow(() -> new ResourceNotFoundException("No flight is found for the given id"));     
       Seat seat=seatRepository.findBySeatnumber(booking.getSeat().getSeatnumber())
    		   .orElseThrow(() -> new ResourceNotFoundException("No booking is found for the given id"));      
       Passenger passenger=passengerRepository.findById(booking.getPassenger().getId())
    		   .orElseThrow(() -> new ResourceNotFoundException("No passenger is found for the given id"));
   
       if(seat.isAvailability()==true)
       {
    	   booking.setFlight(flight);
           booking.setPassenger(passenger);
    	   booking.setSeat(seat);
    	   seat.setAvailability(false);
    	   Booking savedBooking = bookingRepository.save(booking);
           logger.info("Booking created successfully");
           return savedBooking;
       }
       else
       {
    	   logger.warn("Seat unavailable for booking: {}", booking);
    	   throw new SeatUnavailableException("Seat is unavailable");
       }
    }

    public Booking updateBooking(Long id, Booking bookingDetails) {
    	logger.info("Updating booking with id: {}", id);
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        booking.setSource(bookingDetails.getSource());
        booking.setDestination(bookingDetails.getDestination());
        booking.setFlight(bookingDetails.getFlight());
        Booking updatedBooking = bookingRepository.save(booking);
        logger.info("Booking updated successfully");
        return updatedBooking;
    }

    public void cancelBooking(Long id) {
    	logger.info("Cancelling booking with id: {}", id);
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        Seat seat = booking.getSeat();
        bookingRepository.delete(booking);
        seat.setAvailability(true);
        seatRepository.save(seat);
        logger.info("Booking cancelled successfully: {}", id);
    }

    public Booking getBookingById(Long id) {
    	logger.info("Fetching booking with id: {}", id);
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
    }

    public List<Booking> getBookingsByPassengerId(Long passengerId) {
    	logger.info("Fetching bookings for passenger id: {}", passengerId);
        return bookingRepository.findByPassengerId(passengerId);
    }
    
}