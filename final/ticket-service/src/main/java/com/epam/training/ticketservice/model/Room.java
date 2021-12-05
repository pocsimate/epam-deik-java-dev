package com.epam.training.ticketservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cinema_room")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @Column(name = "n_rows")
    private int numberOfRows;

    @Column(name = "n_columns")
    private int numberOfColumns;

    @Override
    public String toString() {
        return "Room " + name + " with " + numberOfRows * numberOfColumns
                + " seats, " + numberOfRows + " rows and " + numberOfColumns + " columns";
    }
}
