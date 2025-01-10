package com.cts.AirTicket.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingDTO{
    private Long id;

    @NotBlank(message = "Source is mandatory")
    private String source;

    @NotBlank(message = "Destination is mandatory")
    private String destination;

    @NotNull(message = "Passenger ID is mandatory")
    private Long passengerId;

    @NotNull(message = "Flight ID is mandatory")
    private Long flightId;
    
    @NotNull(message = "Seat ID is mandatory")
    private String seatnumber;

}