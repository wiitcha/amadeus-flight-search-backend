package com.amadeus.flightsearch.service.impl;

import com.amadeus.flightsearch.dto.AirportDto;
import com.amadeus.flightsearch.entity.Airport;
import com.amadeus.flightsearch.exception.AirportAlreadyExistsException;
import com.amadeus.flightsearch.exception.AirportIsNotFoundException;
import com.amadeus.flightsearch.repository.AirportRepository;
import com.amadeus.flightsearch.service.AirportService;
import com.amadeus.flightsearch.util.converter.AirportConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final AirportConverter airportConverter;
    @Override
    public void deleteAirport(Long id) {
        Optional<Airport> airport = airportRepository.findAirportById(id);
        airport.ifPresentOrElse(airportRepository::delete, AirportIsNotFoundException::new);
    }

    @Override
    public void updateAirport(Long id, AirportDto airportDto) {
        Airport existingAirport = airportRepository.findById(id)
                .orElseThrow(AirportIsNotFoundException::new);

        Airport updatedAirport = airportConverter.toEntity(airportDto);
        updatedAirport.setId(existingAirport.getId());

        airportRepository.save(updatedAirport);
    }

    @Override
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    @Override
    public Airport saveAirport(AirportDto airportDto) {
        if (!airportRepository.existsAirportByCity(airportDto.getCity())) {
            Airport airport = airportConverter.toEntity(airportDto);
            airportRepository.save(airport);
            return airport;
        }
        throw new AirportAlreadyExistsException();
    }

    @Override
    public Airport getAirport(Long id) {
        return airportRepository.findAirportById(id).orElseThrow(AirportIsNotFoundException::new);
    }
}