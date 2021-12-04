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
        Optional<Movie> optionalMovie = findMovieByTitle(movie.getTitle());
        if (optionalMovie.isEmpty()) {
            throw new MovieDoesNotExistsException(movie.getTitle());
        } else {
            Movie dbMovie = optionalMovie.get();
            dbMovie.setTitle(movie.getTitle());
            dbMovie.setCategory(movie.getCategory());
            dbMovie.setLength(movie.getLength());
        }
    }

    @Transactional
    public void deleteMovie(String title) {
        Optional<Movie> optionalMovie = findMovieByTitle(title);
        if (optionalMovie.isEmpty()) {
            throw new MovieDoesNotExistsException(title);
        } else {
            movieRepository.deleteByTitle(title);
        }
    }

    @Transactional
    public List<Movie> getMovieList() {
        return movieRepository.findAll();
    }
}
