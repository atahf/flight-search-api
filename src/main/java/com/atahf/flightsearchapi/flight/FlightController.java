package com.atahf.flightsearchapi.flight;

import com.atahf.flightsearchapi.airport.AirportService;
import com.atahf.flightsearchapi.flight.FlightDto.NewFlightDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@CrossOrigin
@RestController
@RequestMapping("api/v1/flight")
public class FlightController {
    private final FlightService flightService;
    private final AirportService airportService;

    @Autowired
    public FlightController(FlightService flightService, AirportService airportService) {
        this.flightService = flightService;
        this.airportService = airportService;
    }

    @GetMapping("all")
    public ResponseEntity<List<Flight>> getAll() {
        try {
            List<Flight> flights = flightService.getAllFlights();
            return ResponseEntity.ok(flights);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("{ID}")
    public ResponseEntity<Flight> getFlight(@PathVariable Long ID) {
        try {
            Flight flight = flightService.getFlight(ID);
            return ResponseEntity.ok(flight);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("add")
    public ResponseEntity<Flight> addFlight(@RequestBody NewFlightDto newFlightDto) {
        try {
            Flight newFlight = flightService.addFlight(newFlightDto);
            return ResponseEntity.ok(newFlight);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping ("delete/{ID}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long ID) {
        try {
            flightService.deleteFlight(ID);
            return ResponseEntity.ok("Flight Successfully Deleted!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void fetchFlights() {
        System.out.println("Fetching Flights at " + LocalDateTime.now().toString().replace('T', ' '));
        int flightCount = ThreadLocalRandom.current().nextInt(25, 100);
        for(int i = 0; i < flightCount; i++) {
            NewFlightDto newFlightDto = GenerateRandomFlight();
            flightService.addFlight(newFlightDto);
        }
    }

    public NewFlightDto GenerateRandomFlight() {
        long departureSeconds = ThreadLocalRandom.current().nextLong(0, 86400);
        LocalDateTime departure = LocalDateTime.now().plusSeconds(departureSeconds);

        long arrivalSeconds = ThreadLocalRandom.current().nextLong(1, 86400);
        LocalDateTime arrival = departure.plusSeconds(arrivalSeconds);

        List<Long> airportIDs = airportService.getAllAirportIDs();
        Collections.shuffle(airportIDs);

        Long originID = airportIDs.get(0);
        Long destinationID = airportIDs.get(1);

        double uglyPrice = ThreadLocalRandom.current().nextDouble(1000, 20000);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedPrice = decimalFormat.format(uglyPrice);
        double price = Double.parseDouble(formattedPrice);

        return new NewFlightDto(originID, destinationID, departure, arrival, price);
    }

    @PostConstruct
    public void initializeData() {
        if(flightService.getAllFlights().isEmpty()) {
            fetchFlights();
        }
    }
}
