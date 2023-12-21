package com.amadeus.flightsearch.controller;

import com.amadeus.flightsearch.dto.AirportDto;
import com.amadeus.flightsearch.entity.Airport;
import com.amadeus.flightsearch.service.AirportService;
import com.amadeus.flightsearch.util.converter.AirportConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/airports")
public class AirportController {

    private final AirportService airportService;

    @GetMapping
    public ResponseEntity<List<AirportDto>> getAllAirports() {
        List<AirportDto> airports = airportService.getAllAirports().stream()
                .map(AirportConverter::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(airports);
    }

    @PostMapping
    public ResponseEntity<Void> addAirport(@RequestBody AirportDto airportDto) {
        airportService.saveAirport(airportDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportDto> getAirport(@PathVariable Long id) {
        Airport airport = airportService.getAirport(id);
        return ResponseEntity.ok(AirportConverter.toDTO(airport));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAirport(@PathVariable Long id, @RequestBody AirportDto airportDto) {
        airportService.updateAirport(id, airportDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
        airportService.deleteAirport(id);
        return ResponseEntity.noContent().build();
    }
}