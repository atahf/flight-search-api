package com.atahf.flightsearchapi.flight.FlightDto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExternalReturnedFlight {
    private int originAirport;
    private int destinationAirport;
    private String departureDate;
    private String returnDate;
    private double price;

    public NewFlightDto getNewFlightDto() {
        NewFlightDto newFlightDto = new NewFlightDto();
        newFlightDto.setOriginAirport((long) this.originAirport);
        newFlightDto.setDestinationAirport((long) this.destinationAirport);
        newFlightDto.setDepartureDate(LocalDateTime.parse(this.departureDate));
        newFlightDto.setReturnDate(LocalDateTime.parse(this.returnDate));
        newFlightDto.setPrice(this.price);
        return newFlightDto;
    }
}
