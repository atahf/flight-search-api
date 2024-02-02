package com.atahf.flightsearchapi.flight.FlightDto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SingleTripDto {
    private Long originAirport;
    private Long destinationAirport;
    private LocalDate departureDate;
}
