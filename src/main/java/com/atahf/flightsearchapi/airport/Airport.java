package com.atahf.flightsearchapi.airport;

import javax.persistence.*;

import com.atahf.flightsearchapi.airport.AirportDto.AirportInfoDto;
import lombok.*;

@Entity
@Table(name = "airports")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private String city;

    public Airport(AirportInfoDto airportInfoDto) {
        this.city = airportInfoDto.getCity();
    }
}
