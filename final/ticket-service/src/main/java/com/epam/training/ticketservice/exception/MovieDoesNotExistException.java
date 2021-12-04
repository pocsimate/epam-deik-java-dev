package com.epam.training.ticketservice.exception;

public class MovieDoesNotExistException extends RuntimeException {
    public MovieDoesNotExistException(String title){
        super("Cannot update movie '" + title + "'. It does not exist in the database");
    }
}
