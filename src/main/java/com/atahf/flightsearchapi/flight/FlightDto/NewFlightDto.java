package com.atahf.flightsearchapi.flight.FlightDto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewFlightDto {
    private Long originAirport;
    private Long destinationAirport;
    private LocalDateTime departureDate;
    private LocalDateTime returnDate;
    private double price;
}
