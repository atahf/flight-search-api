package com.atahf.flightsearchapi.flight;

import javax.persistence.*;

import com.atahf.flightsearchapi.airport.Airport;
import com.atahf.flightsearchapi.flight.FlightDto.NewFlightInfoDto;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @ManyToOne
    @JoinColumn(name = "origin_id")
    private Airport origin;
    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Airport destination;
    private LocalDateTime departureDate;
    private LocalDateTime returnDate;
    private double price;

    public Flight(NewFlightInfoDto newFlightInfoDto) {
        this.origin = newFlightInfoDto.getOrigin();
        this.destination = newFlightInfoDto.getDestination();
        this.departureDate = newFlightInfoDto.getDepartureDate();
        this.returnDate = newFlightInfoDto.getReturnDate();
        this.price = newFlightInfoDto.getPrice();
    }
}
