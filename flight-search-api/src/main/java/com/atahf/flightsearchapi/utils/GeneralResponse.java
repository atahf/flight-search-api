package com.atahf.flightsearchapi.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "General Response Model")
public class GeneralResponse<T> {
    @ApiModelProperty(value = "status of response object, for cases of 2xx, 4xx response codes")
    private String status;
    @ApiModelProperty(value = "number of results, if result type is an object or generic type it will be 1, otherwise it will be size of list")
    private int result_count;
    @ApiModelProperty(value = "results of the requested operation")
    private T result;
}
