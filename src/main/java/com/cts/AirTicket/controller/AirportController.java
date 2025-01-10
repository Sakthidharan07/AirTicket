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

import com.cts.AirTicket.dto.AirportDTO;
import com.cts.AirTicket.model.Airport;
import com.cts.AirTicket.service.AirportService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/airports")
@Validated
public class AirportController {
    @Autowired
    private AirportService airportService;

    @PostMapping("/create")
	@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Airport> createAirport(@Valid @RequestBody AirportDTO airportDto) {
        Airport airport = convertToEntity(airportDto);
        return new ResponseEntity<>(airportService.createAirport(airport), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Airport> updateAirport(@PathVariable Long id, @Valid @RequestBody AirportDTO airportDTO) {
        Airport airport = convertToEntity(airportDTO);
        return new ResponseEntity<>(airportService.updateAirport(id, airport), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
        airportService.deleteAirport(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Airport> getAirportById(@PathVariable Long id) {
        return new ResponseEntity<>(airportService.getAirportById(id), HttpStatus.OK);
    }
 
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Airport>> getAllAirport() {
    	return new ResponseEntity<>(airportService.getAllAirport(),HttpStatus.OK);
    }
    
    private Airport convertToEntity(AirportDTO airportDTO) {
        Airport airport = new Airport();
        airport.setName(airportDTO.getName());
        airport.setCity(airportDTO.getCity());
        airport.setCountry(airportDTO.getCountry());
        return airport;
    }
}