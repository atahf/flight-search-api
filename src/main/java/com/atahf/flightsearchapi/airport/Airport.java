package com.atahf.flightsearchapi.airport;

import javax.persistence.*;

import com.atahf.flightsearchapi.airport.AirportDto.AirportInfoDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Entity
@Table(name = "airports")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Flight Search API Airport model documentation", description = "Airport Model")
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Unique id field of airport object")
    private Long ID;
    @ApiModelProperty(value = "city of airport object")
    private String city;

    public Airport(AirportInfoDto airportInfoDto) {
        this.city = airportInfoDto.getCity();
    }
}
