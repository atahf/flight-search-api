package com.atahf.flightsearchapi.flight.FlightDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "New Flight Model", description = "New Flight Model documentation, to be used for adding a new Flight object")
public class NewFlightDto {
    @ApiModelProperty(value = "ID of origin airport object related to flight object")
    private Long originAirport;
    @ApiModelProperty(value = "ID of destination airport object related to flight object")
    private Long destinationAirport;
    @ApiModelProperty(value = "departure date and time of flight object")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime departureDate;
    @ApiModelProperty(value = "return date and time of flight object")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime returnDate;
    @ApiModelProperty(value = "price of flight object")
    private double price;
}
