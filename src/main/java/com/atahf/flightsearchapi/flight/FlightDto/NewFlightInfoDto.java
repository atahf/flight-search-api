package com.atahf.flightsearchapi.flight.FlightDto;

import com.atahf.flightsearchapi.airport.Airport;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewFlightInfoDto {
    private Airport origin;
    private Airport destination;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private double price;

    public NewFlightInfoDto(NewFlightDto newFlightDto, Airport origin, Airport destination) {
        this.origin = origin;
        this.destination = destination;
        this.departure = newFlightDto.getDeparture();
        this.arrival = newFlightDto.getArrival();
        this.price = newFlightDto.getPrice();
    }
}
