package com.cts.AirTicket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SeatDTO {
    private Long id;
    
    
    @NotBlank(message = "Seat number is mandatory")
    private String seatnumber;

    private boolean availability;

    @Positive(message = "Price must be positive")
    private double price;

    @NotNull(message = "Flight ID is mandatory")
    private Long flightId;

}
