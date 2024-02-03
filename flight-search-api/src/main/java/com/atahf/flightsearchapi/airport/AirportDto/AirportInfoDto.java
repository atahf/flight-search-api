package com.atahf.flightsearchapi.airport.AirportDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "New Airport Model", description = "New Airport Model documentation, to be used for adding a new Airport object")
public class AirportInfoDto {
    @ApiModelProperty(value = "city of airport object")
    private String city;
}
