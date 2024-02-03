package com.atahf.flightsearchapi.flight;

import com.atahf.flightsearchapi.airport.Airport;
import com.atahf.flightsearchapi.flight.FlightDto.RoundTripDto;
import com.atahf.flightsearchapi.flight.FlightDto.SingleTripDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightDao extends JpaRepository<Flight,Long> {
    Flight findByID(Long ID);
    List<Flight> findAllByOrderByDepartureDate();
    List<Flight> findAllByOrigin(Airport origin);
    List<Flight> findAllByDestination(Airport destination);
    List<Flight> findAllByOriginAndDestination(Airport origin, Airport destination);
    @Query("SELECT f FROM Flight f WHERE f.origin.ID = :#{#info.originAirport} and FUNCTION('DATE', f.departureDate) = :#{#info.departureDate} and f.destination.ID = :#{#info.destinationAirport}")
    List<Flight> findAllSingleTrips(@Param("info") SingleTripDto singleTripDto);
    @Query("SELECT f FROM Flight f WHERE f.origin.ID = :#{#info.originAirport} and FUNCTION('DATE', f.departureDate) = :#{#info.departureDate} and f.destination.ID = :#{#info.destinationAirport} and FUNCTION('DATE', f.departureDate) = :#{#info.returnDate}")
    List<Flight> findAllRoundTrips(@Param("info") RoundTripDto roundTripDto);
}
