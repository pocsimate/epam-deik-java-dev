package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.exception.MovieAlreadyExistsException;
import com.epam.training.ticketservice.exception.MovieDoesNotExistsException;
import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Optional<Movie> findMovieByTitle(String title){
        return movieRepository.findByTitle(title);
    }

    public Movie getMovieIfExists(String title) {
        Optional<Movie> movie = findMovieByTitle(title);
        if (movie.isPresent()) {
            return movie.get();
        } else {
            throw new MovieDoesNotExistsException(title);
        }
    }

    @Transactional
    public void createMovie(Movie movie){
        Optional<Movie> optionalMovie = findMovieByTitle(movie.getTitle());
        if (optionalMovie.isPresent()) {
            throw new MovieAlreadyExistsException(movie.getTitle());
        } else {
            movieRepository.save(movie);
        }
    }

    @Transactional
    public void updateMovie(Movie movie) {
        Movie dbMovie = getMovieIfExists(movie.getTitle());
        dbMovie.setTitle(movie.getTitle());
        dbMovie.setCategory(movie.getCategory());
        dbMovie.setLength(movie.getLength());
    }

    @Transactional
    public void deleteMovie(String title) {
        Movie dbMovie = getMovieIfExists(title);
        movieRepository.deleteByTitle(dbMovie.getTitle());
    }

    @Transactional
    public List<Movie> getMovieList() {
        return movieRepository.findAll();
    }
}
