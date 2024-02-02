package com.atahf.flightsearchapi.flight;

import javax.persistence.*;

import com.atahf.flightsearchapi.airport.Airport;
import com.atahf.flightsearchapi.flight.FlightDto.NewFlightInfoDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Flight Search API Flight model documentation", description = "Flight Model")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Unique id field of flight object")
    private Long ID;
    @ManyToOne
    @JoinColumn(name = "origin_id")
    @ApiModelProperty(value = "origin airport object related to flight object")
    private Airport origin;
    @ManyToOne
    @JoinColumn(name = "destination_id")
    @ApiModelProperty(value = "destination airport object related to flight object")
    private Airport destination;
    @ApiModelProperty(value = "departure date and time of flight object")
    private LocalDateTime departureDate;
    @ApiModelProperty(value = "return date and time of flight object")
    private LocalDateTime returnDate;
    @ApiModelProperty(value = "price of flight object")
    private double price;

    public Flight(NewFlightInfoDto newFlightInfoDto) {
        this.origin = newFlightInfoDto.getOrigin();
        this.destination = newFlightInfoDto.getDestination();
        this.departureDate = newFlightInfoDto.getDepartureDate();
        this.returnDate = newFlightInfoDto.getReturnDate();
        this.price = newFlightInfoDto.getPrice();
    }
}
