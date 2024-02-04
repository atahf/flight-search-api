package com.amadeus.mockflightdata.mock_data;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@CrossOrigin
@RestController
@RequestMapping(path = "/mock-api")
public class MockDataController {
    @GetMapping("get-data")
    public ResponseEntity<List<MockData>> getRandomFlights(@RequestParam Long count) {
        int number = ThreadLocalRandom.current().nextInt(1, 100);

        List<MockData> flights = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            flights.add(GenerateRandomFlight(count));
        }
        return ResponseEntity.ok(flights);
    }

    public static MockData GenerateRandomFlight(Long airport) {
        long departureSeconds = ThreadLocalRandom.current().nextLong(0, 86400);
        LocalDateTime departure = LocalDateTime.now().plusSeconds(departureSeconds);

        long arrivalSeconds = ThreadLocalRandom.current().nextLong(1, 86400);
        LocalDateTime arrival = departure.plusSeconds(arrivalSeconds);

        Long originID = ThreadLocalRandom.current().nextLong(1, airport);
        Long destinationID;
        do {
            destinationID = ThreadLocalRandom.current().nextLong(1, airport);
        } while(destinationID.equals(originID));

        double uglyPrice = ThreadLocalRandom.current().nextDouble(1000, 20000);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedPrice = decimalFormat.format(uglyPrice);
        double price = Double.parseDouble(formattedPrice);

        return new MockData(originID, destinationID, departure, arrival, price);
    }
}
