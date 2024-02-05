package com.atahf.flightsearchapi.flight.FlightDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel(
        value = "External Flight Model",
        description = "External Flight Model documentation, to be used for serializing Flight object from 3rd party API"
)
public class ExternalReturnedFlight {
    @ApiModelProperty(value = "ID of origin airport object related to flight object")
    private int originAirport;
    @ApiModelProperty(value = "ID of destination airport object related to flight object")
    private int destinationAirport;
    @ApiModelProperty(value = "departure date and time of flight object")
    private String departureDate;
    @ApiModelProperty(value = "return date and time of flight object")
    private String returnDate;
    @ApiModelProperty(value = "price of flight object")
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
