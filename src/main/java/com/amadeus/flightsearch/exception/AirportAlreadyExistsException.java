package com.amadeus.flightsearch.exception;

public class AirportAlreadyExistsException extends RuntimeException{
    public AirportAlreadyExistsException(String message) {
        super(message);
    }
}
