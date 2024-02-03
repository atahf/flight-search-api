package com.atahf.flightsearchapi.flight;

import com.atahf.flightsearchapi.airport.AirportService;
import com.atahf.flightsearchapi.flight.FlightDto.*;
import com.atahf.flightsearchapi.utils.GeneralResponse;
import com.atahf.flightsearchapi.utils.NotFoundException;
import com.atahf.flightsearchapi.utils.SameOriginAndDestinationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
@Api(value = "Flight Endpoints documentation")
public class FlightController {
    private final FlightService flightService;
    private final AirportService airportService;

    @Autowired
    public FlightController(FlightService flightService, AirportService airportService) {
        this.flightService = flightService;
        this.airportService = airportService;
    }

    @ApiOperation(value = "Airport list method")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = GeneralResponse.class),
            @ApiResponse(code = 400, message = "bad airport IDs, both with same ID", response = GeneralResponse.class),
            @ApiResponse(code = 403, message = "authorization failed"),
            @ApiResponse(code = 404, message = "flight not found", response = GeneralResponse.class),
            @ApiResponse(code = 500, message = "Server side failure")
    })
    @GetMapping("all")
    public ResponseEntity<GeneralResponse<List<Flight>>> getAllFlights(
            @ApiParam(value = "Origin Airport ID") @RequestParam(required = false, value = "from") Long originID,
            @ApiParam(value = "Destination Airport ID") @RequestParam(required = false, value = "to") Long destinationID
    ) {
        GeneralResponse<List<Flight>> response = new GeneralResponse<>("success", 0, new ArrayList<>());
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
        } catch (NotFoundException e) {
            response.setStatus(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (SameOriginAndDestinationException e) {
            response.setStatus(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "Airport by ID method")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = GeneralResponse.class),
            @ApiResponse(code = 403, message = "authorization failed"),
            @ApiResponse(code = 404, message = "flight not found", response = GeneralResponse.class),
            @ApiResponse(code = 500, message = "Server side failure")
    })
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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = GeneralResponse.class),
            @ApiResponse(code = 403, message = "authorization failed"),
            @ApiResponse(code = 500, message = "Server side failure")
    })
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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = GeneralResponse.class),
            @ApiResponse(code = 403, message = "authorization failed"),
            @ApiResponse(code = 404, message = "flight not found", response = GeneralResponse.class),
            @ApiResponse(code = 500, message = "Server side failure")
    })
    @PutMapping ("update")
    public ResponseEntity<GeneralResponse<String>> updateFlight(@ApiParam(value = "Updated Flight Object", required = true) @RequestBody FlightUpdateDto flightUpdateDto) {
        GeneralResponse<String> response = new GeneralResponse<>("success", 0, "");
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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = GeneralResponse.class),
            @ApiResponse(code = 403, message = "authorization failed"),
            @ApiResponse(code = 404, message = "flight not found", response = GeneralResponse.class),
            @ApiResponse(code = 500, message = "Server side failure")
    })
    @DeleteMapping ("delete/{ID}")
    public ResponseEntity<GeneralResponse<String>> deleteFlight(@ApiParam(value = "ID of Flight to be deleted", required = true) @PathVariable Long ID) {
        GeneralResponse<String> response = new GeneralResponse<>("success", 0, "");
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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = GeneralResponse.class),
            @ApiResponse(code = 400, message = "bad airport IDs, both with same ID", response = GeneralResponse.class),
            @ApiResponse(code = 403, message = "authorization failed"),
            @ApiResponse(code = 500, message = "Server side failure")
    })
    @GetMapping("search/single")
    public ResponseEntity<GeneralResponse<List<Flight>>> getAllSingleTrips(
            @ApiParam(value = "Origin Airport ID", required = true) @RequestParam(value = "from") Long originID,
            @ApiParam(value = "Destination Airport ID", required = true) @RequestParam(value = "to") Long destinationID,
            @ApiParam(value = "Departure Date", required = true) @RequestParam(value = "departure") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate
    ) {
        GeneralResponse<List<Flight>> response = new GeneralResponse<>("success", 0, new ArrayList<>());
        try{
            List<Flight> flights = flightService.searchSingleTrips(new SingleTripDto(originID, destinationID, departureDate));
            response.setResult(flights);
            response.setResult_count(flights.size());
            return ResponseEntity.ok(response);
        } catch (SameOriginAndDestinationException e) {
            response.setStatus(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "Airport list of round-trips method")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = GeneralResponse.class),
            @ApiResponse(code = 400, message = "bad airport IDs, both with same ID", response = GeneralResponse.class),
            @ApiResponse(code = 403, message = "authorization failed"),
            @ApiResponse(code = 500, message = "Server side failure")
    })
    @GetMapping("search/round")
    public ResponseEntity<GeneralResponse<List<Flight>>> getAllRoundTrips(
            @ApiParam(value = "Origin Airport ID", required = true) @RequestParam(value = "from") Long originID,
            @ApiParam(value = "Destination Airport ID", required = true) @RequestParam(value = "to") Long destinationID,
            @ApiParam(value = "Departure Date", required = true) @RequestParam(value = "departure") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
            @ApiParam(value = "Return Date", required = true) @RequestParam(value = "return") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate
    ) {
        GeneralResponse<List<Flight>> response = new GeneralResponse<>("success", 0, new ArrayList<>());
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

        try {
            int count = airportService.getAllAirports().size();
            if(count < 1) throw new RuntimeException("No Airports Do Exist!");

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/mock-api/get-data?count="+ count,
                    //"https://run.mocky.io/v3/f100bbac-42ba-40cf-9573-6b5c6ff882c2",
                    HttpMethod.GET,
                    null,
                    String.class);
            if (response.getBody() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                ExternalReturnedFlight[] flights = objectMapper.readValue(response.getBody(), ExternalReturnedFlight[].class);

                for(ExternalReturnedFlight flight: flights) {
                    flightService.addFlight(flight.getNewFlightDto());
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @PostConstruct
    public void initializeData() {
        if(flightService.getAllFlights().isEmpty()) {
            fetchFlights();
        }
    }
}
