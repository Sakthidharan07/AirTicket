package com.cts.AirTicket.controller;

import java.util.List;
import java.util.Map;

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

import com.cts.AirTicket.dto.PassengerDTO;
import com.cts.AirTicket.model.Passenger;
import com.cts.AirTicket.service.PassengerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/passengers")
@Validated
public class PassengerController {
    @Autowired
    private PassengerService passengerService;

    @PostMapping("/register")
    public ResponseEntity<Passenger> registerPassenger(@Valid @RequestBody PassengerDTO passengerDto) {
        Passenger passenger = convertToEntity(passengerDto);
        return new ResponseEntity<>(passengerService.registerPassenger(passenger), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id, @Valid @RequestBody PassengerDTO passengerDTO) {
        Passenger passenger = convertToEntity(passengerDTO);
        return new ResponseEntity<>(passengerService.updatePassenger(id, passenger), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
        return new ResponseEntity<>(passengerService.getPassengerById(id), HttpStatus.OK);
    }

    private Passenger convertToEntity(PassengerDTO passengerDTO) {
        Passenger passenger = new Passenger();
        passenger.setId(passengerDTO.getId());
        passenger.setName(passengerDTO.getName());
        passenger.setUsername(passengerDTO.getUsername());
        passenger.setEmail(passengerDTO.getEmail());
        passenger.setPassword(passengerDTO.getPassword());
        passenger.setRole(passengerDTO.getRole());
        return passenger;
    }
}