package com.epam.training.ticketservice.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.epam.training.ticketservice.exception.MovieAlreadyExistsException;
import com.epam.training.ticketservice.exception.MovieDoesNotExistException;
import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    MovieService movieService;

    Movie testMovie;

    @BeforeEach
    void setUp() {
        testMovie = Movie.builder()
                .title("Test Movie")
                .category("test category")
                .length(60)
                .build();
    }

    @Test
    public void testFindMovieByTitleShouldReturnMovieInOptionalWhenExists() {
        // Given
        BDDMockito.given(movieRepository.findByTitle(testMovie.getTitle())).willReturn(Optional.of(testMovie));

        // When
        Optional<Movie> optionalMovie = movieService.findMovieByTitle(testMovie.getTitle());

        // Then
        Assertions.assertEquals(testMovie.getTitle(), optionalMovie.get().getTitle());
    }

    @Test
    public void testGetMovieIfExistsShouldReturnMovieWhenExists() {
        // Given
        BDDMockito.given(movieRepository.findByTitle(testMovie.getTitle())).willReturn(Optional.of(testMovie));

        // When
        Movie movie = movieService.getMovieIfExists(testMovie.getTitle());

        // Then
        Assertions.assertTrue(Objects.nonNull(movie));
    }

    @Test
    public void testGetMovieIfExistsShouldThrowMovieDoesNotExistExceptionIfNotExists() {
        // Given
        BDDMockito.given(movieRepository.findByTitle(testMovie.getTitle())).willReturn(Optional.empty());

        // When - Then
        Assertions.assertThrows(MovieDoesNotExistException.class, () -> movieService.getMovieIfExists(testMovie.getTitle()));
    }

    @Test
    public void testCreateMovieShouldCreateMovieWhenNotExists() {
        // Given
        BDDMockito.given(movieRepository.findByTitle(testMovie.getTitle())).willReturn(Optional.empty());

        // When
        movieService.createMovie(testMovie);

        // Then
        Mockito.verify(movieRepository, Mockito.times(1)).save(testMovie);
    }

    @Test
    public void testCreateMovieShouldThrowMovieAlreadyExistExceptionWhenAlreadyExists() {
        // Given
        BDDMockito.given(movieRepository.findByTitle(testMovie.getTitle())).willReturn(Optional.of(testMovie));

        // When - Then
        Assertions.assertThrows(MovieAlreadyExistsException.class, () -> movieService.createMovie(testMovie));
    }

    @Test
    public void testUpdateMovieShouldUpdateMovieWhenExists() {
        // Given
        BDDMockito.given(movieRepository.findByTitle(testMovie.getTitle())).willReturn(Optional.of(testMovie));

        // When
        movieService.updateMovie(testMovie);

        // Then
        Mockito.verify(movieRepository, Mockito.times(1)).save(testMovie);
    }

    @Test
    public void testUpdateMovieShouldThrowMovieDoesNotExistExceptionWhenNotExists() {
        // Given
        BDDMockito.given(movieRepository.findByTitle(testMovie.getTitle())).willReturn(Optional.empty());

        // When - Then
        Assertions.assertThrows(MovieDoesNotExistException.class, () -> movieService.updateMovie(testMovie));
    }

    @Test
    public void testDeleteMovieShouldDeleteMovieWhenExists() {
        // Given
        BDDMockito.given(movieRepository.findByTitle(testMovie.getTitle())).willReturn(Optional.of(testMovie));

        // When
        movieService.deleteMovie(testMovie.getTitle());

        //Then
        Mockito.verify(movieRepository, Mockito.times(1)).deleteByTitle(testMovie.getTitle());
    }

    @Test
    public void testDeleteMovieShouldThrowMovieDoesNotExistExceptionWhenNotExists() {
        // Given
        BDDMockito.given(movieRepository.findByTitle(testMovie.getTitle())).willReturn(Optional.empty());

        // When - Then
        Assertions.assertThrows(MovieDoesNotExistException.class, () -> movieService.deleteMovie(testMovie.getTitle()));
    }

    @Test
    public void testGetMovieListShouldReturnListOfMovies() {
        // Given
        BDDMockito.given(movieRepository.findAll()).willReturn(Collections.emptyList());

        // When
        List<Movie> movieList = movieService.getMovieList();

        // Then
        Assertions.assertTrue(movieList.isEmpty());
    }
}
