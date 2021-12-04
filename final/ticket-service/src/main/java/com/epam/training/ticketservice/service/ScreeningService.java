package com.epam.training.ticketservice.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.epam.training.ticketservice.exception.MovieDoesNotExistsException;
import com.epam.training.ticketservice.exception.OverlappingScreeningException;
import com.epam.training.ticketservice.exception.RoomDoesNotExistException;
import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.model.Screening;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.google.common.collect.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScreeningService {

    private final int BREAK_MINUTES = 10;

    private MovieService movieService;
    private RoomService roomService;

    private ScreeningRepository screeningRepository;

    @Autowired
    public ScreeningService(MovieService movieService, RoomService roomService, ScreeningRepository screeningRepository) {
        this.movieService = movieService;
        this.roomService = roomService;
        this.screeningRepository = screeningRepository;
    }

    @Transactional
    public void createScreening(String movieTitle, String roomName, LocalDateTime screeningStartDate) {
        Movie movie = getMovieIfExists(movieTitle);
        Room room = getRoomIfExists(roomName);
        checkOverlap(room, movie, screeningStartDate);

        Screening screening = Screening.builder()
                .movie(movie)
                .room(room)
                .screeningDate(screeningStartDate)
                .build();

        screeningRepository.save(screening);
    }

    // FIXME nagyon gagyi, csak akkor biztos, ha egymás utáni adogatjuk be a filmeket időrendben
    public void checkOverlap(Room room, Movie movie, LocalDateTime screeningStartDate) {
        List<Screening> screeningList = screeningRepository.findScreeningsInRoomOnDay(room.getId(), screeningStartDate.with(LocalTime.MIN), screeningStartDate.with(LocalTime.MAX));
        Range<LocalDateTime> addableScreeningRange = Range.open(screeningStartDate, screeningStartDate.plusMinutes(movie.getLength()));

        for (Screening screening : screeningList) {
            Range<LocalDateTime> screeningRangeWithBreak = Range.open(screening.getScreeningDate(), screening.getScreeningDate().plusMinutes(screening.getMovie().getLength() + BREAK_MINUTES));
            if (screeningRangeWithBreak.isConnected(addableScreeningRange)) {
                throwOverlapError(screeningRangeWithBreak, addableScreeningRange);
            }
        }
    }

    public void throwOverlapError(Range<LocalDateTime> screeningRangeWithBreak, Range<LocalDateTime> addableScreeningRange) {
        LocalDateTime screeningRangeStart = screeningRangeWithBreak.lowerEndpoint();
        LocalDateTime screeningRangeEnd = screeningRangeWithBreak.upperEndpoint().minusMinutes(BREAK_MINUTES);
        Range<LocalDateTime> screeningRangeWithoutBreak = Range.open(screeningRangeStart, screeningRangeEnd);

        String errorMessage;

        if (screeningRangeWithoutBreak.isConnected(addableScreeningRange)) {
            errorMessage = "There is an overlapping screening";
        } else {
            errorMessage = "This would start in the break period after another screening in this room";
        }
            throw new OverlappingScreeningException(errorMessage);
    }

    // create screening "Minden6ó" "BigBen" "2021-12-06 14:30"

    // TODO nem itt a helye, plusz refaktorhoz jo lesz
    public Room getRoomIfExists(String name) {
        Optional<Room> room = roomService.findRoomByName(name);
        if (room.isPresent()) {
            return room.get();
        } else {
            throw new RoomDoesNotExistException(name);
        }
    }

    // TODO nem itt a helye, plusz refaktorhoz jo lesz
    public Movie getMovieIfExists(String title) {
        Optional<Movie> movie = movieService.findMovieByTitle(title);
        if (movie.isPresent()) {
            return movie.get();
        } else {
            throw new MovieDoesNotExistsException(title);
        }
    }

}
