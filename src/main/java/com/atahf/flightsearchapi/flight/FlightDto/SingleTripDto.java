package com.atahf.flightsearchapi.flight.FlightDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Single-Trip Flight Model", description = "Single-Trip Flight Model documentation, to be used for doing a single-trip flight search object")
public class SingleTripDto {
    @ApiModelProperty(value = "ID of origin airport object related to flight object")
    private Long originAirport;
    @ApiModelProperty(value = "ID of destination airport object related to flight object")
    private Long destinationAirport;
    @ApiModelProperty(value = "departure date of flight object")
    private LocalDate departureDate;
}
