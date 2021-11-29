package com.epam.training.ticketservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "movie")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    private String category;

    private int length;

}