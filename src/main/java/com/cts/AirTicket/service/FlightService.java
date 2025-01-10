package com.cts.AirTicket.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.AirTicket.dto.FlightDTO;
import com.cts.AirTicket.exception.ResourceNotFoundException;
import com.cts.AirTicket.model.Airport;
import com.cts.AirTicket.model.Flight;
import com.cts.AirTicket.repository.AirportRepository;
import com.cts.AirTicket.repository.FlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FlightService {
	private static final Logger logger = LoggerFactory.getLogger(FlightService.class);
	
    @Autowired
    private FlightRepository flightRepository;
    
    @Autowired
    private AirportRepository airportRepository;

    public Flight createFlight(FlightDTO flightdto) {
    	logger.info("Creating flight");
    	Airport airport= airportRepository.findById(flightdto.getAirport_id()).orElseThrow(()-> new RuntimeException("Airport not found"));
    	Flight flight=new Flight();
    	flight.setArrivalTime(flightdto.getArrivalTime());
    	flight.setDepartureTime(flightdto.getDepartureTime());
    	flight.setDestination(flightdto.getDestination());
    	flight.setSource(flightdto.getSource());
    	flight.setTotalCapacity(flightdto.getTotalCapacity());
    	flight.setAirport(airport);
    	airport.getFlights().add(flight);
    	airport.setFlights(airport.getFlights());
    	Flight savedFlight = flightRepository.save(flight);
        logger.info("Flight created successfully");
        return savedFlight;
    }

    public Flight updateFlight(Long id, FlightDTO flightDTO) {
    	logger.info("Updating flight with id: {}", id);
    	Flight flight = flightRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Flight not found"));
        flight.setSource(flightDTO.getSource());
        flight.setDestination(flightDTO.getDestination());
        flight.setDepartureTime(flightDTO.getDepartureTime());
        flight.setArrivalTime(flightDTO.getArrivalTime());
        flight.setTotalCapacity(flightDTO.getTotalCapacity());
        Flight updatedFlight = flightRepository.save(flight);
        logger.info("Flight updated successfully");
        return updatedFlight;
    }

    public void deleteFlight(Long id) {
    	logger.info("Deleting flight with id: {}", id);
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));
        flightRepository.delete(flight);
        logger.info("Flight deleted successfully: {}", id);
    }

    public Flight getFlightById(Long id) {
    	logger.info("Fetching flight with id: {}", id);
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));
    }
    
    public List<Flight> findFlightsBySourceAndDestination(String source,String destination)
	{
    	logger.info("Finding flights from source: {} to destination: {}", source, destination);
		return flightRepository.findBySourceContainingAndDestinationContaining(source, destination);
	}

	public List<Flight> findAllFlights() {
		logger.info("Fetching all flights");
		return flightRepository.findAll();
	}
}