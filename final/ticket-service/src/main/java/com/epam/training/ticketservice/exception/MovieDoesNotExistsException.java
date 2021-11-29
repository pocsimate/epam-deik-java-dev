package com.epam.training.ticketservice.exception;

public class MovieDoesNotExistsException extends RuntimeException {
    public MovieDoesNotExistsException(String title){
        super("Cannot update movie '" + title + "'. It does not exist in the database");
    }
}
