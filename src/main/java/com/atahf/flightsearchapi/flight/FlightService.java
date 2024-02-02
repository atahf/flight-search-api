package com.atahf.flightsearchapi.flight;

import com.atahf.flightsearchapi.airport.Airport;
import com.atahf.flightsearchapi.airport.AirportDao;

import com.atahf.flightsearchapi.flight.FlightDto.NewFlightDto;
import com.atahf.flightsearchapi.flight.FlightDto.NewFlightInfoDto;
import com.atahf.flightsearchapi.flight.FlightDto.RoundTripDto;
import com.atahf.flightsearchapi.flight.FlightDto.SingleTripDto;
import com.atahf.flightsearchapi.utils.NotFoundException;
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

        if(flight == null) throw new NotFoundException("Flight Not Found!");

        flightDao.delete(flight);
    }

    public Flight getFlight(Long ID) throws Exception {
        Flight flight = flightDao.findByID(ID);

        if(flight == null) throw new NotFoundException("Airport Not Found!");

        return flight;
    }

    public List<Flight> getAllFlights() {
        return flightDao.findAllByOrderByDepartureDate();
    }

    public List<Flight> getAllFlightsFrom(Long originID) throws Exception {
        Airport airport = airportDao.findByID(originID);

        if(airport == null) throw new NotFoundException("Airport Not Found!");

        return flightDao.findAllByOrigin(airport);
    }

    public List<Flight> getAllFlightsTo(Long destinationID) throws Exception {
        Airport airport = airportDao.findByID(destinationID);

        if(airport == null) throw new NotFoundException("Airport Not Found!");

        return flightDao.findAllByDestination(airport);
    }

    public List<Flight> getAllFlightsFromNTo(Long originID, Long destinationID) throws Exception {
        Airport airportOrigin = airportDao.findByID(originID);
        if(airportOrigin == null) throw new NotFoundException("Origin Airport Not Found!");

        Airport airportDestination = airportDao.findByID(destinationID);
        if(airportDestination == null) throw new NotFoundException("Destination Airport Not Found!");

        return flightDao.findAllByOriginAndDestination(airportOrigin, airportDestination);
    }

    public List<Flight> searchSingleTrips(SingleTripDto singleTripDto) {
        return flightDao.findAllSingleTrips(singleTripDto);
    }

    public List<Flight> searchRoundTrips(RoundTripDto roundTripDto) {
        return flightDao.findAllRoundTrips(roundTripDto);
    }
}
