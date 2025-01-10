package com.cts.AirTicket.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class AirportDTO {
    
	private Long flightId;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "City is mandatory")
    private String city;

    @NotBlank(message = "Country is mandatory")
    private String country;

}
