package com.cts.AirTicket.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.cts.AirTicket.dto.AirportDTO;
import com.cts.AirTicket.exception.ResourceNotFoundException;
import com.cts.AirTicket.model.Airport;
import com.cts.AirTicket.model.Flight;
import com.cts.AirTicket.repository.AirportRepository;
import com.cts.AirTicket.repository.FlightRepository;
@Service
public class AirportService {
    @Autowired
    private AirportRepository airportRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(AirportService.class);
    
    public Airport createAirport(Airport airport) {
    	
    	logger.info("Creating airport");
        return airportRepository.save(airport);
    }

    public Airport updateAirport(Long id, Airport airportDetails) {
    	logger.info("Updating airport with id: {}", id);
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found"));
        airport.setName(airportDetails.getName());
        airport.setCity(airportDetails.getCity());
        airport.setCountry(airportDetails.getCountry());
        Airport updatedAirport = airportRepository.save(airport);
        logger.info("Updated airport");
        return updatedAirport;
    }

    public void deleteAirport(Long id) {
    	logger.info("Deleting airport with id: {}", id);
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found"));
        airportRepository.delete(airport);
        logger.info("Deleted airport with id: {}", id);
    }

    public Airport getAirportById(Long id) {
    	logger.info("Fetching airport with id: {}", id);
        return airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found"));
    }

	public List<Airport> getAllAirport() {
		logger.info("Fetching all the airports");
		return airportRepository.findAll();
	}
}