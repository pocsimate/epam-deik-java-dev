package com.epam.training.ticketservice.model;

import lombok.*;

import javax.persistence.*;

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

}
