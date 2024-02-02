package com.atahf.flightsearchapi.flight;

import com.atahf.flightsearchapi.airport.Airport;
import com.atahf.flightsearchapi.airport.AirportDao;

import com.atahf.flightsearchapi.flight.FlightDto.NewFlightDto;
import com.atahf.flightsearchapi.flight.FlightDto.NewFlightInfoDto;
import com.atahf.flightsearchapi.flight.FlightDto.RoundTripDto;
import com.atahf.flightsearchapi.flight.FlightDto.SingleTripDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FlightService {
    private final FlightDao flightDao;
    private final AirportDao airportDao;

    public FlightService(FlightDao flightDao, AirportDao airportDao) {
        this.flightDao = flightDao;
        this.airportDao = airportDao;
    }

    @Transactional
    public Flight addFlight(NewFlightDto newFlightDto) {
        Airport origin = airportDao.findByID(newFlightDto.getOriginAirport());
        Airport destination = airportDao.findByID(newFlightDto.getDestinationAirport());
        NewFlightInfoDto newFlightInfoDto = new NewFlightInfoDto(newFlightDto, origin, destination);
        Flight newFlight = new Flight(newFlightInfoDto);

        flightDao.save(newFlight);
        return newFlight;
    }

    @Transactional
    public void deleteFlight(Long ID) throws Exception {
        Flight flight = flightDao.findByID(ID);

        if(flight == null) throw new Exception("Flight Not Found!");

        flightDao.delete(flight);
    }

    public Flight getFlight(Long ID) throws Exception {
        Flight flight = flightDao.findByID(ID);

        if(flight == null) throw new Exception("Airport Not Found!");

        return flight;
    }

    public List<Flight> getAllFlights() {
        return flightDao.findAllByOrderByDepartureDate();
    }

    public List<Flight> searchSingleTrips(SingleTripDto singleTripDto) {
        return flightDao.findAllSingleTrips(singleTripDto);
    }

    public List<Flight> searchRoundTrips(RoundTripDto roundTripDto) {
        return flightDao.findAllRoundTrips(roundTripDto);
    }
}
