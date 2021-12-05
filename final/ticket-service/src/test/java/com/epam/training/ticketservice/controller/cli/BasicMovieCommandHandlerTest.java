package com.epam.training.ticketservice.controller.cli;

import java.util.Collections;
import java.util.List;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.service.MovieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BasicMovieCommandHandlerTest {

    @Mock
    MovieService movieService;

    @InjectMocks
    BasicMovieCommandHandler movieCommandHandler;

    @Test
    void testListMoviesShouldReturnNoMoviesAtTheMomentWhenThereAreNoMovies() {
        // Given
        BDDMockito.when(movieService.getMovieList()).thenReturn(Collections.emptyList());
        String expectedResult = "There are no movies at the moment";

        // When
        String result = movieCommandHandler.listMovies();

        // Then
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void testListMoviesShouldReturnFormattedStringOfMoviesWhenThereAreMovies() {
        // Given
        Movie movie = Movie.builder()
                .title("Test Movie")
                .category("test category")
                .length(60)
                .build();
        BDDMockito.when(movieService.getMovieList()).thenReturn(List.of(movie));
        String expectedResult = movie.getTitle() + " (" + movie.getCategory() + ", " + movie.getLength() + " minutes)";

        // When
        String result = movieCommandHandler.listMovies();

        // Then
        Assertions.assertEquals(expectedResult, result);
    }
}