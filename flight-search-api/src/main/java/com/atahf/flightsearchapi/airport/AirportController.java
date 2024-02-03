package com.atahf.flightsearchapi.airport;

import com.atahf.flightsearchapi.airport.AirportDto.*;
import com.atahf.flightsearchapi.utils.GeneralResponse;
import com.atahf.flightsearchapi.utils.NotFoundException;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/airport")
@Api(value = "Airport Endpoints documentation")
public class AirportController {

    private final AirportService airportService;

    @Autowired
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @ApiOperation(value = "Airports List Method")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = GeneralResponse.class),
            @ApiResponse(code = 403, message = "authorization failed"),
            @ApiResponse(code = 500, message = "Server side failure")
    })
    @GetMapping("all")
    public ResponseEntity<GeneralResponse<List<Airport>>> getAll() {
        GeneralResponse<List<Airport>> response = new GeneralResponse<>("success", 0, null);
        try {
            List<Airport> airports = airportService.getAllAirports();
            response.setResult(airports);
            response.setResult_count(airports.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "Airport by ID Method")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = GeneralResponse.class),
            @ApiResponse(code = 403, message = "authorization failed"),
            @ApiResponse(code = 404, message = "airport not found", response = GeneralResponse.class),
            @ApiResponse(code = 500, message = "Server side failure")
    })
    @GetMapping("{ID}")
    public ResponseEntity<GeneralResponse<Airport>> getAirport(@ApiParam(value = "Airport ID", required = true) @PathVariable Long ID) {
        GeneralResponse<Airport> response = new GeneralResponse<>("success", 0, null);
        try {
            response.setResult(airportService.getAirport(ID));
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
    public ResponseEntity<GeneralResponse<Airport>> addAirport(@ApiParam(value = "New Airport Object", required = true) @RequestBody AirportInfoDto airportInfoDto) {
        GeneralResponse<Airport> response = new GeneralResponse<>("success", 0, null);
        try {
            response.setResult(airportService.addAirport(airportInfoDto));
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
            @ApiResponse(code = 404, message = "airport not found", response = GeneralResponse.class),
            @ApiResponse(code = 500, message = "Server side failure")
    })
    @PutMapping("update")
    public ResponseEntity<GeneralResponse<String>> updateAirport(@ApiParam(value = "Updated Airport Object", required = true) @RequestBody AirportUpdateDto airportUpdateDto) {
        GeneralResponse<String> response = new GeneralResponse<>("success", 0, null);
        try {
            airportService.updateAirport(airportUpdateDto);
            response.setResult("Airport Successfully Updated!");
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
            @ApiResponse(code = 404, message = "airport not found", response = GeneralResponse.class),
            @ApiResponse(code = 500, message = "Server side failure")
    })
    @DeleteMapping ("delete/{ID}")
    public ResponseEntity<GeneralResponse<String>> deleteAirport(@ApiParam(value = "ID of Airport to be deleted", required = true) @PathVariable Long ID) {
        GeneralResponse<String> response = new GeneralResponse<>("success", 0, null);
        try {
            airportService.deleteAirport(ID);
            response.setResult("Airport Successfully Deleted!");
            response.setResult_count(1);
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            response.setStatus(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostConstruct
    public void initializeData() {
        if(airportService.getAllAirports().isEmpty()) {
            List<String> cities = Arrays.asList(
                    "Istanbul",
                    "Ankara",
                    "Izmir",
                    "Antalya",
                    "London",
                    "Berlin",
                    "Paris",
                    "Moscow",
                    "New York",
                    "Tokyo",
                    "Dubai",
                    "Los Angeles",
                    "Singapore",
                    "Sydney",
                    "Toronto",
                    "Rome",
                    "Madrid",
                    "Beijing",
                    "Bangkok",
                    "Amsterdam",
                    "Hong Kong",
                    "Seoul",
                    "Chicago",
                    "Shanghai",
                    "Munich",
                    "Barcelona",
                    "Vienna",
                    "Las Vegas",
                    "San Francisco"
            );

            for(String city: cities) {
                airportService.addAirport(new AirportInfoDto(city));
            }
        }
    }
}
