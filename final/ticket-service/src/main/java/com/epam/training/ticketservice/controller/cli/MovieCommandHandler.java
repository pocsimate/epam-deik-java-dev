package com.epam.training.ticketservice.controller.cli;

import com.epam.training.ticketservice.exception.MovieAlreadyExistsException;
import com.epam.training.ticketservice.model.movie.Movie;
import com.epam.training.ticketservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class MovieCommandHandler {

    @Autowired
    MovieService movieService;

    //TODO itt hagytam abba (insert elég buta)
    @ShellMethod(value = "Create new movie", key = "create movie")
    public String createMovie(String title, String category, int length){
        try{
            Movie movie = new Movie.MovieBuilder()
                    .title(title)
                    .category(category)
                    .length(length)
                    .build();
            movieService.createMovie(movie);
            return "Movie created successfully";
        } catch (MovieAlreadyExistsException ex) {
            return ex.getMessage();
        }
    }
}
