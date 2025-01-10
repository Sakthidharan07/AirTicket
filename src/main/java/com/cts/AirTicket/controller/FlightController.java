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

import com.cts.AirTicket.dto.FlightDTO;
import com.cts.AirTicket.model.Flight;
import com.cts.AirTicket.service.FlightService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/flights")
@Validated
public class FlightController {
    @Autowired
    private FlightService flightService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Flight> createFlight(@Valid @RequestBody FlightDTO flightDTO) {
        return new ResponseEntity<>(flightService.createFlight(flightDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @Valid @RequestBody FlightDTO flightDTO) {
        return new ResponseEntity<>(flightService.updateFlight(id, flightDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        return new ResponseEntity<>(flightService.getFlightById(id), HttpStatus.OK);
    }
    
    @GetMapping("/flightbysourceanddestination/{source}/{destination}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public List<Flight> findbysourceAnddesination(@PathVariable String source,@PathVariable String destination)
	{
		return flightService.findFlightsBySourceAndDestination(source,destination);
	}
    
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Flight> getAllFlights(){
    	return flightService.findAllFlights();
    }

}