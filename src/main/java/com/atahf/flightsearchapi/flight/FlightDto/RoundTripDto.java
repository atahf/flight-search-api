package com.atahf.flightsearchapi.flight.FlightDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Round-Trip Flight Model", description = "Round-Trip Flight Model documentation, to be used for doing a round-trip flight search object")
public class RoundTripDto {
    @ApiModelProperty(value = "ID of origin airport object related to flight object")
    private Long originAirport;
    @ApiModelProperty(value = "ID of destination airport object related to flight object")
    private Long destinationAirport;
    @ApiModelProperty(value = "departure date of flight object")
    private LocalDate departureDate;
    @ApiModelProperty(value = "return date of flight object")
    private LocalDate returnDate;
}
