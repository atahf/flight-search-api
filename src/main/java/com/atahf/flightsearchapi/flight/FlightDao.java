package com.atahf.flightsearchapi.flight;

import com.atahf.flightsearchapi.airport.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightDao extends JpaRepository<Flight,Long> {
    Flight findByID(Long ID);
    List<Flight> findAllByDepartureAndArrivalAndOriginAndDestination(LocalDateTime departure, LocalDateTime arrival, Airport origin, Airport destination);
    List<Flight> findAllByDepartureAndOrigin(LocalDateTime departure, Airport origin);
    List<Flight> findAllByOrderByDeparture();
}
