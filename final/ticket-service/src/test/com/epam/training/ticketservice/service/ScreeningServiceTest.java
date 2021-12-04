package com.epam.training.ticketservice.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import com.epam.training.ticketservice.exception.OverlappingScreeningException;
import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.model.Screening;
import com.epam.training.ticketservice.repository.ScreeningRepository;
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
class ScreeningServiceTest {

    @Mock
    ScreeningRepository screeningRepository;

    @Mock
    private MovieService movieService;

    @Mock
    private RoomService roomService;

    @InjectMocks
    ScreeningService screeningService;

    Movie testMovie;
    Room testRoom;
    Screening testScreening;
    Screening dbScreening;

    @BeforeEach
    void setUp() {
        testMovie = getTestMovie();
        testRoom = getTestRoom();

        testScreening = createScreening(testMovie, testRoom);
        dbScreening = createScreening(testMovie, testRoom);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime screeningStartDate = LocalDateTime.parse("2021-12-28 18:00", dateFormat);

        testScreening.setScreeningDate(screeningStartDate);
        dbScreening.setScreeningDate(screeningStartDate.minusMinutes(45));
    }

    public Screening createScreening(Movie movie, Room room) {
        Screening screening = new Screening();
        screening.setMovie(movie);
        screening.setRoom(room);
        return screening;
    }

    public Movie getTestMovie() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setTitle("Test title");
        movie.setCategory("Test category");
        movie.setLength(60);
        return movie;
    }

    public Room getTestRoom() {
        Room room = new Room();
        room.setId(1);
        room.setName("Test room");
        room.setNumberOfColumns(5);
        room.setNumberOfRows(5);
        return room;
    }

    @Test
    void testCreateScreeningShouldCreateTheScreeningWhenThereIsNoOverlap() {
        // Given
        BDDMockito.given(roomService.getRoomIfExists(testRoom.getName())).willReturn(testRoom);
        BDDMockito.given(movieService.getMovieIfExists(testMovie.getTitle())).willReturn(testMovie);
        BDDMockito.given(screeningRepository.findScreeningsInRoomOnDay(testRoom.getId(), testScreening.getScreeningDate().with(LocalTime.MIN),
                testScreening.getScreeningDate().with(LocalTime.MAX))).willReturn(Collections.emptyList());

        // When
        screeningService.createScreening(testMovie.getTitle(), testRoom.getName(), testScreening.getScreeningDate());

        // Then
        Mockito.verify(screeningRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test void testCreateScreeningShouldThrowOverlappingScreeningExceptionWhenFullyOverlaps() {
        // Given
        BDDMockito.given(roomService.getRoomIfExists(testRoom.getName())).willReturn(testRoom);
        BDDMockito.given(movieService.getMovieIfExists(testMovie.getTitle())).willReturn(testMovie);
        BDDMockito.given(screeningRepository.findScreeningsInRoomOnDay(testRoom.getId(), testScreening.getScreeningDate().with(LocalTime.MIN),
                testScreening.getScreeningDate().with(LocalTime.MAX))).willReturn(List.of(dbScreening));
        String expectedMessage = "There is an overlapping screening";

        // When
        OverlappingScreeningException ex = Assertions.assertThrows(OverlappingScreeningException.class,
                () -> screeningService.createScreening(testMovie.getTitle(), testRoom.getName(), testScreening.getScreeningDate()));

        // Then
        Assertions.assertEquals(expectedMessage, ex.getMessage());
    }

    @Test void testCreateScreeningShouldThrowOverlappingScreeningExceptionWhenOverlapsInBreakTime() {
        // Given
        dbScreening.setScreeningDate(dbScreening.getScreeningDate().minusMinutes(15));

        BDDMockito.given(roomService.getRoomIfExists(testRoom.getName())).willReturn(testRoom);
        BDDMockito.given(movieService.getMovieIfExists(testMovie.getTitle())).willReturn(testMovie);
        BDDMockito.given(screeningRepository.findScreeningsInRoomOnDay(testRoom.getId(), testScreening.getScreeningDate().with(LocalTime.MIN),
                testScreening.getScreeningDate().with(LocalTime.MAX))).willReturn(List.of(dbScreening));
        String expectedMessage = "This would start in the break period after another screening in this room";

        // When
        OverlappingScreeningException ex = Assertions.assertThrows(OverlappingScreeningException.class,
                () -> screeningService.createScreening(testMovie.getTitle(), testRoom.getName(), testScreening.getScreeningDate()));

        // Then
        Assertions.assertEquals(expectedMessage, ex.getMessage());
    }

    @Test
    void testGetScreeningsListShouldReturnListOfScreenings() {
        // Given
        BDDMockito.given(screeningRepository.findAll()).willReturn(Collections.emptyList());

        // When
        List<Screening> screeningList = screeningService.getScreeningsList();

        // Then
        Assertions.assertTrue(screeningList.isEmpty());
    }
}