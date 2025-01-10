package com.cts.AirTicketTest.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.AirTicket.exception.ResourceNotFoundException;
import com.cts.AirTicket.model.Airport;
import com.cts.AirTicket.repository.AirportRepository;
import com.cts.AirTicket.service.AirportService;

class AirportServiceTest {

    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private AirportService airportService;

    private Airport airport;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        airport = new Airport();
        airport.setId(1L);
        airport.setName("Chennai Airport");
        airport.setCity("Chennai");
        airport.setCountry("India");
    }

    @Test
    void testCreateAirport() {
        when(airportRepository.save(any(Airport.class))).thenReturn(airport);
        Airport createdAirport = airportService.createAirport(airport);
        assertNotNull(createdAirport);
        assertEquals(airport.getName(), createdAirport.getName());
    }

    @Test
    void testDeleteAirport() {
        when(airportRepository.findById(anyLong())).thenReturn(Optional.of(airport));
        doNothing().when(airportRepository).delete(any(Airport.class));
        airportService.deleteAirport(1L);
        verify(airportRepository, times(1)).delete(airport);
    }
    
}
