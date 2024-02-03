package com.atahf.flightsearchapi.flight;

import com.atahf.flightsearchapi.airport.Airport;
import com.atahf.flightsearchapi.airport.AirportDao;

import com.atahf.flightsearchapi.flight.FlightDto.FlightUpdateDto;
import com.atahf.flightsearchapi.flight.FlightDto.NewFlightDto;
import com.atahf.flightsearchapi.flight.FlightDto.RoundTripDto;
import com.atahf.flightsearchapi.flight.FlightDto.SingleTripDto;
import com.atahf.flightsearchapi.utils.NotFoundException;
import com.atahf.flightsearchapi.utils.SameOriginAndDestinationException;
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
        Flight newFlight = new Flight(newFlightDto, origin, destination);

        flightDao.save(newFlight);
        return newFlight;
    }

    @Transactional
    public void updateFlight(FlightUpdateDto flightUpdateDto) throws NotFoundException {
        Flight flight = flightDao.findByID(flightUpdateDto.getID());
        if(flight == null) throw new NotFoundException("Flight Not Found!");

        Airport origin = airportDao.findByID(flightUpdateDto.getOriginAirport());
        if(origin == null) throw new NotFoundException("OriginAirport Not Found!");

        Airport destination = airportDao.findByID(flightUpdateDto.getDestinationAirport());
        if(destination == null) throw new NotFoundException("Destination Airport Not Found!");

        flight.setOrigin(origin);
        flight.setDestination(destination);
        flight.setDepartureDate(flightUpdateDto.getDepartureDate());
        flight.setReturnDate(flightUpdateDto.getReturnDate());
        flight.setPrice(flightUpdateDto.getPrice());
    }

    @Transactional
    public void deleteFlight(Long ID) throws Exception {
        Flight flight = flightDao.findByID(ID);

        if(flight == null) throw new NotFoundException("Flight Not Found!");

        flightDao.delete(flight);
    }

    public Flight getFlight(Long ID) throws Exception {
        Flight flight = flightDao.findByID(ID);

        if(flight == null) throw new NotFoundException("Flight Not Found!");

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
        if(originID.equals(destinationID)) throw new SameOriginAndDestinationException("Destination Airport cannot be same with Origin Airport");

        Airport airportOrigin = airportDao.findByID(originID);
        if(airportOrigin == null) throw new NotFoundException("Origin Airport Not Found!");

        Airport airportDestination = airportDao.findByID(destinationID);
        if(airportDestination == null) throw new NotFoundException("Destination Airport Not Found!");

        return flightDao.findAllByOriginAndDestination(airportOrigin, airportDestination);
    }

    public List<Flight> searchSingleTrips(SingleTripDto singleTripDto) throws SameOriginAndDestinationException {
        if(singleTripDto.getOriginAirport().equals(singleTripDto.getDestinationAirport())) throw new SameOriginAndDestinationException("Destination Airport cannot be same with Origin Airport");

        return flightDao.findAllSingleTrips(singleTripDto);
    }

    public List<Flight> searchRoundTrips(RoundTripDto roundTripDto) throws SameOriginAndDestinationException {
        if(roundTripDto.getOriginAirport().equals(roundTripDto.getDestinationAirport())) throw new SameOriginAndDestinationException("Destination Airport cannot be same with Origin Airport");

        return flightDao.findAllRoundTrips(roundTripDto);
    }
}
