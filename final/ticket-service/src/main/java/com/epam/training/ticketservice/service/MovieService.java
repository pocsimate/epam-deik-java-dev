package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.exception.MovieAlreadyExistsException;
import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public void createMovie(Movie movie){
        Optional<Movie> optionalMovie = movieRepository.getMovieByTitle(movie.getTitle());
        if (optionalMovie.isPresent()) {
            throw new MovieAlreadyExistsException(movie.getTitle());
        }
        movieRepository.save(movie);
    }
}
