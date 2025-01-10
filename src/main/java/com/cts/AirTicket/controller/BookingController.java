package com.cts.AirTicket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.AirTicket.dto.BookingDTO;
import com.cts.AirTicket.model.Airport;
import com.cts.AirTicket.model.Booking;
import com.cts.AirTicket.model.Flight;
import com.cts.AirTicket.model.Passenger;
import com.cts.AirTicket.model.Seat;
import com.cts.AirTicket.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
@Validated
public class BookingController { // This is booking controller
    @Autowired
    private BookingService bookingService;

    @PostMapping()
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingDTO bookingDTO) {
        Booking booking = convertToEntity(bookingDTO);
        return new ResponseEntity<>(bookingService.createBooking(booking), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @Valid @RequestBody BookingDTO bookingDTO) {
        Booking booking = convertToEntity(bookingDTO);
        return new ResponseEntity<>(bookingService.updateBooking(id, booking), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return new ResponseEntity<>(bookingService.getBookingById(id), HttpStatus.OK);
    }

    @GetMapping("/passenger/{passengerId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<Booking>> getBookingsByPassengerId(@PathVariable Long passengerId) {
        return new ResponseEntity<>(bookingService.getBookingsByPassengerId(passengerId), HttpStatus.OK);
    }

    private Booking convertToEntity(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setId(bookingDTO.getId());
        booking.setSource(bookingDTO.getSource());
        booking.setDestination(bookingDTO.getDestination());
        booking.setPassenger(new Passenger());
        booking.getPassenger().setId(bookingDTO.getPassengerId());
        booking.setFlight(new Flight());
        booking.getFlight().setId(bookingDTO.getFlightId());
        booking.setSeat(new Seat());
        booking.getSeat().setSeatnumber(bookingDTO.getSeatnumber());
        return booking;
    }
}