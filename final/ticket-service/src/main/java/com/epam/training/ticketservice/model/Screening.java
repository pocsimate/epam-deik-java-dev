package com.epam.training.ticketservice.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cinema_screening")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;

    @OneToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @Column(name = "screening_date")
    private LocalDateTime screeningDate;

    @Override public String toString() {
        return movie.getTitle() + " (" + movie.getCategory() + ", " + movie.getLength() + " minutes, screened in room " + room.getName() + ", at " + screeningDate.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +" )";
    }
}
