package com.atahf.flightsearchapi.airport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportDao extends JpaRepository<Airport,Long> {
    Airport findByID(Long id);
    List<Airport> findAllByCity(String city);
    @Query("SELECT ID FROM Airport")
    List<Long> findAllIDs();
}
