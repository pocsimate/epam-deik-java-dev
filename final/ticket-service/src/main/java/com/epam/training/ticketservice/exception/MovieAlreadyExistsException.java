package com.epam.training.ticketservice.exception;

public class MovieAlreadyExistsException extends RuntimeException {
    public MovieAlreadyExistsException(String title){
        super("Movie " + title + " already exists");
    }
}
