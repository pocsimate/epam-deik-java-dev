package com.epam.training.ticketservice.controller.cli.authorized;

import com.epam.training.ticketservice.exception.MovieAlreadyExistsException;
import com.epam.training.ticketservice.exception.MovieDoesNotExistException;
import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class AdminMovieCommandHandler extends AuthorizedCommand {

    private MovieService movieService;

    @Autowired
    public AdminMovieCommandHandler(MovieService movieService) {
        this.movieService = movieService;
    }

    @ShellMethodAvailability("isAdminAuthorized")
    @ShellMethod(value = "Create new movie", key = "create movie")
    public String createMovie(String title, String category, int length) {
        try {
            Movie movie = Movie.builder()
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

    @ShellMethodAvailability("isAdminAuthorized")
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
        } catch (MovieDoesNotExistException ex) {
            return ex.getMessage();
        }
    }

    @ShellMethodAvailability("isAdminAuthorized")
    @ShellMethod(value = "Delete existing movie", key = "delete movie")
    public String deleteMovie(String title) {
        try {
            movieService.deleteMovie(title);
            return "Movie deleted successfully";
        } catch (MovieDoesNotExistException ex) {
            return ex.getMessage();
        }
    }
}
