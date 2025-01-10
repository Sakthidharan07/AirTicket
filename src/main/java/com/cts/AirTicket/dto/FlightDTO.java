package com.cts.AirTicket.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class FlightDTO {
	@NotNull(message = "Airport ID is manditory")
	private Long airport_id;
	
    @NotBlank(message = "Source is mandatory")
    private String source;

    @NotBlank(message = "Destination is mandatory")
    private String destination;

    @Future(message = "Departure time must be in the future")
    private LocalDateTime departureTime;

    @Future(message = "Arrival time must be in the future")
    private LocalDateTime arrivalTime;

    @Positive(message = "Total capacity must be positive")
    private int totalCapacity;

}
