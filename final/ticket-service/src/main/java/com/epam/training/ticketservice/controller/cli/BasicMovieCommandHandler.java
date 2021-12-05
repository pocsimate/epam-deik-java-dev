package com.epam.training.ticketservice.controller.cli;

import java.util.List;
import java.util.StringJoiner;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class BasicMovieCommandHandler {

    private final MovieService movieService;

    @Autowired
    public BasicMovieCommandHandler(MovieService movieService) {
        this.movieService = movieService;
    }

    @ShellMethod(value = "List the movies", key = "list movies")
    public String listMovies() {
        List<Movie> movieList = movieService.getMovieList();

        if (movieList.isEmpty()) {
            return "There are no movies at the moment";
        }

        StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        movieList.forEach((movie -> stringJoiner.add(movie.toString())));

        return stringJoiner.toString();
    }
}
