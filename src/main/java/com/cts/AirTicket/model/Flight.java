package com.cts.AirTicket.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String source;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int totalCapacity;

    @OneToMany(mappedBy = "flight",cascade=CascadeType.ALL)
    @JsonManagedReference
    private List<Seat> seats;
    
    @ManyToOne
    @JoinColumn(name = "airport_id")
    @JsonBackReference
    private Airport airport;
    
    @OneToMany(mappedBy="flight",cascade=CascadeType.ALL)
    @JsonManagedReference
    private List<Booking> booking;

}
