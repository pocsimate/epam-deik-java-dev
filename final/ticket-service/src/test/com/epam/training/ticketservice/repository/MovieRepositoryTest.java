package com.epam.training.ticketservice.repository;

import java.util.Optional;

import com.epam.training.ticketservice.model.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@TestPropertySource(
        locations = "classpath:application-ci.properties")
class MovieRepositoryTest {

    @Autowired
    private MovieRepository underTest;

    Movie movie;

    @Test
    public void testFindMovieByTitleShouldReturnTheMovieIfExists() {
        // Given
        movie = new Movie();

        movie.setTitle("Test Movie");
        movie.setCategory("Test category");
        movie.setLength(120);

        underTest.save(movie);

        // When
        Optional<Movie> dbMovie = underTest.findByTitle(movie.getTitle());

        // Then
        Assertions.assertTrue(dbMovie.isPresent());
    }

    @Test
    public void testFindMovieByTitleShouldReturnEmptyOptionalIfNotExists() {
        // Given
        movie = new Movie();

        movie.setTitle("Test Movie2");
        movie.setCategory("Test category2");
        movie.setLength(120);

        underTest.delete(movie);

        // When
        Optional<Movie> dbMovie = underTest.findByTitle(movie.getTitle());

        // Then
        Assertions.assertTrue(dbMovie.isEmpty());
    }


    @Test
    public void testDeleteByTitleShouldDeleteTheMovieIfExists() {
        // Given
        movie = new Movie();

        movie.setTitle("Test Movie3");
        movie.setCategory("Test category3");
        movie.setLength(120);
        underTest.save(movie);

        Optional<Movie> dbMovie;

        // When
        underTest.deleteByTitle(movie.getTitle());

        // Then
        dbMovie = underTest.findByTitle(movie.getTitle());
        Assertions.assertTrue(dbMovie.isEmpty());
    }

}