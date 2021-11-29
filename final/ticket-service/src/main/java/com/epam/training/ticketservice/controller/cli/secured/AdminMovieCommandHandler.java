package com.epam.training.ticketservice.controller.cli.secured;

import com.epam.training.ticketservice.exception.MovieAlreadyExistsException;
import com.epam.training.ticketservice.exception.MovieDoesNotExistsException;
import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class AdminMovieCommandHandler {

    @Autowired
    MovieService movieService;

    @ShellMethod(value = "Create new movie", key = "create movie")
    public String createMovie(String title, String category, int length) {
        try {
            Movie movie = Movie.builder()
                    .title(title)
                    .category(category)
                    .length(length)
                    .build();
            movieService.createMovie(movie);
            return "Room created successfully";
        } catch (MovieAlreadyExistsException ex) {
            return ex.getMessage();
        }
    }

    @ShellMethod(value = "Edit existing movie", key = "update movie")
    public String editMovie(String title, String category, int length) {
        try {
            Movie movie = Movie.builder()
                    .title(title)
                    .category(category)
                    .length(length)
                    .build();
            movieService.updateMovie(movie);
            return "Movie updated successfully";
        } catch (MovieDoesNotExistsException ex) {
            return ex.getMessage();
        }
    }

    @ShellMethod(value = "Delete existing movie", key = "delete movie")
    public String editMovie(String title) {
        try {
            movieService.deleteMovie(title);
            return "Movie deleted successfully";
        } catch (MovieDoesNotExistsException ex) {
            return ex.getMessage();
        }
    }
}
