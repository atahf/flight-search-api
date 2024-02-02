package com.atahf.flightsearchapi.flight.FlightDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Updated Flight Model", description = "Updated Flight Model documentation, to be used for updating Flight object")
public class FlightUpdateDto {
    @ApiModelProperty(value = "Unique id field of flight object")
    private Long ID;
    @ApiModelProperty(value = "ID of origin airport object related to flight object")
    private Long originAirport;
    @ApiModelProperty(value = "ID of destination airport object related to flight object")
    private Long destinationAirport;
    @ApiModelProperty(value = "departure date and time of flight object")
    private LocalDateTime departureDate;
    @ApiModelProperty(value = "return date and time of flight object")
    private LocalDateTime returnDate;
    @ApiModelProperty(value = "price of flight object")
    private double price;
}
