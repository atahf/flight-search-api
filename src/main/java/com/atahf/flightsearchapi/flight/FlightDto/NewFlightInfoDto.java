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
    private LocalDateTime departureDate;
    private LocalDateTime returnDate;
    private double price;

    public NewFlightInfoDto(NewFlightDto newFlightDto, Airport origin, Airport destination) {
        this.origin = origin;
        this.destination = destination;
        this.departureDate = newFlightDto.getDepartureDate();
        this.returnDate = newFlightDto.getReturnDate();
        this.price = newFlightDto.getPrice();
    }
}
