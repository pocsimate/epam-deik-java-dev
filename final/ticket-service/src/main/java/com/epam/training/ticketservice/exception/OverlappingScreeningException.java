package com.epam.training.ticketservice.exception;

public class OverlappingScreeningException extends RuntimeException {
    public OverlappingScreeningException(String message) {
        super(message);
    }
}
