package com.atahf.flightsearchapi.airport.AirportDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Updated Airport Model", description = "Updated Airport Model documentation, to be used for updating Airport object")
public class AirportUpdateDto {
    @ApiModelProperty(value = "Unique id field of airport object")
    private Long ID;
    @ApiModelProperty(value = "city of airport object")
    private String city;
}
