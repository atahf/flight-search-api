package com.atahf.flightsearchapi.airport;

import com.atahf.flightsearchapi.airport.AirportDto.AirportEditDto;
import com.atahf.flightsearchapi.airport.AirportDto.AirportInfoDto;
import com.atahf.flightsearchapi.utils.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AirportService {

    private final AirportDao airportDao;

    public AirportService(AirportDao airportDao) {
        this.airportDao = airportDao;
    }

    @Transactional
    public Airport addAirport(AirportInfoDto airportInfoDto) {
        Airport newAirport = new Airport(airportInfoDto);

        airportDao.save(newAirport);
        return newAirport;
    }

    @Transactional
    public void editAirport(AirportEditDto airportEditDto) throws Exception {
        Airport airport = airportDao.findByID(airportEditDto.getID());

        if(airport == null) throw new NotFoundException("Airport Not Found!");

        airport.setCity(airportEditDto.getCity());
    }

    @Transactional
    public void deleteAirport(Long ID) throws Exception {
        Airport airport = airportDao.findByID(ID);

        if(airport == null) throw new NotFoundException("Airport Not Found!");

        airportDao.delete(airport);
    }

    public Airport getAirport(Long ID) throws Exception {
        Airport airport = airportDao.findByID(ID);

        if(airport == null) throw new NotFoundException("Airport Not Found!");

        return airport;
    }

    public List<Airport> getAllAirports() {
        return airportDao.findAll();
    }

    public List<Long> getAllAirportIDs() {
        return airportDao.findAllIDs();
    }
}
