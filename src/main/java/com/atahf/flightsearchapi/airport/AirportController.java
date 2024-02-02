package com.atahf.flightsearchapi.airport;

import com.atahf.flightsearchapi.airport.AirportDto.*;
import com.atahf.flightsearchapi.utils.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(value = "Flight Search API - Airport Endpoints documentation")
public class AirportController {

    private final AirportService airportService;

    @Autowired
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @ApiOperation(value = "Airports List Method")
    @GetMapping("all")
    public ResponseEntity<List<Airport>> getAll() {
        try {
            List<Airport> airports = airportService.getAllAirports();
            return ResponseEntity.ok(airports);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @ApiOperation(value = "Airport by ID Method")
    @GetMapping("{ID}")
    public ResponseEntity<?> getAirport(@ApiParam(value = "Airport ID", required = true) @PathVariable Long ID) {
        try {
            Airport airport = airportService.getAirport(ID);
            return ResponseEntity.ok(airport);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "New Airport adding method")
    @PostMapping("add")
    public ResponseEntity<Airport> addAirport(@ApiParam(value = "New Airport Object", required = true) @RequestBody AirportInfoDto airportInfoDto) {
        try {
            Airport newAirport = airportService.addAirport(airportInfoDto);
            return ResponseEntity.ok(newAirport);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "Airport updating method")
    @PutMapping("update")
    public ResponseEntity<String> editAirport(@ApiParam(value = "Updated Airport Object", required = true) @RequestBody AirportUpdateDto airportUpdateDto) {
        try {
            airportService.updateAirport(airportUpdateDto);
            return ResponseEntity.ok("Airport Successfully Edited!");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ApiOperation(value = "Airport deleting method")
    @DeleteMapping ("delete/{ID}")
    public ResponseEntity<String> deleteAirport(@ApiParam(value = "ID of Airport to be deleted", required = true) @PathVariable Long ID) {
        try {
            airportService.deleteAirport(ID);
            return ResponseEntity.ok("Airport Successfully Deleted!");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
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
