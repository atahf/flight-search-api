package com.atahf.flightsearchapi.flight;

import com.atahf.flightsearchapi.airport.AirportDto.AirportUpdateDto;
import com.atahf.flightsearchapi.airport.AirportService;
import com.atahf.flightsearchapi.flight.FlightDto.FlightUpdateDto;
import com.atahf.flightsearchapi.flight.FlightDto.NewFlightDto;
import com.atahf.flightsearchapi.flight.FlightDto.RoundTripDto;
import com.atahf.flightsearchapi.flight.FlightDto.SingleTripDto;
import com.atahf.flightsearchapi.utils.GeneralResponse;
import com.atahf.flightsearchapi.utils.NotFoundException;
import com.atahf.flightsearchapi.utils.SameOriginAndDestinationException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @ApiOperation(value = "Airport list method")
    @GetMapping("all")
    public ResponseEntity<GeneralResponse<List<Flight>>> getAllFlights(
            @ApiParam(value = "Origin Airport ID") @RequestParam(required = false, value = "from") Long originID,
            @ApiParam(value = "Destination Airport ID") @RequestParam(required = false, value = "to") Long destinationID
    ) {
        GeneralResponse<List<Flight>> response = new GeneralResponse<>("success", 0, null);
        try {
            List<Flight> flights;
            if(originID == null && destinationID == null) {
                flights = flightService.getAllFlights();
            } else if(originID != null && destinationID == null) {
                flights = flightService.getAllFlightsFrom(originID);
            } else if(originID == null) {
                flights = flightService.getAllFlightsTo(destinationID);
            } else {
                flights = flightService.getAllFlightsFromNTo(originID, destinationID);
            }
            response.setResult(flights);
            response.setResult_count(flights.size());
            return ResponseEntity.ok(response);
        } catch (NotFoundException | SameOriginAndDestinationException e) {
            response.setStatus(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "Airport by ID method")
    @GetMapping("{ID}")
    public ResponseEntity<GeneralResponse<Flight>> getFlight(@ApiParam(value = "Flight ID", required = true) @PathVariable Long ID) {
        GeneralResponse<Flight> response = new GeneralResponse<>("success", 0, null);
        try {
            response.setResult(flightService.getFlight(ID));
            response.setResult_count(1);
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            response.setStatus(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "New Airport adding method")
    @PostMapping("add")
    public ResponseEntity<GeneralResponse<Flight>> addFlight(@ApiParam(value = "New Flight Object", required = true) @RequestBody NewFlightDto newFlightDto) {
        GeneralResponse<Flight> response = new GeneralResponse<>("success", 0, null);
        try {
            response.setResult(flightService.addFlight(newFlightDto));
            response.setResult_count(1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "Airport updating method")
    @PutMapping ("update")
    public ResponseEntity<GeneralResponse<String>> updateFlight(@ApiParam(value = "Updated Flight Object", required = true) @RequestBody FlightUpdateDto flightUpdateDto) {
        GeneralResponse<String> response = new GeneralResponse<>("success", 0, null);
        try {
            flightService.updateFlight(flightUpdateDto);
            response.setResult("Flight Successfully Updated!");
            response.setResult_count(1);
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            response.setStatus(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "Airport deleting method")
    @DeleteMapping ("delete/{ID}")
    public ResponseEntity<GeneralResponse<String>> deleteFlight(@ApiParam(value = "ID of Flight to be deleted", required = true) @PathVariable Long ID) {
        GeneralResponse<String> response = new GeneralResponse<>("success", 0, null);
        try {
            flightService.deleteFlight(ID);
            response.setResult("Flight Successfully Deleted!");
            response.setResult_count(1);
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            response.setStatus(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "Airport list of single-trips method")
    @GetMapping("search/single")
    public ResponseEntity<GeneralResponse<List<Flight>>> getAllSingleTrips(
            @ApiParam(value = "Origin Airport ID", required = true) @RequestParam(value = "from") Long originID,
            @ApiParam(value = "Destination Airport ID", required = true) @RequestParam(value = "to") Long destinationID,
            @ApiParam(value = "Departure Date", required = true) @RequestParam(value = "departure") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate
    ) {
        GeneralResponse<List<Flight>> response = new GeneralResponse<>("success", 0, null);
        try{
            List<Flight> flights = flightService.searchSingleTrips(new SingleTripDto(originID, destinationID, departureDate));
            response.setResult(flights);
            response.setResult_count(flights.size());
            return ResponseEntity.ok(response);
        } catch (SameOriginAndDestinationException e) {
            response.setStatus(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "Airport list of round-trips method")
    @GetMapping("search/round")
    public ResponseEntity<GeneralResponse<List<Flight>>> getAllRoundTrips(
            @ApiParam(value = "Origin Airport ID", required = true) @RequestParam(value = "from") Long originID,
            @ApiParam(value = "Destination Airport ID", required = true) @RequestParam(value = "to") Long destinationID,
            @ApiParam(value = "Departure Date", required = true) @RequestParam(value = "departure") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
            @ApiParam(value = "Return Date", required = true) @RequestParam(value = "return") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate
    ) {
        GeneralResponse<List<Flight>> response = new GeneralResponse<>("success", 0, null);
        try{
            List<Flight> flights = flightService.searchRoundTrips(new RoundTripDto(originID, destinationID, departureDate, returnDate));
            response.setResult(flights);
            response.setResult_count(flights.size());
            return ResponseEntity.ok(response);
        } catch (SameOriginAndDestinationException e) {
            response.setStatus(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void fetchFlights() {
        System.out.println("Fetching Flights at " + LocalDateTime.now().toString().replace('T', ' '));
        int flightCount = ThreadLocalRandom.current().nextInt(100, 1000);
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
