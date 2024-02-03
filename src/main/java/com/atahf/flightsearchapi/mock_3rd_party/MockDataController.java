package com.atahf.flightsearchapi.mock_3rd_party;

import com.atahf.flightsearchapi.airport.AirportService;
import com.atahf.flightsearchapi.flight.FlightDto.NewFlightDto;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@CrossOrigin
@RestController
@RequestMapping(path = "/mock-api")
@Api(value = "Mock 3rd party API Endpoints documentation")
public class MockDataController {
    @GetMapping("get-data")
    public ResponseEntity<List<NewFlightDto>> getRandomFlights() {
        int number = ThreadLocalRandom.current().nextInt(1, 100);

        List<NewFlightDto> flights = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            flights.add(GenerateRandomFlight(29L));
        }
        return ResponseEntity.ok(flights);
    }

    public static NewFlightDto GenerateRandomFlight(Long airport) {
        long departureSeconds = ThreadLocalRandom.current().nextLong(0, 86400);
        LocalDateTime departure = LocalDateTime.now().plusSeconds(departureSeconds);

        long arrivalSeconds = ThreadLocalRandom.current().nextLong(1, 86400);
        LocalDateTime arrival = departure.plusSeconds(arrivalSeconds);

        Long originID = ThreadLocalRandom.current().nextLong(1, airport);
        Long destinationID;
        do {
            destinationID = ThreadLocalRandom.current().nextLong(1, airport);
        } while(!destinationID.equals(originID));

        double uglyPrice = ThreadLocalRandom.current().nextDouble(1000, 20000);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedPrice = decimalFormat.format(uglyPrice);
        double price = Double.parseDouble(formattedPrice);

        return new NewFlightDto(originID, destinationID, departure, arrival, price);
    }
}
