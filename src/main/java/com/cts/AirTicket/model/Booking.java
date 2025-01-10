package com.cts.AirTicket.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String source;
    private String destination;
    
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "flight_id")
    private Flight flight;
    
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;
    
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="seat_id")
    private Seat seat;

}
