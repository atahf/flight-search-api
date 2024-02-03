package com.amadeus.mockflightdata.mock_data;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MockData {
    private Long originAirport;
    private Long destinationAirport;
    private LocalDateTime departureDate;
    private LocalDateTime returnDate;
    private double price;
}
