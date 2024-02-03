package com.atahf.flightsearchapi.flight;

import javax.persistence.*;

import com.atahf.flightsearchapi.airport.Airport;
import com.atahf.flightsearchapi.flight.FlightDto.NewFlightDto;
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
@ApiModel(value = "Flight Model", description = "Flight Model Documentation")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Unique id of flight object")
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

    public Flight(NewFlightDto newFlightDto, Airport origin, Airport destination) {
        this.origin = origin;
        this.destination = destination;
        this.departureDate = newFlightDto.getDepartureDate();
        this.returnDate = newFlightDto.getReturnDate();
        this.price = newFlightDto.getPrice();
    }
}
