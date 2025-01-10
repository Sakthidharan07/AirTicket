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

import com.cts.AirTicket.dto.SeatDTO;
import com.cts.AirTicket.model.Flight;
import com.cts.AirTicket.model.Seat;
import com.cts.AirTicket.service.SeatService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/seats")
@Validated
public class SeatController {
    @Autowired
    private SeatService seatService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Seat> createSeat(@Valid @RequestBody SeatDTO seatDTO) {
        return new ResponseEntity<>(seatService.createSeat(seatDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Seat> updateSeat(@PathVariable Long id, @Valid @RequestBody SeatDTO seatDTO) {
        return new ResponseEntity<>(seatService.updateSeat(id, seatDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long id) {
        seatService.deleteSeat(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Seat> getSeatById(@PathVariable Long id) {
        return new ResponseEntity<>(seatService.getSeatById(id), HttpStatus.OK);
    }

    @GetMapping("/flight/{flightId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<List<Seat>> getSeatsByFlightId(@PathVariable Long flightId) {
        return new ResponseEntity<>(seatService.getSeatsByFlightId(flightId), HttpStatus.OK);
    }
}