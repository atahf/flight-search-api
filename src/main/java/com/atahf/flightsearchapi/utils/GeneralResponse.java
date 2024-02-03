package com.atahf.flightsearchapi.utils;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeneralResponse<T> {
    private String status;
    private int result_count;
    private T result;
}
